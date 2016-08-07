package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.CommentChild;
import java.util.List;

public interface CommentChildMapper {
    int deleteByPrimaryKey(Integer commentChildId);

    int insert(CommentChild record);

    CommentChild selectByPrimaryKey(Integer commentChildId);

    List<CommentChild> selectAll();

    int updateByPrimaryKey(CommentChild record);
}