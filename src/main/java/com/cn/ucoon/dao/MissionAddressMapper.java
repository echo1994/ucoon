package com.cn.ucoon.dao;

import java.util.List;

import com.cn.ucoon.pojo.MissionAddress;

public interface MissionAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MissionAddress record);

    MissionAddress selectByPrimaryKey(Integer id);

    List<MissionAddress> selectAll();

    int updateByPrimaryKey(MissionAddress record);
    
    MissionAddress selectByPlace(String place);
    
    List<MissionAddress> selectAllByUserId(Integer userId);
}