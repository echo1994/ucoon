package com.cn.ucoon.pojo.wx;

public class JsApiTicket {

	private String ticket;//获取到 的凭证
	
	private int expiresIn;//凭证的有效期7200秒

	public synchronized String getTicket() {
		return ticket;
	}

	public synchronized void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public synchronized int getExpiresIn() {
		return expiresIn;
	}

	public synchronized void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
}
