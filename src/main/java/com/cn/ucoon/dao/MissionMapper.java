package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Mission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MissionMapper {
	int deleteByPrimaryKey(Integer missionId);

	int insert(Mission record);

	Mission selectByPrimaryKey(Integer missionId);

	List<Mission> selectAll();

	int updateByPrimaryKey(Mission record);

	int updateViewByPrimaryKey(Integer missionId);
	
	int unPaidMissionScan(@Param("date")Date date);
	
	List<HashMap<String, Object>> selectLimited(Integer startIndex,
			Integer endIndex);
	
	List<HashMap<String, Object>> selectLimitedbyKeyWord(String keyWord,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, Object>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, Object>> selectLimitedbyUserIdAndStatus(Map<String, Object> map);

	HashMap<String, Object> selectForMissionDetails(Integer missionId);

	Integer selectUserIdByMissionId(Integer missionId);
	
	List<HashMap<String, Object>> selectNearby(Double lat, Double lon,
			String minLat, String maxLat, String minLng, String maxLng,
			Integer rowStart, Integer rowEnd);
	
	Integer selectStatusByMissionId(Integer missionId);
	
	Integer getUserIdByMissionId(Integer missionId);
	
	Integer selectUnPaidMissionByUserId(Integer userId);
	
	Integer countMissionDoneByUserId(Integer userId);
}