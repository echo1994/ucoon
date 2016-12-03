package com.cn.ucoon.controller;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cn.ucoon.pojo.Balance;
import com.cn.ucoon.pojo.wx.JsAPIConfig;
import com.cn.ucoon.pojo.wx.SendRedPack;
import com.cn.ucoon.pojo.wx.SendRedPackRespose;
import com.cn.ucoon.pojo.wx.Template;
import com.cn.ucoon.pojo.wx.TemplateParam;
import com.cn.ucoon.pojo.wx.UnifiedOrderRespose;
import com.cn.ucoon.service.BalanceService;
import com.cn.ucoon.service.CreditsService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.MessageUtil;
import com.cn.ucoon.util.PayUtil;
import com.cn.ucoon.util.WeixinUtil;

@Controller
@RequestMapping("/wealth")
public class WealthController {

	@Resource
	private UserService userService;

	@Resource
	private MissionService missionService;

	@Resource
	private BalanceService balanceService;

	@Resource
	private CreditsService creditsService;

	@RequestMapping(value = "/")
	public String toIndex(HttpServletRequest request, Model model) {

		Integer userId = (Integer) request.getSession().getAttribute("user_id");

		BigDecimal balance = this.balanceService.countBalance(userId);

		Integer credits = this.creditsService.countCredits(userId);

		BigDecimal plusBalance = this.balanceService.countPlusBalance(userId);

		Integer unPaidMission = this.missionService.countUnPaidMission(userId);

		model.addAttribute("balance", balance);
		model.addAttribute("credits", credits);
		model.addAttribute("plusBalance", plusBalance);
		model.addAttribute("unPaidMission", unPaidMission);
		return "wealth";
	}

	// 充值
	@ResponseBody
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public JSONObject CZ(HttpServletRequest request, Model model,
			@RequestParam(value = "money", required = true) BigDecimal money)
			throws Exception {

		
		JSONObject json = new JSONObject();
		// String pre_prepay_id = (String)
		// request.getSession().getAttribute("prepayid");
		/*
		 * if(pre_prepay_id==null||prepay_id ==
		 * null||!prepay_id.equals(pre_prepay_id)){ json.put("result_type",
		 * "error"); json.put("msg", "支付失败"); return json; }
		 */

		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		Balance orders = new Balance();
		orders.setQuantity(money);
		orders.setOrderNum(PayUtil.getOrdersNum(userId, userId));
		orders.setOrderState(0);// 未支付
		orders.setConsumingRecords("充值");
		orders.setUserId(userId);
		orders.setPlusOrMinus("plus");

		if (!balanceService.insertBalanceOrder(orders)) {
			json.put("result_type", "error");
			json.put("msg", "系统出错");
			return json;

		}

		// 判断是都已支付，防止重复支付
		// if(missionService.isPaid(mission.getMissionId())){
		//
		// json.put("result_type", "error");
		// json.put("msg", "请勿重复支付！");
		// return json;
		// }
		json.put("result_type", "success");
		String ip = getIpAddr(request);
		String body = "有空ucoon-余额充值";
		int fee = orders.getQuantity().multiply(new BigDecimal(100))
				.intValue();
		System.out.println(fee);
		String notify_url = "http://wx.ucoon.cn/wealth/payresult";
		
		String trade_type = "JSAPI";

		String openid = userService.getOpenIdbyUserId(userId);

		System.out.println("本次订单:" + orders.getOrderNum());
		// 生成订单
		String orderInfo = PayUtil.createOrderInfo(orders.getOrderNum(), ip,
				body, fee + "", notify_url, trade_type, openid);
		// 调统一下单API
		UnifiedOrderRespose unifiedOrderRespose = PayUtil.httpOrder(orderInfo);

		JsAPIConfig config = null;
		try {
			config = PayUtil
					.createPayConfig(unifiedOrderRespose.getPrepay_id());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsconfig = JSON.toJSONString(config,
				SerializerFeature.WriteMapNullValue);
		json.put("msg", jsconfig);

		return json;
	}

	// 提现
	@ResponseBody
	@RequestMapping(value = "/withdraw_cash", method = RequestMethod.POST)
	public JSONObject withdraw_cash(HttpServletRequest request, Model model,
			@RequestParam(value = "money", required = true) BigDecimal money)
			throws Exception {
		JSONObject json = new JSONObject();
		//这里判断提现金额要小于等于余额，比如大于一元，超过200元，分开发红包
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		
		BigDecimal balance = this.balanceService.countBalance(userId);
		if(money.intValue() < 1){
			json.put("result_type", "error");
			json.put("msg", "提现金额必须大于一元");
			return json;
		}
		
		if(balance.intValue() < money.intValue()){
			json.put("result_type", "error");
			json.put("msg", "提现金额必须大于余额");
			return json;
			
		}
		
		if(money.intValue() > 200){
			json.put("result_type", "error");
			json.put("msg", "暂不支持200元以上的提现");
			return json;
			
		}
		
		// String pre_prepay_id = (String)
		// request.getSession().getAttribute("prepayid");
		/*
		 * if(pre_prepay_id==null||prepay_id ==
		 * null||!prepay_id.equals(pre_prepay_id)){ json.put("result_type",
		 * "error"); json.put("msg", "支付失败"); return json; }
		 */

		
		Balance orders = new Balance();
		orders.setQuantity(money);
		orders.setOrderNum(PayUtil.getOrdersNum(userId, userId));
		orders.setOrderState(0);// 未支付、未提现
		orders.setConsumingRecords("提现");
		orders.setUserId(userId);
		orders.setPlusOrMinus("minus");
		

		if (!balanceService.insertBalanceOrder(orders)) {
			json.put("result_type", "error");
			json.put("msg", "系统出错");
			return json;

		}
		String openId = userService.getOpenIdbyUserId(userId);
		int fee = money.multiply(new BigDecimal(100)).intValue();

		SendRedPack redPack = new SendRedPack();
		redPack.setAct_name("用户提现");
		redPack.setClient_ip(getIpAddr(request));
		redPack.setMch_billno(orders.getOrderNum());
		redPack.setMch_id(PayUtil.MCH_ID);
		redPack.setNonce_str(PayUtil.makeUUID());
		redPack.setRe_openid(openId);
		redPack.setRemark("有空ucoon");
		redPack.setSend_name("有空ucoon");
		redPack.setTotal_amount(fee);
		redPack.setTotal_num(1);
		redPack.setWishing("感谢你使用有空ucoon平台，这是你的提现金额~");
		redPack.setWxappid(WeixinUtil.appid);
		redPack.setSign(PayUtil.createSendRedPackOrderSign(redPack));
		// 判断是都已支付，防止重复支付
		// if(missionService.isPaid(mission.getMissionId())){
		//
		// json.put("result_type", "error");
		// json.put("msg", "请勿重复支付！");
		// return json;
		// }
		
		SendRedPackRespose respose = PayUtil.sendRedPack(redPack);

		// 根据微信文档return_code 和result_code都为SUCCESS的时候才会返回code_url
		if (null != respose && "SUCCESS".equals(respose.getReturn_code())
				&& "SUCCESS".equals(respose.getResult_code())) {
			json.put("result_type", "success");
			json.put("msg", "提现成功");
			
		

			// 改订单状态
			orders.setOrderState(1);
			

			
		} else {
			json.put("result_type", "error");
			json.put("msg", "提现失败");
			
			// 改订单状态
			orders.setOrderState(2);
		}
		orders.setConsumingTime(new Date());
		balanceService.changeOrderStateByOrderNum(orders);
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

				Balance record = new Balance(); 

				// 改订单状态
				record.setOrderState(1);
				record.setOrderNum(orderId);
				record.setConsumingTime(new Date());
				balanceService.changeOrderStateByOrderNum(record);

				System.out.println("收到充值结果订单：" + orderId);
				String time = map.get("time_end");
				String total_fee = map.get("total_fee");
				String openid = map.get("openid");
				Integer userId = userService.getUserIdbyOpenId(openid);


				if (map.get("is_subscribe").equalsIgnoreCase("Y")) {
					Template tem = new Template();
					tem.setTemplateId("f9WOlW7QygvG9J3ZSW_lyk8KDML5RYhYjb7a5Af0klQ");
					tem.setTopColor("#00DD00");
					tem.setToUser(openid);
					tem.setUrl("http://wx.ucoon.cn/"); // 到时候改为任务列表

					List<TemplateParam> paras = new ArrayList<TemplateParam>();
					paras.add(new TemplateParam("first", "您好，您已成功进行余额充值。",
							"#FF3333"));
					paras.add(new TemplateParam("accountType", "订单号", "#0044BB"));
					paras.add(new TemplateParam("account", orderId, "#0044BB"));
					paras.add(new TemplateParam("amount", (Double
							.valueOf(total_fee) / 100) + "元", "#0044BB"));
					paras.add(new TemplateParam("result", "充值成功", "#0044BB"));
					paras.add(new TemplateParam("remark",
							"备注：如有疑问，请致电13063032247联系我们。", "#0044BB"));
					tem.setTemplateParamList(paras);

					boolean result = WeixinUtil.sendTemplateMsg(tem);
					System.out.println("模板消息结果：" + result);

				}

				// 发短信

				// orderService.updateConfirm(orderId);
			} else {

				System.out.println("失败订单");

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
