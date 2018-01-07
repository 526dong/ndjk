package com.ndjk.cl.operaterecord.model;

import java.util.Map;

/**
 * 系统操作日志实体类
 * @author Created by xzd on 2018/1/7
 */
public class OperateRecord {
    /**操作记录id*/
    private long id;
    /**操作人*/
    private String operator;
    /**操作类型-add/delete/update/select*/
    private String operateType;
    /**操作内容*/
    private String operateContent;
    /**操作人ip地址*/
    private String operateIp;
    /**操作请求路径*/
    private String operateUrlPath;
    /**操作请求参数*/
    private Map<String, String[]> paramMap;
    /**操作时间*/
    private String operateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getOperateUrlPath() {
        return operateUrlPath;
    }

    public void setOperateUrlPath(String operateUrlPath) {
        this.operateUrlPath = operateUrlPath;
    }

    public Map<String, String[]> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String[]> paramMap) {
        this.paramMap = paramMap;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
