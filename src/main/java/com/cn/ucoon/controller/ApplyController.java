package com.cn.ucoon.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.cn.ucoon.pojo.ApplyOrders;
import com.cn.ucoon.pojo.Balance;
import com.cn.ucoon.pojo.Evaluate;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.pojo.wx.Template;
import com.cn.ucoon.pojo.wx.TemplateParam;
import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.BalanceService;
import com.cn.ucoon.service.EvaluateService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.PayUtil;
import com.cn.ucoon.util.TimeUtil;
import com.cn.ucoon.util.WeixinUtil;
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
	private BalanceService balanceService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/addAppliment", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addAppliment(
			@RequestParam(value = "missionId") Integer missionId,
			@RequestParam(value = "msg") String msg,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		List<HashMap<String, Object>> applys = null;
		applys = applyService.selectApplybyUMID(userId, missionId);
		Mission mission = missionService.selectByPrimaryKey(missionId);
		JSONObject json = new JSONObject();
		if (mission.getUserId() == userId) {
			json.put("result", "error");
			json.put("msg", "不能领取自己的任务");
			return json;
		}

		// 这里要判断是否是可执行的任务 现在的时间 + ? 《 截止时间
		if (mission.getEndTime().getTime() < new Date().getTime()) {

			json.put("result", "error");
			json.put("msg", "该任务已截止");
			return json;
		}

		String openId = userService.getOpenIdbyUserId(mission.getUserId());
		User user = userService.getUserById(userId);
		if (applys == null || applys.size() == 0) {
			ApplyOrders applyOrders = new ApplyOrders();
			applyOrders.setMissionId(missionId);
			applyOrders.setOrderNum(PayUtil.getOrdersNum(userId,
					mission.getMissionId()));
			applyOrders.setTakeState(0);
			applyOrders.setTakeTime(new Date());
			applyOrders.setUserId(userId);
			applyOrders.setNote(msg);

			if (applyService.saveOrders(applyOrders)) {

				json.put("result", "success");
				json.put("msg", "申请成功，等待雇主审核");

				// 发模板消息给雇主
				Template tem = new Template();
				tem.setTemplateId("kFCpvy-xYwETnjjAhMe8If9G8wIDutJBymqlWXzpkrg");
				tem.setTopColor("#00DD00");
				tem.setToUser(openId);
				tem.setUrl("http://wx.ucoon.cn/mysend"); // 发布列表

				List<TemplateParam> paras = new ArrayList<TemplateParam>();
				paras.add(new TemplateParam("first", "您发布的'"
						+ mission.getMissionTitle() + "'有人接单啦", "#FF3333"));
				paras.add(new TemplateParam("keyword1", user.getNickName(),
						"#0044BB"));
				int timeStamp = TimeUtil.timeStamp();
				String date = TimeUtil.timeStamp2Date(
						String.valueOf(timeStamp), "yyyy-MM-dd HH:mm:ss");
				paras.add(new TemplateParam("keyword2", date, "#0044BB"));
				paras.add(new TemplateParam("remark", "点击查看详情", "#0044BB"));
				tem.setTemplateParamList(paras);

				boolean result = WeixinUtil.sendTemplateMsg(tem);
				System.out.println("接单模板消息结果：" + result);

				return json;

			} else {
				json.put("result", "error");
				json.put("msg", "申请失败，请重试");
				return json;
			}
		} else {
			// 0待确认 1已确认 2.已完成 3. 已取消 4. 未被选上
			boolean flag = false;
			for (int i = 0; i < applys.size(); i++) {
				if ((int) applys.get(i).get("take_state") != 3) {
					flag = true;
				}
			}

			if (flag) {
				json.put("result", "error");
				json.put("msg", "您已申请过此任务");
				return json;
			}
			ApplyOrders applyOrders = new ApplyOrders();
			applyOrders.setMissionId(missionId);
			applyOrders.setOrderNum(PayUtil.getOrdersNum(userId,
					mission.getMissionId()));
			applyOrders.setTakeState(0);
			applyOrders.setTakeTime(new Date());
			applyOrders.setUserId(userId);
			applyOrders.setNote(msg);

			if (applyService.saveOrders(applyOrders)) {

				json.put("result", "success");
				json.put("msg", "申请成功，等待雇主审核");

				// 发模板消息给雇主
				Template tem = new Template();
				tem.setTemplateId("kFCpvy-xYwETnjjAhMe8If9G8wIDutJBymqlWXzpkrg");
				tem.setTopColor("#00DD00");
				tem.setToUser(openId);
				tem.setUrl("http://wx.ucoon.cn/mysend"); // 发布列表

				List<TemplateParam> paras = new ArrayList<TemplateParam>();
				paras.add(new TemplateParam("first", "您发布的'"
						+ mission.getMissionTitle() + "'有人接单啦", "#FF3333"));
				paras.add(new TemplateParam("keyword1", user.getNickName(),
						"#0044BB"));
				int timeStamp = TimeUtil.timeStamp();
				String date = TimeUtil.timeStamp2Date(
						String.valueOf(timeStamp), "yyyy-MM-dd HH:mm:ss");
				paras.add(new TemplateParam("keyword2", date, "#0044BB"));
				paras.add(new TemplateParam("remark", "点击查看详情", "#0044BB"));
				tem.setTemplateParamList(paras);

				boolean result = WeixinUtil.sendTemplateMsg(tem);
				System.out.println("接单模板消息结果：" + result);
				return json;

			} else {
				json.put("result", "error");
				json.put("msg", "申请失败，请重试");
				return json;
			}
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
	// @RequestMapping(value = "/getApplybyUMID", method = RequestMethod.POST)
	// @ResponseBody
	// public String selectOrderbyUMID(
	// @RequestParam(value = "forjudge", required = true) boolean judge,
	// @RequestParam(value = "missionId", required = true) Integer missionId,
	// HttpServletRequest request) {
	// Integer userId = (Integer) request.getSession().getAttribute("user_id");
	// List<HashMap<String, String>> applys = null;
	// if (judge == true) {// 只用于判断不返回数据
	// applys = applyService.selectApplybyUMID(userId, missionId);
	// if (applys == null || applys.size() == 0) {
	// return "0";// 返回0，表明用户(userId)没接该任务
	// } else {
	// return "1";// 返回1，表明用户(userId)已经接了该任务
	// }
	// } else {
	// // 返回订单数据
	// }
	// return "";
	// }

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
	public ModelAndView mysendTaskInfo(
			@PathVariable("missionId") Integer missionId, ModelAndView mv,
			HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");

		HashMap<String, Object> mdetails = null;
		mdetails = missionService.selectForMissionDetails(missionId);
		User user = userService.getUserById(user_id);

		System.out.println(mdetails);
		mv.addObject("mdetails", mdetails);
		mv.addObject("user", user);

		List<HashMap<String, String>> oulist = null;
		oulist = applyService.selectorderDetailsByUserIdAndMissionId(user_id,
				missionId);
		mv.setViewName("myservice-task-info");
		if (oulist.size() > 0) {
			mv.addObject("ou", oulist.get(0));
		} else {
			mv.addObject("ou", null);
		}

		Evaluate evaluate = evaluateService.selectByMidAndPidAndEid(missionId, (Integer)mdetails.get("user_id"), user_id);
		mv.addObject("evaluate", evaluate);
		mv.setViewName("myservice-task-info");

		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "cancelorder/{applyId}", produces = "text/html;charset=UTF-8;")
	public String cancel(@PathVariable(value = "applyId") Integer applyId,
			HttpServletRequest request) {
		// 1判断是否本人操作
		// 2改变订单状态
		ApplyOrders selectByPrimaryKey = applyService
				.selectByPrimaryKey(applyId);

		Integer cuserId = (Integer) request.getSession()
				.getAttribute("user_id");
		if (cuserId != null && cuserId == selectByPrimaryKey.getUserId()) {
			selectByPrimaryKey.setTakeState(3);
			if (applyService.updateStateByApplyId(selectByPrimaryKey)) {

				return "已取消订单";
			}
			return "系统出错";
		}
		return "操作违规";
	}

	@ResponseBody
	@RequestMapping(value = "confirmorder/{missionId}")
	public JSONObject confirm(@PathVariable(value = "missionId") Integer missionId,
			@RequestParam(value = "userId") Integer userId,
			HttpServletRequest request) {
		// 1判断是否本人操作
		// 2改变订单状态为2 和任务状态 5
		// 3通知以及分钱
		JSONObject json = new JSONObject();
		Mission mission = missionService.selectByPrimaryKey(missionId);

		Integer cuserId = (Integer) request.getSession()
				.getAttribute("user_id");
		if (cuserId != null && cuserId == mission.getUserId()) {
			
			ApplyOrders applyOrders = applyService.selectApplybyUserIdAndMissionId(userId, missionId);
			applyOrders.setTakeState(2);
			if (applyService.updateStateByApplyId(applyOrders)) {
				
				String openId = userService.getOpenIdbyUserId(userId);
				
				//-------------（目前不采用）如果为最后一个人，则任务状态改变为5
				
				//每个人获得的金额 = （任务单价）*平台抽取的服务率 平台抽取 8%
				BigDecimal singleMonney = mission.getMissionPrice();
				
				//往上进位 BigDecimal.ROUND_HALF_UP
		        BigDecimal result = singleMonney.multiply(new BigDecimal((1 - PayUtil.rate))).setScale(2, BigDecimal.ROUND_HALF_UP);
				Balance orders = new Balance();
				orders.setQuantity(result);
				orders.setOrderNum(PayUtil.getOrdersNum(userId, userId));
				orders.setOrderState(1);
				orders.setConsumingRecords("任务'" + mission.getMissionTitle() + "'的佣金");
				orders.setUserId(userId);
				orders.setPlusOrMinus("plus");
				orders.setConsumingTime(new Date());

				if (!balanceService.insertBalanceOrder(orders)) {
					
					json.put("result", "error");
					json.put("msg", "系统出错");
					return json;

				}
				
				Template tem = new Template();
				tem.setTemplateId("Rus1oqq_liorguwFs5e3ZS5nQc9NOjB-wjIY2yWk5Bw");
				tem.setTopColor("#00DD00");
				tem.setToUser(openId);
				tem.setUrl("http://wx.ucoon.cn/applyOrders/evaluate/" + missionId); // 评价

				List<TemplateParam> paras = new ArrayList<TemplateParam>();
				paras.add(new TemplateParam("first", "你好，你服务的任务雇主已确认完成", "#FF3333"));
				paras.add(new TemplateParam("keyword1", mission.getMissionTitle(),
						"#0044BB"));
				paras.add(new TemplateParam("keyword2", "暂无",
						"#0044BB"));
				int timeStamp = TimeUtil.timeStamp();
				String date = TimeUtil.timeStamp2Date(
						String.valueOf(timeStamp), "yyyy-MM-dd HH:mm:ss");
				paras.add(new TemplateParam("keyword3", date, "#0044BB"));
				paras.add(new TemplateParam("remark", "你的佣金" + result + "元已到达余额(平台服务费抽取8%)，在【个人中心】->【财富中心】中查看（没有转入的话，请联系客服）\\n点击对雇主进行评论", "#0044BB"));
				tem.setTemplateParamList(paras);

				boolean result2 = WeixinUtil.sendTemplateMsg(tem);
				System.out.println("雇主确认任务 模板消息结果：" + result2);
				
				
				
				
				json.put("result", "success");
				json.put("msg", "操作成功");
				return json;
			}
			json.put("result", "error");
			json.put("msg", "系统出错");
			return json;
		}
		json.put("result", "error");
		json.put("msg", "操作违规");
		return json;
	}

	@RequestMapping(value = "/finishOrder")
	public ModelAndView finishOrder(
			@RequestParam(value = "missionDoneDetail", required = false) String missionDoneDetail,
			@RequestParam(value = "imgUpload", required = false) MultipartFile[] file,
			@RequestParam(value = "applyId", required = false) Integer applyId,
			HttpServletRequest request, ModelAndView mv) throws ParseException {

		String path = ImageController.APPLYORDERS_IMAGE_LOCATION;
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		String timestamp = String.valueOf(System.currentTimeMillis());
		String uuid = String.valueOf(UUID.randomUUID());
		uuid = uuid.replace("-", "");
		String realpath = path + "/" + userId + timestamp + uuid;// 文件夹位置
		
		Integer fileLength = 0;
		
		
		for (int i = 0; i < file.length; i++) {
			if (!file[i].isEmpty()) {
				fileLength++;
			}
		}
		if(fileLength > 0){
			File dir = new File(realpath);
			dir.mkdirs();
		}
		System.out.println("wenjian:" + fileLength);
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

		ApplyOrders cApplyOrders = applyService.selectByPrimaryKey(applyId);
		if (userId != null && userId == cApplyOrders.getUserId()
				&& cApplyOrders.getTakeState() != 4) {
			ApplyOrders applyOrders = new ApplyOrders();

			applyOrders.setApplyId(applyId);
			applyOrders.setFinishTime(new Date());
			applyOrders.setPicCount(fileLength);
			applyOrders.setPictures(userId + timestamp + uuid);
			applyOrders.setTakeState(4);
			applyOrders.setApplyDetail(missionDoneDetail);

			if (applyService.updateDoneByPrimaryKey(applyOrders)) {
				// 这里通知发布者和执行者
				// 发模板消息给雇主

				Integer missionId = cApplyOrders.getMissionId();
				Mission mission = missionService.selectByPrimaryKey(missionId);
				// 雇主openid
				String openId = userService.getOpenIdbyUserId(mission
						.getUserId());

				// 执行
				User user = userService.getUserById(userId);

				Template tem = new Template();
				tem.setTemplateId("Rus1oqq_liorguwFs5e3ZS5nQc9NOjB-wjIY2yWk5Bw");
				tem.setTopColor("#00DD00");
				tem.setToUser(openId);
				tem.setUrl("http://wx.ucoon.cn/mission/order-info/" + missionId); // 发布列表

				List<TemplateParam> paras = new ArrayList<TemplateParam>();
				paras.add(new TemplateParam("first", "'" + user.getNickName()
						+ "'完成了您发布的'" + mission.getMissionTitle() + "'，请尽快去确认",
						"#FF3333"));
				paras.add(new TemplateParam("keyword1", mission
						.getMissionTitle(), "#0044BB"));
				int timeStamp = TimeUtil.timeStamp();
				String date = TimeUtil.timeStamp2Date(
						String.valueOf(timeStamp), "yyyy-MM-dd HH:mm:ss");
				paras.add(new TemplateParam("keyword2", "无", "#0044BB"));
				paras.add(new TemplateParam("keyword3", date, "#0044BB"));
				paras.add(new TemplateParam("remark", "点击查看详情", "#0044BB"));
				tem.setTemplateParamList(paras);

				boolean result = WeixinUtil.sendTemplateMsg(tem);
				System.out.println("接单模板消息结果：" + result);
				request.getSession().setAttribute("msg", "已通知发布者，等待通过");
				request.getSession().setAttribute("url", "myservice");
				mv.setViewName("redirect:/tip");
				return mv;
			}
			request.getSession().setAttribute("msg", "系统出错");
			request.getSession().setAttribute("url", "myservice");
			mv.setViewName("redirect:/tip");
			return mv;
		}
		request.getSession().setAttribute("msg", "请勿重复提交");
		request.getSession().setAttribute("url", "myservice");
		mv.setViewName("redirect:/tip");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "chosePeople/{applyId}", produces = "text/html;charset=UTF-8;")
	public String chosePeople(@PathVariable(value = "applyId") Integer applyId,
			HttpServletRequest request) {
		// 1判断是否发布者操作
		// 2改变订单状态
		ApplyOrders selectByPrimaryKey = applyService
				.selectByPrimaryKey(applyId);
		Integer missionId = selectByPrimaryKey.getMissionId();
		Mission mission = missionService.selectByPrimaryKey(missionId);
		Integer cuserId = (Integer) request.getSession()
				.getAttribute("user_id");
		if (cuserId != null && cuserId == mission.getUserId()) {
			selectByPrimaryKey.setTakeState(1);
			if (applyService.updateStateByApplyId(selectByPrimaryKey)) {

				return "success";
			}
			return "error";
		}
		return "操作违规";
	}

	@ResponseBody
	@RequestMapping(value = "cancelPeople/{applyId}", produces = "text/html;charset=UTF-8;")
	public String cancelPeople(
			@PathVariable(value = "applyId") Integer applyId,
			HttpServletRequest request) {
		// 1判断是否发布者操作
		// 2改变订单状态
		ApplyOrders selectByPrimaryKey = applyService
				.selectByPrimaryKey(applyId);
		Integer missionId = selectByPrimaryKey.getMissionId();
		Mission mission = missionService.selectByPrimaryKey(missionId);
		Integer cuserId = (Integer) request.getSession()
				.getAttribute("user_id");
		if (cuserId != null && cuserId == mission.getUserId()) {
			selectByPrimaryKey.setTakeState(0);
			if (applyService.updateStateByApplyId(selectByPrimaryKey)) {

				return "success";
			}
			return "error";
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

		if (user_id == null || user_id == 0) {
			json.put("result", "error");
			json.put("msg", "系统出错了");
			return json;

		}

		if (content == null || content == "") {
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

	// 执行者对发布者评价
	@RequestMapping(value = "/evaluate/{missionId}")
	public ModelAndView evaluate(@PathVariable("missionId") Integer missionId,
			ModelAndView mv, HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");
		
		Mission mission = missionService.selectByPrimaryKey(missionId);
		
		Evaluate evaluate = null;
		evaluate = evaluateService.selectByMidAndPidAndEid(missionId, mission.getUserId(), user_id);
		if (evaluate == null) {
			// 生成对象
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

		if (user_id == null || user_id == 0) {
			json.put("result", "error");
			json.put("msg", "系统出错了");
			return json;

		}

		if (score == null || score == 0) {
			json.put("result", "error");
			json.put("msg", "分数不能为0");
			return json;

		}

		Mission mission = missionService.selectByPrimaryKey(missionId);
		
		Evaluate evaluate = new Evaluate();

		evaluate.setEpevaluateTime(new Date());
		evaluate.setExecutorEvaluate(content);
		evaluate.setExecutorScore(score);
		evaluate.setMissionId(missionId);
		evaluate.setPublishId(mission.getUserId());
		evaluate.setExecutorId(user_id);

		// 更新评价表

		Evaluate cEvaluate = evaluateService.selectByMidAndPidAndEid(missionId, mission.getUserId(), user_id);
		if(cEvaluate == null){
			
			evaluateService.insertEvaluate(evaluate);
		}else{
			if(cEvaluate.getExecutorScore() != null){
			
				json.put("result", "success");
				json.put("msg", "请勿重复评价");

				return json;
			}
			
			if(!evaluateService.updateExecutorByMidAndPidAndEid(evaluate)){
				json.put("result", "error");
				json.put("msg", "评价失败");
				return json;
			}
		}
		
		json.put("result", "success");
		json.put("msg", "评价成功");
		

		return json;
	}
}
