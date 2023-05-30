package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.JsonResult;
import com.zsxb.po.Schedule;
import com.zsxb.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 演出计划操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 添加演出计划
     * @param schedule 演出计划信息
     * @return
     */
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Schedule schedule) {

        scheduleService.add(schedule);

        return JsonResult.ok();
    }

    /**
     * 分页查询演出计划
     * @param current   当前页
     * @param size  每页显示个数
     * @param schedId    演出计划id（可为null）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) Integer schedId) {

        Page page = new Page(current, size);
        scheduleService.queryPage(page, schedId);
        return JsonResult.ok(page);
    }

}
