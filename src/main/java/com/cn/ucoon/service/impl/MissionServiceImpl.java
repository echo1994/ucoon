package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.MissionMapper;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.service.MissionService;

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
	public List<HashMap<String, String>> getMissionLimited(Integer startIndex,
			Integer endIndex) {
		// TODO Auto-generated method stub

		return missionMapper.selectLimited(startIndex, endIndex);
	}

	@Override
	public List<HashMap<String, String>> getMissionByKeyWord(String keyWord,
			Integer startIndex, Integer endIndex) {
		// TODO Auto-generated method stub

		return missionMapper.selectLimitedbyKeyWord(keyWord, startIndex,
				endIndex);
	}

	@Override
	public List<HashMap<String, String>> selectLimitedbyUserId(Integer userId,
			Integer startIndex, Integer endIndex) {
		// TODO Auto-generated method stub
		return missionMapper
				.selectLimitedbyUserId(userId, startIndex, endIndex);
	}

	@Override
	public List<HashMap<String, String>> selectLimitedbyUserIdAndStatus(
			Integer userId, Integer missionStatus, Integer startIndex,
			Integer endIndex) {
		// TODO Auto-generated method stub
		return missionMapper.selectLimitedbyUserIdAndStatus(userId,
				missionStatus, startIndex, endIndex);
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
