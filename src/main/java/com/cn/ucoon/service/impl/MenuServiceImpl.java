package com.cn.ucoon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cn.ucoon.dao.MenuMapper;
import com.cn.ucoon.pojo.Menu;
import com.cn.ucoon.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getMenu(Integer adminId) {
		// TODO Auto-generated method stub
		return menuMapper.getMenu(adminId);
	} 
	
	
/*	public List<Menu> getMenu(Integer adminId) {
		List<Menu> menuList = menuMapper.getMenu(adminId);
		int i = 1;
		int level=0;
		for(Menu menu:menuList){
			menu.setId(i);
			level = menu.getParentMenu()==0?1:2;
			menu.setLevel(1);
			i++;
		//	addSubmit(menu,menuList);
		}
		System.out.println(JSON.toJSONString(menuList));
		return menuList;
	}
	
	public void addSubmit(Menu menu,List<Menu> menuList){
		int menuId = menu.getMenuId();
		for(int i = 0;i<menuList.size();i++){
			if(menuList.get(i).getParentMenu()==menuId){
			//	menu.setSubMenus(menu.getSubMenus().add(menuList.get(i)));
				addSubmit(menuList.get(i),menuList);
			}
		}
	}*/
 
	
	/*
	 * 这样的出局处理方法可以用在多重菜单里面， 递归
	 * public List<HashMap<String, Object>> getMenu(Integer adminId) {
				List<HashMap<String, Object>> maps = new ArrayList<>();//泛型的菱形写法 jdk1.7
				//所有的根目标
				List<Menu> permissions = menuMapper.getParentMenu(adminId);
				System.out.println("permissions.size():"+permissions.size());
				if(permissions!=null && permissions.size()>0){
					int i = 1;
					for (Menu permission : permissions) {
						HashMap<String, Object> map = new HashMap<>();
						int level = permission.getParentMenu()==0?1:2;
						map.put("level", level);
						map.put("url", permission.getUrl());
						map.put("menuName", permission.getMenuName());
						map.put("id", i);
						i++;
						map.put("SubMenus",getChildMenus(permission.getMenuId()));
						maps.add(map);
					}
					System.out.println("maps.size()==="+maps.size());
					System.out.println(JSON.toJSONString(maps));
					return maps;
				}else{
					return null;
				}
	}

	private List<HashMap<String, Object>> getChildMenus(Integer adminId) { 
		List<Menu> permissions = menuMapper.getChildMenu(adminId);
		if(permissions!=null && permissions.size()>0){
			List<HashMap<String, Object>> mapchild = new ArrayList<>();//泛型的菱形写法 jdk1.7
			int i = 1;
			for (Menu permission : permissions) {
				HashMap<String, Object> map = new HashMap<>();
				int level = permission.getParentMenu()==0?1:2;
				map.put("level",level);
				map.put("url", permission.getUrl());
				map.put("menuName", permission.getMenuName());
				map.put("id", i);
				i++;
				map.put("SubMenus",getChildMenus(permission.getMenuId()));
				mapchild.add(map);
			}
			return mapchild;
		}
			return null;
	}*/

	

}
