package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.JsonResult;
import com.zsxb.service.SaleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * 订单操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    /**
     * 分页查询售票/退票单
     * @param current
     * @param size
     * @param saleId   销售id
     * @param saleType  销售类型（1销售    -1退款）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) Integer saleId,
                                 @RequestParam(required = true) Integer saleType) {

        Page page = new Page(current, size);
        saleService.queryPage(page, saleId, saleType);
        return JsonResult.ok(page);
    }

    /**
     * 分页查询销售业绩
     * @param current
     * @param size
     * @param empUid    管理员用户名（可为空）
     * @return
     */
    @PostMapping("/performance/page/{current}/{size}")
    public JsonResult<Page> performancePage(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) String empUid) {

        Page page = new Page(current, size);
        saleService.queryPerformancePage(page, empUid);
        return JsonResult.ok(page);
    }


    /**
     * 分页查询销售统计
     * @param current
     * @param size
     * @param playName    剧目名（可为空）
     * @return
     */
    @PostMapping("/count/page/{current}/{size}")
    public JsonResult<Page> countPage(@PathVariable int current,
                                            @PathVariable int size,
                                            @RequestParam(required = false) String playName) {

        Page page = new Page(current, size);
        saleService.queryCountPage(page, playName);
        return JsonResult.ok(page);
    }

}
