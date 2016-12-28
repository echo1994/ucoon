package com.cn.ucoon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.ApplyTeamMapper;
import com.cn.ucoon.dao.MissionMapper;
import com.cn.ucoon.pojo.ApplyTeam;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.TeamService;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

	
	@Autowired
	private ApplyTeamMapper applyTeamMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return applyTeamMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ApplyTeam record) {
		// TODO Auto-generated method stub
		return applyTeamMapper.insert(record);
	}

	@Override
	public ApplyTeam selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return applyTeamMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ApplyTeam> selectAll() {
		// TODO Auto-generated method stub
		return applyTeamMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(ApplyTeam record) {
		// TODO Auto-generated method stub
		return applyTeamMapper.updateByPrimaryKey(record);
	}

}
