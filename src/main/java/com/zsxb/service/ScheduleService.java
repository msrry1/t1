package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Schedule;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface ScheduleService {

    /**
     * 添加演出计划
     * @param schedule
     */
    void add(Schedule schedule);


    /**
     * 根据 演出计划id或无查询演出计划
     * @param page
     * @param schedId
     */
    void queryPage(Page page, Integer schedId);
}
