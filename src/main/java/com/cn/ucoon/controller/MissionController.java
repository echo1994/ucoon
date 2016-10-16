package com.cn.ucoon.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.Evaluate;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.MissionAddress;
import com.cn.ucoon.pojo.MissionOrders;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.EvaluateService;
import com.cn.ucoon.service.MissionOrderService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.PayUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/mission")
public class MissionController {

	@Autowired
	private MissionService missionService;
	
	@Autowired
	private MissionOrderService missionOrderService;

	@Autowired
	private UserService userService;

	@Autowired
	private EvaluateService evaluateService;
	
	@Autowired
	private ApplyService applyService;
	
	/**
	 * 发布任务
	 * 
	 * @param mission
	 *            对表表单 自动封装Mission对象
	 * @param file
	 *            多张图片
	 * @param request
	 *            请求
	 * @return 跳转页面
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/add-mission")
	public String publishMission(
			@RequestParam(value = "missionTitle", required = false) String missionTitle,
			@RequestParam(value = "missionDescribe", required = false) String missionDescribe,
			@RequestParam(value = "missionPrice", required = false) Double missionPrice,
			@RequestParam(value = "peopleCount", required = false) Integer peopleCount,
			@RequestParam(value = "place", required = false) String place,
			@RequestParam(value = "time", required = false) String time,
			@RequestParam(value = "telephone", required = false) String telephone,
			@RequestParam(value = "missionLng", required = false) String missionLng,
			@RequestParam(value = "missionLat", required = false) String missionLat,
			@RequestParam(value = "imgUpload", required = false) MultipartFile[] file,
			HttpServletRequest request) throws ParseException {

		Mission mission = new Mission();
		mission.setMissionDescribe(missionDescribe);
		mission.setMissionPrice(new BigDecimal(missionPrice));
		mission.setMissionTitle(missionTitle);
		mission.setPeopleCount(peopleCount);
		mission.setPlace(place);
		mission.setTelephone(telephone);
		mission.setMissionLat(missionLat);
		mission.setMissionLng(missionLng);
		
		
		
		
		String path = ImageController.MISSION_IMAGE_LOCATION;
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		String timestamp = String.valueOf(System.currentTimeMillis());
		String uuid = String.valueOf(UUID.randomUUID());
		uuid = uuid.replace("-", "");
		String realpath = path + "/" + userId + timestamp + uuid;// 文件夹位置
		File dir = new File(realpath);
		dir.mkdirs();
		System.out.println("wenjian:" + file.length);
		for (int i = 0; i < file.length; i++) {
			if (!file[i].isEmpty()) {
				String fileName = file[i].getOriginalFilename();// 文件原名称
				String type = fileName.indexOf(".") != -1 ? fileName.substring(
						fileName.lastIndexOf(".") + 1, fileName.length())
						: null;
				try {
					file[i].transferTo(new File(realpath + "/" + i + "." + type));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String reg = "[\u4e00-\u9fa5]";  
        Pattern pat = Pattern.compile(reg);    
        Matcher mat = pat.matcher(time);   
        String repickStr = mat.replaceAll(""); 
		Date date = new Date();
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY, Integer.valueOf(repickStr));
		
		mission.setPublishTime(date);
		mission.setEndTime(ca.getTime());
		mission.setPictures(userId + timestamp + uuid);
		mission.setUserId(userId);
		mission.setViewCount(0);
		mission.setMissionStatus(0); //待支付
		mission.setPicCount(file.length);
		MissionOrders missionOrders = new MissionOrders();
		if(missionService.publishMission(mission)){
			
			
			//判断地址是否已存在
			if(!missionService.isAddressExist(place)){
				
				MissionAddress address = new MissionAddress();
				address.setPlace(place);
				address.setMissionLat(missionLat);
				address.setMissionLng(missionLng);
				
				missionService.addMissionAddress(address);
			}
			
			
			
			System.out.println("mission_id:" + mission.getMissionId());
			
			missionOrders.setMissionId(mission.getMissionId());
			missionOrders.setMissionOrderNum(PayUtil.getOrdersNum(userId, mission.getMissionId()));
			missionOrders.setOrderTime(new Date());
			missionOrders.setState(0);//未支付
			missionOrders.setUserId(userId);
			missionOrderService.makeOrders(missionOrders);
		}
		//存order，mission，微信支付时取
		request.getSession().setAttribute("orders", missionOrders);
		request.getSession().setAttribute("mission", mission);
		
		
		//任务发布，订单生成,跳转支付界面
		return "redirect:/mission-pay";
	}

	/**
	 * 分页查询
	 * 
	 * @param userId
	 *            通过userId搜索
	 * @param missionStatus
	 *            通过missionStatus搜索
	 * @param keyWord
	 *            通过关键字搜索任务
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @return json
	 */
	@RequestMapping(value = "/getMissionsLimited", method = RequestMethod.POST)
	@ResponseBody
	public String getMissionsLimited(
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "missionStatus", required = false) Integer missionStatus,
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
		} else if (userId != null) {
			
			userId = (Integer) request.getSession().getAttribute("user_id");
			List<Integer> list = new ArrayList<Integer>();
			if (missionStatus == null) {
				missions = missionService.selectLimitedbyUserId(userId,
						startIndex, endIndex);
			} else if(missionStatus == 1){
				list.add(1);
				list.add(2);
				
				missions = missionService.selectLimitedbyUserIdAndStatus(
						userId,  list, startIndex, endIndex);
			} else if(missionStatus == 2){
				list.add(3);
				list.add(4);
				missions = missionService.selectLimitedbyUserIdAndStatus(
						userId,  list, startIndex, endIndex);
			} else if(missionStatus == 3){
				list.add(0);
				missions = missionService.selectLimitedbyUserIdAndStatus(
						userId,  list, startIndex, endIndex);
			} else if(missionStatus == 4){
				list.add(5);
				missions = missionService.selectLimitedbyUserIdAndStatus(
						userId, list, startIndex, endIndex);
			} 
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
	 * 查询任务详情
	 * 
	 * @param missionId
	 *            任务id
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/missionDetails", method = RequestMethod.POST)
	public String getmissionDetails(
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		HashMap<String, Object> missions = null;
		missions = missionService.selectForMissionDetails(missionId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(missions);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		return jsonfromList;

	}

	
	//退款
	@RequestMapping(value = "missionOffShelf/{missionId}", produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String missionOffShelf(
			@PathVariable(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		// 1判断是否本人操作
		// 2申请退款的条件：任务已支付且未执行
		// 3改变任务状态
		// 4退款
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == userId) {
			Mission mission = missionService.selectByPrimaryKey(missionId);
			mission.setMissionStatus(2);
			missionService.updateByPrimaryKey(mission);// 审核退款
			return "管理员GG正在审核退款，24小时内将以红包的形式在有空ucoon平台上发给你，请注意查收。如有需要请拨打GG电话";
		}
		return "系统异常，请重试";
	}
	
	//取消退款
	@RequestMapping(value = "cancelOff/{missionId}", produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String cancelOff(
			@PathVariable(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == userId) {
			Mission mission = missionService.selectByPrimaryKey(missionId);
			mission.setMissionStatus(1);
			missionService.updateByPrimaryKey(mission);// 审核退款
			return "已取消退款";
		}
		return "系统异常，请重试";
	}
	
	//取消任务
	@RequestMapping(value = "missionCancel/{missionId}", produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String missionCancel(
			@PathVariable(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == userId) {
			Mission mission = missionService.selectByPrimaryKey(missionId);
			mission.setMissionStatus(4);
			missionService.updateByPrimaryKey(mission);// 审核退款
			return "已取消任务";
		}
		return "系统异常，请重试";

	}
	
	//完成任务
	@RequestMapping(value = "missionDone/{missionId}", produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String missionDone(
			@PathVariable(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		// 1判断是否本人操作
		// 2改变任务状态
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == userId) {
			Mission mission = missionService.selectByPrimaryKey(missionId);
			mission.setMissionStatus(5);
			missionService.updateByPrimaryKey(mission);// 下架
			return "任务完成";
		}
		return "系统异常，请重试";

	}
	
	//开始任务任务
	@RequestMapping(value = "missionDoing/{missionId}", produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String missionDoing(
			@PathVariable(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		// 1判断是否本人操作
		// 2改变任务状态
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == userId) {
			Mission mission = missionService.selectByPrimaryKey(missionId);
			mission.setMissionStatus(6);
			missionService.updateByPrimaryKey(mission);
			return "success";
		}
		return "系统异常，请重试";

	}


	@RequestMapping(value = "/task-info/{missionId}")
	public ModelAndView taskInfo(@PathVariable("missionId") Integer missionId,
			ModelAndView mv,HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		
		HashMap<String, Object> mdetails = null;
		mdetails = missionService.selectForMissionDetails(missionId);
		User user = userService.getUserById(user_id);
		
		System.out.println(mdetails);
		mv.addObject("mdetails", mdetails);
		mv.addObject("user", user);
		mv.setViewName("task-info");
		
		
		//浏览量
		missionService.viewCount(missionId);
		
		return mv;
	}

	
	@RequestMapping(value = "/orderDetail/{applyId}")
	public ModelAndView getorderDetailsByOrderId(
			@PathVariable(value = "applyId") Integer applyId, ModelAndView mv) {
		List<HashMap<String, String>> oulist = null;
	//	oulist = applyService.selectorderDetailsByApplyId(applyId);
		mv.setViewName("myservice-task-info");
		if (oulist.size() > 0) {
			mv.addObject("ou", oulist.get(0));
		}else{
			mv.addObject("ou", null);
		}
		return mv;
	}
	
	@RequestMapping(value = "/mysend-task-info/{missionId}")
	public ModelAndView mysendTaskInfo(@PathVariable("missionId") Integer missionId,
			ModelAndView mv,HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		
		HashMap<String, Object> mdetails = null;
		mdetails = missionService.selectForMissionDetails(missionId);
		User user = userService.getUserById(user_id);
		
		System.out.println(mdetails);
		mv.addObject("mdetails", mdetails);
		mv.addObject("user", user);
		mv.setViewName("mysend-task-info");
		
		
		return mv;
	}
	
	@RequestMapping(value = "/more-info/{mid}")
	public ModelAndView moreMinfo(@PathVariable(value = "mid") Integer mid,
			ModelAndView mv) {
		mv.addObject("mid", mid);
		mv.setViewName("more-info");
		return mv;
	}
	
	@RequestMapping(value = "/selectpeople/{mid}")
	public ModelAndView selectpeople(@PathVariable(value = "mid") Integer mid,
			ModelAndView mv) {
		

		HashMap<String, Object> mdetails = null;
		mdetails = missionService.selectForMissionDetails(mid);
		List<HashMap<String, Object>> list = applyService.selectunselectedpeople(mid);
		
		List<HashMap<String, Object>> list2 = applyService.selectselectedpeople(mid);
		System.out.println("~~~~~~~" + list);
		
		
		
		mv.addObject("mdetails", mdetails);
		mv.addObject("list", list);
		mv.addObject("list2", list2);
		mv.setViewName("selectpeople");
		return mv;
	}
	
	
	@RequestMapping(value = "/selectpeopledetail/{mid}")
	@ResponseBody
	public String selectJsonPeople(@PathVariable(value = "mid") Integer mid) {
		

		List<HashMap<String, Object>> list = applyService.selectunselectedpeople(mid);
		
		
		for(int i = 0 ; i<list.size();i++){
			
			Integer userId = (Integer) list.get(i).get("user_id");
			
			Integer helpCount = missionService.countMissionDoneByUserId(userId);
			
			
			String score = this.evaluateService.getEvaluateScore(userId) + "";
			System.out.println(score);
			String[] str = score.split("\\.");
			if(str[1].equals("5")){
				list.get(i).put("half", true);
				list.get(i).put("all", str[0]);
				list.get(i).put("blank", 4 - Integer.valueOf(str[0]));
			}else{
				list.get(i).put("half", false);
				list.get(i).put("all", str[0]);
				list.get(i).put("blank", 5 - Integer.valueOf(str[0]));
			}
			
			
			
			list.get(i).put("helpCount", helpCount);
			
			
			
			
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		return jsonfromList;
	}
	
	
	
	
	//发布者对执行者评价
	@RequestMapping(value = "/evaluate_publish/{missionId}")
	public ModelAndView evaluate(@PathVariable("missionId") Integer missionId,
			ModelAndView mv,HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");//发布者id
		Evaluate evaluate = null;
		evaluate = evaluateService.selectByMissionId(missionId);
		if(evaluate == null){
			//生成对象
			evaluate = new Evaluate();
			evaluate.setPublishId(user_id);
			
			evaluate.setMissionId(missionId);
			Integer publishId = missionService.getUserIdByMissionId(missionId);
			//evaluate.setExecutorId();
			
			evaluateService.insertEvaluate(evaluate);
		}
		
		
		User user = userService.getUserById(evaluate.getPublishId());
		
		mv.addObject("evaluate", evaluate);
		mv.addObject("user", user);
		
		mv.setViewName("evaluate");
		
		
		return mv;
	}
	
	
	@RequestMapping(value = "/addEvaluate", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addEvaluate(
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "missionId", required = true) Integer missionId,
			@RequestParam(value = "score", required = true) Float score,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");
		
		if(user_id == null || user_id == 0){
			json.put("result", "error");
			json.put("msg", "系统出错了");
			return json;
			
		}
		
		if(score == null || score == 0){
			json.put("result", "error");
			json.put("msg", "分数不能为0");
			return json;
			
		}
		
		Evaluate evaluate = new Evaluate();
		
		evaluate.setEpevaluateTime(new Date());
		evaluate.setExecutorEvaluate(content);
		evaluate.setExecutorScore(score);
		evaluate.setMissionId(missionId);
		
		//更新评价表
		
		
		
	

		if (evaluateService.updateExecutorByMissionId(evaluate)) {
			json.put("result", "success");
			json.put("msg", "评价成功");
			
			return json;
		}


		
		
		json.put("result", "error");
		json.put("msg", "评价失败");

		return json;
	}
}
