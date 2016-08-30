package com.cn.ucoon.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		return "index";
	}

	@RequestMapping(value = "/chooseAllocation")
	public String chooseAllocation(HttpServletRequest request) {
		InetAddress addr;
		String ip ="127.0.0.1";
		try {
			addr = InetAddress.getLocalHost();
			ip= addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("ip", ip);
		return "chooseAllocation";
	}
}
