package com.cn.ucoon.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.service.WeChatService;
import com.cn.ucoon.util.SignUtil;
import com.cn.ucoon.util.WeixinUtil;

@Controller
@RequestMapping("/wx")
@SessionAttributes("user_id")
public class WeChatController {

	@Resource
	private WeChatService weChatService;

	@Resource
	private UserService userService;

	/**
	 * 1表示第一个链接上的公众号编号
	 * 
	 * @param request
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public void plugInto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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

	/**
	 * 微信消息核心处理 1表示连接的公众号编号
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/1", method = RequestMethod.POST)
	public void handler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 微信消息核心处理
		String respMessage = weChatService.processRequest(request);

		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

	// 网页授权登陆，服务器保存用户session
	@RequestMapping(value = "/oauth", method = RequestMethod.GET)
	public String testRequestParam(@RequestParam(value = "code") String code,
			@RequestParam(value = "state") String state, Model model,
			HttpSession session) {
		System.out.println("网页授权code------>" + code);

		// 判断state是否为echo -》开发者识别码
		if (state == null || !state.equals("echo")) {
			// 异常授权，跳转授权失败url
			return "auth_error.html";
		}

		int user_id = (int) (session.getAttribute("user_id") == null ? 0:session.getAttribute("user_id"));

		User user = null;
		if(user_id==0){
			user = this.userService.getUserByCode(code);
		}else{
			user = this.userService.getUserById(user_id);
		}
		

		if (user == null) {
			// 拉取用户信息出现异常，跳转授权失败url
			return "auth_error.html";
		}

		model.addAttribute("user", user);
		model.addAttribute("user_id", user.getUserId());

		List<User> userList = this.userService.getAllUser();
		model.addAttribute("userList", userList);
		System.out.println(userList);
		return "test";

	}
	
	@RequestMapping(value = "/sign", method = RequestMethod.GET)  
    public @ResponseBody Map<String, String> getJSSDKVal(@RequestParam(value="url") String url) {  
		Map<String, String> map=WeixinUtil.getJSSDK(url);
		return map;  
    }  

}
