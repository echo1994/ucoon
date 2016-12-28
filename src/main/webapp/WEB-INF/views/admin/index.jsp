<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>


<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>后台管理系统</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="css/cj.css" type="text/css" />
		<link rel="stylesheet" href="css/frame.css" type="text/css"/>
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	    <![endif]-->	
	</head>
	<body>
		<!--头部-->
		<div class="topheader">
			<h1 class="logo"><img src="images/bus_logo.png" width="82"></h1><span class="slogan">后台管理系统</span>
			<div class="pd pull-right">
				欢迎<span>${userName}</span>登录后台管理系统
				<b>|</b>
				<a href="">网站首页</a>
				<b>|</b>
				<a data-toggle="modal" data-target="#edit_password">修改密码</a>
				<b>|</b>
				<a href="admin/logout">退出登录</a>
			</div>
			<br style="clear:both"  />			
		</div>
		<!--导航-->
		<div class="header">
	    	<ul class="headermenu">
	        	<li><a href="index.html?type=index">首页${requestScope.right}</a></li>
	        	<li><a href="index.html?type=user">会员管理</a></li>
	        	<li><a href="index.html?type=company">企业管理</a></li>
	        	<li><a href="index.html?type=order">订单管理</a></li>
	        	<li><a href="index.html?type=letter">站内信管理</a></li>
			</ul>
	    </div>
	    <div class="main">
	    	<!--左侧导航列表-->
	    	<div class="left_nav" id="if_url">
	    		<ul>
	    			 
	    		</ul>
	    	</div>
	    	<!--右侧框架-->
    		<div class="centercontent">
    			<!--tab libox-->
    			<div class="bq_nav" id="ifr_libox">
    				
    			</div>
    			<!--iframe box-->
    			<div id="iframeleft">
    				
    			</div>
    		</div>
	    </div>
	</body>
</html>
<!--修改密码框-->
<div class="modal fade" id="edit_password" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form name="" id="" action="" method="post" class="form-horizontal">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">修改密码</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label">原密码</label>
					    <div class="col-sm-6">
					      <input type="password" name="password"  class="form-control input-sm">
					    </div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label">新密码</label>
					    <div class="col-sm-6">
					      <input type="password" name="new_password"  class="form-control input-sm">
					    </div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label">确认密码</label>
					    <div class="col-sm-6">
					      <input type="password" name="qy_password"  class="form-control input-sm">
					    </div>
					</div>
				</div>
				<div class="modal-footer">
		          <button type="submit" class="btn btn-success btn-sm" onclick="">确定</button>
		        </div>
	        </form>
		</div>
	</div>
</div>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/bootstrap.min.js"></script>
<script src="js/tongyong.js" type="text/javascript"></script> 
<!-- <script src="js/index_list_ajax.js" type="text/javascript"></script> 
  -->
<script type="text/javascript">
var left_list=null;
//加载完html dom就执行
$(document).ready(function(){
	$.ajax({
			url:"admin/getPermission",
			success:function(result){
				left_list=result
				initMenu();
				}
			}); 
});
function initMenu(){
	//获取type值
	var type=getQueryString("type");
	//列表框
	var list_box="";
	for(var i in left_list){
	//对于字符参数要加上引号  否则会被当成不存在变量 导致不执行 
		list_box+=" <li  class=\"one_list\"><a style=\"text-align:center\" href=\"javascript:;\" onclick=\"create_ifr("+left_list[i].menuId+",'"+left_list[i].url+"','"+left_list[i].menuName+"')()\">"+left_list[i].menuName+"<span style=\"float:right\">>></span></a></li>";/* +left_list[i].menuName+"<span style=\"float:right\">  >>  </span></a>"; */
	 }
	$("#if_url ul").html(list_box);
}
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

</script>
