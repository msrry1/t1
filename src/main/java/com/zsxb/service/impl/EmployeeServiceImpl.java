package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.BaseContext;
import com.zsxb.common.CommonDict;
import com.zsxb.po.Employee;
import com.zsxb.exception.EmployeeException;
import com.zsxb.mapper.EmployeeMapper;
import com.zsxb.service.EmployeeService;
import com.zsxb.util.RedisCache;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void queryPage(Page page, String empUid) {
        // 1. 获取当前请求的管理员id
        Long currentEmployeeId = BaseContext.getCurrentId();
        // 2. 构建查询条件
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(empUid)) {
            employeeLambdaQueryWrapper.like(Employee::getEmpUid, empUid);
        }
        employeeLambdaQueryWrapper.orderByAsc(Employee::getEmpId);
        // 如果是root用户，就返回所有管理员
        if (currentEmployeeId == CommonDict.ROOT_CURRENT_ID) {
            employeeMapper.selectPage(page, employeeLambdaQueryWrapper);
            return;
        } else {
            // 是其他管理员
            // 根据当前管理员id查询管理员
            Employee queryEmployee = employeeMapper.selectById(currentEmployeeId);
            if (queryEmployee == null) {
                throw new JwtException("当前用户不存在！");
            }
            // 2. 判断当前用户的类型
            String type = queryEmployee.getType();
            if (CommonDict.MANAGER.equals(type)) {
                employeeLambdaQueryWrapper.eq(Employee::getType, CommonDict.CONDUCTOR);
                // 根据查询条件分页查找
                employeeMapper.selectPage(page, employeeLambdaQueryWrapper);
            }
        }
    }

    @Override
    public void delete(int empId) {

        // 1. 顾客和售票员都会被拦截器拦截，所以删除只会是root用户或经理
        // 如果是root用户
        // 根据管理员id查询管理员
        Employee queryEmployee = employeeMapper.selectById(empId);
        boolean empFlag = queryEmployee != null;
        int result = 0;
        if (BaseContext.getCurrentId() == CommonDict.ROOT_CURRENT_ID) {
            // 是root用户，如果管理员存在，直接删除
            if (empFlag) {
                result = employeeMapper.deleteById(empId);
                if (result <= 0) {
                    throw new EmployeeException("删除管理员失败！");
                }
            } else {
                // 管理员不存在
                throw new EmployeeException("要删除的管理员不存在！");
            }
        } else {
            // 拿到当前登录用户，经理
            Long currentId = BaseContext.getCurrentId();
            Employee currentEmployee = employeeMapper.selectById(currentId);
            if (currentEmployee == null) {
                throw new JwtException("当前用户不存在！");
            }
            // 判断当前用户的类型
            String type = currentEmployee.getType();
            if (CommonDict.MANAGER.equals(type)) {
                // 如果是经理，再执行删除
                result = employeeMapper.deleteById(empId);
                if (result <= 0) {
                    throw new EmployeeException("删除管理员失败！");
                }
            } else {
                throw new JwtException("非法访问！");
            }
        }

    }

    @Override
    public void update(Employee employee) {
        // 1. 顾客和售票员都会被拦截器拦截，所以修改只会是root用户或经理
        // 判断管理员是否存在
        Employee queryEmployee = employeeMapper.selectById(employee.getEmpId());
        boolean empFlag = queryEmployee != null;
        int result = 0;
        // 如果是root用户
        if (BaseContext.getCurrentId() == CommonDict.ROOT_CURRENT_ID) {
            // 是root用户，如果管理员存在，直接修改
            if (empFlag) {
                result = employeeMapper.updateById(employee);
                if (result <= 0) {
                    throw new EmployeeException("修改管理员失败！");
                }
            } else {
                // 管理员不存在
                throw new EmployeeException("要修改的管理员不存在！");
            }
        } else {
            // 拿到当前登录用户，经理
            Long currentId = BaseContext.getCurrentId();
            Employee currentEmployee = employeeMapper.selectById(currentId);
            if (currentEmployee == null) {
                throw new JwtException("当前用户不存在！");
            }
            // 判断当前用户的类型
            String type = currentEmployee.getType();
            if (CommonDict.MANAGER.equals(type)) {
                // 如果当前登录管理员是经理
                // 如果修改的管理员是售票员，再进行修改
                if (queryEmployee.getType().equals(CommonDict.CONDUCTOR)) {
                    result = employeeMapper.updateById(employee);
                    if (result <= 0) {
                        throw new EmployeeException("修改管理员失败！");
                    }
                } else {
                    throw new EmployeeException("非法访问");
                }
            } else {
                throw new JwtException("非法访问！");
            }
        }
    }

    @Override
    public Employee getByEmpId(Integer empId) {
        return employeeMapper.selectById(empId);
    }

    @Override
    public void add(Employee employee) {

        // 拦截器之后，此时只会是经理或root

        // 判断添加管理员是否存在
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getEmpUid, employee.getEmpUid());
        Employee queryEmployee = employeeMapper.selectOne(employeeLambdaQueryWrapper);
        boolean queryEmployeeFlag = queryEmployee != null;
        int result = 0;
        // 1. 获取当前登录管理员
        Long currentId = BaseContext.getCurrentId();
        if (currentId == CommonDict.ROOT_CURRENT_ID) {
            // 当前登录用户是root
            // 添加管理员不存在，执行添加
            if (!queryEmployeeFlag) {
                result = employeeMapper.insert(employee);
                if (result <= 0) {
                    throw new EmployeeException("添加管理员失败！");
                }
            } else {
                // 用户名重复
                throw new EmployeeException("管理员用户名重复！");
            }
        } else {
            // 登录用户是经理
            // 添加管理员不存在
            if (!queryEmployeeFlag) {
                // 添加的管理员是售票员，执行添加
                if (employee.getType().equals(CommonDict.CONDUCTOR)) {
                    result = employeeMapper.insert(employee);
                    if (result <= 0) {
                        throw new EmployeeException("添加管理员失败！");
                    }
                } else {
                    throw new EmployeeException("非法访问");
                }
            } else {
                // 用户名重复
                throw new EmployeeException("管理员用户名重复！");
            }
        }
    }

    @Override
    public List<Employee> list() {
        return employeeMapper.selectList(null);
    }

    @Override
    public List<Employee> selectByEmpUid(String empUid) {
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.like(Employee::getEmpUid, empUid);
        List<Employee> employeeList = employeeMapper.selectList(employeeLambdaQueryWrapper);
        return employeeList;
    }
}
