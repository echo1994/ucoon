package com.cn.ucoon.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/mission")
public class MissionController {

	@Autowired
	private MissionService missionService;

	@Autowired
	private OrderService orderService;

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
	 */
	@RequestMapping(value = "/add-mission")
	public String publishMission(
			Mission mission,
			@RequestParam(value = "imgUpload", required = false) MultipartFile[] file,
			HttpServletRequest request) {

		System.out.println(mission);
		String path = ImageController.MISSION_IMAGE_LOCATION;
		String userId = "1"; // (String)
								// request.getSession().getAttribute("user_id");
		String timestamp = String.valueOf(System.currentTimeMillis());
		String uuid = String.valueOf(UUID.randomUUID());
		uuid = uuid.replace("-", "");
		String realpath = path + "/" + userId + timestamp + uuid;// 文件夹位置
		File dir = new File(realpath);
		dir.mkdirs();
		System.out.println("wenjian:" + file.length);
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

		mission.setPictures(userId + timestamp + uuid);
		mission.setUserId(Integer.parseInt(userId));
		mission.setViewCount(0);
		mission.setMissionStatus(1);
		mission.setPicCount(file.length);
		mission.setPublishTime(new Date());
		missionService.publishMission(mission);
		return "redirect:mysend";
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
			HttpServletRequest request) {
		List<HashMap<String, String>> missions = null;
		if (keyWord != null && keyWord != "") {
			missions = missionService.getMissionByKeyWord("%" + keyWord + "%",
					startIndex, endIndex);
		} else if (userId != null) {
			if (missionStatus == null) {
				userId = 1;// (Integer)
							// request.getSession().getAttribute("user_id");
				missions = missionService.selectLimitedbyUserId(userId,
						startIndex, endIndex);
			} else {
				userId = 1;// (Integer)
				// request.getSession().getAttribute("user_id");
				missions = missionService.selectLimitedbyUserIdAndStatus(
						userId, missionStatus, startIndex, endIndex);
			}
		} else {
			missions = missionService.getMissionLimited(startIndex, endIndex);
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
		HashMap<String, String> missions = null;
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

	@RequestMapping(value = "missionOffShelf/{missionId}", produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String missionOffShelf(
			@PathVariable(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		// 1判断是否本人操作
		// 2查询有几个订单，任务所需人数
		// 3改变任务状态
		// 4退款
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		Integer cuserId = 1;//(Integer) request.getSession()
				//.getAttribute("user_id");
		if (cuserId != null && cuserId == userId) {
			System.out.println(cuserId);
			System.out.println(userId);
			Mission mission = missionService.selectByPrimaryKey(missionId);
			Integer price = mission.getMissionPrice();
			Integer peopleCount = mission.getPeopleCount();
			Integer orderCount = orderService.selectOrdersCountByM(missionId);
			if (orderCount < peopleCount) {
				// 需要退款 price * ( peopleCount - orderCount)
				/**
				 * 退款代码
				 */
				mission.setMissionStatus(2);
				missionService.updateByPrimaryKey(mission);// 下架
				return "任务已下架，正在审核退款";
			} else if (orderCount >= peopleCount) {
				// 不需要退款
				mission.setMissionStatus(2);
				missionService.updateByPrimaryKey(mission);// 下架
				return "任务已下架";
			}
		}
		return "系统异常，请重试";

	}

	@RequestMapping(value = "/task-info/{missionId}")
	public ModelAndView taskInfo(@PathVariable("missionId") Integer missionId,
			ModelAndView mv) {
		HashMap<String, String> mdetails = null;
		mdetails = missionService.selectForMissionDetails(missionId);
		System.out.println(mdetails);
		mv.addObject("mdetails", mdetails);
		mv.setViewName("task-info");
		return mv;
	}

	@RequestMapping(value = "/more-info/{mid}")
	public ModelAndView moreMinfo(@PathVariable(value = "mid") Integer mid,
			ModelAndView mv) {
		mv.addObject("mid", mid);
		mv.setViewName("more-info");
		return mv;
	}
}
