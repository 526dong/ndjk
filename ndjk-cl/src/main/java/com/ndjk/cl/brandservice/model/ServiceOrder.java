package com.ndjk.cl.brandservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceOrder implements Serializable{

    private static final long serialVersionUID = 6314461920092718867L;
    private Integer id;

    private Integer kgId;

    private String applyName;

    private String work;

    private String phone;

    private String activityName;

    private String orderNo;

    private Integer state;

    private Integer isShipping;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    public ServiceOrder() {
    }

    public ServiceOrder(Integer id, Integer kgid, String applyName, String work, String phone, String activityName,
                        String orderNo, Integer state, Integer isShipping, BigDecimal price, Date createTime,
                        Date updateTime) {
        this.id = id;
        this.kgId = kgId;
        this.applyName = applyName;
        this.work = work;
        this.phone = phone;
        this.activityName = activityName;
        this.orderNo = orderNo;
        this.state = state;
        this.isShipping = isShipping;
        this.price = price;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKgid() {
        return kgId;
    }

    public void setKgid(Integer kgid) {
        this.kgId = kgid;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName == null ? null : applyName.trim();
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work == null ? null : work.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsShipping() {
        return isShipping;
    }

    public void setIsShipping(Integer isShipping) {
        this.isShipping = isShipping;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
}