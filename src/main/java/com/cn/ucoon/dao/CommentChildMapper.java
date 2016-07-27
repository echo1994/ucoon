package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.CommentChild;
import java.util.List;

public interface CommentChildMapper {
    int insert(CommentChild record);

    List<CommentChild> selectAll();
}