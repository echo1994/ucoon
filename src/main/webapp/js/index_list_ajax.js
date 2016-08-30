$(function(){

	//获取type值
	var type=getQueryString("type");
	//左侧列表 ajax获取
	var left_list=[
		{"Id":1,"Level":1,"MenuName":"网站基本信息","ParentMenu":0,"SubMenus":[
			{"Id":2,"Level":2,"MenuName":"网站信息设置","ParentMenu":1,"SubMenus":null,"Url":"admin/main_info"},
			{"Id":3,"Level":2,"MenuName":"广告位设置","ParentMenu":1,"SubMenus":null,"Url":"admin/adv_list"}
		],"Url":"www.ucoon.cn"},
		{"Id":5,"Level":1,"MenuName":"管理员","ParentMenu":0,"SubMenus":[
			{"Id":7,"Level":2,"MenuName":"管理员分组列表","ParentMenu":1,"SubMenus":null,"Url":"admin/admingroup_list"},
			{"Id":6,"Level":2,"MenuName":"管理员列表","ParentMenu":1,"SubMenus":null,"Url":"admin/admin_list"}
		],"Url":"www.ucoon.cn"},
		{"Id":8,"Level":1,"MenuName":"会员管理","ParentMenu":0,"SubMenus":[
			{"Id":9,"Level":2,"MenuName":"会员管理","ParentMenu":1,"SubMenus":null,"Url":"user/user_list.html"},
			{"Id":10,"Level":2,"MenuName":"会员管理","ParentMenu":1,"SubMenus":null,"Url":"user/jianli_list.html"}
		],"Url":"www.ucoon.cn"},
		{"Id":11,"Level":1,"MenuName":"企业管理","ParentMenu":0,"SubMenus":[
			{"Id":12,"Level":2,"MenuName":"企业管理","ParentMenu":1,"SubMenus":null,"Url":"company/company_list.html"},
			{"Id":13,"Level":2,"MenuName":"工作管理","ParentMenu":1,"SubMenus":null,"Url":"company/job_list.html"},
			{"Id":14,"Level":2,"MenuName":"职位管理","ParentMenu":1,"SubMenus":null,"Url":"company/position_list.html"}
		],"Url":"www.ucoon.cn"},
		{"Id":15,"Level":1,"MenuName":"订单管理","ParentMenu":0,"SubMenus":[
			{"Id":16,"Level":2,"MenuName":"工作订单","ParentMenu":1,"SubMenus":null,"Url":"order/order_job.html"},
			{"Id":17,"Level":2,"MenuName":"资金明细","ParentMenu":1,"SubMenus":null,"Url":"order/detailed_user.html"}
		],"Url":"www.ucoon.cn"},
		{"Id":18,"Level":1,"MenuName":"站内信","ParentMenu":0,"SubMenus":[
			{"Id":19,"Level":2,"MenuName":"站内信管理","ParentMenu":1,"SubMenus":null,"Url":"msg/msg_list.html"}
		],"Url":"www.ucoon.cn"}
	];

	//列表框
	var list_box="";
	for(var i in left_list){
		list_box+='<li class="one_list"><a href="javascript:;" class="tables">'+left_list[i].MenuName+'<span class="arrow"></span></a><ul>';
		for(var j in left_list[i].SubMenus){
			//list_box+='<li><a href="'+left_list[i].SubMenus[j].Url+'" target="iframeleft">'+left_list[i].SubMenus[j].MenuName+'</a></li>';
			list_box+="<li><a href=\"javascript:;\" onclick=\"create_ifr('"+left_list[i].SubMenus[j].Id+"','"+left_list[i].SubMenus[j].Url+"','"+left_list[i].SubMenus[j].MenuName+"')\">"+left_list[i].SubMenus[j].MenuName+"</a></li>";
		}
		list_box+='</ul></li>';
	}
	
	$("#if_url ul").html(list_box);
	//显示二级分类
	$(document).on("click",".left_nav > ul li a",function(){
　　　　	var obj= $(this).next('ul');
		if($(obj).css("display")=="none"){
			obj.show(300);
		}else{
			obj.hide(300);
		}
　　	});
})
//创建iframe
function create_ifr(Id,Url,MenuName){
	if(document.getElementById("div_" + Id) == null){
		//创建iframe
		var ifrbox=$("<iframe id=\"div_"+Id+"\" src=\""+Url+"\"></iframe>");
		$("#iframeleft").append(ifrbox);
		
		
		//li和iframe 的数量
		var tablist = document.getElementById("ifr_libox").getElementsByTagName('li');
		var pannellist = document.getElementById("iframeleft").getElementsByTagName('iframe');
		if (tablist.length > 0){
			for (i = 0; i < tablist.length; i++)
			{
				tablist[i].className = "";
				pannellist[i].style.display = "none";
			}
		}		
		//创建li菜单
		var tab = $("<li onclick=\"change_tab('"+Id+"')\" class=\"action\" id=\"li_"+Id+"\">"+MenuName+"<i onclick=\"del_ifr('" + Id + "')\" title=\"关闭当前窗口\" class=\"ir\"></i><i onclick=\"reload_ifr('" + Id + "')\" title=\"刷新页面\" class=\"il\"></i></li>")
		$("#ifr_libox").append(tab);
	}else{
		var tablist = document.getElementById("ifr_libox").getElementsByTagName('li');
		var pannellist = document.getElementById("iframeleft").getElementsByTagName('iframe');
		//alert(tablist.length);
		for (i = 0; i < tablist.length; i++)
		{
			tablist[i].className = "";
			pannellist[i].style.display = "none"
		}
		document.getElementById("li_" +Id).className = 'action';
		document.getElementById("div_" + Id).style.display = 'block';
		document.getElementById("div_" + Id).src=Url;
	}
}
//删除iframe
function del_ifr(Id){
	$("#li_"+Id).remove();
	$("#div_"+Id).remove();
	var tablist = document.getElementById("ifr_libox").getElementsByTagName('li');
		var pannellist = document.getElementById("iframeleft").getElementsByTagName('iframe');
	if (tablist.length > 0)
	{
		tablist[tablist.length - 1].className = 'action';
		pannellist[tablist.length - 1].style.display = 'block';
	}
	stopPropagation(event);
}
//刷新iframe
function reload_ifr(Id){
	document.getElementById("div_" + Id).contentWindow.location.reload();
	stopPropagation(event);
}
//阻止冒泡
function stopPropagation(e) {  
    e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }  
}
//切换tab
function change_tab(Id){
	var tablist = document.getElementById("ifr_libox").getElementsByTagName('li');
	var pannellist = document.getElementById("iframeleft").getElementsByTagName('iframe');
	//alert(tablist.length);
	for (i = 0; i < tablist.length; i++)
	{
		tablist[i].className = "";
		pannellist[i].style.display = "none"
	}
	document.getElementById("li_" +Id).className = 'action';
	document.getElementById("div_" + Id).style.display = 'block';
}
