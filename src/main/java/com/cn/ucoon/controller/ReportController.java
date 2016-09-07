package com.cn.ucoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.service.CommentsService;
import com.cn.ucoon.service.ReportService;



@Controller
@RequestMapping("/report")
public class ReportController {

	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value = "/saveMissionReport", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveReport( 
			@RequestParam(value = "missionId", required = true) Integer missionId,
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "content", required = true) String content) {
		JSONObject json = new JSONObject();
		
		if(reportService.saveMissionReport(missionId, userId, content)){
			json.put("result", "success");
			json.put("msg", "举报成功");
		}else{
			json.put("result", "fail");
			json.put("msg", "举报失败");
		}
		return json;
	}
}
