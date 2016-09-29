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
		
		
		$(".cancelorder").on('tap',function(){
			var btnArray = ['否', '是'];
		    mui.confirm('是否取消订单，确认？', '有空ucoon', btnArray, function(e) 
		    {
		        if (e.index == 1) {
					$.ajax({
						url : 'applyOrders/cancelorder/' + $(".cancelorder").attr("data-m"),
						data : {},
						async : false,
						type : 'post',
						dataType : 'text',
						success : function(data) {
							alert(data);
							window.history.go(0);
						}
					})
				} else {
		            
		        }
		    })
		});
		
			
		$(".done").on('tap',function(){
			
			var btnArray = ['还未完成', '我已完成'];
		    mui.confirm('请确认您的任务已完成', '有空ucoon', btnArray, function(e) 
		    {
		        if (e.index == 1) {
					$.ajax({
						url : 'applyOrders/finishOrder/' + $(".done").attr("data-m"),
						data : {},
						async : false,
						type : 'post',
						dataType : 'text',
						success : function(data) {
							alert(data);
							window.history.go(0);
						}
					}) 
				} else {
		            
		        }
		    })
		});
		
		
		$(".evaluate").on('tap',function(){
			window.location.href = "applyOrders/evaluate/"
							+ $(this).attr("data-m");
		});
		
		$(".order").on('tap',function(){
			window.location.href = "applyOrders/myservice-task-info/"
							+ $(this).attr("data-m");
		});
		
		
	}))
	function loaddata(startIndex, endIndex, clearable) {
		$
				.ajax({
					url : 'applyOrders/getOrdersLimited',
					data : {
						startIndex : 0,
						endIndex : 9
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
							var handle = '';
							switch (data[i].take_state) {
								case 0:
									status = '待确认';
									handle = "<button class='fr cancelorder' data-m='"+data[i].apply_id+"'>取消任务</button><button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr contact'>联系Ta</button>";
									break;
								case 1:
									if(data[i].selectpeople == data[i].people_count){
										status = '可以开始执行任务';
										handle = "<button class='fr done' data-m='"+data[i].apply_id+"'>完成任务</button><button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr contact'>联系Ta</button>";
									}else{
										status = '发布人已确认，等待通知任务开始';
										handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr contact'>联系Ta</button>";
									
									}
									break;
								case 2:
									if(data[i].isEvaluate > 0){
										status = '已完成';
										handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									}else{
										status = '待评价';
										handle = "<button class='fr evaluate' data-m='"+data[i].mission_id+"'>评价</button><button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									}								
									//判断是否已评价
									
									break;
								case 3:
									status = '已取消';
									handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									
									break;
								case 4:
									status = '被拒绝';
									handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									
									break;
							}
							$(".mysend").append("<li class='mysend-col'>"
													+ "<div class='m-t'>"
													+ "<img class='fl' src='"+data[i].head_img_url+"'>"
													+ "<div class='t-r fr'>"
													+ "<p class='task-status'>"
													+ status
													+ "</p>"
													+ "<p class='send-time'>"
													+ getMonthDay(data[i].take_time)
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
													+ handle
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