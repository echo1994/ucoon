package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Feedback;
import java.util.List;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer feedbackId);

    int insert(Feedback record);

    Feedback selectByPrimaryKey(Integer feedbackId);

    List<Feedback> selectAll();

    int updateByPrimaryKey(Feedback record);
}