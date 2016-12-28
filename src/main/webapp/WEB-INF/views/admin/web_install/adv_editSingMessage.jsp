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
			<div class="pagetitle">单发短信管理</div>
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
					<th width="10%">接收者</th>
					<th width="57%">内容</th>		
					<th width="8%">时间</th>	
					<th width="20%">操作</th>
				</tr>
				<c:forEach items="${servicemessages}" var="message" varStatus="Status">
					<tr opid=${message.shortMessageId}>
						<td width="1%" class="text-center"><input type="checkbox" name="checklist" value="${message.shortMessageId}"></td>
						<th width="4%">${Status.index}</th>
						<th width="10%">${message.openId}</th>
						<th width="57%">${message.shortMessageContent}</th>		
						<th width="8%">${message.sendTime}</th>	
						<td width="20%">
							<a href="javascript:;" onclick="delone_mission(${mission.missionId})">删除</a>		
							<a href="admin/adv_mission?missionId=${mission.missionId}">更多</a>	
						</td>					
					</tr>	
				</c:forEach>
						
						
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
		alert(missionStatus);
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
		window.location.href="admin/adv_missionOrder/"+missionStatus;
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
</script>