package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Mission;

public interface MissionService {

	public Mission selectByPrimaryKey(Integer missionId);
	
	public Integer insertMission(Mission mission);
	
	public Integer updateByPrimaryKey(Mission mission);
	
	public boolean publishMission(Mission mission);

	public List<HashMap<String, Object>> getMissionLimited(Integer startIndex,
			Integer endIndex,String latitude,String longitude,String type);

	public List<HashMap<String, Object>> getMissionByKeyWord(String keyWord,
			Integer startIndex, Integer endIndex,String latitude,String longitude);

	public List<HashMap<String, Object>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex);

	public List<HashMap<String, Object>> selectLimitedbyUserIdAndStatus(
			Integer userId, List<Integer> list, Integer startIndex,
			Integer endIndex);

	public HashMap<String, String> selectForMissionDetails(
			Integer missionId);

	public Integer selectUserIdByMissionId(Integer missionId);
	
	
	public void viewCount(Integer missionId);
	
	
	public boolean isPaid(Integer missionId);
	
	public Integer getUserIdByMissionId(Integer missionId);
	
	public Integer countUnPaidMission(Integer userId);
}
