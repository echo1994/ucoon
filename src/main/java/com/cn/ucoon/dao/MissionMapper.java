package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Mission;
import java.util.List;

public interface MissionMapper {
    int deleteByPrimaryKey(Integer missionId);

    int insert(Mission record);

    Mission selectByPrimaryKey(Integer missionId);

    List<Mission> selectAll();

    int updateByPrimaryKey(Mission record);
}