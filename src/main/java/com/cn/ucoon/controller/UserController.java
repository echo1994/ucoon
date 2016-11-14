package com.cn.ucoon.controller;

import java.util.HashMap;
import java.util.List;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.ApplyOrders;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	
	@Resource
	private ApplyService applyService;
	
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
	public String getHeadUrl(@RequestParam("userId")Integer userId){
		
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
	
	
	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 *            页面传的userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public ModelAndView me(ModelAndView mv,
			HttpServletRequest request) {

		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		
		User user = null;
		user = this.userService.getUserById(userId);
		mv.setViewName("me");
		mv.addObject("u", user);
		return mv;
	}

	
	/**
	 * 修改签名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/signature", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject me(HttpServletRequest request,
			@RequestParam(value = "content", required = true) String content) {

		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		
		User user = new User();
		
		user.setUserId(userId);
		user.setSignature(content);
		
		JSONObject json = new JSONObject();
		
		if(this.userService.updateUserSignature(user)){
			json.put("result", "success");
			return json;
			
		}
		json.put("result", "error");
		json.put("msg", "保存失败");
		
		return json;
	}
	
	/**
	 * 修改微信号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wxId", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject wx(HttpServletRequest request,
			@RequestParam(value = "content", required = true) String content) {

		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		
		User user = new User();
		
		user.setUserId(userId);
		user.setWeixinId(content);
		
		JSONObject json = new JSONObject();
		
		if(this.userService.updateUserWxId(user)){
			json.put("result", "success");
			return json;
			
		}
		json.put("result", "error");
		json.put("msg", "保存失败");
		
		return json;
	}
	
	
	/**
	 * 修改微信名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/name", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject name(HttpServletRequest request,
			@RequestParam(value = "content", required = true) String content) {

		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		
		User user = new User();
		
		user.setUserId(userId);
		user.setNickName(content);		
		JSONObject json = new JSONObject();
		
		if(this.userService.updateUserName(user)){
			json.put("result", "success");
			return json;
			
		}
		json.put("result", "error");
		json.put("msg", "保存失败");
		
		return json;
	}
	
	@RequestMapping(value = "/isBindPhone", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject dialog(HttpServletRequest request) {

		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		
		JSONObject json = new JSONObject();
		
		if(this.userService.isBindPhone(userId)){
			json.put("result", "success");
			return json;
			
		}
		json.put("result", "error");
		
		
		return json;
	}
	
	
	/**
	 * 选人的信息展示
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detailWithEvaluate/{aid}")
	public ModelAndView detail(
			@PathVariable(value = "aid") Integer apply,ModelAndView mv) {

		
		ApplyOrders orders = applyService.selectByPrimaryKey(apply);
		
		User user = this.userService.getUserById(orders.getUserId());
		List<HashMap<String, Object>> infos = this.userService.selectDetailWithEvabyUserId(orders.getUserId());
		System.out.println(infos);
		mv.addObject("infos", infos);
		mv.addObject("user", user);
		mv.addObject("orders", orders);
		mv.addObject("size", infos.size());
		mv.setViewName("user-info");
		return mv;
	}

}