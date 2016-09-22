package com.cn.ucoon.controller;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.BalanceService;
import com.cn.ucoon.service.CreditsService;
import com.cn.ucoon.service.UserService;

@Controller
public class HomeController {

	
	@Resource
	private UserService userService;
	
	@Resource
	private BalanceService balanceService;
	
	@Resource
	private CreditsService creditsService;
	
	
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
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session,Model model) {
		int userId = (int) session.getAttribute("user_id");
		
		User user = null;
		user = this.userService.getUserById(userId);

		BigDecimal balance = this.balanceService.countBalance(userId);
		
		Integer credits = this.creditsService.countCredits(userId);
		
		
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
}
