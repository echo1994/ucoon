package com.cn.ucoon.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

/**
 * 创建握手（handshake）接口 建立HTTP，请求协议升级（或叫协议转换）
 * 
 * @author mlk
 *
 */

public class HandshakeInterceptor implements
		org.springframework.web.socket.server.HandshakeInterceptor {

	// 初次握手访问前
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Map<String, Object> map){
		System.out.println("握手前");
		if (request instanceof ServletServerHttpRequest) {
			HttpServletRequest servletRequest = ((ServletServerHttpRequest) request)
					.getServletRequest();

			// 进入聊天
			System.out.println("进入聊天");
			String fromuserid = servletRequest.getParameter("fromuserid");
			String touserid = servletRequest.getParameter("touserid");
			// 使用userName区分WebSocketHandler，以便定向发送消息
			// String userName = (String)
			// session.getAttribute("WEBSOCKET_USERNAME");
			map.put("FROMUSERID", fromuserid); // 发送方
			map.put("TOUSERID", touserid); // 接收方
			// servletRequest.getSession().setAttribute("USERID", userID);
		}
		return true;
	}

	// 初次握手访问后
	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Exception e) {

		System.out.println("握手后");
	}
}
