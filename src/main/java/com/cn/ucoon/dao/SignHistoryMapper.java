package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.SignHistory;
import java.util.List;

public interface SignHistoryMapper {
    int deleteByPrimaryKey(Integer signHistoryId);

    int insert(SignHistory record);

    SignHistory selectByPrimaryKey(Integer signHistoryId);

    List<SignHistory> selectAll();

    int updateByPrimaryKey(SignHistory record);
}