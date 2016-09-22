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
<meta charset="utf-8">
<title>有空UCOON</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet" href="css/mui.min.css">
<link rel="stylesheet" type="text/css" href="css/mui.picker.min.css" />
<link href="css/style-new.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />
<link href="css/create-aty.css" rel="stylesheet" />

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=YLE4hjTj9BjxFk2adsU8IBGifmrBtgzZ"></script>
<script type="text/javascript"
	src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
</head>

<body>

	<div class="mui-content">
		<form class="mui-input-group who-form-content" method="post"
			action="activity/add-activity" enctype="multipart/form-data"  onsubmit="return toVaild()" >
			<h2 class="title-who">
				<i class="mui-icon iconfont icon-plane"></i>发布活动
			</h2>
			<div class="mui-input-row who-form">
				<label>活动名称</label> <input type="text" name="activityName"
					placeholder="请输入活动名称">
			</div>
			<div class="mui-input-row who-form">
				<label>活动时间</label> <input id='result1' name="activityTime" type="text"
					data-options=''
					class="btn mui-btn mui-btn-block ui-alert" placeholder="点击选择时间"/>

			</div>
		
			<div class="mui-input-row who-form">
				<label>活动地点</label> <input type="text" name="activityPlace"
					placeholder="点击选择地址" id="menu-btn">
			</div>
			<div class="mui-input-row who-form">
				<label>详细地址</label> <input type="tel" name="activityDetailPlace"  placeholder="选填，请输入详细地址">
			</div>
			<div class="publish-des">
				<textarea id="textarea" rows="5" name="activityDesc" placeholder="请输入详细简介"></textarea>
				
			</div>
			<input type="hidden" name="activityLng" id="lng" placeholder="经度">
			<input type="hidden" name="activityLat" id="lat" placeholder="纬度">
		</form>
		<button class="send-btn" id="send-btn">创建</button>
		
		
		<script src="js/mui.min.js"></script>
		<script src="js/jquery-2.1.4.min.js"></script>
		<script src="js/mui.picker.min.js"></script>
		<script type="text/javascript">
			function toVaild() {
				var ec = 0;
				var isprint = false;
				$("input[type=text]").each(function() {
					if ($(this).val() == '' && isprint == false) {
						alert($($(this).prev()).text() + '不能为空');
						ec++;
						isprint = true;
					}
				});
				
				if (ec > 0) {
					return false;
				}
			}
			
			var date = new Date();
			var datez = getMonthDay2(date);
			var dataoptions = "{\"value\":\"" + datez
					+ "\",\"beginYear\":2016,\"endYear\":2020}";
			$("#result1").attr("data-options", dataoptions);
			function getMonthDay2(timestamp) {
				var date = new Date(timestamp);
				year = date.getYear() + 1900 + '-';
				month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) + "-"
						: date.getMonth() + 1 + "-";
				day = date.getDate() + 1 < 10 ? "0" + date.getDate() + " " : date
						.getDate()
						+ " ";
				hour = date.getHours() < 10 ? "0" + date.getHours() + ":" : date
						.getHours()
						+ ":";
				minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date
						.getMinutes();
				return year + month + day + hour + minute;
			}
		</script>

	</div>
	<div id="menu-wrapper" class="menu-wrapper hidden">
		<div id="menu" class="menu">
			<p style="">
				<input type="text" name="place" class="mui-input-clear"
						placeholder="输入地址名即可" id="suggestId">
				<button id="search" type="button" class="mui-btn mui-btn-primary">
					同城搜索
				</button>
			</p>
			<div id="l-map"></div>
			<p style="padding: 5px 20%;margin-bottom: 5px;">
				<button id="cancel" type="button" class="mui-btn mui-btn-primary" style="padding: 10px;">
					取消
				</button>
				<button id="save" type="button" class="mui-btn mui-btn-primary" style="padding: 10px;">
					完成
				</button>
				
			</p>
			
		</div>
	</div>
	<div id="menu-backdrop" class="menu-backdrop"></div>
	
	<div id="searchResultPanel"
		style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
	
	

</body>
<script src="js/create-aty.js"></script>
</html>
