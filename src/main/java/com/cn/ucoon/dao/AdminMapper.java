package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Admin;
import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer adminId);
    
    Admin selectByName(String adminName);

    List<Admin> selectAll();

    int updateByName(Admin record);
    
    
    /**
     * 验证用户名
     */
    Admin selectByUsernameAndPassword(Admin record);
}