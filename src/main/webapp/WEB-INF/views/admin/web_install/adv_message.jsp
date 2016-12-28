<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<style>
			td{text-align:center}
			td a{margin-right:20px}
		</style>
	</head>
	<body >
		<div class="listbox">
			<div class="pagetitle">消息管理 
			</div>
			
				<table class="table table-bordered table-condensed" style="width:40%;margin:0 auto;">
					<tr>
						<th style="width:3%">消息类型</th>
						<th style="width:20%">操作</th>
					</tr>
					<tr>
						<td style="width:3%">客服消息</td>
						<td colspan="7">
							<a href="admin/adv_mobile" >查看群发</a>
							<a href="admin/show_singserviceMessageMapping" >查看单发</a>
							<a href="admin/edit_allserviceMessageMapping" >群发</a>
							<a href="admin/edit_oneserviceMessageMapping" >单发</a>
						</td>
					</tr>
					<tr>
						<td style="width:3%">模板消息</td>
						<td colspan="7">
							<a href="admin/edit_mobile" >添加模板</a> 
							<a href="javascript:void(0);" >查看群发</a> 
							<a href="admin/adv_singMobile" >查看模板信息</a> 
							<a href="javascript:void(0);" >群发</a>  
							<a href="admin/edit_singMobile" >单发</a>
						</td>
					</tr>
					<tr>
						<td style="width:3%">短信消息</td>
						<td colspan="7">
							<a href="javascript:void(0);" >查看群发</a> 
							<a href="admin/show_singshortMessageMapping" >查看单发</a> 
							<a href="javascript:void(0);" >群发</a>
							<a href="admin/edit_oneshortMessageMapping" >单发</a>
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
