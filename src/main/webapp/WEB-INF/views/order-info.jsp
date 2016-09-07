<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
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
<script src="js/jquery-2.1.4.min.js"></script>
<link rel="stylesheet" href="css/style.css">
<style>
body {
	background-color: #efeff4;
}
</style>
</head>
<script type="text/javascript">
	$(function() {
		$('.status').text(${ou.state} == "0" ? "正在服务" : "已完成");
		$('.num').text(${ou.order_num});
		$('.ot').text("${ou.order_time}");
		$('.ft').text("${ou.finish_num}" == "" ? "未完成" : "${ou.finish_num}");
		$('.wei').text(${ou.weixin_id});
		$('.phone').text(${ou.phone});

		$('.u').bind('click', function() {
			window.location.href='user/orderUser/'+$(this).attr("data-u");
		});
	})
</script>
<body>
	<div class="mui-content">
		<div class="order-model">
			<div class="model-col clearfix">
				<span class="fl">交易状态</span><span class="fr status"></span>
			</div>
		</div>
		<div class="order-model">
			<div class="model-col clearfix">
				<span class="fl">订单编号</span><span class="fr num"></span>
			</div>
			<div class="model-col clearfix">
				<span class="fl">接单时间</span><span class="fr ot"></span>
			</div>
			<div class="model-col clearfix">
				<span class="fl">完成时间</span><span class="fr ft"></span>
			</div>
			<div class="model-col clearfix">
				<span class="fl">Ta的微信</span><span class="fr wei"></span>
			</div>
			<div class="model-col clearfix">
				<span class="fl">Ta的电话</span><span class="fr phone"></span>
			</div>
			<div class="model-col clearfix u" data-u="${ou.user_id}">
				<span class="fl">Ta的信息</span><span class="fr order-maker"><img
					src="${ou.head_img_url}">></span>
			</div>

		</div>
		<div class="order-check" data-o="${ou.order_id}">完成&评价</div>
	</div>

</body>
</html>
