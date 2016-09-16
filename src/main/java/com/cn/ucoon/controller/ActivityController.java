package com.cn.ucoon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ucoon.pojo.Activity;
import com.cn.ucoon.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	@RequestMapping("/create")
	public String toLogin() {

		return "create-aty";
	}
	
	@RequestMapping("/add-activity")
	public String save(
			@RequestParam(value = "activityName", required = false) String activityName,
			@RequestParam(value = "activityTime", required = false) String activityTime,
			@RequestParam(value = "activityPlace", required = false) String activityPlace,
			@RequestParam(value = "activityDetailPlace", required = false) String activityDetailPlace,
			@RequestParam(value = "activityDesc", required = false) String activityDesc,
			@RequestParam(value = "activityLng", required = false) String activityLng,
			@RequestParam(value = "activityLat", required = false) String activityLat,
			HttpServletRequest request) throws ParseException {

		int user_id = 3;
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟  
		Date ActivityDate=sdf.parse(activityTime);
		Activity activity = new Activity();
		activity.setActivityCreateTime(new Date());
		activity.setActivityDesc(activityDesc);
		activity.setActivityDetailPlace(activityDetailPlace);
		activity.setActivityLat(activityLat);
		activity.setActivityLng(activityLng);
		activity.setActivityName(activityName);
		activity.setActivityPlace(activityPlace);
		activity.setActivityTime(ActivityDate);
		activity.setUserId(user_id);
		
		
		if(activityService.saveActivity(activity)){
			return "redirect:/we";
		}
		//存order，mission，微信支付时取
//		request.getSession().setAttribute("orders", missionOrders);
//		request.getSession().setAttribute("mission", mission);
		
		
		//任务发布，订单生成,跳转支付界面
		return "create-aty";
	}
	
	
	/**
	 * 分页查询
	 * 
	 * @param userId
	 *            通过userId搜索
	 * @param keyWord
	 *            通过关键字搜索任务
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @return json
	 */
	@RequestMapping(value = "/getActivityLimited", method = RequestMethod.POST)
	@ResponseBody
	public String getMissionsLimited(
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "keyWord", required = false) String keyWord,
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex,
			@RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request) {
		
		List<HashMap<String, Object>> activities = null;
		if (keyWord != null && keyWord != "") {
			activities = activityService.getActivityByKeyWord("%" + keyWord + "%",
					startIndex, endIndex,latitude,longitude);
		} else if (userId != null) {
			
			userId =  (Integer)request.getSession().getAttribute("user_id");
			activities = activityService.selectLimitedbyUserId(
					userId, startIndex, endIndex);
		} else {
			activities = activityService.getActivityLimited(startIndex, endIndex,latitude,longitude,type);
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(activities);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		System.out.println(jsonfromList);
		return jsonfromList;
	}
	
}
