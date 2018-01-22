package com.ndjk.cl.brandservice.model;

import java.io.Serializable;
import java.util.Date;

public class Kindergarten implements Serializable{

    private static final long serialVersionUID = 1596630657057491002L;
    private Integer id;

    private String loginName;

    private String account;

    private String password;

    private String kgName;

    private Integer status;

    private String statusStr;

    private Date createTime;

    private Date updateTime;

    public Kindergarten() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getKgName() {
        return kgName;
    }

    public void setKgName(String kgName) {
        this.kgName = kgName == null ? null : kgName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}