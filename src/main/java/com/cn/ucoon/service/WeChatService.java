package com.cn.ucoon.service;

import javax.servlet.http.HttpServletRequest;

/*
 * 核心服务类
 * */
public interface WeChatService {

	/**
	 * 处理微信发来的请求
	 * **/
	public String processRequest(HttpServletRequest request);

	

}
