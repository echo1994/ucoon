package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    int deleteByPrimaryKey(@Param("commentId") Integer commentId, @Param("userId") Integer userId, @Param("missionId") Integer missionId);

    int insert(Comment record);

    Comment selectByPrimaryKey(@Param("commentId") Integer commentId, @Param("userId") Integer userId, @Param("missionId") Integer missionId);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);
}