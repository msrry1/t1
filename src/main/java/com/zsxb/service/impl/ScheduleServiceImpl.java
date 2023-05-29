package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.entity.Schedule;
import com.zsxb.exception.ScheduleException;
import com.zsxb.mapper.ScheduleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsxb.po.SchedulePO;
import com.zsxb.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        LambdaQueryWrapper<SchedulePO> schedulePOLambdaQueryWrapper = new LambdaQueryWrapper<>();
    }
}
