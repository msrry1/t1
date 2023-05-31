package com.zsxb.common;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: CommonDict
 * Package: com.zsxb.common
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/10 8:21
 * @Version 1.0
 */
public class CommonDict {

    /* 状态码 */
    // 返回成功状态码
    public static final int SUCCESS = 200;
    // 管理员操作异常状态码
    public static final int EMPLOYEE_EXCEPTION = 1001;
    // 顾客操作异常状态码
    public static final int CUSTOMER_EXCEPTION = 2001;
    // 演出厅操作异常状态码
    public static final int STUDIO_EXCEPTION = 3001;
    // 文件操作异常状态码
    public static final int FILE_EXCEPTION = 4001;
    // 剧目操作异常状态码
    public static final int PLAY_EXCEPTION = 5001;
    // 演出计划操作异常状态码
    public static final int SCHEDULE_EXCEPTION = 6001;
    // 票务操作异常状态码
    public static final int TICKET_EXCEPTION = 7001;
    // 未知异常状态码
    public static final int UNKNOWN_EXCEPTION = 9999;



    /* 前缀相关 */
    // 管理员 redisKey
    public static final String EMPLOYEE_REDISKEY = "employee:";
    // 顾客 redisKey
    public static final String CUSTOMER_REDISKEY = "customer:";
    // 管理员 tokenKey
    public static final String EMPLOYEE_TOKENKEY = "employee:";
    // 顾客 tokenKey
    public static final String CUSTOMER_TOKENKEY = "customer:";






    /* 时间相关 */
    // redis存储key时间 默认24小时
    public static final int REDIS_EXPIRE_TIME = 24;
    // redis存储key时间单位
    public static final TimeUnit REDIS_EXPIRE_UNIT = TimeUnit.HOURS;
    // token过期时间（/ms）默认12小时
    public static final long TOKEN_EXPIRE_TIME = 12 * 60 * 60 * 1000;



    /* dict字典 */
    // 经理字典id
    public static final String MANAGER = "manager";
    // 售票员字典id
    public static final String CONDUCTOR = "conductor";
    // 剧目下线状态
    public static final Integer PLAY_STATUS_DOWN = -1;

}
