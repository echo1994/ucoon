package com.cn.ucoon.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.ucoon.service.WeChatService;
import com.cn.ucoon.util.SignUtil;

@Controller
@RequestMapping("/wx")
public class WeChatController {
	
	@Resource
	private WeChatService weChatService;
	
	/**
	 * 1表示第一个链接上的公众号编号
	 * @param request
	 * @param model
	 * @throws IOException 
	 */
	@RequestMapping(value="/1",method = RequestMethod.GET)
	public void toIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("进来了");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (timestamp != null) {
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
		}

		out.close();
		out = null;
	}

}
