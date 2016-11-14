package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Messages;

public interface MessageService {

	
	public List<HashMap<String, Object>> selectMsgListbyUserId(Integer userId);
	
	public List<HashMap<String, Object>> selectMsgHistory(Integer fromUserId,Integer toUserId,Integer startPage,Integer pageSize);
	
	public boolean insert(Messages messages);
}
