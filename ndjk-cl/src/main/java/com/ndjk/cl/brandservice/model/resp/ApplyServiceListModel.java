package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zfwlz on 2017/12/31.
 */
public class ApplyServiceListModel implements Serializable {

    private static final long serialVersionUID = -2154441825163724789L;

    private String acName; //活动名称

    private Date applyTime; //活动申请时间

    private String state;   //状态

    private String stateStr; //状态说明

    private String detail; //详情

    public ApplyServiceListModel(String acName, Date applyTime, String state) {
        this.acName = acName;
        this.applyTime = applyTime;
        this.state = state;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStateStr() {
        if("1".equals(state)){
            return "未受理";
        }else if("2".equals(state)){
            return "已受理";
        }else if("3".equals(state)){
            return "已完成";
        }
        return null;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }
}
