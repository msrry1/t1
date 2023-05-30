package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.JsonResult;
import com.zsxb.po.Customer;
import com.zsxb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 顾客操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 分页查询顾客
     * @param current 当前页
     * @param size 每页显示记录数
     * @param cusUid 顾客用户名（可为空）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) String cusUid) {
        Page<Customer> page = new Page<>(current, size);
        customerService.queryPage(page, cusUid);
        return JsonResult.ok(page);
    }


    /**
     * 添加顾客
     * @param customer 新增的顾客信息
     * @return
     */
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Customer customer) {

        customerService.add(customer);

        return JsonResult.ok();
    }


    /**
     * 删除顾客
     * @param cusId 顾客id
     * @return
     */
    @PostMapping("/delete")
    public JsonResult<Void> delete(int cusId) {

        customerService.delete(cusId);

        return JsonResult.ok();
    }


    /**
     * 修改顾客
     * @param customer 修改后的顾客信息
     * @return
     */
    @PostMapping("/update")
    public JsonResult<Void> update(@RequestBody Customer customer) {
        customerService.update(customer);

        return JsonResult.ok();
    }

}
