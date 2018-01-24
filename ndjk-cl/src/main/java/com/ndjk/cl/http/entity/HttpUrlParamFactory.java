package com.ndjk.cl.http.entity;


import com.ndjk.cl.http.enumeration.HttpContentTypeEnum;
import com.ndjk.cl.http.enumeration.HttpMethodEnum;

public class HttpUrlParamFactory {
    public static final String DEFAULT_ENCODE="utf-8";
    public static final Integer DEFAULT_READ_TIMEOUT_DEFAULT=300;
    public static final Integer DEFAULT_CONNECT_TIMEOUT_DEFAULT=60;

    private HttpUrlParamFactory(){
    }

    public static HttpUrlParam createDefaultPostRequestWithForm(String url){
        return new HttpUrlParam(url, HttpMethodEnum.POST, HttpContentTypeEnum.FORM,DEFAULT_ENCODE,DEFAULT_READ_TIMEOUT_DEFAULT,DEFAULT_CONNECT_TIMEOUT_DEFAULT);
    }

    public static HttpUrlParam createDefaultPostRequestWithJson(String url){
        return new HttpUrlParam(url, HttpMethodEnum.POST, HttpContentTypeEnum.JSON,DEFAULT_ENCODE,DEFAULT_READ_TIMEOUT_DEFAULT,DEFAULT_CONNECT_TIMEOUT_DEFAULT);
    }

    public static HttpUrlParam createDefaultPostRequestWithText(String url){
        return new HttpUrlParam(url,HttpMethodEnum.POST, HttpContentTypeEnum.TEXT,DEFAULT_ENCODE,DEFAULT_READ_TIMEOUT_DEFAULT,DEFAULT_CONNECT_TIMEOUT_DEFAULT);
    }

    public static HttpUrlParam createDefaultGetRequest(String url){
        return new HttpUrlParam(url,HttpMethodEnum.GET, HttpContentTypeEnum.FORM,DEFAULT_ENCODE,DEFAULT_READ_TIMEOUT_DEFAULT,DEFAULT_CONNECT_TIMEOUT_DEFAULT);
    }
}
