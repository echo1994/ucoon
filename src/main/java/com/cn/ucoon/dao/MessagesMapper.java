package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Messages;
import java.util.List;

public interface MessagesMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Messages record);

    Messages selectByPrimaryKey(Integer messageId);

    List<Messages> selectAll();

    int updateByPrimaryKey(Messages record);
}