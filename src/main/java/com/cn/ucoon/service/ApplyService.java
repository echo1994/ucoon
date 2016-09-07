package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Apply;

public interface ApplyService {
	List<HashMap<String, String>> selectApplybyUMID(Integer userId,
			Integer missionId);

	List<HashMap<String, String>> selectByMissionId(Integer missionId);
	
	List<HashMap<String, String>> selectApplyUser(Integer applyId);

	int addAppliment(Apply apply);

	boolean confirmApply(Integer applyId,Integer result);
	
	Apply selectByPrimaryKey(Integer applyId);
}
