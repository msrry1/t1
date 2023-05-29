package com.zsxb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: LoginVO
 * Package: com.zsxb.vo
 * Description:
 *  登录前端传的参数，可以扩展其他登录方式
 * @Author lyh
 * @Create 2023/5/24 21:20
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

    // 用户名
    private String username;
    // 密码
    private String password;


}
