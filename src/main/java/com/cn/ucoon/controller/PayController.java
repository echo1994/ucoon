package com.cn.ucoon.controller;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cn.ucoon.pojo.BalanceOrder;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.MissionOrders;
import com.cn.ucoon.pojo.wx.JsAPIConfig;
import com.cn.ucoon.pojo.wx.PayRefund;
import com.cn.ucoon.pojo.wx.PayRefundRespose;
import com.cn.ucoon.pojo.wx.SendRedPack;
import com.cn.ucoon.pojo.wx.SendRedPackRespose;
import com.cn.ucoon.pojo.wx.Template;
import com.cn.ucoon.pojo.wx.TemplateParam;
import com.cn.ucoon.pojo.wx.UnifiedOrderRespose;
import com.cn.ucoon.service.MissionOrderService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.MessageUtil;
import com.cn.ucoon.util.PayUtil;
import com.cn.ucoon.util.WeixinUtil;

@Controller
@RequestMapping("/pay")
public class PayController {

	@Resource
	private UserService userService;

	@Resource
	private MissionService missionService;

	@Resource
	private MissionOrderService missionOrdersService;

	// 退款 ：  管理员操作
	@ResponseBody
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	public JSONObject withdraw_cash(HttpServletRequest request, Model model,
			@RequestParam(value = "missionId", required = true) Integer missionId)
			throws Exception {
		JSONObject json = new JSONObject();
		Integer userId = (Integer) request.getSession().getAttribute("user_id");

		
		Mission mission = missionService.selectByPrimaryKey(missionId);
		MissionOrders orders = missionOrdersService.getOrdersbyMissionId(missionId);

		//判断订单状态是否是审核退款
		if (mission.getMissionStatus() != 2) {
			json.put("result_type", "error");
			json.put("msg", "改订单状态错误");
			return json;

		}
		String openId = userService.getOpenIdbyUserId(userId);
		int fee = mission.getMissionPrice().multiply(new BigDecimal(100)).intValue();

		PayRefund payRefund = new PayRefund();
		payRefund.setAppid(WeixinUtil.appid);
		payRefund.setMch_id(PayUtil.MCH_ID);
		payRefund.setNonce_str(PayUtil.makeUUID());
		payRefund.setOp_user_id(PayUtil.MCH_ID);
		payRefund.setOut_refund_no(orders.getMissionOrderNum());
		payRefund.setOut_trade_no(orders.getMissionOrderNum());
		payRefund.setRefund_fee(fee);
		payRefund.setTotal_fee(fee);
		payRefund.setSign(PayUtil.createPayRefundOrderSign(payRefund));
		// 判断是都已支付，防止重复支付
		// if(missionService.isPaid(mission.getMissionId())){
		//
		// json.put("result_type", "error");
		// json.put("msg", "请勿重复支付！");
		// return json;
		// }

		PayRefundRespose respose = PayUtil.payRefund(payRefund);

		// 修改订单状态
		Mission record = new Mission();
		//record.setOrderNum(orders.getOrderNum());
		// 根据微信文档return_code 和result_code都为SUCCESS的时候才会返回code_url
		if (null != respose && "SUCCESS".equals(respose.getReturn_code())
				&& "SUCCESS".equals(respose.getResult_code())) {
			json.put("result_type", "success");
			json.put("msg", "退款成功");

			// 改订单状态
			//record.setOrderState(3);

		} else {
			json.put("result_type", "error");
			json.put("msg", "退款失败");

			// 改订单状态
			//record.setOrderState(2);
		}
		//balanceService.changeOrderStateByOrderNum(record);
		return json;
	}

	// 支付预支付处理，生成prepayid编号，存放在session中，在支付时效验
	@RequestMapping("/prepay")
	public String prepay(Model model) {
		// prepayid格式 ：ucoon + 用户id + 6位随机数
		String userid = "1";
		Random random = new Random();
		int num = random.nextInt(100000);
		String prepayid = "ucoon" + userid + num;

		model.addAttribute("prepayid", prepayid);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		request.getSession().setAttribute("prepayid", prepayid);

		return "prepay";
	}

	// 微信支付
	@ResponseBody
	@RequestMapping("/getPay")
	public JSONObject pay() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		JSONObject json = new JSONObject();
		// String pre_prepay_id = (String)
		// request.getSession().getAttribute("prepayid");
		/*
		 * if(pre_prepay_id==null||prepay_id ==
		 * null||!prepay_id.equals(pre_prepay_id)){ json.put("result_type",
		 * "error"); json.put("msg", "支付失败"); return json; }
		 */
		Mission mission = (Mission) request.getSession()
				.getAttribute("mission");
		MissionOrders orders = (MissionOrders) request.getSession()
				.getAttribute("orders");
		// 判断是都已支付，防止重复支付
		if (missionService.isPaid(mission.getMissionId())) {

			json.put("result_type", "error");
			json.put("msg", "请勿重复支付！");
			return json;
		}
		json.put("result_type", "success");

		System.out.println("=============我是访问IP：" + getIpAddr(request)
				+ "==================");
		String ip = getIpAddr(request);
		String body = "有空ucoon-任务发布";
		int fee = mission.getMissionPrice().multiply(new BigDecimal(100))
				.intValue();
		System.out.println(fee);
		String notify_url = "http://wx.ucoon.cn/ucoon/pay/payresult";
		;
		String trade_type = "JSAPI";

		// 这里如果没有登录，跳转到登录界面
		int user_id = (int) request.getSession().getAttribute("user_id");
		System.out.println("user_id" + user_id);

		String openid = userService.getOpenIdbyUserId(user_id);

		String orderId = orders.getMissionOrderNum();
		System.out.println("本次订单:" + orderId);
		// 生成订单
		String orderInfo = PayUtil.createOrderInfo(orderId, ip, body, fee + "",
				notify_url, trade_type, openid);
		// 调统一下单API
		UnifiedOrderRespose unifiedOrderRespose = PayUtil.httpOrder(orderInfo);

		JsAPIConfig config = PayUtil.createPayConfig(unifiedOrderRespose
				.getPrepay_id());
		String jsconfig = JSON.toJSONString(config,
				SerializerFeature.WriteMapNullValue);
		json.put("msg", jsconfig);

		return json;
	}

	// 用于“我发布的”中支付
	@ResponseBody
	@RequestMapping("/getPay/{missionId}")
	public JSONObject pay2(@PathVariable(value = "missionId") Integer missionId)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		JSONObject json = new JSONObject();
		// 判断是都已支付，防止重复支付
		if (missionService.isPaid(missionId)) {

			json.put("result_type", "error");
			json.put("msg", "请勿重复支付！");
			return json;
		}

		Mission mission = missionService.selectByPrimaryKey(missionId);
		MissionOrders orders = missionOrdersService
				.getOrdersbyMissionId(missionId);

		json.put("result_type", "success");

		System.out.println("=============我是访问IP：" + getIpAddr(request)
				+ "==================");
		String ip = getIpAddr(request);
		String body = "有空ucoon-任务发布";
		int fee = mission.getMissionPrice().multiply(new BigDecimal(100))
				.intValue();
		System.out.println(fee);
		String notify_url = "http://wx.ucoon.cn/ucoon/pay/payresult";
		;
		String trade_type = "JSAPI";

		// 这里如果没有登录，跳转到登录界面
		int user_id = (int) request.getSession().getAttribute("user_id");
		System.out.println("user_id" + user_id);

		String openid = userService.getOpenIdbyUserId(user_id);

		String orderId = orders.getMissionOrderNum();
		System.out.println("本次订单:" + orderId);
		// 生成订单
		String orderInfo = PayUtil.createOrderInfo(orderId, ip, body, fee + "",
				notify_url, trade_type, openid);
		// 调统一下单API
		UnifiedOrderRespose unifiedOrderRespose = PayUtil.httpOrder(orderInfo);

		JsAPIConfig config = PayUtil.createPayConfig(unifiedOrderRespose
				.getPrepay_id());
		String jsconfig = JSON.toJSONString(config,
				SerializerFeature.WriteMapNullValue);
		json.put("msg", jsconfig);

		return json;
	}

	/**
	 * 微信支付回调页面
	 * 
	 * @Title: wechatPayNotify
	 * @Description: TODO
	 * @param @param request
	 * @param @param trade_status
	 * @param @param out_trade_no
	 * @param @param trade_no
	 * @return void
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/payresult")
	public String payResult(HttpServletRequest request) {

		try {
			Map<String, String> map = new MessageUtil().parseXml(request);
			if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				// 这里写成功后的业务逻辑： 改订单状态，发模板消息等
				String orderId = map.get("out_trade_no");

				MissionOrders record = new MissionOrders();

				// 改订单状态
				record.setMissionOrderNum(orderId);
				missionOrdersService.updateMissionStatusbyOrdersId(record);

				System.out.println("收到支付结果订单：" + orderId);
				String time = map.get("time_end");
				String total_fee = map.get("total_fee");
				String openid = map.get("openid");
				if (map.get("is_subscribe").equalsIgnoreCase("Y")) {
					Template tem = new Template();
					tem.setTemplateId("azcHIxhzpMgkzMvYM2kdmRxrf4ciwII2FTp9dRitoms");
					tem.setTopColor("#00DD00");
					tem.setToUser(openid);
					tem.setUrl("http://wx.ucoon.cn/ucoon/"); // 到时候改为任务列表

					List<TemplateParam> paras = new ArrayList<TemplateParam>();
					paras.add(new TemplateParam("first", "任务订单支付成功，已发布到任务大厅",
							"#FF3333"));
					paras.add(new TemplateParam("keyword1",
							strToDateLong(time), "#0044BB"));
					paras.add(new TemplateParam("keyword2", "任务名称", "#0044BB"));
					paras.add(new TemplateParam("keyword3", (Double
							.valueOf(total_fee) / 100) + "", "#0044BB"));
					paras.add(new TemplateParam("keyword4", "支付成功", "#0044BB"));
					paras.add(new TemplateParam("remark", "感谢您的使用", "#0044BB"));
					tem.setTemplateParamList(paras);

					boolean result = WeixinUtil.sendTemplateMsg(tem);
					System.out.println("模板消息结果：" + result);

				}

				// orderService.updateConfirm(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PayUtil.getPayCallback();
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) throws Exception {
		String ip = "";
		ip = request.getHeader("X-Real-IP");
		if (ip != null && !ip.equals("") && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (ip != null && !ip.equals("") && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	public String strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		String dateString = formatter2.format(strtodate);
		return dateString;
	}

}
