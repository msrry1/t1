package com.zsxb.controller;

import com.zsxb.util.JsonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: TestController
 * Package: com.zsxb.controller
 * Description:
 *
 * @Author lyh
 * @Create 2023/4/11 16:13
 * @Version 1.0
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public JsonResult<String> test(){
        return new JsonResult<>(200,"sucess");
    }

    @GetMapping("/skt")
    public JsonResult<String> skt(){
        return new JsonResult<>(200,"t1");
    }
}
