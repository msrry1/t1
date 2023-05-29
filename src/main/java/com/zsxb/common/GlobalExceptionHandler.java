package com.zsxb.common;

import com.zsxb.exception.*;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class, })
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 管理员异常处理
     * @return 异常状态码和异常原因
     */
    @ExceptionHandler(EmployeeException.class)
    public JsonResult<Void> employeeException(EmployeeException ex){
        log.error("管理员异常！原因：{}", ex.getMessage());
        return JsonResult.error(CommonDict.EMPLOYEE_EXCEPTION, ex.getMessage());
    }

    /**
     * 顾客异常处理
     * @return 异常状态码和异常原因
     */
    @ExceptionHandler(CustomerException.class)
    public JsonResult<Void> customerException(CustomerException ex){
        log.error("顾客异常！原因：{}", ex.getMessage());
        return JsonResult.error(CommonDict.CUSTOMER_EXCEPTION, ex.getMessage());
    }

    /**
     * 演出厅异常处理
     * @return 异常状态码和异常原因
     */
    @ExceptionHandler(StudioException.class)
    public JsonResult<Void> studioException(StudioException ex){
        log.error("演出厅异常！原因：{}", ex.getMessage());
        return JsonResult.error(CommonDict.STUDIO_EXCEPTION, ex.getMessage());
    }

    /**
     * 文件异常处理
     * @return 异常状态码和异常原因
     */
    @ExceptionHandler(FileException.class)
    public JsonResult<Void> studioException(FileException ex){
        log.error("文件异常！原因：{}", ex.getMessage());
        return JsonResult.error(CommonDict.FILE_EXCEPTION, ex.getMessage());
    }

    /**
     * 剧目异常处理
     * @return 异常状态码和异常原因
     */
    @ExceptionHandler(PlayException.class)
    public JsonResult<Void> playException(PlayException ex){
        log.error("剧目异常！原因：{}", ex.getMessage());
        return JsonResult.error(CommonDict.PLAY_EXCEPTION, ex.getMessage());
    }

    /**
     * 演出计划异常处理
     * @return 异常状态码和异常原因
     */
    @ExceptionHandler(ScheduleException.class)
    public JsonResult<Void> scheduleException(ScheduleException ex){
        log.error("演出计划异常！原因：{}", ex.getMessage());
        return JsonResult.error(CommonDict.SCHEDULE_EXCEPTION, ex.getMessage());
    }




    /**
     * 未知异常处理
     * @return 异常状态码和异常原因
     */
    @ExceptionHandler(Exception.class)
    public JsonResult<Void> unknownException(Exception ex){
        log.error("未知异常！原因：{}", ex.getMessage());
        return JsonResult.error(CommonDict.UNKNOWN_EXCEPTION, "服务器正忙，请稍后重试！");
    }

}

