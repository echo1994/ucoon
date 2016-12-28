package com.cn.ucoon.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ucoon.pojo.ApplyTeam;
import com.cn.ucoon.service.TeamService;



@Controller
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	
	// 地推申请
	@RequestMapping(value = "/apply_team", method = RequestMethod.GET)
	public String whoNew(HttpSession session, Model model) {
		//Integer userId = (int) session.getAttribute("user_id");

		

		return "apply_team";
	}
	
	@RequestMapping(value = "/saveApplyteam")
	public ModelAndView saveApplyteam(
			@RequestParam(value = "question", required = false) String question,
			@RequestParam(value = "imgUpload", required = true) MultipartFile[] file,
			@RequestParam(value = "checkbox", required = true) String[] checkbox,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "school", required = true) String school,
			HttpServletRequest request, ModelAndView mv) throws ParseException {

		String path = ImageController.APPLYTEAM_IMAGE_LOCATION;
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
		if (userId != null) {
			String tags = "";
			for (int i = 0; i < checkbox.length; i++) {
				tags += checkbox[i] + ",";
			}
			ApplyTeam applyTeam = new ApplyTeam();
			applyTeam.setApplyerName(name);
			applyTeam.setApplyerPhone(phone);
			applyTeam.setApplyStatus(0);
			applyTeam.setApplyTime(new Date());
			applyTeam.setCertificateImg(userId + timestamp + uuid);
			applyTeam.setPicCount(fileLength);
			applyTeam.setSchool(school);
			applyTeam.setSelfIntroduce(question);
			applyTeam.setTags(tags);
			applyTeam.setUserId(userId);
			teamService.insert(applyTeam);
		}
		mv.setViewName("redirect:/index");
		return mv;
	}

}
