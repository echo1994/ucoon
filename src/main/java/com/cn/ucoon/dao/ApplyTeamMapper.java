package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.ApplyTeam;
import java.util.List;

public interface ApplyTeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplyTeam record);

    ApplyTeam selectByPrimaryKey(Integer id);

    List<ApplyTeam> selectAll();

    int updateByPrimaryKey(ApplyTeam record);
}