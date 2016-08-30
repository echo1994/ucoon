<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>我服务的</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet" href="css/mui.min.css">
<link rel="stylesheet" href="css/style.css">
<link href="css/iconfont.css" rel="stylesheet" />
<script src="js/jquery-2.1.4.min.js"></script>
<style>
.mysend {
	margin-top: 0;
}

.mysend-col .m-t {
	margin-top: 0;
}

.mysend .t-m .read-times {
	color: #C3D94F;
}
</style>

</head>
<script type="text/javascript">
	($(function() {
		loaddata(0, 9, true);
	}))
	function loaddata(startIndex, endIndex, clearable) {
		$
				.ajax({
					url : 'orders/getOrdersLimited',
					data : {
						startIndex : 0,
						endIndex : 9,
						userId : 1
					},
					async : false,
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (clearable == true) {
							$(".mysend").empty();
						}
						for (var i = 0; i < data.length; i++) {
							var status = '';
							switch (data[i].state) {
							case 0:
								status = '正在服务';
								break;
							case 1:
								status = '已完成';
								break;
							}
							$(".mysend")
									.append(
											"<li class='mysend-col'>"
													+ "<div class='m-t'>"
													+ "<img class='fl' src='"+data[i].head_img_url+"'>"
													+ "<div class='t-r fr'>"
													+ "<p class='task-status'>"
													+ status
													+ "</p>"
													+ "<p class='send-time'>"
													+ getMonthDay(data[i].order_time)
													+ "</p>"
													+ "</div>"
													+ "<div class='t-m'>"
													+ "	<p class='task-title'>"
													+ data[i].mission_title
													+ "</p>"
													+ "	<p class='read-times'>"
													+ "		<i class='mui-icon iconfont icon-qian'></i>"
													+ data[i].mission_price
													+ "	</p>"
													+ "	</div>"
													+ "</div>"
													+ "<div class='m-b'>"
													+ "	<button class='fr'>查看订单</button>"
													+ "	<button class='fr'>联系Ta</button>"
													+ "</div></li>");
						}
					}
				})
	}
	function getMonthDay(timestamp) {
		var date = new Date(timestamp);
		month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) + "-"
				: date.getMonth() + 1 + "-";
		day = date.getDate() + 1 < 10 ? "0" + date.getDate() + " " : date
				.getDate()
				+ " ";
		hour = date.getHours() < 10 ? "0" + date.getHours() + ":" : date
				.getHours()
				+ ":";
		minute = date.getMinutes() < 10 ? date.getMinutes() : date.getMinutes();
		return month + day + hour + minute;
	}
</script>
<body>

	<div class="mui-content">
		<ul class="mysend">
			<!-- <li class="mysend-col">
				<div class="m-t">
					<img class="fl" src="images/muwu.jpg">
					<div class="t-r fr">
						<p class="task-status">正在服务</p>
						<p class="send-time">08-08 13:30</p>
					</div>
					<div class="t-m">
						<p class="task-title">求代课</p>
						<p class="read-times">
							<i class="mui-icon iconfont icon-qian"></i>520
						</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">查看订单</button>
					<button class="fr">联系Ta</button>
				</div>
			</li>
			<li class="mysend-col">
				<div class="m-t">
					<img class="fl" src="images/logo.png">
					<div class="t-r fr">
						<p class="task-status">已完成</p>
						<p class="send-time">08-08 13:30</p>
					</div>
					<div class="t-m">
						<p class="task-title">带领快递</p>
						<p class="read-times">
							<i class="mui-icon iconfont icon-qian"></i>520
						</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">查看订单</button>
					<button class="fr">联系Ta</button>
				</div>
			</li> -->
		</ul>
	</div>
	<script src="js/mui.min.js"></script>
</body>
</html>