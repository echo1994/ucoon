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
		<title>财富中心</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="css/mui.min.css">
		<link rel="stylesheet" href="css/style.css">
		<style>
			body {
				background-color: #efeff4;
			}
		</style>
	</head>
	<body>
		<div class="mui-content">
			<div class="g-twobox clearfix">
				<div class="col fl">
					<h3>可用余额</h3>
					<p>88.6</p>
				</div>
				<div class="col fr">
					<h3>可用空点</h3>
					<p>72</p>
				</div>
			</div>
			<div class="g-twobox clearfix">
				<div class="col fl">
					<h3>累计收入</h3>
					<p>672</p>
				</div>
				<div class="col fr">
					<h3>待处理</h3>
					<p>45</p>
				</div>
			</div>
			<div class="contrl-btn">
				<button class="recharge-btn">充值</button>
				<button>提现</button>
			</div>
			
		</div>
	</body>
	<script src="js/mui.min.js"></script>
	<script>
		document.getElementsByClassName("recharge-btn")[0].addEventListener('tap', function() {
				var btnArray = ['确定', '取消'];
				mui.prompt('请输入你对MUI的评语：', '性能好', 'Hello MUI', btnArray, function(e) {
					if (e.index == 1) {
						info.innerText = '谢谢你的评语：' + e.value;
					} else {
						info.innerText = '你点了取消按钮';
					}
				})
				document.querySelector('.mui-popup-input').innerHTML="<button>hhhhh</button>"
				})

	</script>
</html>
