package com.cn.ucoon.dao;

import java.util.List;

import com.cn.ucoon.pojo.Menu;

public interface MenuMapper {
	public List<Menu> getMenu(Integer adminId); 
}
