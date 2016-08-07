package com.cn.ucoon.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class OnlineCountListener implements HttpSessionAttributeListener,
		HttpSessionListener, ServletContextListener {

	//spring注入
//	@Resource
//	private UserService userService;
	
	/**
	 * 定义监听的session属性名.
	 */
	public final static String LISTENER_NAME = "user_id";

	/**
	 * 定义存储客户登录session的集合.
	 */
	private static List<Object> sessions = new ArrayList<Object>();

	private static int userSum = 0;

	/**
	 * session加入某值时此方法会被调用.
	 * 
	 * @param HttpSessionBindingEvent
	 *            session事件
	 */
	public void attributeAdded(HttpSessionBindingEvent sbe) {
		if (LISTENER_NAME.equals(sbe.getName())) {
			sessions.add(sbe.getValue());
			
			//将用户登录状态改为1
			System.out.println("用户加入：" + sbe.getValue());
		}
	}

	/**
	 * session失效时的监听方法.
	 * 
	 * @param HttpSessionBindingEvent
	 *            session事件
	 */
	public void attributeRemoved(HttpSessionBindingEvent sbe) {
		if (LISTENER_NAME.equals(sbe.getName())) {
			sessions.remove(sbe.getValue());
			//将用户登录状态改为0
			System.out.println("用户注销：" + sbe.getValue());
		}
	}

	/**
	 * session覆盖时的监听方法.
	 * 
	 * @param HttpSessionBindingEvent
	 *            session事件
	 */
	public void attributeReplaced(HttpSessionBindingEvent sbe) {
		if (LISTENER_NAME.equals(sbe.getName())) {
			sessions.remove(sbe.getValue());
			sessions.add(sbe.getValue());
		}
	}

	public void sessionCreated(HttpSessionEvent arg) {
		System.out.println("user ++ ");
		userSum++;
	}

	public void sessionDestroyed(HttpSessionEvent arg) {
		userSum--;
	}

	/**
	 * 返回客户登录session的集合.
	 * 
	 * @return
	 */
	public static List<Object> getSessions() {
		return sessions;
	}

	/**
	 * 返回在线用户数量
	 * @return
	 */
	public static int getMemberSum() {
		return sessions.size();
	}

	public static int getSum() {
		return userSum;
	}

	public static Boolean isExistInSessions(String username) {
		String user_name;
		for (Integer i = 0; i < sessions.size(); i++) {
			user_name = (String) sessions.get(i);
			if (user_name.equals(username)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	// 此方法在初始化监听器的时候会调用，用于spring注入
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		
//		this.xxxService = (XxxService) applicationContext.getBean("xxxService");

	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

//	private XxxService xxxService;// 这里是你希望注入的服务类
//
//	public XxxService getXxxService() {
//		return this.xxxService;
//	}
//
//	public void setxxxService(XxxService xxxService) {
//		this.xxxService = xxxService;
//	}

}
