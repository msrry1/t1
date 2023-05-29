package com.zsxb;

import com.zsxb.common.JsonResult;
import lombok.ToString;

/**
 * ClassName: Aplication
 * Package: com.zsxb
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/24 18:53
 * @Version 1.0
 */
public class Aplication {
    public static void main(String[] args) {


    }
}

@ToString
class Re<E>{
    private Integer state; //状态码
    private String message; //描述信息
    private E data; //数据

    public Re(Integer state) {
        this.state = state;
    }

    public Re(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public Re(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public Re() {
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

    public Re(Throwable e){
        this.message = e.getMessage();
    }
}