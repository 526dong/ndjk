package com.ndjk.cl.http.enumeration;

public enum HttpContentTypeEnum {
    FORM("application/x-www-form-urlencoded"),
    TEXT("text/html"),
    JSON("application/json"),
    UPLOAD("multipart/form-data"),
    ;
    private String key;

    HttpContentTypeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
