package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.exception.EmployeeException;
import com.zsxb.po.Customer;
import com.zsxb.exception.CustomerException;
import com.zsxb.exception.StudioException;
import com.zsxb.mapper.CustomerMapper;
import com.zsxb.po.Employee;
import com.zsxb.service.CustomerService;
import com.zsxb.util.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void queryPage(Page<Customer> page, String cusUid) {
        // 封装查询条件
        LambdaQueryWrapper<Customer> customerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据用户名模糊查询
        if (!StringUtils.isBlank(cusUid)) {
            customerLambdaQueryWrapper.like(Customer::getCusUid, cusUid);
        }
        // 按id升序
        customerLambdaQueryWrapper.orderByAsc(Customer::getCusId);

        // 查询顾客
        customerMapper.selectPage(page, customerLambdaQueryWrapper);
    }

    @Override
    public void add(Customer customer) {

        // 拿到顾客用户名
        String cusName = customer.getCusName();

        // 1. 判断顾客用户名是否重复
        LambdaQueryWrapper<Customer> customerNameQueryWrapper = new LambdaQueryWrapper<>();
        customerNameQueryWrapper.eq(Customer::getCusName, cusName);
        Customer queryCustomer = customerMapper.selectOne(customerNameQueryWrapper);
        if (queryCustomer != null) {
            // 名称重复
            throw new CustomerException("顾客用户名已存在，请重新添加！");
        }

        // 2. 添加顾客
        int result = customerMapper.insert(customer);
        if (result <= 0) {
            // 添加顾客失败，请检查输入是否合法
            throw new CustomerException("添加顾客失败，请检查输入是否合法！");
        }

    }

    @Override
    public void delete(int cusId) {
        // 1. 查询顾客是否存在
        Customer customer = customerMapper.selectById(cusId);
        if (customer == null) {
            // 顾客不存在，抛出异常
            throw new CustomerException("删除的顾客不存在！");
        }
        // 2. 执行删除演出厅
        int result = customerMapper.deleteById(cusId);
        if (result <= 0) {
            // 删除失败
            throw new CustomerException("删除顾客失败！");
        }
    }

    @Override
    public void update(Customer customer) {
        // 1. 查询顾客是否存在
        Customer queryCustomer = customerMapper.selectById(customer.getCusId());
        if (queryCustomer == null) {
            // 顾客不存在，抛出异常
            throw new CustomerException("修改的顾客不存在！");
        }
        // 2. 修改顾客
        int result = customerMapper.updateById(customer);
        if (result <= 0) {
            throw new StudioException("修改顾客失败！");
        }
    }

    @Override
    public Customer getByCusId(Integer cusId) {
        return customerMapper.selectById(cusId);
    }

    @Override
    public Customer login(String username, String password) {
        // 1. 根据用户名去数据库查询顾客
        LambdaQueryWrapper<Customer> query = new LambdaQueryWrapper<>();
        query.eq(Customer::getCusUid, username);
        Customer customer = customerMapper.selectOne(query);

        // 2. 判断用户合法性
        // 2.1 判断顾客是否为空
        if (customer == null) {
            // 管理员为空，抛出异常
            throw new CustomerException("顾客不存在！");
        }

        // 2.2 判断密码是否正确
        if (!password.equals(customer.getCusPwd())) {
            // 密码不匹配，抛出异常
            throw new CustomerException("顾客密码不正确！");
        }

        // 3. 此时管理员合法，将管理员信息存入redis，以便于管理员后续的其他请求，键为用户id
        String redisKey = CommonDict.CUSTOMER_TOKENKEY + customer.getCusId();
        redisCache.setCacheObject(redisKey, customer, CommonDict.REDIS_EXPIRE_TIME, CommonDict.REDIS_EXPIRE_UNIT);

        return customer;
    }

    @Override
    public void register(String username, String password) {
        // 1. 查询顾客用户名是否重复
        LambdaQueryWrapper<Customer> customerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerLambdaQueryWrapper.eq(Customer::getCusUid, username);
        Customer queryCustomer = customerMapper.selectOne(customerLambdaQueryWrapper);
        if (queryCustomer != null) {
            // 顾客用户名重复
            throw new CustomerException("顾客用户名重复！");
        }
        // 2. 添加顾客
        Customer customer = new Customer();
        customer.setCusUid(username);
        customer.setCusPwd(password);
        int result = customerMapper.insert(customer);
        if (result <= 0) {
            throw new CustomerException("顾客添加失败");
        }
    }
}
