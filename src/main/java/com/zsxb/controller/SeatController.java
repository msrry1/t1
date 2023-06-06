package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.JsonResult;
import com.zsxb.service.SeatService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * 座位操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    /**
     * 根据演出计划id分页查询座位信息
     * @param current
     * @param size
     * @param schedId
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = true) Integer schedId) {

        Page page = new Page(current, size);
        seatService.queryPageBySchedId(page, schedId);
        return JsonResult.ok(page);
    }

}
