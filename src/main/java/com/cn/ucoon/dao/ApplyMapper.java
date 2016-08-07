package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Apply;
import java.util.List;

public interface ApplyMapper {
    int deleteByPrimaryKey(Integer applyId);

    int insert(Apply record);

    Apply selectByPrimaryKey(Integer applyId);

    List<Apply> selectAll();

    int updateByPrimaryKey(Apply record);
}