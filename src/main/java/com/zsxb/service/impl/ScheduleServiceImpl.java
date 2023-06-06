package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.exception.PlayException;
import com.zsxb.po.*;
import com.zsxb.exception.ScheduleException;
import com.zsxb.mapper.ScheduleMapper;
import com.zsxb.service.*;
import com.zsxb.vo.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private StudioService studioService;

    @Autowired
    private PlayService playService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketService ticketService;

    @Override
    @Transactional
    public void add(Schedule schedule) {

        // 添加演出计划时，要判断演出计划演出的时间和演出厅的其他演出计划的时间有没有冲突
        // 比如演出厅1在下午2点有演出计划，此时添加的演出计划如果指定是演出厅1，演出计划时间就不能和演出厅1下午2点的演出计划有冲突
        // 能否添加演出计划，取决于在这个演出计划的开始和结束之间，对应演出厅没有其他演出计划包含当前演出计划或被当前演出计划包含，此时才可以添加演出计划
        // 1. 先获取演出厅信息
        Studio studio = studioService.selectById(schedule.getStudioId());
        // 2. 获取剧目信息
        Play play = playService.selectById(schedule.getPlayId());

        // 3. 获取演出开始时间结束时间
        Date startTime = schedule.getSchedTime();
        long playLength = play.getPlayLength() * 60 * 1000;
        Date endTime = new Date(startTime.getTime() + playLength);
        // 4. 构建查询条件，查询演出厅的所有演出计划
        LambdaQueryWrapper<Schedule> scheduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 指定演出厅id
        scheduleLambdaQueryWrapper.eq(Schedule::getStudioId, studio.getStudioId());

        // 5. 查询该演出厅的所有演出计划
        List<Schedule> schedules = scheduleMapper.selectList(scheduleLambdaQueryWrapper);
        // 遍历演出厅的所有演出计划
        for (Schedule sc : schedules) {
            // 演出计划开始时间
            Date queryStartTime = sc.getSchedTime();
            Play queryPlay = playService.selectById(sc.getPlayId());
            // 演出计划结束时间
            Date queryEndTime = new Date(queryStartTime.getTime() + queryPlay.getPlayLength() * 60 * 1000);
            // 如果当前演出计划范围里有其他演出计划
            // 或者其他演出计划范围里有当前演出计划
            // 就说明有冲突
            // 如果当前演出计划范围里有其他演出计划
            if ( (queryStartTime.after(startTime) && queryStartTime.before(endTime)) ||
                 (queryEndTime.before(endTime) && queryEndTime.after(startTime)) ) {
                throw new PlayException("添加演出计划和演出计划" + sc.getSchedId() + "有冲突，请重新添加！");
            }
            // 如果其他演出计划范围里有当前演出计划
            if ( (startTime.after(queryStartTime) && startTime.before(queryEndTime)) ||
                 (endTime.before(queryEndTime) && endTime.after(queryStartTime)) ) {
                throw new PlayException("添加演出计划和演出计划" + sc.getSchedId() + "有冲突，请重新添加！");
            }
        }
        // 此时和演出厅的其他演出计划的演出时间没有冲突，可以尝试添加
        // 修改演出计划票价为剧目票价
        schedule.setSchedTicketPrice(play.getPlayTicketPrice());
        int result = scheduleMapper.insert(schedule);
        if (result <= 0) {
            throw new ScheduleException("添加演出计划失败！请检查输入参数是否正确！");
        }
        // 6. 为该演出计划添加对应演出票
        // 拿到演出厅的id
        Integer studioId = schedule.getStudioId();
        // 根据演出厅id构建演出厅座位查询条件
        LambdaQueryWrapper<Seat> seatLambdaQueryWrapper = new LambdaQueryWrapper<>();
        seatLambdaQueryWrapper.eq(Seat::getStudioId, studioId);
        List<Seat> seats = seatService.list(seatLambdaQueryWrapper);

        // 根据演出计划id和演出计划票价和演出厅座位生成演出票
        List<Ticket> tickets = ticketService.createTickets(schedule, seats);

        // 7. 批量添加演出票
        ticketService.saveBatch(tickets);

        // 8. 修改剧目的已安排演出状态
        Integer playStatus = play.getPlayStatus();
        if (playStatus != CommonDict.PLAY_STATUS_BUSY) {
            play.setPlayStatus(CommonDict.PLAY_STATUS_BUSY);
        }
        playService.update(play);

    }

    @Override
    public void queryPage(Page page, Integer schedId) {
        List<ScheduleVO> records = null;

        if (schedId == null) {
            // 查询所有演出计划
            records = scheduleMapper.selectVOByPage((page.getCurrent() - 1) * page.getSize(), page.getSize(),null);
        } else {
            // 查询单个演出计划
            ScheduleVO scheduleVO = scheduleMapper.selectVOByScheduleId(schedId);
            records = new ArrayList<>(1);
            records.add(scheduleVO);
        }
        // 查询总记录数

        Long count = scheduleMapper.selectCount(null);
        // 设置分页的records和total
        // 如果是根据id查询演出计划，个数就设置为1
        if (schedId != null) {
            count = 1l;
        }
        page.setTotal(count);
        page.setRecords(records);
    }

    @Override
    public void delete(Integer schedId) {

        // 判断演出计划是否存在
        Schedule schedule = scheduleMapper.selectById(schedId);
        if (schedule == null) {
            // 删除的演出计划不存在
            throw new ScheduleException("删除的演出计划不存在！");
        }
        // 删除演出计划
        int result = scheduleMapper.deleteById(schedule);
        if (result <= 0) {
            throw new ScheduleException("删除演出计划失败！");
        }
    }

    @Override
    @Transactional
    public void update(Schedule schedule) {
        // 1. 先查询演出计划是否存在
        Schedule querySchedule = scheduleMapper.selectById(schedule.getSchedId());
        if (querySchedule == null) {
            throw new ScheduleException("修改的演出计划不存在！");
        }

        // 2. 修改演出计划的信息
        int result = scheduleMapper.updateById(schedule);
        if (result <= 0) {
            throw new ScheduleException("修改演出计划失败！");
        }
        // 3. 修改该演出计划生成的所有票的价格
        LambdaUpdateWrapper<Ticket> ticketLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        ticketLambdaUpdateWrapper.eq(Ticket::getSchedId, schedule.getSchedId());
        ticketLambdaUpdateWrapper.set(Ticket::getTicketPrice, schedule.getSchedTicketPrice());
        boolean updateFlag = ticketService.update(ticketLambdaUpdateWrapper);
        if (!updateFlag) {
            throw new ScheduleException("修改演出计划生成票信息失败！");
        }
    }

    @Override
    public void queryPageByPlayId(Page page, Integer playId) {
        List<ScheduleVO> records = null;

        // 查询该剧目所有演出计划
        records = scheduleMapper.selectVOByPage((page.getCurrent() - 1) * page.getSize(), page.getSize(), playId);
        // 查询总记录数
        LambdaQueryWrapper<Schedule> scheduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        scheduleLambdaQueryWrapper.eq(Schedule::getPlayId, playId);
        Long count = scheduleMapper.selectCount(scheduleLambdaQueryWrapper);
        // 设置分页的records和total
        page.setTotal(count);
        page.setRecords(records);
    }

    @Override
    public Schedule selectById(Integer schedId) {
        return scheduleMapper.selectById(schedId);
    }
}
