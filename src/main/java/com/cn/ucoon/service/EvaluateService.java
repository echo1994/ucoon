package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Evaluate;

public interface EvaluateService {
	
	
	public Evaluate selectByMissionId(Integer missionId);
	
	public void insertEvaluate(Evaluate evaluate);
	
	public boolean updateExecutorByMissionId(Evaluate evaluate);
	
	
	public float getEvaluateScore(Integer userId);
	
	public List<HashMap<String, Object>> selectLimitedbyPublishId(Integer publishId,
			Integer startIndex, Integer endIndex);
}
