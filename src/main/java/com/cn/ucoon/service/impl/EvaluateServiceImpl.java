package com.cn.ucoon.service.impl;

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

}