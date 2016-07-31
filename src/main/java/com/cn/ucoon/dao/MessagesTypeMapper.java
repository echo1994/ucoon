package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.MessagesType;
import java.util.List;

public interface MessagesTypeMapper {
    int deleteByPrimaryKey(Integer messageTypeId);

    int insert(MessagesType record);

    MessagesType selectByPrimaryKey(Integer messageTypeId);

    List<MessagesType> selectAll();

    int updateByPrimaryKey(MessagesType record);
}