package com.alienlab.activityserver.web.wechat.bean;

/**
 * Created by admin on 2017-01-21.
 */
public class ServiceToken {
    // 获取到的凭证
    private String token;

    private long tokenTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public long getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(long tokenTime) {
        this.tokenTime = tokenTime;
    }
}
