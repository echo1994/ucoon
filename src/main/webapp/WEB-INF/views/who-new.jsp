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
<link href="css/style.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />
<link href="css/create-aty.css" rel="stylesheet" />


<script src="js/jquery-2.1.4.min.js"></script>

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=5tvfGzOQjpNsnVNXhUZ0xkxDCK6sDpRF"></script>
<script type="text/javascript"
	src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
	

<style>
	html, body {
		background-color: #efeff4;
	}
	
	.mui-btn-blue{
		background: #c3d94f;
		border: none;
	}
	.address{
		position: relative;
	}
	.mui-input-row .addinput{
		padding-right: 30px;
	
	}
	
	.mui-input-row .select{
		position: absolute;
		z-index: 9;
		top: 10px;
		right:1px;
		width: 26px;
		height: 26px;
		font-size: 0;
		margin: 3px 8px 3px 5px;
		background: url("images/pulldown.png") no-repeat;
	
	}
	.mui-input-row .tel{
		padding-right: 40px;
	}
	.tip{
		position: absolute;
		font-size: 8px;
		color: #999;
		/*text-align: right;*/
		/*padding:10px 50px 10px 0;*/
		bottom:12px;
		right: 10px;


	}
	.deleted{
		position: absolute;
		right: 2px;
		top:-10px;
		z-index: 99;
		color:rgb(250,75,75);
	}
	.publish-des .addimg li{
		position: relative;
	}
	
	/*修改的发布按钮样式*/
	.fix-btn{
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 50px;
		font-size: 16px;
		color: #555;
	}
	.fix-btn span{
		display: inline-block;
		background-color:#eee;
		width: 60%;
		height: 50px;
		line-height: 50px;
		text-align: center;
	}
	.fix-btn span i{
		font-style: normal;
	}
	.fix-btn input{
		float: right;
		background-color: #C3D94F;
		width: 40%;
		height:  50px;
		font-size: 16px;
		border-radius: 0;
		border: none;
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
				<label>标题</label> <input type="text" name="missionTitle" placeholder="如领快递，买盒饭等">
			</div>
			<div class="publish-des">
				<textarea name="missionDescribe" placeholder="选填，添加详细描述，有助于快速被接单哦！"></textarea>
				<ul class="addimg">
					<li style="position: relative;"><img id="addimgCo"
						src="images/addimg.png" /> <input type="file" id="imgUpload"
						name="imgUpload" draggable="true"
						style="position:absolute ;top:0;left:0;width: 100%;height: 100%;visibility: hidden;"
						multiple="multiple" /></li>
				</ul>
			</div>
			<div class="mui-input-row who-form">
				<label>单价</label> <input type="number" name="missionPrice"
					placeholder="一个人的价格" id="price">
			</div>
			<div class="mui-input-row who-form">
				<label>需要人数</label> <input type="text" name="peopleCount" value="1"
					placeholder="最少1人，最多5人" id="peopleCount">
			</div>
				<div class="mui-input-row who-form">
				<label>执行地点</label>
				<input class="tel" type="text" id="menu-btn" name="place" placeholder="点击选择地点"  value="">
				<select class="select" id="sel" onchange="changeF()">
					
				  	<c:forEach items="${infos }" var="info">
						<option value="${info.place }" data-m="${info.missionLng }" data-t="${info.missionLat }">${info.place }</option>
					</c:forEach>	
				</select>
			</div>
			<div class="mui-input-row who-form">
				<label>截止接单</label>
				<input id='showUserPicker' type="text" name="time" class="mui-btn mui-btn-block ui-alert" value="24小时候后">
			</div>
			<div class="mui-input-row who-form">
				<label>联系电话</label> <input type="tel" name="telephone"
					value="${user.phone }" placeholder="请填写你的电话" id="telephone">
				<p class="tip">(接单可见)</p>
			</div>
			<input type="hidden" name="missionLng" id="lng" placeholder="经度">
			<input type="hidden" name="missionLat" id="lat" placeholder="纬度">
		</form>
		
		<div class="fix-btn clearfix">
			<span >需支付 <i id="payPrice">0</i>元</span>
			<input type='submit' class="send-btn" id="send-btn" value="发布"/>
		</div>
		
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
			var reg1 = /^\+?(?!0+(\.00?)?$)\d+(\.\d\d?)?$/;
			var prize = $('#price').val();
			if (prize.match(reg1) == null && isprint == false) {
				alert("请填写正确售价");
				ec++;
				isprint = true;
			}
			if (!reg1.test($('#peopleCount').val()) && isprint == false) {
				alert("请填写正确人数");
				ec++;
				isprint = true;
			}
			
			if ($('#peopleCount').val()>5 && isprint == false) {
				alert("人数不能大于5人");
				ec++;
				isprint = true;
			}
			
			if ((prize<1 && $('#peopleCount').val()>1) && isprint == false) {
				alert("金额小于1元，填写的人数只能为1人");
				ec++;
				isprint = true;
			} 
			
			
			if (!(/^1[3|4|5|7|8]\d{9}$/.test($('#telephone').val()))
					&& isprint == false) {
				alert("请填写正确手机号");
				ec++;
				isprint = true;
			}
			if (ec > 0) {
				return false;
			}
		}
		
		
		
		var date = new Date();
		var datez = getMonthDay2(date);
		var dataoptions = "{\"value\":\"" + datez
				+ "\",\"beginYear\":2016,\"endYear\":2020}";
		$("#result1").attr("data-options", dataoptions);
		$("#result2").attr("data-options", dataoptions);
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
			
			
			 $(document).on("input", "#price", function(){
		        var options=$(this).val().trim();
		        var peopleCount = $("#peopleCount").val().trim();
		        if(options != "" && peopleCount!=""){
		        	options = parseFloat(options);
		        	var price = parseFloat(options.toFixed(2)); //四舍五入保留两位小数
		        	var count = parseInt(peopleCount); 
		        	$("#payPrice").html(parseFloat(price*count).toFixed(2));
		        }
		    });
		    
		    $(document).on("input", "#peopleCount", function(){
		        var options=$(this).val().trim();
		        var price = $("#price").val().trim();
		        if(options != "" && price !=""){
		        	price = parseFloat(price);
		        	var price = parseFloat(price.toFixed(2)); //四舍五入保留两位小数
		        	var count = parseInt(options); 
		        	$("#payPrice").html(parseFloat(price*count).toFixed(2));
		        }
		    });
		</script>

	</div>
	<div id="menu-wrapper" class="menu-wrapper hidden">
		<div id="menu" class="menu">
			<p class="add-input-box">
				<input type="text" name="place" class="mui-input-clear"
						placeholder="输入地址名即可" id="suggestId">
			</p>
			<div id="l-map"></div>
			<p class="add-btn-group">
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
<script src="js/mui.min.js"></script>
<script src="js/mui.zoom.js"></script>
<script src="js/mui.previewimage.js"></script>
<script>
	
	$(document).on("tap",'#deleteimg',function () {
		 $(this).parent().remove()
	})
</script>
<script src="js/mui.picker.min.js"></script>
<script src="js/who.js"></script>

	

</html>
