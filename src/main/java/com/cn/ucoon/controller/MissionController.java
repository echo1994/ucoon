package com.cn.ucoon.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
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

import com.cn.ucoon.pojo.Balance;
import com.cn.ucoon.pojo.Evaluate;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.MissionAddress;
import com.cn.ucoon.pojo.MissionOrders;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.pojo.wx.Template;
import com.cn.ucoon.pojo.wx.TemplateParam;
import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.BalanceService;
import com.cn.ucoon.service.EvaluateService;
import com.cn.ucoon.service.MissionOrderService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.PayUtil;
import com.cn.ucoon.util.WeixinUtil;
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
	
	@Resource
	private BalanceService balanceService;
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
		
		
		//System.out.println("wenjian:" + fileLength);
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
		
		/*String reg = "[\u4e00-\u9fa5]";  
        Pattern pat = Pattern.compile(reg);    
        Matcher mat = pat.matcher(time);   
        String repickStr = mat.replaceAll(""); */
		String repickStr = time.replace("小时后", "");
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
		mission.setPicCount(fileLength);
		MissionOrders missionOrders = new MissionOrders();
		if(missionService.publishMission(mission)){
			
			
			//判断地址是否已存在
			if(!missionService.isAddressExist(place)){
				
				MissionAddress address = new MissionAddress();
				address.setPlace(place);
				address.setMissionLat(missionLat);
				address.setMissionLng(missionLng);
				address.setUserId(userId);
				missionService.addMissionAddress(address);
			}
			
			
			
			//System.out.println("mission_id:" + mission.getMissionId());
			
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
	
	@RequestMapping(value = "/mission_pay/{missionId}")
	public ModelAndView missionPay(@PathVariable("missionId") Integer missionId,
			ModelAndView mv,HttpServletRequest request) {
		//Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		Mission mission = missionService.selectByPrimaryKey(missionId);
		MissionOrders missionOrders = missionOrderService.getOrdersbyMissionId(missionId);
		
		request.getSession().setAttribute("orders", missionOrders);
		request.getSession().setAttribute("mission", mission);
		mv.setViewName("redirect:/mission-pay");
		
		return mv;
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
				list.add(6);
				
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
			return "管理员GG正在审核退款，24小时内将退款至余额中，请注意查收。如有需要请拨打GG电话";
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
			missionService.updateByPrimaryKey(mission);
			
			MissionOrders orders = missionOrderService.getOrdersbyMissionId(missionId);
			orders.setFinishTime(new Date());
			missionOrderService.update(orders);
			
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
			HttpServletRequest request) throws Exception {
		// 1判断是否本人操作
		// 2改变任务状态
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		Integer cuserId = (Integer) request.getSession().getAttribute("user_id");
		if (cuserId != null && cuserId == userId) {
			Mission mission = missionService.selectByPrimaryKey(missionId);
			mission.setMissionStatus(6);
			if(missionService.updateByPrimaryKey(mission) > 0){
				//这里发模板消息
				List<HashMap<String, Object>> selectselectedpeople = applyService.selectselectedpeople(missionId);
				Integer allPeople = mission.getPeopleCount();
				
				if(allPeople != selectselectedpeople.size()){
					//需要退款
					//退款人数
					Integer refundPeople = allPeople - selectselectedpeople.size();
					/*
					//订单总金额
					Integer tolFee = mission.getMissionPrice()
							.multiply(new BigDecimal(100))
							.multiply(new BigDecimal(allPeople)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
					
					//退款金额
					Integer fee = mission.getMissionPrice()
							.multiply(new BigDecimal(100))
							.multiply(new BigDecimal(refundPeople)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				
					
					//商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
					//微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额。 
					String out_refund_no = PayUtil.getOrdersNum(userId, userId);
					
					PayRefund payRefund = new PayRefund();
					payRefund.setAppid(WeixinUtil.appid);
					payRefund.setMch_id(PayUtil.MCH_ID);
					payRefund.setNonce_str(PayUtil.makeUUID());
					payRefund.setOut_refund_no(out_refund_no);
					payRefund.setOut_trade_no(missionOrders.getMissionOrderNum());
					payRefund.setRefund_fee(fee);
					payRefund.setTotal_fee(tolFee); //订单总金额
					payRefund.setOp_user_id(PayUtil.MCH_ID);
					payRefund.setSign(PayUtil.createPayRefundOrderSign(payRefund));
					PayRefundRespose respose = PayUtil.payRefund(payRefund);

					// 根据微信文档return_code 和result_code都为SUCCESS的时候才会返回code_url
					if (null != respose && "SUCCESS".equals(respose.getReturn_code())
							&& "SUCCESS".equals(respose.getResult_code())) {*/
					//MissionOrders missionOrders = missionOrderService.getOrdersbyMissionId(missionId);
					String out_refund_no = PayUtil.getOrdersNum(userId, userId);	
					Balance orders = new Balance();
					orders.setQuantity(mission.getMissionPrice().multiply(new BigDecimal(refundPeople)).setScale(2, BigDecimal.ROUND_HALF_UP));
					orders.setOrderNum(out_refund_no);
					orders.setOrderState(4);// 退款
					orders.setConsumingRecords("退款金额，任务编号:" + missionId);
					orders.setUserId(userId);
					orders.setPlusOrMinus("plus");
					orders.setConsumingTime(new Date());;
					String openId = userService.getOpenIdbyUserId(userId);
					if(balanceService.insertBalanceOrder(orders)){
						Template tem = new Template();
						tem.setTemplateId("FrSuqhbz7PYYCpafyJoDVAf2li6js_R_wi3TIjeXztw");
						tem.setTopColor("#00DD00");
						tem.setToUser(openId);
						tem.setUrl("http://wx.ucoon.cn/wealth"); //我服务的

						List<TemplateParam> paras = new ArrayList<TemplateParam>();
						paras.add(new TemplateParam("first", "您好，您的任务剩余名额" + refundPeople + "人的金额，已退款至你的余额中。",
								"#FF3333"));
						paras.add(new TemplateParam("reason","任务已达所需名额", "#0044BB"));
						paras.add(new TemplateParam("refund", mission.getMissionPrice().multiply(new BigDecimal(refundPeople)).setScale(2, BigDecimal.ROUND_HALF_UP) + "元", "#0044BB"));
						paras.add(new TemplateParam("remark", "点击查看详情 \\n\\n备注：如有疑问，请致电13063032247联系我们。", "#0044BB"));
						tem.setTemplateParamList(paras);

						boolean result = WeixinUtil.sendTemplateMsg(tem);
						System.out.println("退款模板消息结果：" + result);
					}else{
						
						Template tem = new Template();
						tem.setTemplateId("FrSuqhbz7PYYCpafyJoDVAf2li6js_R_wi3TIjeXztw");
						tem.setTopColor("#00DD00");
						tem.setToUser(openId);
						tem.setUrl("http://wx.ucoon.cn/wealth"); //我服务的

						List<TemplateParam> paras = new ArrayList<TemplateParam>();
						paras.add(new TemplateParam("first", "退款失败，请拨打电话13063032247，联系客服",
								"#FF3333"));
						paras.add(new TemplateParam("reason","退款失败", "#0044BB"));
						paras.add(new TemplateParam("refund", mission.getMissionPrice().multiply(new BigDecimal(refundPeople)).setScale(2, BigDecimal.ROUND_HALF_UP) + "元", "#0044BB"));
						paras.add(new TemplateParam("remark", "点击查看详情 \\n\\n备注：如有疑问，请致电13063032247联系我们。", "#0044BB"));
						tem.setTemplateParamList(paras);

						boolean result = WeixinUtil.sendTemplateMsg(tem);
						System.out.println("退款模板消息结果：" + result);
					
					}
						
						
						
				}


						
				
				for (int i = 0; i < selectselectedpeople.size(); i++) {
					Integer toUserId = (Integer) selectselectedpeople.get(i).get("user_id");
					String openId = userService.getOpenIdbyUserId(toUserId);
					
					SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日HH点");
					String sDate=sdf.format(mission.getEndTime());
					
					Template tem = new Template();
					tem.setTemplateId("j0aLsa1V_oRpRP3qmkpy56JVrYyfRBvgS324LZK4CDA");
					tem.setTopColor("#00DD00");
					tem.setToUser(openId);
					tem.setUrl("http://wx.ucoon.cn/myservice"); //我服务的

					List<TemplateParam> paras = new ArrayList<TemplateParam>();
					paras.add(new TemplateParam("first", "你好，雇主已通过你对'" + mission.getMissionTitle() + "'任务的申请",
							"#FF3333"));
					paras.add(new TemplateParam("keyword1",
							mission.getMissionTitle(), "#0044BB"));
					paras.add(new TemplateParam("keyword2", "请在" + sDate + "前完成任务", "#0044BB"));
					paras.add(new TemplateParam("remark", "点击查看详情", "#0044BB"));
					tem.setTemplateParamList(paras);

					boolean result = WeixinUtil.sendTemplateMsg(tem);
					System.out.println("选完人模板消息结果：" + result);
					
				}
			}
			
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
	
	@RequestMapping(value = "/order-info/{missionId}")
	public ModelAndView mysendTaskInfo(@PathVariable("missionId") Integer missionId,
			ModelAndView mv,HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		
		HashMap<String, Object> mdetails = missionService.selectForMissionDetails(missionId);
		if(user_id == mdetails.get("user_id")){
			
			List<HashMap<String, Object>> list = null;
			switch ((int)mdetails.get("mission_status")) {
			case 1:
				//表示已支付 待选人 任务状态1
				list = applyService.selectDetailByMissionId2(missionId);
				break;

			default:
				//任务状态 5（已完成，待评价） 和6（正在进行）  
				list = applyService.selectDetailByMissionId(missionId);
				break;
			}
			
			System.out.println(list);
			mv.addObject("mdetails", mdetails);
			mv.addObject("list", list);
			mv.setViewName("order-info");
		}else{
			
			mv.setViewName("404");
		}
		
		
		
		
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
		
		Integer status = (Integer) mdetails.get("mission_status");
		
		if(status != 1){
			
			mv.setViewName("redirect:/mysend");
			return mv;
			
		}
		
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
		
		
		//取出被成功选择的人
		List<HashMap<String, Object>> list = this.applyService.selectEvaluatepeople(missionId);
		
		System.out.println(list);
		//User user = userService.getUserById(evaluate.getPublishId());
		
		//mv.addObject("evaluate", evaluate);
		mv.addObject("list", list);
		mv.addObject("missionId", missionId);
		mv.setViewName("evaluate_publish");
		
		
		return mv;
	}
	
	
	@RequestMapping(value = "/addEvaluate", method = RequestMethod.POST)
	public String addEvaluate(
			@RequestParam(value = "content", required = true) String[] content,
			@RequestParam(value = "userId", required = true) Integer[] userId,
			@RequestParam(value = "score", required = true) Float[] score,
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		
		Integer publish_id = (Integer) request.getSession()
				.getAttribute("user_id");
		
		if(score.length == 0){
			request.getSession().setAttribute("msg", "请选择星星");
			request.getSession().setAttribute("url", "mission/evaluate_publish/" + missionId);
			return "redirect:/tip";
		}
		
		for (int i = 0; i < score.length; i++) {
			
			Evaluate evaluate = new Evaluate();
			//System.out.println(content[i]);
			evaluate.setPeevaluateTime(new Date());
			if(content.length != 0){
				
				evaluate.setPublishEvaluate(content[i]);
			}
			
			evaluate.setPublishScore(score[i]);
			evaluate.setMissionId(missionId);
			evaluate.setPublishId(publish_id);
			evaluate.setExecutorId(userId[i]);
			
			
			//更新评价表
			Evaluate cEvaluate = evaluateService.selectByMidAndPidAndEid(missionId, publish_id, userId[i]);
			if(cEvaluate == null){
				
				evaluateService.insertEvaluate(evaluate);
			}else{
				if(cEvaluate.getPublishScore() != null){
					request.getSession().setAttribute("msg", "请勿重复评价");
					request.getSession().setAttribute("url", "mission/order-info/" + missionId);
					return "redirect:/tip";
				}
				if(!evaluateService.updatePublishByMidAndPidAndEid(evaluate)){
					
					
					request.getSession().setAttribute("msg", "评价失败");
					request.getSession().setAttribute("url", "mission/evaluate_publish/" + missionId);
					return "redirect:/tip";
				}
			}
			
//			if () {
//				json.put("result", "success");
//				json.put("msg", "评价成功");
//				
//				return json;
//			}
		}
		
		
//		if(publish_id == null || publish_id == 0){
//			json.put("result", "error");
//			json.put("msg", "系统出错了");
//			return json;
//			
//		}
//		
//		if(score == null || score == 0){
//			json.put("result", "error");
//			json.put("msg", "分数不能为0");
//			return json;
//			
//		}
		request.getSession().setAttribute("msg", "评价成功");
		request.getSession().setAttribute("url", "mission/order-info/" + missionId);
		return "redirect:/tip";
	}
	
	
	/**
	 * 分页查询
	 * 
	 * @param missionId
	 *            通过missionId加载
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @return json
	 */
	@RequestMapping(value = "/getEvaluateLimited", method = RequestMethod.POST)
	@ResponseBody
	public String getMissionsLimited(
			@RequestParam(value = "publishId", required = true) Integer publishId,
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex,
			HttpServletRequest request) {
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
