package com.cn.ucoon.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.MissionAddress;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.BalanceService;
import com.cn.ucoon.service.CreditsService;
import com.cn.ucoon.service.EvaluateService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.SendUtil;

@Controller
public class HomeController {

	
	@Resource
	private UserService userService;
	
	@Resource
	private BalanceService balanceService;
	
	@Resource
	private CreditsService creditsService;
	
	@Resource
	private MissionService missionService;
	
	@Resource
	private EvaluateService evaluateService;
	
	
	@RequestMapping(value = "/we", method = RequestMethod.GET)
	public String we(HttpSession session,Model model) {
		int userId = (int) session.getAttribute("user_id");
		
		User user = null;
		user = this.userService.getUserById(userId);
		
		BigDecimal balance = this.balanceService.countBalance(userId);
		
		Integer credits = this.creditsService.countCredits(userId);
		
		model.addAttribute("user", user);
		model.addAttribute("balance", balance);
		model.addAttribute("credits", credits);
		model.addAttribute("user_id", user.getUserId());
		return "we";
	}
	
	//谁有空
	@RequestMapping(value = "/who-new", method = RequestMethod.GET)
	public String whoNew(HttpSession session,Model model) {
		int userId = (int) session.getAttribute("user_id");
		
		User user = null;
		user = this.userService.getUserById(userId);
		
		
		List<MissionAddress> infos = missionService.selectAllMissionAddressByUserId(userId);
		
		model.addAttribute("infos", infos);
		model.addAttribute("user", user);
		return "who-new";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session,Model model) {
		int userId = (int) session.getAttribute("user_id");
		
		User user = null;
		user = this.userService.getUserById(userId);

		BigDecimal balance = this.balanceService.countBalance(userId);
		
		Integer credits = this.creditsService.countCredits(userId);
		String score = this.evaluateService.getEvaluateScore(userId) + "";
		System.out.println(score);
		String[] str = score.split("\\.");
		if(str[1].equals("5")){
			
			model.addAttribute("half", true);
			model.addAttribute("all", str[0]);
			model.addAttribute("blank", 4 - Integer.valueOf(str[0]));
		}else{
			
			model.addAttribute("half", false);
			model.addAttribute("all", str[0]);
			model.addAttribute("blank", 5 - Integer.valueOf(str[0]));
		}
		
		model.addAttribute("user", user);
		model.addAttribute("balance", balance);
		model.addAttribute("credits", credits);
		model.addAttribute("user_id", user.getUserId());
		return "index";
	}

	@RequestMapping(value = "/chooseAllocation")
	public String chooseAllocation(HttpServletRequest request) {
		InetAddress addr;
		String ip ="127.0.0.1";
		try {
			addr = InetAddress.getLocalHost();
			ip= addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("ip", ip);
		return "chooseAllocation";
	}
	
	@RequestMapping(value = "/feedback")
	public String feedback() {
		
		return "feedback";
	}
	
	
	//发短信 4位随机数
	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public JSONObject msg(HttpServletRequest request,
			@RequestParam(value = "phone", required = true) String phone) {
		
		//这个捕获异常，出错重新授权
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		int s = (int) (Math.random()*9000+1000);
		String k = s + "";
		System.out.println(k);
		JSONObject json = new JSONObject();
		
		if(phone == null || phone.equals("")){
			
			json.put("result", "error");
			json.put("msg", "手机号出错");
			return json;
		}
		
		
		try {
			if(SendUtil.send(phone, "【有空总部】聪明的你终于发现了神秘时间大陆，" + k + "是你的专属时间密匙，拿好它来开启未来大门吧！（密匙5分钟后失效，没时间解释了快上船！）")){
				request.getSession().setAttribute("msgduanxin", k);
				request.getSession(true).setMaxInactiveInterval(5*60); //5分钟
				
				json.put("result", "success");
				return json;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		json.put("result", "error");
		json.put("msg", "系统错误，发送失败");
		return json;
	}
	
	@RequestMapping(value = "/checkMsg")
	@ResponseBody
	public JSONObject checkMsg(HttpServletRequest request,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "phone", required = true) String phone) {
		
		//这个捕获异常，出错重新授权
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		
		JSONObject json = new JSONObject();
		String s = (String) request.getSession().getAttribute("msgduanxin");
		if(code == null || code.equals("")){
			
			json.put("result", "error");
			json.put("msg", "验证码出错");
			return json;
		}
		User user = new User();
		user.setUserId(userId);
		user.setPhone(phone);
		
		if(code.equals(s) && userService.updateUserPhone(user)){
			request.getSession().removeAttribute("msgduanxin");
			json.put("result", "success");
			return json;
		}
		
		json.put("result", "error");
		json.put("msg", "系统错误，验证失败");
		return json;
	}
	
}
