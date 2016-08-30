package com.cn.ucoon.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ucoon.pojo.Apply;
import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping(value = "/apply")
@Controller
public class ApplyController {

	@Autowired
	private ApplyService applyService;

	@Autowired
	private MissionService missionService;

	@RequestMapping(value = "/addAppliment", method = RequestMethod.POST, produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String addAppliment(
			@RequestParam(value = "missionId") Integer missionId,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = 1;// (Integer)
							// request.getSession().getAttribute("user_id");
		List<HashMap<String, String>> applys = null;
		applys = applyService.selectApplybyUMID(userId, missionId);
		if (applys == null || applys.size() == 0) {
			Apply apply = new Apply(userId, missionId, new Date(), 0);
			int i = applyService.addAppliment(apply);
			if (i > 0) {
				return "已成功申请";
			} else {
				return "申请失败，请重试";
			}
		} else {
			return "您已申请过此任务";
		}
	}

	/**
	 * 根据任务ID和用户ID获取申请信息
	 * 
	 * @param judge
	 *            是否只是判断存在申请信息
	 * @param missionId
	 *            任务ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getApplybyUMID", method = RequestMethod.POST)
	@ResponseBody
	public String selectOrderbyUMID(
			@RequestParam(value = "forjudge", required = true) boolean judge,
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		List<HashMap<String, String>> applys = null;
		if (judge == true) {// 只用于判断不返回数据
			applys = applyService.selectApplybyUMID(userId, missionId);
			if (applys == null || applys.size() == 0) {
				return "0";// 返回0，表明用户(userId)没接该任务
			} else {
				return "1";// 返回1，表明用户(userId)已经接了该任务
			}
		} else {
			// 返回订单数据
		}
		return "";
	}

	/**
	 * 发布者 通过 missionId 查看 所有申请信息
	 * 
	 * @param missionId
	 *            任务ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getApByMid", method = RequestMethod.POST)
	@ResponseBody
	public String getApByMid(
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		List<HashMap<String, String>> applys = null;
		Integer currentUserId = 1; // Integer.parseInt(request.getParameter("user_id"));
		Integer userId = missionService.selectUserIdByMissionId(missionId);
		if (true) {// 条件userId == currentUserId
			applys = applyService.selectByMissionId(missionId);
			System.out.println(applys);
			ObjectMapper mapper = new ObjectMapper();
			String jsonfromList = "";
			try {
				jsonfromList = mapper.writeValueAsString(applys);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonfromList = "{}";
			}
			return jsonfromList;
		} else {
			return "{}";
		}
	}

	@RequestMapping(value = "/confirmAppliment/{applyId}/{result}")
	@ResponseBody
	public String confirmAppliment(@PathVariable Integer applyId,
			@PathVariable Integer result,// 1接收2拒绝
			HttpServletRequest request) {
		if (applyService.confirmApply(applyId, result)) {
			return "true";
		} else {
			return "false";
		}
	}
	/*
	 * @RequestMapping(value = "/confirmAppliment", method = RequestMethod.POST)
	 * public String confirmAppliment(
	 * 
	 * @RequestParam(value = "userIds[]", required = true) Integer[] userIds,
	 * 
	 * @RequestParam(value = "missionId", required = true) Integer missionId,
	 * HttpServletRequest request) { if (applyService.confirmApply(userIds,
	 * missionId)) { return "true"; } else { return "false"; } }
	 */
	
}
