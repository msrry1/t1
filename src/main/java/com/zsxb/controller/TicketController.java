package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.JsonResult;
import com.zsxb.service.TicketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 票务操作controller
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;


    /**
     * 分页查询票务信息
     * @param current  当前页
     * @param size  每页展示数量
     * @param ticketStatus   查询票的状态（0待销售 9卖出）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = true) Integer ticketStatus) {

        Page page = new Page(current - 1, size);
        ticketService.queryPage(page, ticketStatus);
        return JsonResult.ok(page);
    }

}
