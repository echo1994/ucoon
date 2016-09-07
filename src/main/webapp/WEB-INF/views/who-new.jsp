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


<script src="js/mui.min.js"></script>
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/mui.picker.min.js"></script>


<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=5tvfGzOQjpNsnVNXhUZ0xkxDCK6sDpRF"></script>
<script type="text/javascript"
	src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
	

<style>
html, body {
	background-color: #efeff4;
}
</style>
</head>

<body>

	<div class="mui-content">
		<form class="mui-input-group who-form-content" method="post"
			action="mission/add-mission" enctype="multipart/form-data" onsubmit="return toVaild()">
			<h2 class="title-who">
				<i class="mui-icon iconfont icon-plane"></i>发布任务
			</h2>
			<div class="mui-input-row who-form">
				<label>标题</label> <input type="text" name="missionTitle"
					placeholder="如领快递，买盒饭等">
			</div>
			<div class="publish-des">
				<textarea type="text" name="missionDescribe"
					placeholder="选填，添加详细描述，有助于快速被接单哦！"></textarea>
				<ul class="addimg">
					<li style="position: relative;"><img id="addimgCo"
						src="images/addimg.png" /> <input type="file" id="imgUpload"
						name="imgUpload" draggable="true"
						style="position:absolute ;top:0;left:0;width: 100%;height: 100%;visibility: hidden;"
						multiple="multiple" /></li>
				</ul>
			</div>
			<div class="mui-input-row who-form">
				<label>售价</label> <input type="text" name="missionPrice"
					placeholder="价格" id="price">
			</div>
			<div class="mui-input-row who-form">
				<label>需要人数</label> <input type="text" name="peopleCount"
					placeholder="请填写你需要的人数">
			</div>
			<div class="mui-input-row who-form">
				<label>活动地点</label> <input type="text" name="place"
					placeholder="目前仅限厦门地区" id="suggestId">
			</div>
			<div class="mui-input-row who-form">
				<label>开始时间</label> <input id='result1' name="startTime" type="text"
					data-options='{"value":"2016-08-08 10:10","beginYear":2016,"endYear":2020}'
					class="btn mui-btn mui-btn-block ui-alert"  />

			</div>
			<div class="mui-input-row who-form">
				<label>截止时间</label> <input id='result2' name="endTime" type="text"
					data-options='{"value":"2016-08-08 10:10","beginYear":2016,"endYear":2020}'
					class="btn mui-btn mui-btn-block ui-alert" />

			</div>
			<div class="mui-input-row who-form">
				<label>联系电话</label> <input type="tel" name="telephone"
					value="13074852391" placeholder="请填写你的电话">
			</div>
		</form>
		<button class="send-btn" id="send-btn">发布</button>
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
				var reg1 = /^[0-9]+(\.[0-9]+)?$/;
				if (!reg1.test($('#missionPrice').val()) && isprint == false) {
					alert("请填写正确售价");
					ec++;
				}
				if (!reg1.test($('#peopleCount').val()) && isprint == false) {
					alert("请填写正确人数");
					ec++;
				}
				if (!(/^1[3|4|5|7|8]\d{9}$/.test($('#telephone').val()))
						&& isprint == false) {
					alert("请填写正确手机号");
					ec++;
				}
				if (ec > 0) {
					return false;
				}
			}
		</script>
	</div>
	<div id="l-map" style="display: none"></div>
	<div id="searchResultPanel"
		style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
	<!-- Modal -->
	<!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">支付方式选择</h4>
	      </div>
	      <div class="modal-body">
	      	   余额<input type="radio" name="payment" checked="checked">
	     	   微信<input type="radio" name="payment">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-success" id="pay">支付</button>
	      </div>
	    </div>
	  </div>
	</div> -->
	

</body>
<script src="js/who.js"></script>
</html>
