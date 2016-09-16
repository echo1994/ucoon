package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Credits;
import java.util.List;

public interface CreditsMapper {
    int deleteByPrimaryKey(Integer creditsId);

    int insert(Credits record);

    Credits selectByPrimaryKey(Integer creditsId);

    List<Credits> selectAll();

    int updateByPrimaryKey(Credits record);
}