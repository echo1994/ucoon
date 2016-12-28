package com.cn.ucoon.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cn.ucoon.pojo.PhotoTurn;
import com.cn.ucoon.service.PhotoTurnService;


@Controller
public class PhotoindexController {
	
	@Resource
	PhotoTurnService photoService;
	
	@RequestMapping("/index")
	public String aa(HttpServletRequest request){
		List<PhotoTurn> photos = photoService.getOutPic();
		System.out.println(JSON.toJSONString(photos));
		request.setAttribute("photos",photos);
		return "index";
	}
}
