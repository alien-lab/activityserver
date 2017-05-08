package com.alienlab.activityserver.web.wechat.bean;

public class AccessToken {
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	private long tokenTime;

	public long getTokenTime() {
		return tokenTime;
	}

	public void setTokenTime(long tokenTime) {
		this.tokenTime = tokenTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
