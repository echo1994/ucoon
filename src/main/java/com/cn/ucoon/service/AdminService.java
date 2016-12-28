package com.cn.ucoon.service;

import java.util.List;

import com.cn.ucoon.pojo.Admin;
import com.cn.ucoon.pojo.AdminGroup;
import com.cn.ucoon.pojo.Feedback;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.Pemission;
import com.cn.ucoon.pojo.Report;
import com.cn.ucoon.pojo.User;

public interface AdminService {

	/**
	 * 验证用户名和密码 
	 * @param username
	 * @param password
	 * @return
	 */
	public Admin checkUser(String username,String password);
	
	public List<Admin> getAllAdmins();
	
public int insertAdmin(Admin record);
	
	public Admin findbyName(String adminName);
	
	public int updateByname(Admin admin);
	
	public int deleteAdmins(int id);
	
	
	
	public List<AdminGroup> getAllAdminGroups();
	
	public int deleteAdminGroups(int id);
	
	public int insertAdminGroups(AdminGroup record);
	
	public AdminGroup findgroupByName(String name);
	
	//获取权限
	public List<Pemission> getAllPemission();
	
	//插入权限分配表
	public int insertGroup_Pemission(Integer groupId,Integer pemissionId);
	
	//删除权限分配表
	public int deleteGroup_Pemission(Integer groupId);
	
	//根据groupId获取权限
	public String[] findPemissionBygroupId(Integer groupId);
	
	//会员
	public List<User> getAllUser();
	
	//获取反馈信息
	public List<Feedback> getAllFeedback();
	
	//更新反馈信息
	public int updateFeedback(Feedback feedback);
	
	//获取举报信息
	public List<Report> getAllReports();
	
	//查询举报信息
	public Report findReportById(int reportId);
	
	//更新举报信息
	public int updateReport(Report rep);
	
	
	//删除微信用户
	public int delUserById(int userId);
	
	//查询微信用户
	public User findUserById(int userId);
	
	//删除任务
	public int delMissionById(int missionId);
	
	//查询任务
	public Mission findMissionById(int missionId);
	
	
	//更改微信用户信息
	public int updateUserByPrimaryKey(User user);
	
	//添加微信用户
	public int addUser(User user);
	
	
	//下架任务
	public int xiaMission(Mission mission);
	
}
