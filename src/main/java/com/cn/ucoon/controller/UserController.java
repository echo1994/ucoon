package com.cn.ucoon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "test";
	}
	
	@RequestMapping(value="/getHeadUrl",method=RequestMethod.GET)
	@ResponseBody
	public String getHeadUrl(@RequestParam("userId")String userId){
		
		JSONObject json = new JSONObject();
		
		if(userId == null){
			json.put("result", "error");
			return json.toJSONString();
		}
		String url = this.userService.getHeadUrl(userId);
		json.put("result", "success");
		json.put("url", url);
		
		
		return json.toJSONString();
	} 
	
}