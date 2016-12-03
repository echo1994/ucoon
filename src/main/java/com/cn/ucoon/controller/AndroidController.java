package com.cn.ucoon.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.EvaluateService;
import com.cn.ucoon.service.MissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * api 1.0
 * @author mlk
 *
 */
@Controller
@RequestMapping("/api")
public class AndroidController {

	
	@Autowired
	private MissionService missionService;
	
	@Autowired
	private EvaluateService evaluateService;
	
	@Autowired
	private ApplyService applyService;
	
	/**
	 * 分页查询
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 * @param keyWord
	 *            通过关键字搜索任务
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @param type
	 * 			  类型 全部 和附近
	 * @return json
	 */
	@RequestMapping(value = "/getMissionsLimited", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getMissionsLimited(
			@RequestParam(value = "keyWord", required = false) String keyWord,
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex,
			@RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request) {
		List<HashMap<String, Object>> missions = null;
		if (keyWord != null && keyWord != "") {
			missions = missionService.getMissionByKeyWord("%" + keyWord + "%",
					startIndex, endIndex,latitude,longitude);
		} else {
			
			//取出的任务： 已支付，被选择的执行人 还小于需要的人数
			missions = missionService.getMissionLimited(startIndex, endIndex,latitude,longitude,type);
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(missions);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		System.out.println(jsonfromList);
		return jsonfromList;
	}
	
	/**
	 * 
	 * @param missionId
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMissionDetail" ,method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String taskInfo(@RequestParam(value="missionId", required = true) Integer missionId) {
		HashMap<String, Object> mdetails = missionService.selectForMissionDetails(missionId);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(mdetails);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		System.out.println(jsonfromList);
		return jsonfromList;
	}
	
	
	/**
	 * 分页查询
	 * 
	 * @param publishId
	 *            发布者id
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @return json
	 */
	@RequestMapping(value = "/getEvaluateLimited", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getMissionsLimited(
			@RequestParam(value = "publishId", required = true) Integer publishId,
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex) {
		List<HashMap<String, Object>> evaluates = null;

		evaluates = evaluateService.selectLimitedbyPublishId(publishId,
				startIndex, endIndex);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(evaluates);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		System.out.println(jsonfromList);
		return jsonfromList;
	}
}
