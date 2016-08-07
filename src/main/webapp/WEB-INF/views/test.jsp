<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">

	<!--标准mui.css-->
	<link rel="stylesheet" href="css/mui.min.css"> 
	<style>
		.title {
			margin: 20px 15px 10px;
			color: #6d6d72;
			font-size: 15px;
		}
	</style>
  </head>
  <body>
  <header class="mui-bar mui-bar-nav">
			<h1 class="mui-title">用户列表</h1>
		</header>
		<div class="mui-content">
			
			
			<div class="title">
				${user.nickName},欢迎你
			</div>
			<ul class="mui-table-view mui-table-view-chevron">
				<c:forEach var="info" items="${userList}" >
   					<li class="mui-table-view-cell mui-media">
						<a class="mui-navigate-right" href="http://dq7kesuqpw.proxy.qqbrowser.cc/ucoon/chat/api-1?fromuserid=${user_id}&touserid=${info.userId}">
							<img class="mui-media-object mui-pull-left" src="${info.headImgUrl}" />
							<div class="mui-media-body">
								${info.nickName}
								<p class='mui-ellipsis'>快跟我聊天吧</p>
							</div>
						</a>
					</li>
				</c:forEach>
			</ul> 
			
		</div>

	</body>
	<script src="js/mui.min.js"></script>
	<script>
		mui.init({
			swipeBack:true //启用右滑关闭功能
		});
	</script>
</html>
