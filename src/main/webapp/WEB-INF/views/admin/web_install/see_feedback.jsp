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
			<div class="pagetitle">反馈信息查看
				
			</div> 
				<table class="table table-bordered table-condensed">
					
					<tr>
						<td class="item_title">用户姓名：</td>
						<td class="item_input">${feedBack.userName}</td>
					</tr>
					<tr>
						<td class="item_title">联系方式：</td>
						<td class="item_input">
							${feedBack.feedbackcantact}
						</td>
					</tr>
					<tr>
						<td class="item_title">反馈问题：</td>
						<td class="item_input">
							${feedBack.feedbackquestion}
						</td>
					</tr>
					<tr>
						<td class="item_title">反馈时间：</td>
						<td class="item_input">
							${feedBack.feedbackTime}
						</td>
					</tr>
					 
					<tr>
						<td class="item_title">图片:</td>
						<td class="item_input">
						<c:forEach begin="1" end="${feedBack.picCount}" varStatus="s">
							<img src="feedbackImage/${feedBack.pictures}/${s.index-1}">
						</c:forEach>
						</td>
					</tr>
				</table> 
		</div>
	</body>
	<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
<script src="editor/kindeditor.js"></script>
<script src="editor/lang/zh_CN.js"></script>
</html>



