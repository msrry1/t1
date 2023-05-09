package com.zsxb.service.impl;

import com.zsxb.entity.Employee;
import com.zsxb.mapper.EmployeeMapper;
import com.zsxb.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
