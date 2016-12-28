package com.cn.ucoon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.ServerWeixinMapper;
import com.cn.ucoon.pojo.ServerWeixin;
import com.cn.ucoon.pojo.ServiceMessage;
import com.cn.ucoon.service.ServerWeixinService;
import com.cn.ucoon.service.ServiceMessageService;

@Service
@Transactional
public class ServerWeixinServiceImpl implements ServerWeixinService{

	@Autowired
	private ServerWeixinMapper serverWeixinMapper;
	
	@Override
	public List<ServerWeixin> getServer() {
		
		return serverWeixinMapper.getServer();
	}
	
}
