package com.cn.ucoon.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.MissionAddressMapper;
import com.cn.ucoon.dao.MissionMapper;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.MissionAddress;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.util.MapDistanceUtil;

@Service
@Transactional
public class MissionServiceImpl implements MissionService {

	@Autowired
	private MissionMapper missionMapper;
	
	@Autowired
	private MissionAddressMapper addressMapper;

	@Override
	public boolean publishMission(Mission mission) {
		// TODO Auto-generated method stub
		
		if(missionMapper.insert(mission) > 0){
			return true;
		}
		
		return false;
	}

	@SuppressWarnings("unused")
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
				
				BigDecimal count = new BigDecimal( (Integer)list.get(i).get("people_count"));
				BigDecimal prize = (BigDecimal) list.get(i).get("mission_price");
				BigDecimal singlePrize = prize.divide(count,2, BigDecimal.ROUND_FLOOR);//接近负无穷大的舍入
				list.get(i).put("singlePrize", singlePrize);
				
				String lng = (String) list.get(i).get("mission_lng");
				String lat = (String) list.get(i).get("mission_lat");
				if(latitude != null || latitude != ""){
					String distance = MapDistanceUtil.getDistance(lat, lng, latitude,
							longitude);
					list.get(i).put("distance", distance);
					
				}else{
					
					list.get(i).put("distance", "");
				}
				

			}
			
			
		}else {
			if(latitude == null || latitude == ""){
				
				return null;
			}
			
			Map<String, String> map = MapDistanceUtil.getAround(latitude,
					longitude, "20000");
			list = missionMapper.selectNearby(Double.valueOf(latitude),Double.valueOf(longitude) ,
					map.get("minLat"), map.get("maxLat"), map.get("minLng"),
					map.get("maxLng"), startIndex, endIndex);
			for (int i = 0; i < list.size(); i++) {
				
				BigDecimal count = new BigDecimal( (Integer)list.get(i).get("people_count"));
				BigDecimal prize = (BigDecimal) list.get(i).get("mission_price");
				BigDecimal singlePrize = prize.divide(count,2, BigDecimal.ROUND_FLOOR);//接近负无穷大的舍入
				list.get(i).put("singlePrize", singlePrize);
				
				
				
				Double distanceD =(Double) list.get(i).get("distance");
				String distance = MapDistanceUtil.getStandardDistance(distanceD*1000 + "");
				
				list.get(i).put("distance", distance);

			}
			
		}
		
		
		return list;
	}

	@SuppressWarnings("unused")
	@Override
	public List<HashMap<String, Object>> getMissionByKeyWord(String keyWord,
			Integer startIndex, Integer endIndex,String latitude,String longitude) {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> list = null;

		list = missionMapper.selectLimitedbyKeyWord(keyWord, startIndex,
				endIndex);

		for (int i = 0; i < list.size(); i++) {
			
			BigDecimal count = new BigDecimal( (Integer)list.get(i).get("people_count"));
			BigDecimal prize = (BigDecimal) list.get(i).get("mission_price");
			BigDecimal singlePrize = prize.divide(count,2, BigDecimal.ROUND_FLOOR);//接近负无穷大的舍入
			list.get(i).put("singlePrize", singlePrize);
			
			
			String lng = (String) list.get(i).get("mission_lng");
			String lat = (String) list.get(i).get("mission_lat");
			
			if(latitude != null || latitude != ""){
				String distance = MapDistanceUtil.getDistance(lat, lng, latitude,
						longitude);
				list.get(i).put("distance", distance);
				
			}else{
				
				list.get(i).put("distance", "");
			}
			

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
	public HashMap<String, Object> selectForMissionDetails(
			Integer missionId) {
		// TODO Auto-generated method stub
		HashMap<String, Object> detail = missionMapper.selectForMissionDetails(missionId);
		BigDecimal count = new BigDecimal( (Integer)detail.get("people_count"));
		BigDecimal prize = (BigDecimal) detail.get("mission_price");
		BigDecimal singlePrize = prize.divide(count,2, BigDecimal.ROUND_FLOOR);
		detail.put("singlePrize", singlePrize);
		return detail;
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

	@Override
	public boolean isPaid(Integer missionId) {
		Integer status = missionMapper.selectStatusByMissionId(missionId);
		
		if(status == 1){
			
			//表示已支付
			return true;
			
		}
		
		return false;
	}

	@Override
	public Integer getUserIdByMissionId(Integer missionId) {
		// TODO Auto-generated method stub
		return missionMapper.getUserIdByMissionId(missionId);
	}

	@Override
	public Integer countUnPaidMission(Integer userId) {
		// TODO Auto-generated method stub
		return missionMapper.selectUnPaidMissionByUserId(userId);
	}

	@Override
	public Integer countMissionDoneByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return missionMapper.countMissionDoneByUserId(userId);
	}

	@Override
	public boolean addMissionAddress(MissionAddress address) {
		if(addressMapper.insert(address) > 0){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isAddressExist(String place) {
		
		MissionAddress address = addressMapper.selectByPlace(place);
		
		if(address != null){
			return true;
		}
		
		return false;
	}

	@Override
	public List<MissionAddress> selectAllMissionAddressByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return addressMapper.selectAllByUserId(userId);
	}

	@Override
	public void unPaidMissionScan(Date date) {
		// TODO Auto-generated method stub
		int i = missionMapper.unPaidMissionScan(date);
		
		System.out.println("未支付订单列号 ： " + i);
	}
}
