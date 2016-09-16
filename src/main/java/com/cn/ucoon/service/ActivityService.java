package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Activity;

public interface ActivityService {
	
	public boolean saveActivity(Activity activity);
	
	public List<HashMap<String, Object>> getActivityLimited(Integer startIndex,
			Integer endIndex,String latitude,String longitude,String type);

	public List<HashMap<String, Object>> getActivityByKeyWord(String keyWord,
			Integer startIndex, Integer endIndex,String latitude, String longitude);

	public List<HashMap<String, Object>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex);

	
}
