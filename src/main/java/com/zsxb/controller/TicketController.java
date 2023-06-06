package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.JsonResult;
import com.zsxb.service.TicketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 票务操作控制层
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
     * 分页查询当前顾客的演出票（买了没退）
     * @param current  当前页
     * @param size  每页展示数量
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size) {

        Page page = new Page(current - 1, size);
        ticketService.queryPage(page);
        return JsonResult.ok(page);
    }

    /**
     * 买票
     * @param schedId   演出计划id
     * @param seatId    座位id
     * @return
     */
    @PostMapping("/buy")
    public JsonResult<Void> buy(@RequestParam Integer schedId,
                                @RequestParam Integer seatId) {
        ticketService.buy(schedId, seatId);
        return JsonResult.ok();
    }

    /**
     * 退票
     * @param ticketId
     * @return
     */
    @PostMapping("/returnTicket")
    public JsonResult<Void> returnTicket(@RequestParam Integer ticketId) {
        ticketService.returnTicket(ticketId);

        return JsonResult.ok();
    }
}
