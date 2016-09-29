package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.ApplyOrders;

public interface ApplyService {
	List<HashMap<String, String>> selectApplybyUMID(Integer userId,
			Integer missionId);

	List<HashMap<String, String>> selectByMissionId(Integer missionId);
	
	List<HashMap<String, String>> selectApplyUser(Integer applyId);

	
	ApplyOrders selectByPrimaryKey(Integer applyId);
	
	
	List<HashMap<String, String>> selectOrdersLimited(Integer userId,
			Integer startIndex, Integer endIndex);

	Integer selectOrdersCountByM(Integer missionId);
	
	List<HashMap<String, String>> selectorderDetailsByApplyId(Integer applyId);
	
	List<HashMap<String, String>> selectorderDetailsByUserIdAndMissionId(Integer userId,Integer missionId);
	
	boolean updateStateByApplyId(ApplyOrders applyOrders);
	
	boolean updateByPrimaryKey(ApplyOrders applyOrders);
	
	boolean updateNoteByPrimaryKey(ApplyOrders applyOrders);
	
	
	boolean saveOrders(ApplyOrders applyOrders);
}
