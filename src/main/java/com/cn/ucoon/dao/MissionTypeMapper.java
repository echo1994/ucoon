package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.MissionType;
import java.util.List;

public interface MissionTypeMapper {
    int deleteByPrimaryKey(Integer missionTypeId);

    int insert(MissionType record);

    MissionType selectByPrimaryKey(Integer missionTypeId);

    List<MissionType> selectAll();

    int updateByPrimaryKey(MissionType record);
}