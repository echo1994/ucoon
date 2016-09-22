package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
	List<HashMap<String, String>> selectOrdersLimited(Integer userId,
			Integer startIndex, Integer endIndex);

	List<HashMap<String, String>> selectOrderbyUMID(Integer userId,
			Integer missionId);

	Integer selectOrdersCountByM(Integer missionId);
	
	List<HashMap<String, String>> selectorderDetailsByOrderId(Integer orderId);
	
	Integer updateOrderStateFinished(Integer orderId);
}
