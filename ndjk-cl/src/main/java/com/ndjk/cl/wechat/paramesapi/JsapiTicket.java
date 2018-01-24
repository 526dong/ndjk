package com.ndjk.cl.wechat.paramesapi;

/**
 * @Author: wl
 * @Description: 微信获取JsapiTicket
 * @Date: 2018/1/17  10:49
 * @Version: 2.0
 */
public class JsapiTicket {
    // 获取到的JsapiTicket
    private String jsapiTicket;
    // 凭证有效时间，单位：秒
    private int expiresIn;

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
