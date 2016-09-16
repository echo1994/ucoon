package com.cn.ucoon.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cn.ucoon.service.UserService;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@Resource
	private UserService userService;

	// 进入聊天列表
	@RequestMapping(value = "/chat-list", method = RequestMethod.GET)
	public String testRequestParam( Model model,
			HttpSession session) {
		

		/*int user_id = (int) session.getAttribute("user_id");

		User user = null;
		if (user_id == 0) {
			user = this.userService.getUserByCode(code);
		} else {
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
		System.out.println(userList);*/
		return "chat-list";

	}

	/**
	 * 聊天
	 * 
	 * @param request
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/api-1", method = RequestMethod.GET)
	public String chat(@RequestParam(value = "fromuserid") String fromuserid,
			@RequestParam(value = "touserid") String touserid) {

		if (fromuserid == null || touserid == null) {
			return "redirect:/html/404.html";
		}

		return "redirect:/html/chat.html?fromuserid=" + fromuserid
				+ "&touserid=" + touserid;
	}

}
