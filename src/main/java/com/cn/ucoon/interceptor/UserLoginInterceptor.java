package com.cn.ucoon.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserLoginInterceptor implements HandlerInterceptor {

	private final String USERSESSION = "user_id";

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		Object sessionObj = request.getSession().getAttribute(USERSESSION);

		if (sessionObj != null) {
			return true;
		}

		// 参数
//		String params = request.getQueryString();
		// url
//		StringBuffer domain = request.getRequestURL();
		
		String path = request.getServletPath(); 
		
//		String url = domain.toString();
//		if (params != null) {
//			url = url.substring(0, url.length() - 1);
//			url = url + "?" + params;
//			path = path + "?" + params;
//		}

		//保存当前url，用户授权后跳转回来
		request.getSession().setAttribute("lastUrl", path);

		response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx14236620e0b8201e&redirect_uri=http%3A%2F%2Fwx.ucoon.cn%2Fucoon%2Fwx%2Foauth&response_type=code&scope=snsapi_userinfo&state=echo#wechat_redirect");
		return false;
	}

}
