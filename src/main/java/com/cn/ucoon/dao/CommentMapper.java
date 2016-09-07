package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Comments;

import java.util.HashMap;
import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comments record);

    Comments selectByPrimaryKey(Integer commentId);

    List<Comments> selectAll();

    int updateByPrimaryKey(Comments record);
    
    List<HashMap<String, Object>> selectLimitedbyMissionId(Integer missionId,
			Integer startIndex, Integer endIndex);
}