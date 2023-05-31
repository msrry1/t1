package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.exception.PlayException;
import com.zsxb.po.Play;
import com.zsxb.po.Schedule;
import com.zsxb.exception.ScheduleException;
import com.zsxb.mapper.ScheduleMapper;
import com.zsxb.po.Studio;
import com.zsxb.service.PlayService;
import com.zsxb.service.ScheduleService;
import com.zsxb.service.StudioService;
import com.zsxb.vo.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
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

        int result = scheduleMapper.insert(schedule);
        if (result <= 0) {
            throw new ScheduleException("添加演出计划失败！请检查输入参数是否正确！");
        }
    }

    @Override
    public void queryPage(Page page, Integer schedId) {
        List<ScheduleVO> records = null;

        LambdaQueryWrapper<ScheduleVO> schedulePOLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (schedId == null) {
            // 查询所有演出计划
            records = scheduleMapper.selectpByPage(page.getCurrent() - 1, page.getSize());
        } else {
            // 查询单个演出计划
            ScheduleVO schedulePO = scheduleMapper.selectPOByScheduleId(schedId);
            records = new ArrayList<>(1);
            records.add(schedulePO);
        }
        // 查询总记录数
        Long count = scheduleMapper.selectCount(null);
        // 设置分页的records和count
        page.setCountId(String.valueOf(count));
        page.setRecords(records);

        System.out.println(records);
    }
}
