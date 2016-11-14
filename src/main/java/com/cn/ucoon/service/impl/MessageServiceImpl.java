package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.MessagesMapper;
import com.cn.ucoon.pojo.Messages;
import com.cn.ucoon.service.MessageService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessagesMapper messagesMapper;

	
	@Override
	public List<HashMap<String, Object>> selectMsgListbyUserId(Integer userId) {
		// TODO Auto-generated method stub
		return messagesMapper.selectMsgListbyUserId(userId);
	}


	@Override
	public List<HashMap<String, Object>> selectMsgHistory(Integer fromUserId,
			Integer toUserId, Integer startPage, Integer pageSize) {
		// TODO Auto-generated method stub
		return messagesMapper.selectMsgHistory(fromUserId, toUserId, startPage, pageSize);
	}


	@Override
	public boolean insert(Messages messages) {
		int i = messagesMapper.insert(messages);
		if(i > 0){
			return true;
		}
		
		
		return false;
	}

}
