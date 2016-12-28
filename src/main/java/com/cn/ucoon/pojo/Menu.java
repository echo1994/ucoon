package com.cn.ucoon.pojo;
 

public class Menu implements java.io.Serializable{
	private Integer Id;
	private Integer MenuId;
	private String MenuName;
	private String Url;
	/*{"Id":1,"Level":1,"MenuName":"网站基本信息","ParentMenu":0,"SubMenus":[
{"Id":2,"Level":2,"MenuName":"网站信息设置","ParentMenu":1,"SubMenus":null,"Url":"admin/main_info"},
{"Id":3,"Level":2,"MenuName":"广告位设置","ParentMenu":1,"SubMenus":null,"Url":"admin/adv_list"}
],"Url":"www.ucoon.cn"},*/
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getMenuName() {
		return MenuName;
	}
	public void setMenuName(String menuName) {
		MenuName = menuName;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public Integer getMenuId() {
		return MenuId;
	}
	public void setMenuId(Integer menuId) {
		MenuId = menuId;
	}
	
}
