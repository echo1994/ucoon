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
			<div class="pagetitle">订单详情
				<a href="javascript:void(0);" onclick="aGo();">>>返回列表</a>
			</div>
			
				<table class="table table-bordered table-condensed" style="width:40%;margin:0 auto;">
					<tr>
						<th style="width:3%">栏目</th>
						<th style="width:20%">信息</th>
					</tr>
					<tr>
						<td style="width:3%">任务标题</td>
						<td colspan="7">${mission.missionTitle}</td>
					</tr>
					<tr>
						<td style="width:3%">任务描述</td>
						<td colspan="7">${mission.missionDescribe}</td>
					</tr>
					<tr>
						<td style="width:3%">接单人数</td>
						<td colspan="7">${mission.peopleCount}</td>
					</tr>
					<tr>
						<td style="width:3%">任务地点</td>
						<td colspan="7">${mission.place}</td>
					</tr>
					<tr>
						<td style="width:3%">开始时间</td>
						<td colspan="7">${mission.startTime}</td>
					</tr> 
					<tr>
						<td style="width:3%">结束时间</td>
						<td colspan="7">${mission.endTime}</td>
					</tr>
					<tr>
						<td style="width:3%">发布者</td>
						<td colspan="7">${mission.userId}</td>
					</tr>
					<tr>
						<td style="width:3%">任务价格</td>
						<td colspan="7">${mission.missionPrice}</td>
					</tr>
					<tr>
						<td style="width:3%">联系方式</td>
						<td colspan="7">${mission.telephone}</td>
					</tr>
					<tr opid="${mission.missionId}">
						<td style="width:3%">订单状态</td>
						<td colspan="7">
							<select opid="${mission.missionStatus}">
								<option value="0">未支付</option>
								<option value="1">已支付</option>
								<option value="2">申请退款</option>
								<option value="3">已退款</option>
								<option value="4">已取消</option>
								<option value="5">已完成</option>
							</select>
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
<script src="editor/kindeditor.js"></script>
<script src="editor/lang/zh_CN.js"></script>
<script>
		//对select选择好自动提交数据
	$("select").change(function(){
		var missionId = $(this).parent().parent().attr("opid");
		var missionStatus = $(this).find("option:selected").val();
		var param ={"missionId":missionId,"missionStatus":missionStatus};
		var select = $(this);
		$.ajax({
			type:"post",
			url:"admin/edit_missionOrder",
			data:param,
			success:function(data){
				if(data=="success")
					alert("修改成功")
				else alert("修改失败")
			}
		})
		
	})
	function aGo(){
			var missionStatus = $("select").find("option:selected").val();
			window.location.href="admin/adv_missionOrder/"+missionStatus+"/1"
	}
	//自动确认选中的状态
	$("select").find("option").each(function(){
			if($(this).val()==$(this).parent().attr("opid")){
				$(this).attr("selected","selected");
			}
		})
	
</script>
