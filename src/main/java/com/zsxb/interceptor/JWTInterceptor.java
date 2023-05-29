package com.zsxb.interceptor;
import com.zsxb.common.CommonDict;
import com.zsxb.entity.Customer;
import com.zsxb.entity.Employee;
import com.zsxb.exception.EmployeeException;
import com.zsxb.util.JwtUtil;
import com.zsxb.util.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCache redisCache;

    // 保存允许经理、售票员、顾客的权限
    private static List<String> allowAuthManager;
    private static List<String> allowAuthConductor;
    private static List<String> allowAuthCustomer;

    // 请求的路径
    private String requestUri;

    static {
        // 经理权限
//        allowAuthManager.addAll(Arrays.asList(
//                "",
//                "",
//                ""));
        // 售票员权限

        // 顾客权限
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        log.info("访问资源 {}", request.getRequestURI());
        requestUri = request.getRequestURI();

        // 1. 认证
        // 1.1 获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            // token为空，说明用户没有登录，不能继续访问
            throw new RuntimeException("请先登录！");
        }

        // 1.2 解析token
        Claims claims = JwtUtil.parseJWT(token);
        // 1.3 获取key（tokenKey和redisKey）（用户类型:id）
        String key = claims.getSubject();

        Object user = redisCache.getCacheObject(key);
        if (user == null) {
            // redis保存的用户信息为空，说明该用户已经退出登录，携带的token已经从redis删除
            throw new RuntimeException("用户已经退出，请删除token再请求登录");
        }
        Employee employee;
        Customer customer;
        // 2. 认为redis中的用户和数据库中的数据一致认证通过，认证之后鉴权
        if (user instanceof Employee) {
            // 2.1 用户是管理员
            employee = (Employee) user;
            String type = employee.getType();
            String empName = employee.getEmpName();
            if (type.equals(CommonDict.CONDUCTOR)) {
                // 管理员是售票员
                log.info("售票员 {} 已经认证，下一步进行鉴权", empName);
                if (!authConductor()) {
                    log.info("售票员 {} 访问 {} 资源无权限", empName, requestUri);
                    throw new RuntimeException("没有对应权限");
                } else {
                    return true;
                }
            } else if (type.equals(CommonDict.MANAGER)) {
                // 2.2 管理员是经理
                log.info("经理 {} 已经登录", empName);
                return true;
            }
        } else {
            // 2.2 用户是顾客
            customer = (Customer) user;
            String cusName = customer.getCusName();
            log.info("顾客 {} 已经认证，下一步进行鉴权", cusName);
            if (!authCustomer()) {
                log.info("顾客 {} 访问 {} 资源无权限", cusName, requestUri);
                throw new RuntimeException("没有对应权限");
            }
        }
        return true;
    }

    /**
     *
     */
    @PostMapping("/dd")
    public static void sd() {
        System.out.println("hh");
    }

    /**
     * 校验顾客权限
     * @return
     */
    private boolean authCustomer() {
        return true;
    }

    /**
     * 校验售票员权限
     * @return
     */
    private boolean authConductor() {
        return true;
    }
}
