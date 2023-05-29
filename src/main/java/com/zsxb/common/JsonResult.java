package com.zsxb.common;

import io.swagger.util.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Json 格式的数据响应
 * @ClassName:JsonResult
 * @Auther: Lyh
 * @Date: 2022/8/8 16:02
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
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

    /**
     * 成功
     * 只返回状态码 200
     * @return
     */
    public static JsonResult ok() {
        return new JsonResult(CommonDict.SUCCESS);
    }

    /**
     * 成功
     * 返回状态码200和返回对象 data
     * @param data
     * @return
     */
    public static JsonResult ok(Object data) {
        return new JsonResult(CommonDict.SUCCESS, data);
    }

    /**
     * 失败
     * 返回状态码和失败原因
     * @param state
     * @param message
     * @return
     */
    public static JsonResult error(Integer state, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(state);
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
