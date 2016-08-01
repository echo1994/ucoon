package com.cn.ucoon.pojo.wx;

/**
 * 
 * 微信通用接口凭证封装
 * */
public class AccessToken {
	
	private String token;//获取到 的凭证
	
	private int expiresIn;//凭证的有效期7200秒

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
