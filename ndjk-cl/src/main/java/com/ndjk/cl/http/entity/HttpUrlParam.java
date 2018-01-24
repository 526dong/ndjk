package com.ndjk.cl.http.entity;



import com.ndjk.cl.http.enumeration.HttpContentTypeEnum;
import com.ndjk.cl.http.enumeration.HttpMethodEnum;

import java.util.Map;

public class HttpUrlParam {
    private String url;
    private HttpMethodEnum httpMethod;
    private HttpContentTypeEnum httpContentType;
    private String encode;
    private Integer readTimeout;
    private Integer connectTimeout;

    private Map<String,String> headers;

    public HttpUrlParam(String url, HttpMethodEnum httpMethod, HttpContentTypeEnum httpContentType, String encode, Integer readTimeout, Integer connectTimeout) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.httpContentType = httpContentType;
        this.encode = encode;
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
    }

    public HttpUrlParam(String url, HttpMethodEnum httpMethod, HttpContentTypeEnum httpContentType, String encode, Integer readTimeout, Integer connectTimeout, Map<String, String> headers) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.httpContentType = httpContentType;
        this.encode = encode;
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
        this.headers = headers;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethodEnum getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethodEnum httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpContentTypeEnum getHttpContentType() {
        return httpContentType;
    }

    public void setHttpContentType(HttpContentTypeEnum httpContentType) {
        this.httpContentType = httpContentType;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
