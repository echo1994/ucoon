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
			<div class="pagetitle">轮播图编辑
				<a href="admin/adv_photo">>>返回列表</a>
			</div>
			<form  name="myform" id="myform" >
				<input type="text" name="photoId" value="${photo.photoId}" style="display:none">
				<table class="table table-bordered table-condensed">
					
					<tr>
						<td colspan="2">轮播图信息</td>
					</tr>
					<tr>
						<td class="item_title">图片名称:</td>
						<td class="item_input"><input type="text" name="photoName" value="${photo.photoName}" class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">图片跳转路径:</td>
						<td class="item_input"><input type="text" name="photoGoUrl" value="${photo.photoGoUrl}" class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">轮播位置:</td>
						<td class="item_input"><select class="form-control selectpicker" name="local"></select></td>
					</tr>
					<tr>
						<td class="item_title">图片:</td>
						<td class="item_input"><img src="admin/reqmImage/${photo.photoUrl}"></td>
					</tr>
					<tr>
						<td class="item_title">是否跳转:</td>
						<td class="item_input">
							<c:if test="${photo.isGo==1}">
								<label class="radio-inline"><input type="radio" name="isGo" value="1" checked="checked">允许</label>
								<label class="radio-inline"><input type="radio" name="isGo" value="0">停止</label>
							</c:if>
							<c:if test="${photo.isGo==0}">
								<label class="radio-inline"><input type="radio" name="isGo" value="1" >允许</label>
								<label class="radio-inline"><input type="radio" name="isGo" value="0" checked="checked">停止</label>
							</c:if>
						</td>
					</tr> 
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<p  onclick="editUp()" class="btn btn-primary btn-sm" style="width:150px;">保存</p>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
<script src="editor/kindeditor.js"></script>
<script src="editor/lang/zh_CN.js"></script>
<script>
	//初始化select框
	for(var i = 1;i<10;i++){
		var option=null;
		if(i==${photo.local})
			option = '<option selected="selected" value="'+i+'">'+i+'</option>';
		else	
			option = '<option value="'+i+'">'+i+'</option>';
			$("select").append(option);
	}
							
	function editUp(){
		var photoMsg  = $("#myform").serialize();
		$.ajax({
				type:"post",
				url:"admin/update_phototurn",
				data:photoMsg,
				success:function(result){
					window.location.href="admin/adv_photo"
				}
			}); 
			return false;
	}
	
</script>
