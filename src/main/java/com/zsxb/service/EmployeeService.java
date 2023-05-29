package com.zsxb.service;

import com.zsxb.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface EmployeeService {


    /**
     * 根据用户名和密码返回雇员信息
     * @param username
     * @param password
     * @return
     */
    Employee login(String username, String password);
}
