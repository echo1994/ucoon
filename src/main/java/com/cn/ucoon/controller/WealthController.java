package com.cn.ucoon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/wealth")
public class WealthController {

	
	@RequestMapping(value="/")
	public String toIndex(HttpServletRequest request,Model model){
		return "wealth";
	}
	
	
	
}
