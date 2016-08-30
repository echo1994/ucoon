package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Mission;

import java.util.HashMap;
import java.util.List;

public interface MissionMapper {
	int deleteByPrimaryKey(Integer missionId);

	int insert(Mission record);

	Mission selectByPrimaryKey(Integer missionId);

	List<Mission> selectAll();

	int updateByPrimaryKey(Mission record);

	List<HashMap<String, String>> selectLimited(Integer startIndex,
			Integer endIndex);

	List<HashMap<String, String>> selectLimitedbyKeyWord(String keyWord,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, String>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, String>> selectLimitedbyUserIdAndStatus(
			Integer userId, Integer missionStatus, Integer startIndex,
			Integer endIndex);

	HashMap<String, String> selectForMissionDetails(Integer missionId);

	Integer selectUserIdByMissionId(Integer missionId);
}