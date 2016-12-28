<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		
			<div class="pagetitle">递推团队申请管理</div>
	 
	 	<div class="buttonz">
				 
				<a href="javascript:;" class="btn btn-danger btn-sm" onclick="delall_mission();">查看申请状态：</a>
				<select style="width:75px" id="select" opid="${missionStatus}">
								<option value="0">未审核</option>
								<option value="1">审核通过</option>
								<option value="2">审核失败</option>
							<!-- 	<option value="3">全部</option> -->
				</select>
			</div>
			<table class="table table-bordered table-condensed">
			  <thead>	
				<tr>
					<th width="5%">ID</th>
					<th width="10%">用户</th>
					<th width="30%">联系方式</th>
					<th width="20%">申请时间</th>
					<th width="10%">学校</th>
					<th width="20%">状态</th>
					<th width="20%">操作</th>
				</tr>
			  </thead>
			  <tbody id="tb1">
			  		<c:forEach items="${applyTeams}" var="a" varStatus="index">
			  	<tr>
			  		<td class="text-center">${index.index+1}</td>
			  		<td class="text-center">${a.userName}</td>
			  		<td class="text-center">${a.applyerPhone}</td> 
			  		<td class="text-center">${a.applyTime}</td>
			  		<td class="text-center">${a.school}</td>
			  		<td class="text-center">
			  			<select class="status" opid="${a.id}"  uid="${a.userId}">
			  				<option value="0">未审核</option>
							<option value="1">审核通过</option>
							<option value="2">审核失败</option>
			  			</select>
			  		</td>
			  		<td class="text-center">
			  			<a href="admin/cApplyteam?id=${a.id}" >查看详情</a>
			  		</td>
			  	 
			  	</tr>
			  		</c:forEach>
			  	</tr>
			  </tbody>
			</table>
			<div class="paging">
			   <ul class="pagination">
			    <li onclick="chan(1)"><span aria-hidden="true">‹</span></li>
			 	<li onclick="chan(2)"><span aria-hidden="true">›</span></li>
			    <li><span>${page2}/${pageAll} 页</span></li>
			    <li><input type="text" id="go" class="form-control"></li>
				<li onclick="go()"><span aria-hidden="true">GO</span></li> 
			  </ul>
			</div>
		</div>
	</body>
	<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	$("#select").change(function(){
		var status = $(this).val();
		window.location.href="admin/applyteam_list?status="+status+"&page=1";
	})
	$("#select").find("option").each(function(){
		if($(this).val()==${status})
			$(this).attr("selected","selected");
	}) 
	$(".status").find("option").each(function(){
		if($(this).val()==${status})
			$(this).attr("selected","selected");
	}) 
	$(".status").change(function(){
		var status = $(this).val();
		var id=$(this).attr("opid");
		var userId=$(this).attr("uid");
		var param={id:id,applyStatus:status,userId:userId}
		$.ajax({
			type:"post",
			url:"admin/applyteamUp",
			data:param,
			success:function(data){
				if(data=="success")
					alert("修改成功")
				else alert("修改失败")
			}
		})
	})
	function chan(num){
		var status = $("#select").val();
		var page = ${page2}; 
		if(num==1){
			page = (page-1);
			window.location.href="admin/applyteam_list?status="+status+"&page="+page;
		}
		if(num==2){
			page = (page+1);
			window.location.href="admin/applyteam_list?status="+status+"&page="+page;
		}
	}
	function go(){
		var page=$("#go").val();
		var status = $("#select").val();
		if(!page){
			alert("请输入页码")
			return;
		}
		window.location.href="admin/applyteam_list?status="+status+"&page="+page;
	}
</script>
</html>
