package com.cn.ucoon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.AdminGroupMapper;
import com.cn.ucoon.dao.AdminMapper;
import com.cn.ucoon.dao.FeedbackMapper;
import com.cn.ucoon.dao.MissionMapper;
import com.cn.ucoon.dao.ReportMapper;
import com.cn.ucoon.dao.UserMapper;
import com.cn.ucoon.pojo.Admin;
import com.cn.ucoon.pojo.AdminGroup;
import com.cn.ucoon.pojo.Feedback;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.Pemission;
import com.cn.ucoon.pojo.Report;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

	@Resource
	private AdminMapper adminDao;
	@Resource
	private AdminGroupMapper admingroupDao;
	@Resource
	private UserMapper userDao;
	@Resource
	private FeedbackMapper feedbackDao;
	@Resource
	private ReportMapper reportDao;
	@Resource
	private MissionMapper missionDao;
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
	
	@Override
	public List<AdminGroup> getAllAdminGroups() {
		// TODO Auto-generated method stub
		return admingroupDao.selectAll();
	}
	
	@Override
	public int insertAdmin(Admin record) {
		// TODO Auto-generated method stub
		return adminDao.insert(record);
	}
	
	@Override
	public int deleteAdmins(int id) {
		// TODO Auto-generated method stub
		return adminDao.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteAdminGroups(int id) {
		// TODO Auto-generated method stub
		return admingroupDao.deleteByPrimaryKey(id);
	}

	@Override
	public Admin findbyName(String adminName) {
		// TODO Auto-generated method stub
		return adminDao.selectByName(adminName);
	}

	@Override
	public int updateByname(Admin admin) {
		// TODO Auto-generated method stub
		return adminDao.updateByName(admin);
	}

	@Override
	public List<Pemission> getAllPemission() {
		// TODO Auto-generated method stub
		return admingroupDao.selectAllPemission();
	}

	@Override
	public int insertAdminGroups(AdminGroup record) {
		// TODO Auto-generated method stub
		return admingroupDao.insert(record);
	}
	
	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userDao.selectAll();
	}

	@Override
	public AdminGroup findgroupByName(String name) {
		// TODO Auto-generated method stub
		return admingroupDao.selectByName(name);
	}

	@Override
	public int insertGroup_Pemission(Integer groupId, Integer pemissionId) {
		// TODO Auto-generated method stub
		return admingroupDao.insertPemission_group(groupId, pemissionId);
	}

	@Override
	public int deleteGroup_Pemission(Integer groupId) {
		// TODO Auto-generated method stub
		return admingroupDao.deletePemission_group(groupId);
	}

	@Override
	public String[] findPemissionBygroupId(Integer groupId) {
		// TODO Auto-generated method stub
		return admingroupDao.selectPemissionByGroupId(groupId);
	}

	@Override
	public List<Feedback> getAllFeedback() {
		// TODO Auto-generated method stub
		return feedbackDao.selectAll();
	}

	@Override
	public List<Report> getAllReports() {
		// TODO Auto-generated method stub
		return reportDao.selectAll();
	}

	@Override
	public int delUserById(int userId) {
		// TODO Auto-generated method stub
		return userDao.deleteByPrimaryKey(userId);
	}

	@Override
	public int delMissionById(int missionId) {
		// TODO Auto-generated method stub
		return missionDao.deleteByPrimaryKey(missionId);
	}

	@Override
	public Mission findMissionById(int missionId) {
		// TODO Auto-generated method stub
		return missionDao.selectByPrimaryKey(missionId);
	}

	@Override
	public User findUserById(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectByPrimaryKey(userId);
	}

	@Override
	public Report findReportById(int reportId) {
		// TODO Auto-generated method stub
		return reportDao.selectByPrimaryKey(reportId);
	}

	@Override
	public int updateReport(Report rep) {
		// TODO Auto-generated method stub
		return reportDao.updateByPrimaryKey(rep);
	}

	@Override
	public int updateFeedback(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackDao.updateStatus(feedback);
	}
	
	@Override
	public int updateUserByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return userDao.updateByPrimaryKey(user);
	}

	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insert(user);
	}

	@Override
	public int xiaMission(Mission mission) {
		// TODO Auto-generated method stub
		return missionDao.updateByPrimaryKey(mission);
	}

}
