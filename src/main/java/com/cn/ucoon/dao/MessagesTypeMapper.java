package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.MessagesType;
import java.util.List;

public interface MessagesTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessagesType record);

    MessagesType selectByPrimaryKey(Integer id);

    List<MessagesType> selectAll();

    int updateByPrimaryKey(MessagesType record);
}