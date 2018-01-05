package com.ndjk.cl.brandservice.model.req;

import java.io.Serializable;

/**
 * Created by zfwlz on 2017/12/26.
 */
public class ApplyServiceReq implements Serializable {
    private static final long serialVersionUID = 7560175924237998154L;

    private Integer kgId; //幼儿园id
    private String applyName; //申请人姓名
    private String work; //所在单位职务
    private String phone; //真是有效的手机号码
    private String activityName; //活动名称
    private String freeServices; //免费服务json数组字符串"[{\"id\":1,\"count\":1},{\"id\":2,\"count\":2}]"
    private String noFreeServices; //付费服务  服务id","逗号隔开的字符转

    public ApplyServiceReq() {
    }

    public ApplyServiceReq(Integer kgId, String applyName, String work, String phone, String activityName,
                           String freeServices, String noFreeServices) {
        this.kgId = kgId;
        this.applyName = applyName;
        this.work = work;
        this.phone = phone;
        this.activityName = activityName;
        this.freeServices = freeServices;
        this.noFreeServices = noFreeServices;
    }

    public Integer getKgId() {
        return kgId;
    }

    public void setKgId(Integer kgId) {
        this.kgId = kgId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getFreeServices() {
        return freeServices;
    }

    public void setFreeServices(String freeServices) {
        this.freeServices = freeServices;
    }

    public String getNoFreeServices() {
        return noFreeServices;
    }

    public void setNoFreeServices(String noFreeServices) {
        this.noFreeServices = noFreeServices;
    }
}
