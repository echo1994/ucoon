package com.cn.ucoon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.SignMapper;
import com.cn.ucoon.pojo.Sign;
import com.cn.ucoon.service.SignService;

@Service
@Transactional
public class SignServiceImpl implements SignService{

	@Autowired
	private SignMapper signMapper;
	
	@Override
	public Sign selectByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return signMapper.selectByUserId(userId);
	}

}
