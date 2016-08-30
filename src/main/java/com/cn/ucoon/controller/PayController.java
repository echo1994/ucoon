package com.cn.ucoon.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cn.ucoon.pojo.wx.JsAPIConfig;
import com.cn.ucoon.pojo.wx.UnifiedOrderRespose;
import com.cn.ucoon.util.MessageUtil;
import com.cn.ucoon.util.PayUtil;

@Controller
@RequestMapping("/pay")
public class PayController {

	//支付预支付处理，生成prepayid编号，存放在session中，在支付时效验
	@RequestMapping("/prepay")
	public String prepay(Model model) {
		//prepayid格式 ：ucoon + 用户id + 6位随机数
		String userid = "1";
		Random random = new Random();
		int num = random.nextInt(100000);
		String prepayid = "ucoon" + userid + num;
		
		
		
		model.addAttribute("prepayid", prepayid);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		request.getSession().setAttribute("prepayid", prepayid);
		
		return "prepay";
	}
	
	// 微信支付
	@ResponseBody
	@RequestMapping(value = "/getPay/{prepay_id}")  
	public JSONObject pay(@PathVariable("prepay_id") String prepay_id) throws Exception {  
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       
		JSONObject json = new JSONObject();
		
		String pre_prepay_id = (String) request.getSession().getAttribute("prepayid");
		if(pre_prepay_id==null||prepay_id == null||!prepay_id.equals(pre_prepay_id)){
			json.put("result_type", "error");
			json.put("msg", "支付失败");
			return json;
		}
		
		json.put("result_type", "success");
		
		System.out.println("=============我是访问IP："+getIpAddr(request)+ "==================");
		String ip = "219.228.251.28";
		System.out.println(ip);
		String body = "test";
		String fee = "1";
		String notify_url = "http://www.jmutong.com/ucoon/pay/payresult";;
		String trade_type = "JSAPI";
		
		
		String openid = "ogF_wvuJ_E4axtC729eTozgyyJTM";
		
		String userId = "1";
		String orderId = PayUtil.getOrderId(userId);
		System.out.println("本次订单:" + orderId);
		//生成订单  
		String orderInfo = PayUtil.createOrderInfo(orderId, ip, body, fee, notify_url, trade_type,openid); 
	    //调统一下单API  
		UnifiedOrderRespose unifiedOrderRespose = PayUtil.httpOrder(orderInfo);  
		
		JsAPIConfig config = PayUtil.createPayConfig(unifiedOrderRespose.getPrepay_id());
		String jsconfig = JSON.toJSONString(config,SerializerFeature.WriteMapNullValue);
		json.put("msg", jsconfig);
		
		return json;  
	}
	/**
     * 微信支付回调页面
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
                String orderId = map.get("out_trade_no");
                System.out.println("收到支付结果订单：" + orderId);
                //这里写成功后的业务逻辑
                //orderService.updateConfirm(orderId);
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
		if (ip!=null&&!ip.equals("") && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (ip!=null&&!ip.equals("") && !"unknown".equalsIgnoreCase(ip)) {
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

}
