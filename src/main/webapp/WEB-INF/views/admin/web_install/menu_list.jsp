<%@page import="com.alibaba.fastjson.JSON"%>
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
		<link rel="stylesheet" href="css/list.css" type="text/css" />	
	</head>
	<body >
		<% JSON json=(JSON)request.getAttribute("data");  %>
		<%=json %>
		<div class="listbox">
			<div class="pagetitle">管理员列表</div>
			<div class="buttonz">
				<a href="admin/editMenu" class="btn btn-success btn-sm">修改菜单</a>
			</div>
		</div>
  </body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var anArray = <%=json%>;
		for(var i=0;i<anArray.length;i++){
			$("body").append("主菜单");
			$("body").append("<div class='item_title'>菜单名：</div>"+anArray[i].name+"<br>");
			$("body").append("<div class='item_title'>类型：</div>"+anArray[i].type+"<br>");
			$("body").append("<div class='item_title'>url：</div>"+anArray[i].url+"<br>");
			var zi=anArray[i].sub_button;
			if(zi.length>0){
			$("body").append("子菜单：");
			for(var j=0;j<zi.length;j++){
			$("body").append("<div class='item_title'>才单名：</div>"+anArray[i].name+"<br>");
			$("body").append("<div class='item_title'>类型：</div>"+anArray[i].type+"<br>");
			$("body").append("<div class='item_title'>url：</div>"+anArray[i].url+"<br>");
			}
			}
		}
	})
	
</script>
