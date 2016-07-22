package com.cn.ucoon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ucoon.dao.IUserDao;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserDao userDao;

	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}
	
	
}
