package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;

/**
 * 基本返回信息类，返回的业务信息继承与次类
 * Created by zfwlz on 2017/12/26.
 */
public class BaseResponseModel implements Serializable{

    private static final long serialVersionUID = 1705234885908792437L;

    private Integer code;

    private String msg;

    public BaseResponseModel() {
    }

    public BaseResponseModel(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
