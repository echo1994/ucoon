package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Orders;

import java.util.HashMap;
import java.util.List;

public interface OrderMapper {
	int deleteByPrimaryKey(Integer orderId);

	int insert(Orders record);

	Orders selectByPrimaryKey(Integer orderId);

	List<Orders> selectAll();

	int updateByPrimaryKey(Orders record);

	List<HashMap<String, String>> selectOrdersLimited(Integer userId,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, String>> selectOrderbyUMID(Integer userId,
			Integer missionId);

	Integer selectOrdersCountByM(Integer missionId);
}