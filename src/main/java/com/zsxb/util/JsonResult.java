package com.zsxb.util;

import java.io.Serializable;

/**
 * Json 格式的数据响应
 * @ClassName:JsonResult
 * @Auther: Lyh
 * @Date: 2022/8/8 16:02
 * @Version: v1.0
 */
public class JsonResult<E> implements Serializable {

    private Integer state; //状态码
    private String message; //描述信息
    private E data; //数据

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult() {
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public JsonResult(Throwable e){
        this.message = e.getMessage();
    }
}
