package com.cn.ucoon.service;

import com.cn.ucoon.pojo.Evaluate;

public interface EvaluateService {
	
	
	public Evaluate selectByMissionId(Integer missionId);
	
	public void insertEvaluate(Evaluate evaluate);
	
	public boolean updateExecutorByMissionId(Evaluate evaluate);
}
