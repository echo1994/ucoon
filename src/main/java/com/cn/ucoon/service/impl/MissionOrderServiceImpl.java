package com.cn.ucoon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.MissionOrdersMapper;
import com.cn.ucoon.pojo.MissionOrders;
import com.cn.ucoon.service.MissionOrderService;

@Service
@Transactional
public class MissionOrderServiceImpl implements MissionOrderService {

	@Autowired
	private MissionOrdersMapper missionOrdersMapper;
	
	
	@Override
	public boolean makeOrders(MissionOrders missionOrders) {
		if(missionOrdersMapper.insert(missionOrders) > 0){
			return true;
		}
		
		return false;
	}


	@Override
	public MissionOrders getOrdersbyMissionId(Integer missionId) {
		// TODO Auto-generated method stub
		return missionOrdersMapper.selectByMissionId(missionId);
	}


	@Override
	public void updateMissionStatusbyOrdersId(MissionOrders record) {
		// TODO Auto-generated method stub
		//将支付状态更新为已支付
		missionOrdersMapper.updateMissionStatusbyOrdersId(record);
	}

}
