package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsxb.common.BaseContext;
import com.zsxb.common.CommonDict;
import com.zsxb.exception.TicketException;
import com.zsxb.mapper.SaleMapper;
import com.zsxb.mapper.SeatMapper;
import com.zsxb.mapper.TicketMapper;
import com.zsxb.po.*;
import com.zsxb.service.CustomerService;
import com.zsxb.service.SaleService;
import com.zsxb.service.ScheduleService;
import com.zsxb.service.TicketService;
import com.zsxb.vo.ScheduleVO;
import com.zsxb.vo.TicketVO;
import io.swagger.models.auth.In;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket>  implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private CustomerService customerService;

    @Override
    public void queryPage(Page page) {

        List<TicketVO> records = null;

        // 查询所有演出票
        Integer cusId = Math.toIntExact(-BaseContext.getCurrentId());
        records = ticketMapper.selectVOByPage((page.getCurrent() - 1) * page.getSize(), page.getSize(), cusId);
        int total = 0;
        if (!records.isEmpty()) {
            TicketVO ticketVO = records.get(0);
            total = ticketVO.getCount();
        }
        // 设置分页的records和total
        page.setTotal(total);
        page.setRecords(records);
    }

    @Override
    public List<Ticket> createTickets(Schedule schedule, List<Seat> seats) {

        // 生成演出票的个数
        int ticketCounts = seats.size();
        List<Ticket> tickets = new ArrayList<>(ticketCounts);
        for (Seat seat : seats) {
            tickets.add(new Ticket()
                    .setSeatId(seat.getSeatId())    // 座位id
                    .setSchedId(schedule.getSchedId())  //演出计划id
                    .setTicketPrice(schedule.getSchedTicketPrice()) // 票价
                    .setTicketStatus(CommonDict.TICKET_WAIT_SALE));  //演出票待售状态
        }

        return tickets;
    }

    @Override
    @Transactional
    public void buy(Integer schedId, Integer seatId) {

        Schedule schedule = scheduleService.selectById(schedId);
        // 1. 判断当前用户余额是否足够
        BigDecimal ticketPrice = schedule.getSchedTicketPrice();
        Long cusId = -BaseContext.getCurrentId();
        Customer customer = customerService.getByCusId(Math.toIntExact(cusId));
        if (customer.getCusBalance().compareTo(ticketPrice) == -1) {
            // 余额不足买票
            throw new TicketException("余额不足，请充值！");
        }
        // 1.1 账户扣款
        customer.setCusBalance(customer.getCusBalance().subtract(ticketPrice));
        customerService.update(customer);
        // 2. 修改票状态
        LambdaUpdateWrapper<Ticket> ticketLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        ticketLambdaUpdateWrapper.eq(Ticket::getSeatId, seatId);
        ticketLambdaUpdateWrapper.eq(Ticket::getSchedId, schedId);
        ticketLambdaUpdateWrapper.set(Ticket::getTicketStatus, CommonDict.TICKET_SELL_SALE);
        int result = ticketMapper.update(null, ticketLambdaUpdateWrapper);
        if (result <= 0) {
            throw new TicketException("买票失败！");
        }
        // 3. 添加购票订单
        LambdaQueryWrapper<Ticket> ticketLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据演出计划id和座位id获取ticket
        ticketLambdaQueryWrapper.eq(Ticket::getSchedId, schedId);
        ticketLambdaQueryWrapper.eq(Ticket::getSeatId, seatId);
        Ticket queryTicket = ticketMapper.selectOne(ticketLambdaQueryWrapper);

        Sale sale = new Sale();
        sale.setCusId(Math.toIntExact(-BaseContext.getCurrentId()))
                .setSaleTime(new Date())
                .setSalePayment(schedule.getSchedTicketPrice())
                .setSaleType(CommonDict.SALE_TYPE_SALED)
                .setSaleSort(CommonDict.SALE_SORT_CUSTOMER)
                .setPlayId(schedule.getPlayId())
                .setEmpId(schedule.getEmpId())
                .setTicketId(Math.toIntExact(queryTicket.getTicketId()));
        boolean save = saleService.save(sale);
        if (!save) {
            throw new TicketException("买票失败！");
        }
    }

    @Override
    @Transactional
    public void returnTicket(Integer ticketId) {
        // 1. 修改票状态
        LambdaUpdateWrapper<Ticket> ticketLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        ticketLambdaUpdateWrapper.set(Ticket::getTicketStatus, CommonDict.TICKET_WAIT_SALE);
        ticketLambdaUpdateWrapper.eq(Ticket::getTicketId, ticketId);
        int result = ticketMapper.update(new Ticket().setTicketId(Long.valueOf(ticketId)), ticketLambdaUpdateWrapper);
        if (result <= 0) {
            throw new TicketException("退票失败！");
        }
        // 2. 添加退票订单
        Ticket ticket = ticketMapper.selectById(ticketId);
        Schedule schedule = scheduleService.selectById(ticket.getSchedId());
        Sale sale = new Sale();
        sale.setCusId(Math.toIntExact(-BaseContext.getCurrentId()))
                .setSaleTime(new Date())
                .setSalePayment(schedule.getSchedTicketPrice())
                .setSaleType(CommonDict.SALE_TYPE_RETURN)
                .setSaleSort(CommonDict.SALE_SORT_CUSTOMER)
                .setPlayId(schedule.getPlayId())
                .setEmpId(schedule.getEmpId())
                .setTicketId(ticketId);
        boolean save = saleService.save(sale);
        if (!save) {
            throw new TicketException("退票失败！");
        }
        // 3. 返回余额给当前顾客
        Long cusId = -BaseContext.getCurrentId();
        Customer customer = customerService.getByCusId(Math.toIntExact(cusId));
        customer.setCusBalance(customer.getCusBalance().add(ticket.getTicketPrice()));
        customerService.update(customer);
    }
}
