package com.zsxb.service.impl;

import com.zsxb.entity.Customer;
import com.zsxb.mapper.CustomerMapper;
import com.zsxb.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
