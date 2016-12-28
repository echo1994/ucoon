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
			<div class="pagetitle">模板消息添加
				<a href="admin/adv_singMobile">>>返回列表</a>
			</div>
			<form  name="myform" id="myform" >
				<table class="table table-bordered table-condensed">
					
					<tr>
						<td colspan="2">模板信息</td>
					</tr>
	
					<tr>
						<td class="item_title">模板ID</td>
						<td class="item_input"><input type="text" id="mobileId" name="mobileId" class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">标题</td>
						<td class="item_input"><input type="text" id="mobileTitle" name="mobileTitle"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">头部参数</td>
						<td class="item_input"><input type="text" id="mobileFirstParam" name="mobileFirstParam"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">尾部参数</td>
						<td class="item_input"><input type="text" id="mobileRemark" name="mobileRemark"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">内容参数</td>
						<td class="item_input">
							<input type="text" id="mobileContents" placeholder="多个参数之间用英文   逗号   隔开 " name="mobileContents"  class="form-control input-sm"></td>
					</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<p  onclick="addMobile()" class="btn btn-primary btn-sm" style="width:150px;">添加</p>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
	<script src="js/ajaxfileupload.js" type="text/javascript"></script> 
<script>








function addMobile(){
	var mobileId = $("#mobileId").val();
		var mobileTitle = $("#mobileTitle").val();
		var mobileFirstParam = $("#mobileFirstParam").val();
		var mobileRemark = $("#mobileRemark").val();
		var mobileContents = $("#mobileContents").val();
		var param = {"mobileId":mobileId,"mobileTitle":mobileTitle,"mobileFirstParam":mobileFirstParam,"mobileRemark":mobileRemark,"mobileContents":mobileContents}
	$.ajax({
			type:"post",
			url:"admin/add_mobile",
			data:param,
			success:function(result){
				if(result=="success"){
					//发送成功跳到 单发查看页面
						window.location.href="admin/adv_singMobile"
					}else{
						alert("添加模板失败,请重新添加");
					}
			}
		});	
}
	
</script>
