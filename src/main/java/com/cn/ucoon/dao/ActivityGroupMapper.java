package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.ActivityGroup;
import java.util.List;

public interface ActivityGroupMapper {
    int insert(ActivityGroup record);

    List<ActivityGroup> selectAll();
}