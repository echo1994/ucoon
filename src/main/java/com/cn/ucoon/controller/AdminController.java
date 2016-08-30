package com.cn.ucoon.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ucoon.pojo.Admin;
import com.cn.ucoon.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private AdminService adminService;
	
	//登陆界面
	@RequestMapping("/login")
	public String toLogin() {

		return "/admin/login";
	}
	
	//退出登陆
	@RequestMapping("/logout")
	public String toLogout() {

		return "/admin/login";
	}

	//登陆成功后跳转界面
	@RequestMapping("/index")
	public String toIndex(HttpSession session) {
		session.removeAttribute("adminId");
		return "/admin/index";
	}
	
	//修改密码
	@RequestMapping("/changepassword")
	public String changePW(HttpSession session) {
		session.removeAttribute("adminId");
		return "/admin/index";
	}
	
	//验证
	@RequestMapping(value = "/checkuser", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> check(
			@RequestParam("username") String username,
			@RequestParam("password")String password,
			@RequestParam("code")String code,HttpSession session) {

		Map<String, String> json = new HashMap<String, String>();
		String imgCode = (String) session.getAttribute("code")==null ? "":(String) session.getAttribute("code");
		
		if(code.length() < 4 || !imgCode.equals(code.toUpperCase())){
			json.put("result", "error");
			json.put("msg", "请输入正确的验证码");
			return json;
		}
		Admin admin = adminService.checkUser(username, password);
		if(admin != null){
			session.setAttribute("adminId", admin.getAdminId());
			json.put("result", "success");
			json.put("msg", "ok");
		}else{
			json.put("result", "error");
			json.put("msg", "用户名或密码错误");
		}
		
		return json;
	}

	//网站信息设置
	@RequestMapping("/main_info")
	public String mainInfo() {

		return "/admin/web_install/main_info";
	}
	
	//广告位设置
	@RequestMapping("/adv_list")
	public String advList() {

		return "/admin/web_install/adv_list";
	}
	
	//广告编辑
	@RequestMapping("/edit_adv")
	public String editAdv() {

		return "/admin/web_install/edit_adv";
	}
	
	//管理员分组列表
	@RequestMapping("/admingroup_list")
	public String admingroupList() {
		return "/admin/web_install/admingroup_list";
	}
	
	//管理员分组列表
	@RequestMapping("/admin_list")
	public String adminList() {

		return "/admin/web_install/admin_list";
	}
}
