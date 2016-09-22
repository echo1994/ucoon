package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.ActivityMapper;
import com.cn.ucoon.pojo.Activity;
import com.cn.ucoon.service.ActivityService;
import com.cn.ucoon.util.MapDistanceUtil;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityMapper activityMapper;

	@Override
	public boolean saveActivity(Activity activity) {
		int i = activityMapper.insert(activity);
		if (i > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<HashMap<String, Object>> getActivityLimited(Integer startIndex,
			Integer endIndex, String latitude, String longitude, String type) {

		List<HashMap<String, Object>> list = null;
		if (type == null || type.equals("")) {
			type = "all";
		}

		if (type.equals("all")) {

			list = activityMapper.selectLimited(startIndex, endIndex,
					"activity_create_time");

			for (int i = 0; i < list.size(); i++) {
				String lng = (String) list.get(i).get("activity_lng");
				String lat = (String) list.get(i).get("activity_lat");
				String distance = MapDistanceUtil.getDistance(lat, lng, latitude,
						longitude);
				list.get(i).put("distance", distance);

			}
		} else if(type.equals("time")) {
			list = activityMapper.selectLimited(startIndex, endIndex,
					"activity_time");
			for (int i = 0; i < list.size(); i++) {
				String lng = (String) list.get(i).get("activity_lng");
				String lat = (String) list.get(i).get("activity_lat");
				String distance = MapDistanceUtil.getDistance(lat, lng, latitude,
						longitude);
				list.get(i).put("distance", distance);

			}

		}else {
			Map<String, String> map = MapDistanceUtil.getAround(latitude,
					longitude, "20000");
			list = activityMapper.selectNearby(Double.valueOf(latitude),Double.valueOf(longitude) ,
					map.get("minLat"), map.get("maxLat"), map.get("minLng"),
					map.get("maxLng"), startIndex, endIndex);
			for (int i = 0; i < list.size(); i++) {
				Double distanceD =(Double) list.get(i).get("distance");
				String distance = MapDistanceUtil.getStandardDistance(distanceD*1000 + "");
				list.get(i).put("distance", distance);

			}
			
		}

		return list;
	}

	@Override
	public List<HashMap<String, Object>> getActivityByKeyWord(String keyWord,
			Integer startIndex, Integer endIndex,String latitude, String longitude ) {
		
		List<HashMap<String, Object>> list = null;

		list = activityMapper.selectLimitedbyKeyWord(keyWord, startIndex,
				endIndex);

		
		
		for (int i = 0; i < list.size(); i++) {
			String lng = (String) list.get(i).get("activity_lng");
			String lat = (String) list.get(i).get("activity_lat");
			String distance = MapDistanceUtil.getDistance(lat, lng, latitude,
					longitude);
			list.get(i).put("distance", distance);

		}
		
		return list;
	}

	@Override
	public List<HashMap<String, Object>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex) {
		// TODO Auto-generated method stub
		return activityMapper.selectLimitedbyUserId(userId, startIndex,
				endIndex);
	}

}
