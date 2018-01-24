package com.ndjk.cl.http.enumeration;

public enum HttpUrlExceptionTypeEnum {
    INIT_FAIL(1,"初始化失败"),
    CONNECT_FAIL(2,"连接失败"),
    READ_FAIL(3,"读取失败"),
    READ_TIMEOUT(4,"读取超时"),
    ASSEMBLE_PARAM_FAIL(5,"装配参数失败"),
    ;

    private Integer key;
    private String desc;

    HttpUrlExceptionTypeEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

}
