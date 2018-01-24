package com.ndjk.cl.http.enumeration;

public enum HttpHeaderEnum {
    CONTENT_TYPE("Content-type"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT("accept"),
    CONNECTION("connection"),
    AUTHORIZATION("Authorization"),
    ;
    private String key;

    HttpHeaderEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
