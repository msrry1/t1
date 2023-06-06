package com.zsxb.interceptor;
import com.zsxb.common.BaseContext;
import com.zsxb.common.CommonDict;
import com.zsxb.exception.CustomerException;
import com.zsxb.exception.EmployeeException;
import com.zsxb.po.Customer;
import com.zsxb.po.Employee;
import com.zsxb.service.CustomerService;
import com.zsxb.service.EmployeeService;
import com.zsxb.util.JwtUtil;
import com.zsxb.util.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.ValueRef;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    // 保存允许经理、售票员、顾客的权限
    private static List<String> noAllowAuthManager = Arrays.asList(

    );
    // 不允许售票员访问的资源
    private static List<String> noAllowAuthConductor = Arrays.asList(
            "/employee",
            "/sale/performance",
            "/sale/count");
    private static List<String> allowAuthCustomer = Arrays.asList(
            "/play/page",
            "/schedule/page",
            "/studio/page",
            "/seat/page",
            "/ticket/buy",
            "/ticket/returnTicket",
            "/ticket/page",
            "/play/get");
    private static List<String> allowEveryone = Arrays.asList(
            "/play/page",
            "/play/get",
            "/schedule/page",
            "/seat/page"
    );

    // 请求的路径
    private String requestUri;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {


        log.info("访问资源 {}", request.getRequestURI());
        requestUri = request.getRequestURI();

        // 如果是任何人都可以访问的资源，就放行
        for (String prefix : allowEveryone) {
            if (requestUri.startsWith(prefix)) {
                return true;
            }
        }
        // 1. 认证
        // 1.1 获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || "null".equals(token)) {
            // token为空，说明用户没有登录，不能继续访问
            throw new JwtException("请先登录！");
        }

        // 1.2 解析token
        Claims claims = JwtUtil.parseJWT(token);
        // 1.3 获取key（tokenKey和redisKey）（用户类型:id）
        String key = claims.getSubject();
        if (key.equals("root")) {
            // 是root用户
            // 保存当前登录用户id
            BaseContext.setCurrentId(CommonDict.ROOT_CURRENT_ID);
            return true;
        }
        Object user = redisCache.getCacheObject(key);
        if (user == null) {
            // redis保存的用户信息为空，说明该用户已经退出登录，携带的token已经从redis删除
            throw new JwtException("用户已经退出，请删除token再请求登录");
        }
        Employee employee;
        Customer customer;
        // 2. 认为redis中的用户和数据库中的数据可能不一致，因为经理或root用户可能会修改用户信息，此时用户需要重新登录
        // 如果是用户自己修改了信息，就要修改redis或重新登录，认证之后鉴权
        if (user instanceof Employee) {
            // 2.1 用户是管理员
            employee = (Employee) user;

            String type = employee.getType();
            String empName = employee.getEmpName();
            if (type.equals(CommonDict.CONDUCTOR)) {
                // 管理员是售票员
                // 认证售票员数据是否和数据库一致
                authenticationEmployee(employee);
                log.info("售票员 {} 已经认证通过，下一步进行鉴权", empName);
                // 对售票员进行鉴权
                authorizationConductor();
                log.info("售票员 {} 已经鉴权通过，访问url = {}", empName, requestUri);
                // 保存登录售票员id，售票员不能进行员工管理，表字段也没有创建人，暂时无用
                BaseContext.setCurrentId(Long.valueOf(employee.getEmpId()));
            } else if (type.equals(CommonDict.MANAGER)) {
                // 管理员是经理
                // 认证经理数据是否和数据库一致
                authenticationEmployee(employee);
                // 经理不需要鉴权，因为经理只是不能变更其他经理或查询其他经理的信息，service层已经处理
                log.info("经理 {} 已经登录", empName);
                // 保存登录管理员id
                BaseContext.setCurrentId(Long.valueOf(employee.getEmpId()));
                return true;
            }
        } else {
            // 2.2 用户是顾客
            customer = (Customer) user;
            String cusName = customer.getCusName();
            // 进行认证
            authenticationCustomer(customer);
            log.info("顾客 {} 已经认证，下一步进行鉴权", cusName);
            // 对顾客进行鉴权
            authorizationCustomer();
            BaseContext.setCurrentId(-Long.valueOf(customer.getCusId()));
        }
        return true;
    }

    /**
     * 认证管理员
     * @return
     */
    private void authenticationEmployee(Employee employee) {
        Employee queryEmployee = employeeService.getByEmpId(employee.getEmpId());
        // 判断当前用户是否存在
        if (queryEmployee == null) {
            // 当前用户已经被删除
            throw new JwtException("当前用户已被删除，请联系管理员！");
        }
        // 判断当前用户登录密码信息是否被修改，查出来密码是空或不一样
        if (StringUtils.isBlank(queryEmployee.getEmpPwd())
                || !queryEmployee.getEmpPwd().equals(employee.getEmpPwd())) {
            throw new JwtException("当前用户密码已被修改，请重新登录！");
        }
    }

    /**
     * 认证顾客
     * @return
     */
    private void authenticationCustomer(Customer customer) {
        Customer queryCustomer = customerService.getByCusId(customer.getCusId());
        // 判断当前用户是否存在
        if (queryCustomer == null) {
            // 当前用户已经被删除
            throw new JwtException("当前用户已被删除，请联系管理员！");
        }
        // 判断当前用户登录密码信息是否被修改，查出来密码是空或不一样
        if (StringUtils.isBlank(queryCustomer.getCusPwd())
                || !queryCustomer.getCusPwd().equals(customer.getCusPwd())) {
            throw new JwtException("当前用户密码已被修改，请重新登录！");
        }
    }
    /**
     * 校验顾客权限
     * @return
     */
    private void authorizationCustomer() {

        for (String prefix : allowAuthCustomer) {
            // 如果请求路径以允许前缀访问，就通过
            if (requestUri.startsWith(prefix)) {
                return;
            }
        }
        throw new CustomerException("访问无权限");
    }

    /**
     * 校验售票员权限
     * @return
     */
    private void authorizationConductor() {

        // 遍历所有售票员不能访问的前缀
        for (String noAllowPrefix : noAllowAuthConductor) {
            if (requestUri.startsWith(noAllowPrefix)) {
                throw new EmployeeException("访问无权限！");
            }
        }
    }
}
