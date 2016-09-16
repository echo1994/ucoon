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
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="css/mui.min.css">
		<link rel="stylesheet" href="css/style.css">
	</head>
	<body>
	<div class="mui-content">
				<ul class="chat-list">
				<li class="list-col">
						<img class="fl" src="images/muwu.jpg">
						<div class="f-r fr">
							<p class="chat-time">00:03</p>
						</div>
						<div class="f-m">
							<p>满血复活大魔王</p>
							<p class="chat-content">有钱啥都干fsdfsdfff啥都干fsdfsdff啥都干fsdfsdff啥都干fsdfsdff啥都干fsdfsdfffffffffffffffffffffff</p>
						</div>
				</li>
				<li class="list-col">
						<img class="fl" src="images/pic1.png">
						<div class="f-r fr">
							<p class="chat-time">00:03</p>
						</div>
						<div class="f-m">
							<p>Echo</p>
							<p class="chat-content">哈哈哈哈哈fsdff啥都干</p>
						</div>
				</li>
				<li class="list-col">
						<img class="fl" src="images/pic2.jpg">
						<div class="f-r fr">
							<p class="chat-time">昨天</p>
						</div>
						<div class="f-m">
							<p>Toad</p>
							<p class="chat-content">有钱啥都干fsdfsdfff啥都干fsdfsdff啥都干fsdfsdff啥都干fsdfsdff啥都干fsdfsdfffffffffffffffffffffff</p>
						</div>
				</li>
			</ul>
			</div>
	</body>
</html>
