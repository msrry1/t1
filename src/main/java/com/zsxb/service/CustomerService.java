package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Customer;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface CustomerService {

    /**
     * 根据用户名分页查询顾客
     * @param page
     * @param cusUid
     */
    void queryPage(Page<Customer> page, String cusUid);

    /**
     * 添加顾客
     * @param customer
     */
    void add(Customer customer);

    /**
     * 删除顾客
     * @param cusId 顾客id
     */
    void delete(int cusId);

    /**
     * 修改顾客
     * @param customer
     */
    void update(Customer customer);

    /**
     * 根据顾客id查询顾客
     * @param cusId
     * @return
     */
    Customer getByCusId(Integer cusId);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    Customer login(String username, String password);

    /**
     * 注册顾客
     */
    void register(String username,
                  String password);
}
