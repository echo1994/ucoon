package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.ServiceMessageMapper;
import com.cn.ucoon.pojo.ServiceMessage;
import com.cn.ucoon.service.ServiceMessageService;

@Service
@Transactional
public class ServiceMessageServiceImpl implements ServiceMessageService{

	@Resource
	private ServiceMessageMapper  serviceMessageMapper;
	
	 
	@Override
	public List<ServiceMessage> getAllServiceMessage() {
		// TODO Auto-generated method stub
		return serviceMessageMapper.getAllServiceMessage();
	}
 

	@Override
	public Integer delOneServiceMessage(Integer servicemessageId) {
		// TODO Auto-generated method stub
		return serviceMessageMapper.delOneServiceMessage(servicemessageId);
	}

	@Override
	public Integer delmoreServiceMessage(Integer[] servicemessageIds) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shortmessageIds", servicemessageIds);
		Integer status = serviceMessageMapper.delmoreServiceMessage(map);
		return status;
	}


	@Override
	public Integer sendOneServiceMessage(ServiceMessage servicemsg) {
		// TODO Auto-generated method stub
		return serviceMessageMapper.sendOneServiceMessage(servicemsg);
	}


	@Override
	public List<String> getAllOpenId() {
		// TODO Auto-generated method stub
		return serviceMessageMapper.getAllOpenId();
	}

}
