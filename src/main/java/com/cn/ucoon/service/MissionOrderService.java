package com.cn.ucoon.service;

import com.cn.ucoon.pojo.MissionOrders;

public interface MissionOrderService {

	public boolean makeOrders(MissionOrders missionOrders);
	
	public MissionOrders getOrdersbyMissionId(Integer missionId);
	
	public void updateMissionStatusbyOrdersId(MissionOrders record);
	
	public void update(MissionOrders record);
	
	public MissionOrders getOrderByOrderNum(String orderNum);
}
