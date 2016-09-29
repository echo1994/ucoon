package com.cn.ucoon.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.ApplyOrders;
import com.cn.ucoon.pojo.Evaluate;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.EvaluateService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.PayUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping(value = "/applyOrders")
@Controller
public class ApplyController {

	@Autowired
	private ApplyService applyService;

	@Autowired
	private MissionService missionService;
	
	@Autowired
	private EvaluateService evaluateService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/addAppliment", method = RequestMethod.POST, produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String addAppliment(
			@RequestParam(value = "missionId") Integer missionId,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId =  (Integer) request.getSession().getAttribute("user_id");
		List<HashMap<String, String>> applys = null;
		applys = applyService.selectApplybyUMID(userId, missionId);
		Mission mission = missionService.selectByPrimaryKey(missionId);
		if(mission.getUserId() == userId){
			
			return "不能领取自己的任务";
		}
		
		//这里要判断是否是可执行的任务
	/*	if(mission){
		}*/
		
		if (applys == null || applys.size() == 0) {
			ApplyOrders applyOrders = new ApplyOrders();
			applyOrders.setMissionId(missionId);
			applyOrders.setOrderNum(PayUtil.getOrdersNum(userId, mission.getMissionId()));
			applyOrders.setTakeState(0);
			applyOrders.setTakeTime(new Date());
			applyOrders.setUserId(userId);
			
			
			if (applyService.saveOrders(applyOrders)) {
					
				return "已成功申请";
				
				
			} else {
				return "申请失败，请重试";
			}
		} else {
			return "您已申请过此任务";
		}
	}

	/**
	 * 根据任务ID和用户ID获取申请信息
	 * 
	 * @param judge
	 *            是否只是判断存在申请信息
	 * @param missionId
	 *            任务ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getApplybyUMID", method = RequestMethod.POST)
	@ResponseBody
	public String selectOrderbyUMID(
			@RequestParam(value = "forjudge", required = true) boolean judge,
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		List<HashMap<String, String>> applys = null;
		if (judge == true) {// 只用于判断不返回数据
			applys = applyService.selectApplybyUMID(userId, missionId);
			if (applys == null || applys.size() == 0) {
				return "0";// 返回0，表明用户(userId)没接该任务
			} else {
				return "1";// 返回1，表明用户(userId)已经接了该任务
			}
		} else {
			// 返回订单数据
		}
		return "";
	}

	/**
	 * 发布者 通过 missionId 查看 所有申请信息
	 * 
	 * @param missionId
	 *            任务ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getApByMid", method = RequestMethod.POST)
	@ResponseBody
	public String getApByMid(
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		List<HashMap<String, String>> applys = null;
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		if (true) {// 条件userId == currentUserId
			applys = applyService.selectByMissionId(missionId);
			System.out.println(applys);
			ObjectMapper mapper = new ObjectMapper();
			String jsonfromList = "";
			try {
				jsonfromList = mapper.writeValueAsString(applys);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonfromList = "{}";
			}
			return jsonfromList;
		} else {
			return "{}";
		}
	}


	/*
	 * @RequestMapping(value = "/confirmAppliment", method = RequestMethod.POST)
	 * public String confirmAppliment(
	 * 
	 * @RequestParam(value = "userIds[]", required = true) Integer[] userIds,
	 * 
	 * @RequestParam(value = "missionId", required = true) Integer missionId,
	 * HttpServletRequest request) { if (applyService.confirmApply(userIds,
	 * missionId)) { return "true"; } else { return "false"; } }
	 */

	@RequestMapping(value = "getApplyUser/{applyId}")
	public ModelAndView getApplyUser(
			@PathVariable(value = "applyId") Integer applyId, ModelAndView mv) {
		List<HashMap<String, String>> aulist = null;
		aulist = applyService.selectApplyUser(applyId);
		if (aulist.size() > 0) {
			mv.addObject("au", aulist.get(0));
		} else {
			mv.addObject("au", null);
		}
		
		mv.addObject("aId", applyId);
		mv.setViewName("user-info");
		return mv;
	}
	
	/**
	 * 根据用户ID查询所有订单
	 * 
	 * @param userId
	 *            用户ID
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrdersLimited", method = RequestMethod.POST)
	public String getOrdersLimited(
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex,
			HttpServletRequest request) {
		List<HashMap<String, String>> orders = null;
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		try {
			orders = applyService.selectOrdersLimited(userId, startIndex,
					endIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(orders);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonfromList;
	}

	
	/**
	 * 查询任务的已确认的订单数量，用于页面判断是否多选
	 * 
	 * @param missionId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOrdersCountByM", method = RequestMethod.POST)
	@ResponseBody
	public String getOrdersCountByM(
			@RequestParam(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		Integer count = applyService.selectOrdersCountByM(missionId);
		return "" + count;
	}

	
	@RequestMapping(value = "/myservice-task-info/{missionId}")
	public ModelAndView mysendTaskInfo(@PathVariable("missionId") Integer missionId,
			ModelAndView mv,HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		
		HashMap<String, String> mdetails = null;
		mdetails = missionService.selectForMissionDetails(missionId);
		User user = userService.getUserById(user_id);
		
		System.out.println(mdetails);
		mv.addObject("mdetails", mdetails);
		mv.addObject("user", user);
		
		List<HashMap<String, String>> oulist = null;
		oulist = applyService.selectorderDetailsByUserIdAndMissionId(user_id, missionId);
		mv.setViewName("myservice-task-info");
		if (oulist.size() > 0) {
			mv.addObject("ou", oulist.get(0));
		}else{
			mv.addObject("ou", null);
		}
		
		Evaluate evaluate = evaluateService.selectByMissionId(missionId);
		mv.addObject("evaluate", evaluate);
		mv.setViewName("myservice-task-info");
		
		
		return mv;
	}
	

	
	
	@ResponseBody
	@RequestMapping(value="cancelorder/{applyId}", produces = "text/html;charset=UTF-8;")
	public String cancel(@PathVariable(value = "applyId") Integer applyId,
			HttpServletRequest request){
		// 1判断是否本人操作
		// 2改变订单状态
		ApplyOrders selectByPrimaryKey = applyService.selectByPrimaryKey(applyId);
		
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == selectByPrimaryKey.getUserId()) {
			selectByPrimaryKey.setTakeState(3);
			if(applyService.updateStateByApplyId(selectByPrimaryKey)){
				
				return "已取消订单";
			}
			return "系统出错";
		}
		return "操作违规";
	}
	
	@ResponseBody
	@RequestMapping(value = "/finishOrder/{applyId}", produces = "text/html;charset=UTF-8;")
	public String finishOrder(@PathVariable(value = "applyId") Integer applyId,
			HttpServletRequest request) {
		ApplyOrders selectByPrimaryKey = applyService.selectByPrimaryKey(applyId);
		
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == selectByPrimaryKey.getUserId()) {
			selectByPrimaryKey.setTakeState(2);
			if(applyService.updateStateByApplyId(selectByPrimaryKey)){
				//这里通知发布者和执行者
				return "已通知发布者，等待通过";
			}
			return "系统出错";
		}
		return "操作违规";
	}
	

	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveCommentChild(
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "applyId", required = true) Integer applyId,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");
		
		if(user_id == null || user_id == 0){
			json.put("result", "error");
			json.put("msg", "系统出错了");
			return json;
			
		}
		
		if(content == null || content == ""){
			json.put("result", "error");
			json.put("msg", "留言不能为空");
			return json;
			
		}
		
		ApplyOrders applyOrders = new ApplyOrders();
		applyOrders.setApplyId(applyId);
		applyOrders.setNote(content);

		if (applyService.updateNoteByPrimaryKey(applyOrders)) {
			json.put("result", "success");
			json.put("msg", "留言成功");
			return json;
		}

		json.put("result", "error");
		json.put("msg", "留言失败");

		return json;
	}
	
	
	//执行者对发布者评价
	@RequestMapping(value = "/evaluate/{missionId}")
	public ModelAndView evaluate(@PathVariable("missionId") Integer missionId,
			ModelAndView mv,HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		Evaluate evaluate = null;
		evaluate = evaluateService.selectByMissionId(missionId);
		if(evaluate == null){
			//生成对象
			evaluate = new Evaluate();
			evaluate.setExecutorId(user_id);
			evaluate.setMissionId(missionId);
			Integer publishId = missionService.getUserIdByMissionId(missionId);
			evaluate.setPublishId(publishId);
			
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
