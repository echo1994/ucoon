package com.cn.ucoon.dao;

import java.util.List;

import com.cn.ucoon.pojo.AdminGroup;
import com.cn.ucoon.pojo.Pemission;

public interface AdminGroupMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(AdminGroup record);

    AdminGroup selectByPrimaryKey(Integer groupId);
    
    AdminGroup selectByName(String name);

    List<AdminGroup> selectAll();

    int updateByPrimaryKey(AdminGroup record);
    
    //按groupId查询权限
    String[] selectPemissionByGroupId(Integer groupId);
    
    //查询所有权限
    List<Pemission> selectAllPemission();
    
    //插入权限分配表
    int insertPemission_group(Integer groupId,Integer pemissionId);
    
    //删除权限分配表
    int deletePemission_group(Integer groupId);
}