package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;

/**
 * Created by zfwlz on 2017/12/31.
 */
public class GetBrandServiceInfo implements Serializable {

    private String name;  //服务名称

    private String url; //图片链接url

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
}
