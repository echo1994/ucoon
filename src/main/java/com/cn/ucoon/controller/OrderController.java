package com.cn.ucoon.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ucoon.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 根据用户ID查询所有订单
	 * 
	 * @param userId
	 *            用户ID
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrdersLimited", method = RequestMethod.POST)
	public String getOrdersLimited(
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex,
			HttpServletRequest request) {
		List<HashMap<String, String>> orders = null;
		try {
			orders = orderService.selectOrdersLimited(userId, startIndex,
					endIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(orders);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonfromList;
	}

	/**
	 * 根据任务ID和用户ID获取订单
	 * 
	 * @param judge
	 *            是否只是判断存在订单
	 * @param missionId
	 *            任务ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOrdersbyUMID", method = RequestMethod.POST)
	@ResponseBody
	public String selectOrderbyUMID(
			@RequestParam(value = "forjudge", required = true) boolean judge,
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		List<HashMap<String, String>> orders = null;
		if (judge == true) {// 只用于判断不返回数据
			orders = orderService.selectOrderbyUMID(userId, missionId);
			if (orders == null || orders.size() == 0) {
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
	 * 查询任务的已确认的订单数量，用于页面判断是否多选
	 * 
	 * @param missionId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOrdersCountByM", method = RequestMethod.POST)
	@ResponseBody
	public String getOrdersCountByM(
			@RequestParam(value = "missionId") Integer missionId,
			HttpServletRequest request) {
		Integer count = orderService.selectOrdersCountByM(missionId);
		return "" + count;
	}

}
