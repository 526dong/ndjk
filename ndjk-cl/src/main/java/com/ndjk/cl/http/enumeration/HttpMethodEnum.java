package com.ndjk.cl.http.enumeration;

/**
 *
 * HTTP 0.9 这个版本只有GET方法
 * HTTP 1.0 这个版本有GET HEAD POST这三个方法
 * HTTP 1.1 这个版本是当前版本，包含GET HEAD POST OPTIONS PUT DELETE TRACE CONNECT这8个方法
 */
public enum HttpMethodEnum {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    OPTIONS("OPTIONS"),
    PUT("PUT"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
    ;
    private String key;

    HttpMethodEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
