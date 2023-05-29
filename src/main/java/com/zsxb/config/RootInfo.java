package com.zsxb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: RootInfo
 * Package: com.zsxb.common
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/29 13:11
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "root")
@Configuration
@Data
public class RootInfo {

    @Value("${username}")
    public static String username;

    @Value("${password}")
    public static String password;

    /**
     * 判断当前用户是不是root用户
     * @param username
     * @param password
     * @return
     */
    public static boolean isRoot(String username,
                                 String password) {
       if (username.equals(RootInfo.username) && password.equals(RootInfo.password)) {
           return true;
       }
       return false;
    }
}
