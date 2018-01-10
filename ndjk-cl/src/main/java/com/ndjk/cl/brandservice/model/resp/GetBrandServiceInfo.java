package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;

/**
 * Created by zfwlz on 2017/12/31.
 */
public class GetBrandServiceInfo implements Serializable {

    private String name;  //服务名称

    private String url; //图片链接url

    private Integer id; //服务id

    private Integer type; //服务类型

    private String typeStr; //服务类型说明

    public GetBrandServiceInfo() {
    }

    public GetBrandServiceInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
