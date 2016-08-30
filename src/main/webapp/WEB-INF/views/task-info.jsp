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
<title>任务详情</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/mui.min.js"></script>
<link href="css/mui.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />
</head>
<style>
.basic-mes {
	margin: 0;
	padding: 15px 0;
	background: #fff;
}

.basic-mes img {
	width: 60px;
	height: 60px;
}

.mysend {
	margin: 0;
}

.mysend .mysend-col {
	height: 70px;
	border-bottom: 1px solid #ddd;
	margin: 0;
}

.mysend .mysend-col img {
	width: 45px;
	height: 45px;
	margin: 10.5px 5px;
}

.mysend .mysend-col .m-t .t-m {
	padding-top: 9px;
}

.mysend .mysend-col .m-t {
	border-bottom: 0;
	height: 70px;
}
</style>
<script type="text/javascript">
	$(function() {
		var pt = getMonthDay1("${mdetails.publish_time}");
		var st = getMonthDay2("${mdetails.start_time}");
		var et = getMonthDay2("${mdetails.end_time}");
		$("#pt").html(pt);
		$("#st").html(st);
		$("#et").html(et);
		$.ajax({
			url : 'orders/getOrdersCountByM',
			data : {
				missionId : ${mdetails.mission_id}
			},
			async : true,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				var remain = ${mdetails.people_count} - data;
				$("#remain").html("剩余" + remain + "个名额");
			}
		});
		var max = ${mdetails.pic_count};
		for (var i = 0; i < max; i++) {
			$("#imgbox").append(
					"<img src='reqmImage/${mdetails.pictures}/"+i+"'>");

		}
	})

	function getMonthDay1(timestamp) {
		var date = new Date(timestamp);
		month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) + "月"
				: date.getMonth() + 1 + "月";
		day = date.getDate() + 1 < 10 ? "0" + date.getDate() + "日" : date
				.getDate()
				+ "日";

		return month + day;
	}
	function getMonthDay2(timestamp) {
		var date = new Date(timestamp);
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
		return month + day + hour + minute;
	}
	function syncApply() {
		$.ajax({
			url : 'apply/addAppliment',
			data : {
				missionId : ${mdetails.mission_id}
			},
			async : true,
			type : 'post',
			dataType : 'text',
			success : function(data) {
				alert(data);
				
			}
		});
	}
</script>
<body>
	<div class="mui-content">
		<div class="basic-mes">
			<!--头像-->
			<img src="images/pic2.jpg">
			<div class="ucoon-user">
				${mdetails.nick_name}<i class="mui-icon iconfont icon-man"></i>
			</div>
		</div>
		<div class="task-info-sec1">
			<div class="top clearfix">
				<span class="task-name fl">${mdetails.mission_title}</span>
				<button class="task-name fr">举报</button>
			</div>
			<p>
				<i class="mui-icon iconfont icon-qian"></i>${mdetails.mission_price}
			</p>
			<div class="bottom clearfix">
				<span id="remain">剩余2个名额</span><span>${mdetails.view_count}次浏览</span><span
					id="pt">08月09日</span>
			</div>
		</div>
		<div class="task-info-sec2">
			<p>
				<i class="mui-icon  mui-icon-person "></i><span>人数</span><span
					class="innertxt">${mdetails.people_count}人</span>
			</p>
			<p>
				<i class="mui-icon  mui-icon-location "></i><span>地点</span><span
					class="innertxt">${mdetails.place}</span>
			</p>
			<p>
				<i class="mui-icon iconfont icon-time"></i><span>开始</span><span
					class="innertxt" id="st">08-09 17:00</span>
			</p>
			<p>
				<i class="mui-icon iconfont icon-time"></i><span>截止</span><span
					class="innertxt" id="et">08-09 19:00</span>
			</p>
		</div>
		<div class="task-info-description">
			<p>详细描述:</p>
			<p>${mdetails.mission_describe}</p>
			<div class="imgbox" id="imgbox"></div>

		</div>
		<div class="discus">
			<p class="pinglun">
				<span>评论</span><span>(5)</span>
			</p>
			<ul class="mysend">
				<li class="mysend-col">
					<div class="m-t">
						<img class="fl" src="images/muwu.jpg">
						<div class="t-r fr">
							<p class="send-time">08-08 13:30</p>

						</div>
						<div class="t-m">
							<p>
								满血复活大魔王<i class="mui-icon iconfont icon-man"></i>
							</p>
							<p class="read-times">有钱啥都干</p>
						</div>
					</div>
				</li>
				<li class="mysend-col">
					<div class="m-t">
						<img class="fl" src="images/muwu.jpg">
						<div class="t-r fr">
							<p class="send-time">08-08 13:30</p>
						</div>
						<div class="t-m">
							<p>
								超级女神经<i class="mui-icon iconfont icon-women"></i>
							</p>
							<p class="read-times">美女哦</p>
						</div>
					</div>
				</li>


			</ul>
		</div>
		<br /> <br /> <br /> <br />

		<div class="fix-btn">
			<button class="fl">联系Ta</button>
			<button class="fl cur" onclick="syncApply()">立刻接单</button>
		</div>

	</div>

</body>
<script>
	
</script>
</html>
