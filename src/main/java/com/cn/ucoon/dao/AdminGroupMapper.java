package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.AdminGroup;
import java.util.List;

public interface AdminGroupMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(AdminGroup record);

    AdminGroup selectByPrimaryKey(Integer groupId);

    List<AdminGroup> selectAll();

    int updateByPrimaryKey(AdminGroup record);
}