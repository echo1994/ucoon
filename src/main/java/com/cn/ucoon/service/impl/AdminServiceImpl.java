package com.cn.ucoon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ucoon.dao.AdminMapper;
import com.cn.ucoon.pojo.Admin;
import com.cn.ucoon.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Resource
	private AdminMapper adminDao;
	
	@Override
	public Admin checkUser(String username, String password) {
		
		Admin selectAdmin = new Admin();
		selectAdmin.setAdminName(username);
		selectAdmin.setAdminPsw(password);
 		
		Admin admin = adminDao.selectByUsernameAndPassword(selectAdmin);
		return admin;
	}

	@Override
	public List<Admin> getAllAdmins() {
		// TODO Auto-generated method stub
		return adminDao.selectAll();
	}

}
