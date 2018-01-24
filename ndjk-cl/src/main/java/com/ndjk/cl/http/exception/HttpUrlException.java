package com.ndjk.cl.http.exception;


import com.ndjk.cl.http.enumeration.HttpUrlExceptionTypeEnum;

public class HttpUrlException extends Exception {
    private final HttpUrlExceptionTypeEnum httpUrlExceptionTypeEnum;

    public HttpUrlException(HttpUrlExceptionTypeEnum httpUrlExceptionTypeEnum) {
        this.httpUrlExceptionTypeEnum = httpUrlExceptionTypeEnum;
    }

    public HttpUrlExceptionTypeEnum getHttpUrlExceptionTypeEnum() {
        return httpUrlExceptionTypeEnum;
    }
}
