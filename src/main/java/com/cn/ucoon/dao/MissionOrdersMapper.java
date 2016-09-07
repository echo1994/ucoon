package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.MissionOrders;
import java.util.List;

public interface MissionOrdersMapper {
    int deleteByPrimaryKey(Integer missionOrderId);

    int insert(MissionOrders record);

    MissionOrders selectByPrimaryKey(Integer missionOrderId);

    List<MissionOrders> selectAll();

    int updateByPrimaryKey(MissionOrders record);
}