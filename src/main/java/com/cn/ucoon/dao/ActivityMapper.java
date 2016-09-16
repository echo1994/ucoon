package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Activity;

import java.util.HashMap;
import java.util.List;

public interface ActivityMapper {
	int deleteByPrimaryKey(Integer activityId);

	int insert(Activity record);

	Activity selectByPrimaryKey(Integer activityId);

	List<Activity> selectAll();

	int updateByPrimaryKey(Activity record);

	List<HashMap<String, Object>> selectLimited(Integer startIndex,
			Integer endIndex, String orderBy);

	List<HashMap<String, Object>> selectLimitedbyKeyWord(String keyWord,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, Object>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, Object>> selectNearby(Double lat, Double lon,
			String minLat, String maxLat, String minLng, String maxLng,
			Integer rowStart, Integer rowEnd);
}