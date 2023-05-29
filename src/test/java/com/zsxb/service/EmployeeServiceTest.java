package com.zsxb.service;

import com.zsxb.entity.Employee;
import com.zsxb.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: EmployeeServiceTest
 * Package: com.zsxb.service
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/25 14:36
 * @Version 1.0
 */
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;


    @Test
    public void login() {
        Employee lyh = employeeService.login("001", "rj2103lyh");

    }

}
