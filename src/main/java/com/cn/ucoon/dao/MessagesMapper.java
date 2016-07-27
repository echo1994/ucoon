package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Messages;
import java.util.List;

public interface MessagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Messages record);

    Messages selectByPrimaryKey(Integer id);

    List<Messages> selectAll();

    int updateByPrimaryKey(Messages record);
}