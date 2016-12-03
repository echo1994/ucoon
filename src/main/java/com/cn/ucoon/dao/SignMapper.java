package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Sign;
import java.util.List;

public interface SignMapper {
    int deleteByPrimaryKey(Integer signId);

    int insert(Sign record);

    Sign selectByPrimaryKey(Integer signId);

    Sign selectByUserId(Integer userId);
    
    List<Sign> selectAll();

    int updateByPrimaryKey(Sign record);
}