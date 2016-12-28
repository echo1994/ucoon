package com.cn.ucoon.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AdminLoginInteceptor implements HandlerInterceptor{

	private final String ADMINSESSION = "admin_id";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute(ADMINSESSION)!=null){
			//System.out.println("session.getAttribute(ADMINSESSION)"+session.getAttribute(ADMINSESSION));
			return true;
		}
		else{
			response.sendRedirect("/ucoon/admin/login");
			return false;
		}
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
 

}
