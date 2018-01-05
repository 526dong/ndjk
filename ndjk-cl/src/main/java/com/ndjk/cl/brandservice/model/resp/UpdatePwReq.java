package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;

/**
 * Created by zfwlz on 2017/12/31.
 */
public class UpdatePwReq implements Serializable {

    private static final long serialVersionUID = 3825580655118033505L;

    private int kgId; //幼儿园id
    private String oldPassword; //旧密码
    private String newPassword; //新密码

    public UpdatePwReq() {
    }

    public int getKgId() {
        return kgId;
    }

    public void setKgId(int kgId) {
        this.kgId = kgId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
