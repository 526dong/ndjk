package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;

/**
 * Created by zfwlz on 2017/12/28.
 */
public class LoginRespModel extends BaseResponseModel implements Serializable{

    private static final long serialVersionUID = -2279860032130459188L;
    private Integer data;

    public LoginRespModel(Integer code, String msg, Integer data) {
        super(code, msg);
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
