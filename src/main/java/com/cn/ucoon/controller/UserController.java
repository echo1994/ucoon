package com.cn.ucoon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	
	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 *            页面传的userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showUser", method = RequestMethod.POST)
	@ResponseBody
	public String getUserDetails(
			@RequestParam(value = "userId", required = false) Integer userId,
			HttpServletRequest request) {
		User user = null;
		if (userId == null) {
			Integer realUserId = Integer.parseInt(request.getParameter("id"));
			user = this.userService.getUserById(realUserId);
		} else {
			user = this.userService.getUserById(userId);
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		return jsonfromList;
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
	
	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 *            页面传的userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderUser/{userId}", method = RequestMethod.GET)
	public ModelAndView getOrderUserDetails(
			@PathVariable(value = "userId") Integer userId, ModelAndView mv,
			HttpServletRequest request) {
		User user = null;
		user = this.userService.getUserById(userId);
		mv.setViewName("order-user-details");
		mv.addObject("u", user);
		return mv;
	}

	
}