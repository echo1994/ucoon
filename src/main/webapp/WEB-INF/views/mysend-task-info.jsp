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
	
		var state = ${ou.take_state};
		${ou.state} == "0" ? $('.order-check').css("display", "block") : $(
				'.order-check').css("display", "none");
				
		switch (state) {
			case 0:
				$('.status').text("正在审核");
				$('.order-check').text("取消");
				break;
			case 1:
				if(data[i].selectpeople == data[i].people_count){
					$('.status').text("正在进行");
					$('.order-check').text("完成&评价");
				}else{
					$('.status').text("等待系统通知任务开始");
					$('.order-check').remove();
				}
				break;
			case 2:
				if(data[i].isEvaluate > 0){
					status = '已完成';
					handle = "<button class='fr order' data-m='"+data[i].apply_id+"'>查看订单</button><button class='fr'>联系Ta</button>";
				}else{
					status = '待评价';
					handle = "<button class='fr evaluate' data-m='"+data[i].apply_id+"'>评价</button><button class='fr order' data-m='"+data[i].apply_id+"'>查看订单</button><button class='fr'>联系Ta</button>";
				}								
				//判断是否已评价
				
				break;
			case 3:
				status = '已取消';
				handle = "<button class='fr order' data-m='"+data[i].apply_id+"'>查看订单</button><button class='fr'>联系Ta</button>";
				
				break;
			case 4:
				status = '被拒绝';
				handle = "<button class='fr order' data-m='"+data[i].apply_id+"'>查看订单</button><button class='fr'>联系Ta</button>";
				
				break;
		}
		
		
		$('.num').text(${ou.order_num});
		$('.ot').text('${ou.take_time}'.substring(0,'${ou.take_time}'.indexOf(".")));
		$('.ft').text('${ou.finish_time}' == "" ? "未完成" : getDateDiff('${ou.finish_time}'));
		$('.wei').text('${ou.weixin_id}' == "" ? "未填写" : '${ou.weixin_id}');
		$('.phone').text('${ou.phone}' == "" ? "未绑定" : '${ou.phone}');

		$('.u').bind('click', function() {
			window.location.href = 'user/orderUser/' + $(this).attr("data-u");
		});
		$('.order-check').bind('click', function() {
			$.ajax({
				url : 'orders/finishOrder/' + $(this).attr("data-o"),
				data : {},
				async : false,
				type : 'post',
				dataType : 'text',
				success : function(data) {
					if (data == "true") {
						alert("操作成功");
					} else {
						alert("提交失败，请重试");
					}
					window.history.go(0);
				}
			});
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
		<div class="order-check" data-o="${ou.apply_id}"></div>
	</div>

</body>
</html>
