package com.zsxb.exception;

/**
 * ClassName: EmployeeException
 * Package: com.zsxb.exception
 * Description:
 * 顾客操作相关异常
 * @Author lyh
 * @Create 2023/5/23 17:54
 * @Version 1.0
 */
public class FileException extends RuntimeException{

    public FileException(String message) {
        super(message);
    }
}
