package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.MissionType;
import java.util.List;

public interface MissionTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MissionType record);

    MissionType selectByPrimaryKey(Integer id);

    List<MissionType> selectAll();

    int updateByPrimaryKey(MissionType record);
}