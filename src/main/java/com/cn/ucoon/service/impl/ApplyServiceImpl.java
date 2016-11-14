package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.ApplyOrdersMapper;
import com.cn.ucoon.pojo.ApplyOrders;
import com.cn.ucoon.service.ApplyService;

@Service
@Transactional
public class ApplyServiceImpl implements ApplyService {

	@Autowired
	private ApplyOrdersMapper applyOrdersMapper;


	@Override
	public List<HashMap<String, Object>> selectApplybyUMID(Integer userId,
			Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectApplybyUMID(userId, missionId);
	}


	@Override
	public List<HashMap<String, String>> selectByMissionId(Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectByMissionId(missionId);
	}



	@Override
	public ApplyOrders selectByPrimaryKey(Integer applyId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectByPrimaryKey(applyId);
	}

	@Override
	public List<HashMap<String, String>> selectApplyUser(Integer applyId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectApplyUser(applyId);
	}

	
	@Override
	public List<HashMap<String, String>> selectOrdersLimited(Integer userId,
			Integer startIndex, Integer endIndex) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectOrdersLimited(userId, startIndex, endIndex);
	}


	@Override
	public Integer selectOrdersCountByM(Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectOrdersCountByM(missionId);
	}

	@Override
	public List<HashMap<String, String>> selectorderDetailsByApplyId(
			Integer applyId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectorderDetailsByApplyId(applyId);
	}
	

	@Override
	public boolean saveOrders(ApplyOrders orders) {
		// TODO Auto-generated method stub
		int i = applyOrdersMapper.insert(orders);
		if(i > 0){
			return true;
			
		}
		return false;
	}


	@Override
	public boolean updateStateByApplyId(ApplyOrders applyOrders) {
		
		Integer i = applyOrdersMapper.updateByPrimaryKey(applyOrders);
		if(i >0){
			
			return true;
			
		}
		return false;
	}


	@Override
	public List<HashMap<String, String>> selectorderDetailsByUserIdAndMissionId(
			Integer userId, Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectorderDetailsByUserIdAndMissionId(userId, missionId);
	}


	@Override
	public boolean updateByPrimaryKey(ApplyOrders applyOrders) {
		int i = applyOrdersMapper.updateByPrimaryKey(applyOrders);
		
		if(i > 0){
			return true;
		}
		
		return false;
	}


	@Override
	public boolean updateNoteByPrimaryKey(ApplyOrders applyOrders) {
		int i = applyOrdersMapper.updateNoteByPrimaryKey(applyOrders);
		
		if(i > 0){
			return true;
		}
		
		return false;
	}


	@Override
	public List<HashMap<String, Object>> selectunselectedpeople(
			Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectunselectedpeople(missionId);
	}


	@Override
	public List<HashMap<String, Object>> selectselectedpeople(Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectselectedpeople(missionId);
	}


	@Override
	public boolean updateDoneByPrimaryKey(ApplyOrders applyOrders) {
		int i = applyOrdersMapper.updateDoneByPrimaryKey(applyOrders);
		
		if(i > 0){
			return true;
		}
		
		return false;
	}


	@Override
	public List<HashMap<String, Object>> selectDetailByMissionId(
			Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectDetailByMissionId(missionId);
	}


	@Override
	public List<HashMap<String, Object>> selectselectpeople(Integer missionId) {
		// TODO Auto-generated method stub
		return applyOrdersMapper.selectselectpeople(missionId);
	}
	
	
}
