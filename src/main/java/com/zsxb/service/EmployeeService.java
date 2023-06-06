package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Employee;

import java.util.List;

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

    /**
     * 分页查询管理员
     * @param page
     * @param empUid
     */
    void queryPage(Page page, String empUid);

    /**
     * 根据管理员id删除管理员
     * @param empId
     */
    void delete(int empId);

    /**
     * 修改管理员信息
     * @param employee
     */
    void update(Employee employee);

    /**
     * 根据用户名获取用户
     * @param empId
     * @return
     */
    Employee getByEmpId(Integer empId);

    /**
     * 添加管理员
     * @param employee
     */
    void add(Employee employee);

    /**
     * 查询所有管理员
     * @return
     */
    List<Employee> list();

    /**
     * 根据管理员用户名模糊查询所有管理员
     * @param empUid
     * @return
     */
    List<Employee> selectByEmpUid(String empUid);
}
