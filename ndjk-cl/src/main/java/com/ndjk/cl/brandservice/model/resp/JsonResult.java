package com.ndjk.cl.brandservice.model.resp;

import com.ndjk.cl.utils.RdPage;

import java.io.Serializable;

/**
 * json返回
 * @author Created by xzd on 2018/1/3.
 */
public class JsonResult implements Serializable{
    private static final long serialVersionUID = 1705234885908792437L;

    private Integer code;

    private Object data;

    private String msg;

    private RdPage page;

    public JsonResult() {
    }

    public JsonResult(Integer code) {
        this.code = code;
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public JsonResult(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static JsonResult ok() {
        return new JsonResult(200);
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RdPage getPage() {
        return page;
    }

    public void setPage(RdPage page) {
        this.page = page;
    }
}
