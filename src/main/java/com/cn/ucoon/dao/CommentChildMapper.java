package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.CommentsChild;

import java.util.HashMap;
import java.util.List;

public interface CommentChildMapper {
    int deleteByPrimaryKey(Integer commentChildId);

    int deleteByCommentsId(Integer commentId);

    int insert(CommentsChild record);

    CommentsChild selectByPrimaryKey(Integer commentChildId);

    List<CommentsChild> selectAll();

    int updateByPrimaryKey(CommentsChild record);
    
    List<HashMap<String, Object>> selectbyCommentsId(Integer commentsId);
    
   
}