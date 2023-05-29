package com.zsxb.vo;

import com.zsxb.po.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * ClassName: EmployeeReturnVO
 * Package: com.zsxb.vo
 * Description:
 * 管理员登录成功的返回对象
 * @Author lyh
 * @Create 2023/5/24 21:36
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EmployeeReturnVO extends Employee implements Serializable {

    // 登录成功返回token
    private String token;

}
