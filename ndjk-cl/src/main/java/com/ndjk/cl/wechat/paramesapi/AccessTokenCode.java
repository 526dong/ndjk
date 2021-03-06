package com.ndjk.cl.wechat.paramesapi;

import java.io.Serializable;

/**
 * Created by wl on 2018/1/19.
 */
public class AccessTokenCode implements Serializable {
    private static final long serialVersionUID = 8040290186711789428L;
    private String accessToken;
    private int expiresIn;
    private String refreshToken;
    private String openid;
    private String scope;

    public AccessTokenCode() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
