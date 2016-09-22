package com.cn.ucoon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.MissionMapper;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.util.MapDistanceUtil;

@Service
@Transactional
public class MissionServiceImpl implements MissionService {

	@Autowired
	private MissionMapper missionMapper;

	@Override
	public boolean publishMission(Mission mission) {
		// TODO Auto-generated method stub
		
		if(missionMapper.insert(mission) > 0){
			return true;
		}
		
		return false;
	}

	@Override
	public List<HashMap<String, Object>> getMissionLimited(Integer startIndex,
			Integer endIndex,String latitude,String longitude,String type) {

		List<HashMap<String, Object>> list = null;
		if (type == null || type.equals("")) {
			type = "all";
		}

		if (type.equals("all")) {

			list = missionMapper.selectLimited(startIndex, endIndex);


			for (int i = 0; i < list.size(); i++) {
				String lng = (String) list.get(i).get("mission_lng");
				String lat = (String) list.get(i).get("mission_lat");
				String distance = MapDistanceUtil.getDistance(lat, lng, latitude,
						longitude);
				list.get(i).put("distance", distance);

			}
		}else {
			Map<String, String> map = MapDistanceUtil.getAround(latitude,
					longitude, "20000");
			list = missionMapper.selectNearby(Double.valueOf(latitude),Double.valueOf(longitude) ,
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
	public List<HashMap<String, Object>> getMissionByKeyWord(String keyWord,
			Integer startIndex, Integer endIndex,String latitude,String longitude) {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> list = null;

		list = missionMapper.selectLimitedbyKeyWord(keyWord, startIndex,
				endIndex);

		for (int i = 0; i < list.size(); i++) {
			String lng = (String) list.get(i).get("mission_lng");
			String lat = (String) list.get(i).get("mission_lat");
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
		return missionMapper
				.selectLimitedbyUserId(userId, startIndex, endIndex);
	}

	@Override
	public List<HashMap<String, Object>> selectLimitedbyUserIdAndStatus(
			Integer userId, List<Integer> list, Integer startIndex,
			Integer endIndex) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);

		map.put("startIndex", startIndex);
		
		map.put("userId", userId);
		
		map.put("endIndex", endIndex);
		
		return missionMapper.selectLimitedbyUserIdAndStatus(map);
	}

	@Override
	public HashMap<String, String> selectForMissionDetails(
			Integer missionId) {
		// TODO Auto-generated method stub
		return missionMapper.selectForMissionDetails(missionId);
	}

	@Override
	public Integer selectUserIdByMissionId(Integer missionId) {
		// TODO Auto-generated method stub
		return missionMapper.selectUserIdByMissionId(missionId);
	}

	@Override
	public Mission selectByPrimaryKey(Integer missionId) {
		// TODO Auto-generated method stub
		return missionMapper.selectByPrimaryKey(missionId);
	}

	@Override
	public Integer insertMission(Mission mission) {
		// TODO Auto-generated method stub
		return missionMapper.insert(mission);
	}

	@Override
	public Integer updateByPrimaryKey(Mission mission) {
		// TODO Auto-generated method stub
		return missionMapper.updateByPrimaryKey(mission);
	}

	@Override
	public void viewCount(Integer missionId) {
		// TODO Auto-generated method stub
		missionMapper.updateViewByPrimaryKey(missionId);
	}
}
