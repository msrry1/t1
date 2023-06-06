package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsxb.po.Schedule;
import com.zsxb.po.Seat;
import com.zsxb.po.Ticket;

import java.util.List;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface TicketService extends IService<Ticket> {

    /**
     * 分页查询票务信息
     * @param page
     */
    void queryPage(Page page);
    /**
     * 根据演出计划id和演出计划票价和演出计划对应演出厅的座位id生成演出票
     * @param schedule
     * @param seats
     * @return
     */
    List<Ticket> createTickets(Schedule schedule, List<Seat> seats);

    /**
     * 根据演出计划id和座位id购票
     * @param schedId
     * @param seatId
     */
    void buy(Integer schedId, Integer seatId);

    /**
     * 根据票的id退票
     * @param ticketId
     */
    void returnTicket(Integer ticketId);
}
