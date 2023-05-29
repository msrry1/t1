package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Schedule;
import com.zsxb.exception.ScheduleException;
import com.zsxb.mapper.ScheduleMapper;
import com.zsxb.vo.SchedulePO;
import com.zsxb.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public void add(Schedule schedule) {
        int result = scheduleMapper.insert(schedule);
        if (result <= 0) {
            throw new ScheduleException("添加演出计划失败！请检查输入参数是否正确！");
        }
    }

    @Override
    public void queryPage(Page page, Integer schedId) {
        List<SchedulePO> records = null;

        LambdaQueryWrapper<SchedulePO> schedulePOLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (schedId == null) {
            // 查询所有演出计划
            records = scheduleMapper.selectpByPage(page.getCurrent(), page.getSize());
        } else {
            // 查询单个演出计划
            SchedulePO schedulePO = scheduleMapper.selectPOByScheduleId(schedId);
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
