package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Mission;

public interface MissionService {

	public Mission selectByPrimaryKey(Integer missionId);
	
	public Integer insertMission(Mission mission);
	
	public Integer updateByPrimaryKey(Mission mission);
	
	public boolean publishMission(Mission mission);

	public List<HashMap<String, String>> getMissionLimited(Integer startIndex,
			Integer endIndex);

	public List<HashMap<String, String>> getMissionByKeyWord(String keyWord,
			Integer startIndex, Integer endIndex);

	public List<HashMap<String, String>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex);

	public List<HashMap<String, String>> selectLimitedbyUserIdAndStatus(
			Integer userId, Integer missionStatus, Integer startIndex,
			Integer endIndex);

	public HashMap<String, String> selectForMissionDetails(
			Integer missionId);

	public Integer selectUserIdByMissionId(Integer missionId);
	
	
	public void viewCount(Integer missionId);
}
