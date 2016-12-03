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
<meta charset="utf-8">
<title>我发布的</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="css/mui.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="js/jquery-2.1.4.min.js"></script>
<style>
.mui-popup-inner {
	padding: 50px 15px;
}

.mui-popup-inner .mui-popup-text {
	color: #888;
	font-size: 18px;
}

.mui-popup-button {
	color: #C3D94F;
}

.mui-popup-buttons .mui-popup-button-bold {
	background-color: #C3D94F;
	color: #fff;
}
</style>
</head>

<body>
	<!--筛选导航-->
	<div class="send-select">
		<div class="send-select-col mui-pull-left" id="onselling">
			<span class="title-active" id="onselling-span">已发布</span>
		</div>
		<div class="send-select-col mui-pull-left" id="offtheshelf">
			<span id="offtheshelf-span">已下架</span>
		</div>
		<div class="send-select-col mui-pull-left" id="tobepaid">
			<span id="tobepaid-span">待支付</span>
		</div>
		<!-- <div class="send-select-col mui-pull-left" id="allstt">
			<span id="allstt-span">已完成</span>
		</div> -->
	</div>


	<div class="mui-content">

		<ul class="mysend">
			<!-- <li class="mysend-col">
				<div class="m-t">
					<img class="fl" src="images/muwu.jpg">
					<div class="t-r fr">
						<p class="task-status">已发布</p>
						<p class="send-time">08-08 13:30</p>
					</div>
					<div class="t-m">
						<p class="task-title">求代课</p>
						<p class="read-times">
							<i class="mui-icon mui-icon-eye"></i>520
						</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">查看详情</button>
					<button type="button"
						class="fr mui-btn mui-btn-blue mui-btn-outlined">删除</button>
				</div>
			</li>
			<li class="mysend-col">
				<div class="m-t">
					<img class="fl" src="images/logo.png">
					<div class="t-r fr">
						<p class="task-status">已发布</p>
						<p class="send-time">08-08 13:30</p>
					</div>
					<div class="t-m">
						<p class="task-title">带领快递</p>
						<p class="read-times">
							<i class="mui-icon mui-icon-eye"></i>520
						</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">查看详情</button>
					<button type="button"
						class="fr mui-btn mui-btn-blue mui-btn-outlined">删除</button>
				</div>
			</li>
 -->

		</ul>

	</div>

</body>
<script src="js/mui.min.js"></script>

<script type="text/javascript">
	var activeIndex = "#onselling-span";
	var currentPage = 0;
	var onePageNums = 10;
	$(function() {
		loaddata(1,currentPage * onePageNums, (currentPage + 1) * onePageNums - 1, true);
		action();

		var onselling = document.getElementById('onselling');
		onselling.addEventListener('tap', function() {
			//已支付、申请退款、正在进行,1-2-6
			$(activeIndex).attr("class", "");
			$("#onselling-span").attr("class", "title-active");
			activeIndex = "#onselling-span";
			loaddata(1, currentPage * onePageNums, (currentPage + 1) * onePageNums - 1, true);
			action();
		});

		var offtheshelf = document.getElementById('offtheshelf');
		offtheshelf.addEventListener('tap', function() {
			//已下架：已取消、已退款,3-4
			$(activeIndex).attr("class", "");
			$("#offtheshelf-span").attr("class", "title-active");
			activeIndex = "#offtheshelf-span";
			loaddata(2,currentPage * onePageNums, (currentPage + 1) * onePageNums - 1, true);
			action();
		});
		var tobepaid = document.getElementById('tobepaid');
		tobepaid.addEventListener('tap', function() {
			//未支付,0
			$(activeIndex).attr("class", "");
			$("#tobepaid-span").attr("class", "title-active");
			activeIndex = "#tobepaid-span";
			loaddata(3, currentPage * onePageNums, (currentPage + 1) * onePageNums - 1, true);
			action();
		});
		
		/* var allstt = document.getElementById('allstt');
		allstt.addEventListener('tap', function() {
			//已完成：待评价、已完成,5
			$(activeIndex).attr("class", "");
			$("#allstt-span").attr("class", "title-active");
			activeIndex = "#allstt-span";
			loaddata(4, currentPage * onePageNums, (currentPage + 1) * onePageNums - 1, true);
			action();
		}); */

		
	})
	
	
	function action(){
		$(".detail").bind(
			//查看任务详情
				"tap",
				function() {
					window.location.href = "mission/order-info/"
							+ $(this).attr("data-m");
				})
				
		$(".pick").bind(
				"tap",
				function() {
					//选人
					window.location.href = "mission/selectpeople/"
							+ $(this).attr("data-m");
				})
		$(".evaluate").bind("tap",function() {
		//跳转评价界面
					window.location.href = "mission/evaluate_publish/"
							+ $(this).attr("data-m");
				})
		
		
		$(".pay").bind("tap", function() {
			window.location.href = "mission-pay?id="
							+ $(this).attr("data-m");
		})
		
		$(".offshelf").bind("tap", function() {
			//申请退款
			var btnArray = ['我不听', '好'];
		    mui.confirm('需帅气的管理员同意后将退款金额以红包的形式发给你', '有空ucoon', btnArray, function(e) 
		    {
		        if (e.index == 1) 
		        {
		            $.ajax({
						url : 'mission/missionOffShelf/' + $(".offshelf").attr("data-m"),
						data : {},
						async : false,
						type : 'post',
						dataType : 'text',
						success : function(data) {
							alert(data);
							window.history.go(0);
						}
					})
		        } 
		        else 
		        {
		            
		        }
		    })
			
		})
		
		
		$(".cancleoff").bind("tap", function() {
			//取消退款
			$.ajax({
				url : 'mission/cancelOff/' + $(this).attr("data-m"),
				data : {},
				async : false,
				type : 'post',
				dataType : 'text',
				success : function(data) {
					alert(data);
					window.history.go(0);
				}
			})
		})
		$(".sure").bind("tap", function() {
			//确认完成
			$.ajax({
				url : 'mission/missionDone/' + $(this).attr("data-m"),
				data : {},
				async : false,
				type : 'post',
				dataType : 'text',
				success : function(data) {
					alert(data);
					window.history.go(0);
				}
			})
		})
		
		$(".cancel").bind("tap", function() {
			$.ajax({
				url : 'mission/missionCancel/' + $(this).attr("data-m"),
				data : {},
				async : false,
				type : 'post',
				dataType : 'text',
				success : function(data) {
					alert(data);
					window.history.go(0);
				}
			})
		})
	}
	
	function initIndex() {
		currentPage = 0;
		onePageNums = 10;
	}
	function loaddata(missionStatus, startIndex, endIndex, clearable) {
		$
				.ajax({
					url : 'mission/getMissionsLimited',
					data : {
						startIndex : startIndex,
						endIndex : endIndex,
						userId : 0,
						missionStatus : missionStatus
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
							switch (data[i].mission_status) {
							case 1:
								//这里要算人数
								if(data[i].people_count == (data[i].selectpeople + data[i].donepeople)){
									if(data[i].selectpeople == 0){
										//执行者已完成 待确认
										status = "任务已完成，待确认";
										handle = "<button class='fr sure' data-m='"+data[i].mission_id+"'>确认完成</button><button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
									}else{
										//任务正在进行
										status = "正在进行中," + data[i].donepeople + "人已完成";
										handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
									}
								}else{
									status = "需要" + data[i].people_count + "人,报名" + data[i].totalpeople + "人,选择" + data[i].selectpeople + "人";
									handle = "<button class='fr pick' data-m='"+data[i].mission_id+"'>选人</button><button class='fr offshelf' data-m='"+data[i].mission_id+"'>下架</button><button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
								}
								
								
								break;
							case 2:
								status = '退款审核中';
								handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button><button class='fr cancleoff' data-m='"+data[i].mission_id+"'>取消退款</button>";
								break;
							case 0:
								status = '待支付';
								handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button><button class='fr pay' data-m='"+data[i].mission_id+"'>支付</button><button class='fr cancel' data-m='"+data[i].mission_id+"'>取消订单</button>";
								break;
							case 3:
								status = '已退款';
								handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
								break;
							case 4:
								status = '已取消';
								handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
								break;
							case 5:
							
								if(data[i].people_count == data[i].evlpeople){
									status = '已完成';
									handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
								}else{
									status = '待评价,还有' + (data[i].people_count - data[i].evlpeople) + '人未评价';
									handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button><button class='fr evaluate' data-m='"+data[i].mission_id+"'>评价</button>";
								}
								break;
							case 6:
							
								if(data[i].people_count == data[i].donepeople){
									status = '任务完成';
									handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
								}else{
									status = "正在进行中," + data[i].donepeople + "人已完成";
									handle = "<button class='fr detail' data-m='"+data[i].mission_id+"'>查看详情</button>";
								}
								break;
							} 
							$(".mysend")
									.append(
											"<li class='mysend-col'>"
													+ "<div class='m-t'>"
													+ "<div class='missionKey' style='display:none;'>"
													+ data[i].mission_id
													+ "</div>"
												
													+ "	<div class='t-r fr'>"
													+ "		<p class='task-status'>"
													+ status
													+ "</p>"
													+ "	<p class='send-time'>"
													+ getMonthDay(data[i].publish_time)
													+ "</p>"
													+ "	</div>"
													+ "	<div class='t-m'>"
													+ "		<p class='task-title'>"
													+ data[i].mission_title
													+ "</p>"
													+ "		<p class='read-times'>"
													+ "			<i class='mui-icon mui-icon-eye'></i>"
													+ data[i].view_count
													+ ""
													+ "	</p>"
													+ "	</div>"
													+ "</div>"
													+ "<div class='m-b'>"
													+ handle
													+ "	</div>"
													+ "</li>");
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
		minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
		return month + day + hour + minute;
	}
</script>
</html>