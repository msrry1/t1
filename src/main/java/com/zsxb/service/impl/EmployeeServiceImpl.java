package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsxb.common.CommonDict;
import com.zsxb.po.Employee;
import com.zsxb.exception.EmployeeException;
import com.zsxb.mapper.EmployeeMapper;
import com.zsxb.service.EmployeeService;
import com.zsxb.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 管理 Service
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Employee login(String username, String password) {

        // 1. 根据用户名去数据库查询雇员
        LambdaQueryWrapper<Employee> query = new LambdaQueryWrapper<>();
        query.eq(Employee::getEmpUid, username);
        Employee employee = employeeMapper.selectOne(query);

        // 2. 判断用户合法性
        // 2.1 判断管理员是否为空
        if (employee == null) {
            // 管理员为空，抛出异常
            throw new EmployeeException("管理员不存在！");
        }

        // 2.2 判断密码是否正确
        if (!password.equals(employee.getEmpPwd())) {
            // 密码不匹配，抛出异常
            throw new EmployeeException("管理员密码不正确！");
        }

        // 3. 此时管理员合法，将管理员信息存入redis，以便于管理员后续的其他请求，键为用户id
        String redisKey = CommonDict.EMPLOYEE_REDISKEY + employee.getEmpId();
        redisCache.setCacheObject(redisKey, employee, CommonDict.REDIS_EXPIRE_TIME, CommonDict.REDIS_EXPIRE_UNIT);

        return employee;
    }
}
