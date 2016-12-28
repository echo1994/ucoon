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
			<div class="pagetitle">客服消息编辑
				<a href="admin/show_singserviceMessageMapping">>>返回列表</a>
			</div>
				<table class="table table-bordered table-condensed">
					<tr>
						<td colspan="2">客服群发消息编辑</td>
					</tr>
					
					<tr>
						<td class="item_title">客服对象</td>
						<td class="item_input">
						<select id="custom">
							<c:forEach items="${servers}" var="server">
								<option value="${server.serverId}">${server.serverName}</option>
							</c:forEach>
						</select>
					</tr>
					<tr>
						<td class="item_title">消息内容</td>
						<td class="item_input">
						<textarea name="content" id="content"style="width:500px;height:100px" class="form-control input-sm"></textarea></td>
					</tr>
					<tr>
						<td class="item_title">消息类型</td>
						<td class="item_input">
						<!-- <input name="content" id="content"style="width:500px;height:100px" class="form-control input-sm"></textarea> -->
							<select id="type">
								<option value="text">文本</option>
								<option value="text">语音</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<p class="btn btn-primary btn-sm" onclick="editUp()">发送客服消息</p>	
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
<script src="editor/kindeditor.js"></script>
<script src="editor/lang/zh_CN.js"></script>
<script type="text/javascript"> 
	//发送编辑短信					
	function editUp(){ 
		var content = $("#content").val(); 
		var msgtype=$("#type").find("option:selected").val();
		var serName=$("#custom").find("option:selected").html();
		var param = {"content":content,"msgtype":msgtype,"serviceId":1,"serName":serName}
		//alert(JSON.stringify(param))
		$.ajax({
				type:"post",
				url:"admin/send_AllserviceMessage",
				data:param,
				success:function(result){
					if(result=="success"){
					//发送成功跳到 单发查看页面
						window.location.href="admin/show_singserviceMessageMapping"
					}else{
						alert("发送失败")
					}
				}
			}); 
			return false;
	}
	
</script>
