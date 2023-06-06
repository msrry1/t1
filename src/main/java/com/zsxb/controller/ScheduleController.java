package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.BaseContext;
import com.zsxb.common.JsonResult;
import com.zsxb.po.Schedule;
import com.zsxb.service.ScheduleService;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
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

        schedule.setEmpId(Math.toIntExact(BaseContext.getCurrentId()));
        scheduleService.add(schedule);

        return JsonResult.ok();
    }

    /**
     * 分页查询演出计划
     * @param current   当前页
     * @param size  每页显示个数
     * @param schedId    演出计划id（可为null）
     * @param playId    剧目id（可为null）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) Integer schedId,
                                 @RequestParam(required = false) Integer playId) {

        Page page = new Page(current, size);
        if (playId != null) {
            // 根据剧目id查询演出计划
            scheduleService.queryPageByPlayId(page, playId);
        } else {
            scheduleService.queryPage(page, schedId);
        }
        return JsonResult.ok(page);
    }

    /**
     * 删除演出计划
     * @param schedId   演出计划id
     * @return
     */
    @PostMapping("/delete")
    public JsonResult<Void> delete(Integer schedId) {

        // 调用delete删除演出计划
        scheduleService.delete(schedId);

        return JsonResult.ok();
    }

    /**
     * 修改演出计划
     * @param schedule  演出计划信息，目前只能修改票价
     * @return
     */
    @PostMapping("/update")
    public JsonResult<Void> update(@RequestBody Schedule schedule) {

        // 调用update修改演出计划
        scheduleService.update(schedule);

        return JsonResult.ok();
    }


}
