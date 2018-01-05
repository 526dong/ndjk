package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;

/**
 * json返回
 * Created by xzd on 2018/1/3.
 */
public class JsonResult implements Serializable{
    private static final long serialVersionUID = 1705234885908792437L;

    private Integer code;

    private Object data;

    private String message;

    public JsonResult() {
    }

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public JsonResult(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static JsonResult ok(String msg) {
        return new JsonResult(200, msg);
    }

    public static JsonResult ok(Object data, String msg) {
        return new JsonResult(200, data, msg);
    }

    public static JsonResult error(String msg) {
        return new JsonResult(500, msg);
    }

    public static JsonResult error(Integer code, String msg) {
        return new JsonResult(code, msg);
    }

    public static JsonResult error(Integer code, Object data, String msg) {
        return new JsonResult(code, data, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
