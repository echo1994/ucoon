package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.ApplyOrders;

import java.util.HashMap;
import java.util.List;

public interface ApplyOrdersMapper {
    int deleteByPrimaryKey(Integer applyId);

    int insert(ApplyOrders record);

    ApplyOrders selectByPrimaryKey(Integer applyId);

    List<ApplyOrders> selectAll();

    int updateByPrimaryKey(ApplyOrders record);
    
    
    int updateNoteByPrimaryKey(ApplyOrders record);
    
    int updateDoneByPrimaryKey(ApplyOrders record);
    
    ApplyOrders selectApplybyUserIdAndMissionId(Integer userId,Integer missionId);
    
    
    List<HashMap<String, Object>> selectApplybyUMID(Integer userId,
			Integer missionId);
	
	List<HashMap<String, String>> selectApplyUser(Integer applyId);

	
	List<HashMap<String, String>> selectByMissionId(Integer missionId);

	List<HashMap<String, Object>> selectDetailByMissionId(Integer missionId);
	
	List<HashMap<String, Object>> selectDetailByMissionId2(Integer missionId);
	
	List<HashMap<String, String>> selectOrdersLimited(Integer userId,
			Integer startIndex, Integer endIndex);

	Integer selectOrdersCountByM(Integer missionId);
	
	List<HashMap<String, String>> selectorderDetailsByApplyId(Integer applyId);
	
	List<HashMap<String, String>> selectorderDetailsByUserIdAndMissionId(Integer userId,Integer missionId);

	
	List<HashMap<String, Object>> selectunselectedpeople(Integer missionId);
	
	List<HashMap<String, Object>> selectselectedpeople(Integer missionId);
	
	List<HashMap<String, Object>> selectselectpeople(Integer missionId);
	
	List<HashMap<String, Object>> selectEvalueatepeople(Integer missionId);

}