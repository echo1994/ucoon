package com.cn.ucoon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.MessageService;
import com.cn.ucoon.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@Resource
	private UserService userService;
	
	@Resource
	private MessageService messageService;

	// 进入聊天列表 
	@RequestMapping(value = "/chat-list", method = RequestMethod.GET)
	public String testRequestParam( Model model,
			HttpSession session) {
		

		Integer user_id = (int) session.getAttribute("user_id");

		List<HashMap<String, Object>> list = messageService.selectMsgListbyUserId(user_id);

		for(int i = 0;i<list.size();i++){
			
			if(list.get(i).get("from_user_id") != user_id){
				Integer temp = (Integer) list.get(i).get("from_user_id");
				list.get(i).put("from_user_id", user_id);
				list.get(i).put("to_user_id", temp);
				
			}
			Integer userId = (Integer) list.get(i).get("to_user_id");
			User user = userService.getUserById(userId);
			list.get(i).put("head_img_url", user.getHeadImgUrl());
			list.get(i).put("nick_name", user.getNickName());
			list.get(i).put("sex", user.getSex());
		}
		
		
		System.out.println(list);
//		model.addAttribute("user", user);
		model.addAttribute("list", list);

		return "chat-list";

	}
	
	
	@RequestMapping(value = "/getChatHistoryMsg", method = RequestMethod.POST)
	@ResponseBody
	public String getChatHistoryMsg( 
			@RequestParam(value = "fromUserID", required = true) Integer fromUserID,
			@RequestParam(value = "toUserID", required = true) Integer toUserID,
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex,
			HttpSession session) {
		

		Integer user_id = (int) session.getAttribute("user_id");
     	List<HashMap<String, Object>> list = messageService.selectMsgHistory(fromUserID, toUserID, startIndex, endIndex);
     	for (int i = 0; i < list.size(); i++) {
			Integer fromUserId = (Integer) list.get(i).get("from_user_id");
			User user = userService.getUserById(fromUserId);
			list.get(i).put("nick_name", user.getNickName());
			list.get(i).put("head_img_url", user.getHeadImgUrl());
			if(fromUserId == user_id){
				//自己发的
				list.get(i).put("sender", "self");
			}else{
				
				list.get(i).put("sender", "echo");
			}
		}
     	
     	
     	ObjectMapper mapper = new ObjectMapper();
 		String jsonfromList = "";
 		try {
 			jsonfromList = mapper.writeValueAsString(list);
 		} catch (JsonProcessingException e) {
 			e.printStackTrace();
 			jsonfromList = "{}";
 		}
     	System.out.println("历史记录" + jsonfromList);
		
		return jsonfromList;

	}
	

	/**
	 * 聊天
	 * 
	 * @param request
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/api-1/{fromuserid}/{touserid}", method = RequestMethod.GET)
	public String chat(@PathVariable(value = "fromuserid") Integer fromuserid,
			@PathVariable(value = "touserid") Integer touserid, Model model) {

		if (fromuserid == null || touserid == null) {
			return "redirect:/html/404.html";
		}
		
		User user = userService.getUserById(touserid);
		
		
		model.addAttribute("user", user);
		model.addAttribute("fromuserid", fromuserid);
		model.addAttribute("touserid", touserid);
		
		return "chat";
	}

}
