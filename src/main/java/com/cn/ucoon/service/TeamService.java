package com.cn.ucoon.service;

import java.util.List;

import com.cn.ucoon.pojo.ApplyTeam;

public interface TeamService {

	public int deleteByPrimaryKey(Integer id);

	public int insert(ApplyTeam record);

	public ApplyTeam selectByPrimaryKey(Integer id);

	public List<ApplyTeam> selectAll();

	public int updateByPrimaryKey(ApplyTeam record);
}
