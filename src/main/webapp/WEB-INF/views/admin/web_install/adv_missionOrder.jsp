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
	</head>
	<body >
		<div class="listbox"> 
			<div class="pagetitle">订单管理</div>
			<div class="buttonz">
				<a href="add_admingroup.html" class="btn btn-success btn-sm">增加分组</a>
				<a href="javascript:;" class="btn btn-danger btn-sm" onclick="delall_mission();">批量删除</a>
				订单状态：<select id="select" opid="${missionStatus}">
								<option value="0">未支付</option>
								<option value="1">已支付</option>
								<option value="2">申请退款</option>
								<option value="3">已退款</option>
								<option value="4">已取消</option>
								<option value="5">已完成</option>
				</select>
			</div> 
			<table class="table table-bordered table-condensed" >
				<tr>
					<th width="1%"><input type="checkbox" id="checkall"></th>
					<th width="4%">ID</th>
					<th width="10%">发布者</th>
					<th width="17%">标题</th>		
					<th width="8%">接单人数</th>				
					<th width="10%">发布时间</th>
					<th width="20%">联系方式</th>
					<th width="10%">状态</th>
					<th width="20%">操作</th>
				</tr>
				<c:forEach items="${missions}" var="mission" varStatus="Status">
					<tr opid=${mission.missionId}>
						<td width="1%" class="text-center"><input type="checkbox" name="checklist" value="${mission.missionId}"></td>
						<td width="4%" >${Status.index+1}</td>
						<td width="15%">${mission.nickName}</td>
						<td width="15%">${mission.missionTitle}</td>
						<td width="5%">${mission.peopleCount}</td>
						<td width="10%">${mission.startTime}</td>
						<td width="20%">${mission.telephone}</td>
						<td width="10%">
							<select opid="${mission.missionStatus}" class="select1">
								<option value="0">未支付</option>
								<option value="1">已支付</option>
								<option value="2">申请退款</option>
								<option value="3">已退款</option>
								<option value="4">已取消</option>
								<option value="5">已完成</option>
							</select>
						</td>
						<td>
							<a href="javascript:;" onclick="delone_mission(${mission.missionId})">删除</a>		
							<a href="admin/adv_mission?missionId=${mission.missionId}">更多</a>	
							<a href="admin/edit_oneshortMessageMapping?telephone=${mission.telephone}">发送消息</a>	
						</td>					
					</tr>	
				</c:forEach>
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
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	function delone_mission(missionId){
		var missionStatus = ${missionStatus}
		alert(missionStatus);
		$.ajax({
			type:"post",
			url:"admin/delone_mission",
			data:{"missionId":missionId},
			success:function(data){
				if(data=="success")
					window.location.href="admin/adv_missionOrder/"+missionStatus;
				else alert("删除失败")
				
			}
		})
	}
	function delall_mission(){
	var missionStatus = ${missionStatus}
		var missionIds = [];
		$("input[name='checklist']:checked").each(function(){
			missionIds.push($.trim($(this).val()));
		}) 
		$.ajax({
			type:"post",
			url:"admin/delall_mission",
			data:{"missionIds":missionIds},
			success:function(data){
				if(data=="success")
					window.location.href="admin/adv_missionOrder"+missionStatus;
				else alert("删除失败")
			}
		})
	}
	$("#select").change(function(){
		var missionStatus = $(this).find("option:selected").val(); 
		window.location.href="admin/adv_missionOrder/"+missionStatus+"/1";
	})
	//对select选择好自动提交数据
	$(".select1").change(function(){ 
		var missionId = $(this).parent().parent().attr("opid");
		var missionStatus = $(this).find("option:selected").val();
		var param ={"missionId":missionId,"missionStatus":missionStatus};
		var select = $(this);
		$.ajax({
			type:"post",
			url:"admin/edit_missionOrder",
			data:param,
			success:function(data){
				alert("修改成功")
			}
		})
		
	})
	//自动确认选中的状态
	$("select").find("option").each(function(){
			if($(this).val()==$(this).parent().attr("opid")){
				$(this).attr("selected","selected");
			}
		})
	function chan(num){
		var status = $("#select").val();
		//window.href.location="admin/adv_missionOrder/{status}/${pages}"
		var page = ${pages}; 
		if(num==1){
			page = (page-1);
			window.location.href="admin/adv_missionOrder/"+status+"/"+page;
		}
		if(num==2){
			page = (page+1);
			window.location.href="admin/adv_missionOrder/"+status+"/"+page;
		}
	}
	function go(){
		var page=$("#go").val();
		var status = $("#select").val();
		alert("admin/adv_missionOrder/"+status+"/"+page)
		window.location.href="admin/adv_missionOrder/"+status+"/"+page;
	}
	
</script>