<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="css/mui.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="mui-content">
		<ul class="chat-list">
			<c:forEach items="${list}" var="info">
				<li class="list-col">
					<img class="fl" src="${info.head_img_url}">
					<a href="chat/api-1/${info.from_user_id}/${info.to_user_id}">
						<div class="f-r fr">
							<p class="chat-time">${info.psot_time}</p>
						</div>
						<div class="f-m">
							<p>${info.nick_name}</p>
							<p class="chat-content">${info.message_detail}</p>
						</div>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>
