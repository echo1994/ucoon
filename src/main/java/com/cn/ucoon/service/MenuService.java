package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Menu;


public interface MenuService {

	public List<Menu> getMenu(Integer adminId);
	//public List<Menu> getChildMenu(Integer adminId);
}
