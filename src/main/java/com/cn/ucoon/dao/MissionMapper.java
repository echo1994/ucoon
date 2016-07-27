package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Mission;
import java.util.List;

public interface MissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mission record);

    Mission selectByPrimaryKey(Integer id);

    List<Mission> selectAll();

    int updateByPrimaryKey(Mission record);
}