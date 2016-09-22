package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.OrderMapper;
import com.cn.ucoon.pojo.Orders;
import com.cn.ucoon.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public List<HashMap<String, String>> selectOrdersLimited(Integer userId,
			Integer startIndex, Integer endIndex) {
		// TODO Auto-generated method stub
		return orderMapper.selectOrdersLimited(userId, startIndex, endIndex);
	}

	@Override
	public List<HashMap<String, String>> selectOrderbyUMID(Integer userId,
			Integer missionId) {
		// TODO Auto-generated method stub
		return orderMapper.selectOrderbyUMID(userId, missionId);
	}

	@Override
	public Integer selectOrdersCountByM(Integer missionId) {
		// TODO Auto-generated method stub
		return orderMapper.selectOrdersCountByM(missionId);
	}

	@Override
	public List<HashMap<String, String>> selectorderDetailsByOrderId(
			Integer orderId) {
		// TODO Auto-generated method stub
		return orderMapper.selectorderDetailsByOrderId(orderId);
	}
	
	@Override
	public Integer updateOrderStateFinished(Integer orderId) {
		// TODO Auto-generated method stub
		Orders orders = orderMapper.selectByPrimaryKey(orderId);
		orders.setState(1);
		return orderMapper.updateByPrimaryKey(orders);
	}
	
}
