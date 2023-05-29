package com.zsxb;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: ZsxbMainApplication
 * Package: com.zsxb
 * Description:
 *
 * @Author lyh
 * @Create 2023/4/11 16:11
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.zsxb.mapper")
public class ZsxbMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZsxbMainApplication.class,args);
    }
}
