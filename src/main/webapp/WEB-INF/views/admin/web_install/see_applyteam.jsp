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
			<div class="pagetitle">递推申请查看
				
			</div> 
				<table class="table table-bordered table-condensed">
					
					<tr>
						<td class="item_title">用户名：</td>
						<td class="item_input">${applyTeam.userName}</td>
					</tr>
					<tr>
						<td class="item_title">姓名：</td>
						<td class="item_input">${applyTeam.applyerName}</td>
					</tr>
					<tr>
						<td class="item_title">联系方式：</td>
						<td class="item_input">
							${applyTeam.applyerPhone}
						</td>
					</tr>
					<tr>
						<td class="item_title">技能：</td>
						<td class="item_input">
							${applyTeam.tags}
						</td>
					</tr>
					<tr>
						<td class="item_title">自我介绍：</td>
						<td class="item_input">
							${applyTeam.selfIntroduce}
						</td>
					</tr>
					<tr>
						<td class="item_title">学校：</td>
						<td class="item_input">
							${applyTeam.school}
						</td>
					</tr>
					<tr>
						<td class="item_title">申请时间：</td>
						<td class="item_input">
							${applyTeam.applyTime}
						</td>
					</tr>
					<tr>
						<td class="item_title">状态：</td>
						<td class="item_input">
							<select id="status" opid="${applyTeam.id}">
			  				<option value="0">未审核</option>
							<option value="1">审核通过</option>
							<option value="2">审核失败</option>
			  			</select>
						</td>
					</tr>
					 
					<tr>
						<td class="item_title">图片:</td>
						<td class="item_input">
						<c:forEach begin="1" end="${applyTeam.picCount}" varStatus="s">
						<!-- feedbackImage/ 到时候需要修改 可以成功显示 -->
							<img src="feedbackImage/${applyTeam.certificateImg}/${s.index-1}">
						</c:forEach>
						</td>
					</tr>
				</table> 
		</div>
	</body>
	<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
<script src="editor/kindeditor.js"></script>
<script src="editor/lang/zh_CN.js"></script>
<script type="text/javascript">

$("#status").find("option").each(function(){
		if($(this).val()==${applyTeam.applyStatus})
			$(this).attr("selected","selected");
}) 

$("#status").change(function(){
		var status = $(this).val();
		var id=$(this).attr("opid");
		var param={id:id,applyStatus:status}
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

</script>


</html>



