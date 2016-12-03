package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.EvaluateMapper;
import com.cn.ucoon.pojo.Evaluate;
import com.cn.ucoon.service.EvaluateService;

@Service
@Transactional
public class EvaluateServiceImpl implements EvaluateService{


	@Autowired
	private EvaluateMapper evaluateMapper;
	
	
	@Override
	public Evaluate selectByMissionId(Integer missionId) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectByMissionId(missionId);
	}


	@Override
	public void insertEvaluate(Evaluate evaluate) {
		// TODO Auto-generated method stub
		evaluateMapper.insert(evaluate);
	}


	@Override
	public boolean updateExecutorByMissionId(Evaluate missionId) {
		int i = evaluateMapper.updateExecutorByMissionId(missionId);
		if(i>0){
			return true;
		}
		
		return false;
	}


	@Override
	public float getEvaluateScore(Integer userId) {
		// TODO Auto-generated method stub
		
		
		float total =  5.0f; //评分默认5分
		if((evaluateMapper.selectExecutorScoreByUserId(userId) != null) && (evaluateMapper.selectPublishScoreByUserId(userId) != null) ){
			
			total = (evaluateMapper.selectExecutorScoreByUserId(userId) + evaluateMapper.selectPublishScoreByUserId(userId))/2;
		}
		
		
		return total;
	}


	@Override
	public List<HashMap<String, Object>> selectLimitedbyPublishId(
			Integer publishId, Integer startIndex, Integer endIndex) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectLimitedbyPublishId(publishId, startIndex, endIndex);
	}


	@Override
	public Evaluate selectByMidAndPidAndEid(Integer missionId,
			Integer publishId, Integer executorId) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectByMidAndPidAndEid(missionId, publishId, executorId);
	}


	@Override
	public boolean updatePublishByMidAndPidAndEid(Evaluate evaluate) {
		int i = evaluateMapper.updatePublishByMidAndPidAndEid(evaluate);
		if(i>0){
			return true;
		}
		
		return false;
	}

}
