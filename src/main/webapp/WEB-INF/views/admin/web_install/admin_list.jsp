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
		<div class="listbox">
			<div class="pagetitle">管理员列表</div>
			<div class="buttonz">
				<a href="add_admin.html" class="btn btn-success btn-sm">增加成员</a>
				<a href="javascript:;" class="btn btn-danger btn-sm" onclick="delall_admin();">批量删除</a>
			</div>
			<table class="table table-bordered table-condensed">
				<tr>
					<th width="1%"><input type="checkbox" id="checkall"></th>
					<th width="5%">ID</th>
					<th width="20%">账号</th>
					<th width="50%">所属分组</th>
					<th width="24%">操作</th>
				</tr>
			
				<tr>
					<td class="text-center"><input type="checkbox" name="checklist" value="01"></td>
					<td class="text-center">03</td>
					<td>sfc</td>
					<td class="text-center">普通管理员</td>
					<td>
						<a href="edit_admin.html?id=03">编辑</a>
						<a href="javascript:;" onclick="deladmin(03);">删除</a>				
					</td>					
				</tr>			
			</table>
			<div class="paging">
			  <ul class="pagination">
			    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">‹</span></a></li>
			    <li class="active"><a href="#">1</a></li>
			    <li><a href="#">2</a></li>								    
			    <li><a href="#" aria-label="Next"><span aria-hidden="true">›</span></a></li>
			    <li><span>1/1022 页</span></li>
			    <li><input type="text" class="form-control"></li>
				<li><a href="javascript:;">Go</a></li>
				<li><span>30655 条记录</span></li>
			  </ul>
			</div>
		</div>
	</body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/admin.js" type="text/javascript"></script>