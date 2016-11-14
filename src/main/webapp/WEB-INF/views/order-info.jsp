<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>  
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
			.mui-preview-image.mui-fullscreen {
				position: fixed;
				z-index: 20;
				background-color: #000;
			}
			.mui-preview-header,
			.mui-preview-footer {
				position: absolute;
				width: 100%;
				left: 0;
				z-index: 10;
			}
			.mui-preview-header {
				height: 44px;
				top: 0;
			}
			.mui-preview-footer {
				height: 50px;
				bottom: 0px;
			}
			.mui-preview-header .mui-preview-indicator {
				display: block;
				line-height: 25px;
				color: #fff;
				text-align: center;
				margin: 15px auto 4;
				width: 70px;
				background-color: rgba(0, 0, 0, 0.4);
				border-radius: 12px;
				font-size: 16px;
			}
			.mui-preview-image {
				display: none;
				-webkit-animation-duration: 0.5s;
				animation-duration: 0.5s;
				-webkit-animation-fill-mode: both;
				animation-fill-mode: both;
			}
			.mui-preview-image.mui-preview-in {
				-webkit-animation-name: fadeIn;
				animation-name: fadeIn;
			}
			.mui-preview-image.mui-preview-out {
				background: none;
				-webkit-animation-name: fadeOut;
				animation-name: fadeOut;
			}
			.mui-preview-image.mui-preview-out .mui-preview-header,
			.mui-preview-image.mui-preview-out .mui-preview-footer {
				display: none;
			}
			.mui-zoom-scroller {
				position: absolute;
				display: -webkit-box;
				display: -webkit-flex;
				display: flex;
				-webkit-box-align: center;
				-webkit-align-items: center;
				align-items: center;
				-webkit-box-pack: center;
				-webkit-justify-content: center;
				justify-content: center;
				left: 0;
				right: 0;
				bottom: 0;
				top: 0;
				width: 100%;
				height: 100%;
				margin: 0;
				-webkit-backface-visibility: hidden;
			}
			.mui-zoom {
				-webkit-transform-style: preserve-3d;
				transform-style: preserve-3d;
			}
			.mui-slider .mui-slider-group .mui-slider-item img {
				width: auto;
				height: auto;
				max-width: 100%;
				max-height: 100%;
			}
			.mui-android-4-1 .mui-slider .mui-slider-group .mui-slider-item img {
				width: 100%;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-slider-group .mui-slider-item {
				display: inline-table;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-zoom-scroller img {
				display: table-cell;
				vertical-align: middle;
			}
			.mui-preview-loading {
				position: absolute;
				width: 100%;
				height: 100%;
				top: 0;
				left: 0;
				display: none;
			}
			.mui-preview-loading.mui-active {
				display: block;
			}
			.mui-preview-loading .mui-spinner-white {
				position: absolute;
				top: 50%;
				left: 50%;
				margin-left: -25px;
				margin-top: -25px;
				height: 50px;
				width: 50px;
			}
			.mui-preview-image img.mui-transitioning {
				-webkit-transition: -webkit-transform 0.5s ease, opacity 0.5s ease;
				transition: transform 0.5s ease, opacity 0.5s ease;
			}
			@-webkit-keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@-webkit-keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			@keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			p img {
				max-width: 100%;
				height: auto;
			}
		</style>
	</head>
	<script src="js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		$(function() {
		
			var state = ${mdetails.mission_status};
			/* ${mdetails.mission_status} == "0" ? $('.order-check').css("display", "block") : $(
					'.order-check').css("display", "none"); */
					
			$('.fb').text('${mdetails.publish_time}'.substring(0,'${mdetails.publish_time}'.indexOf(".")));
			$('.jz').text('${mdetails.end_time}'.substring(0,'${mdetails.end_time}'.indexOf(".")));
			$('.wc').text('${mdetails.finish_time}' == ""?'未完成 ':'${mdetails.finish_time}'.substring(0,'${mdetails.finish_time}'.indexOf(".")));
			/* switch (state) {
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
			} */
			
			
	
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
				<span class="fl">交易状态</span>
				<span class="fr status">
					<c:choose>
					    <c:when test="${mdetails.mission_status == 0}">
					        	未支付	
					    </c:when>
						<c:when test="${mdetails.mission_status == 1}">
					        	已支付，待选人 / 待确认	
					    </c:when>
					    <c:when test="${mdetails.mission_status == 2}">
					        	申请退款	
					    </c:when>
					    <c:when test="${mdetails.mission_status == 3}">
					        	已取消	
					    </c:when>
					    <c:when test="${mdetails.mission_status == 4}">
					        	已退款	
					    </c:when>
					    <c:when test="${mdetails.mission_status == 5}">
					        	待评价	
					    </c:when>
					    <c:when test="${mdetails.mission_status == 6}">
					        	正在服务	
					    </c:when>
					    <c:when test="${mdetails.mission_status == 7}">
					        	任务完成	
					    </c:when>
						<c:otherwise>
				        
					    </c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
		<div class="order-model">
			<div class="model-col clearfix">
				<span class="fl">订单编号</span><span class="fr">${mdetails.mission_order_num }</span>
			</div>
			<div class="model-col clearfix">
				<span class="fl">发布时间</span><span class="fr fb"></span>
			</div>
			<div class="model-col clearfix">
				<span class="fl">截止时间</span><span class="fr jz"></span>
			</div>
			<div class="model-col clearfix">
				<span class="fl">完成时间</span><span class="fr wc"></span>
			</div>

		</div>
		<div class="order-model padded">
			<div class="model-col clearfix">
				<span class="fl">接单者详情及反馈</span>
			</div>
			
			<c:choose>
			    <c:when test="${mdetails.mission_status == 0}">
			        	无	
			    </c:when>
			    <c:when test="${mdetails.mission_status == 1}">
			        	这里显示可选人数	
			    </c:when>
			    
			    <c:when test="${mdetails.mission_status == 5 ||mdetails.mission_status == 6}">
			        <ul class="clearfix">
						<c:forEach items="${list }" var="info" varStatus="status">
							<c:if test="${status.first==true}">
								<li class="user-list-col cur">
									<img src="${info.head_img_url }" alt="">
									<span>${info.nick_name }</span>
								</li>
							</c:if>
							<c:if test="${status.first!=true}">
								<li class="user-list-col">
									<img src="${info.head_img_url }" alt="">
									<span>${info.nick_name }</span>
								</li>
							</c:if>
						</c:forEach>
					</ul>
					<div class="user-feedback">
						
						<c:forEach items="${list }" var="info" varStatus="status">
							<c:if test="${status.first==true}">
								<div class="feedback-col" >
									<div class="col">
										<span class="label">TA的电话：</span><span>${info.phone }</span>
									</div>
									<div class="col">
										<span  class="label">TA的微信：</span><span>${info.weixin_id }</span>
									</div>
									<div class="col">
										<div  class="label">任务完成情况：</div>
										<div class="feed-cot">
											${info.apply_detail }
										</div>
				
										<div class="img-list clearfix">
											<c:if test="${info.pic_count >0}">
												<c:forEach begin="0" end="${info.pic_count -1}" var="i" >
													
													<img src="applyImage/${info.pictures }/${i}" data-preview-src="" data-preview-group="1"  alt="">
												</c:forEach>
											</c:if>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${status.first!=true}">
								<div class="feedback-col" style="display: none;">
									<div class="col">
										<span class="label">TA的电话：</span><span>${info.phone }</span>
									</div>
									<div class="col">
										<span  class="label">TA的微信：</span><span>${info.weixin_id }</span>
									</div>
									<div class="col">
										<div  class="label">任务完成情况：</div>
										<div class="feed-cot">
											${info.apply_detail }
										</div>
										<div class="img-list clearfix">
											<c:if test="${info.pic_count >0}">
												
												<c:forEach begin="0" end="${info.pic_count -1}" var="i" >
													
													<img src="applyImage/${info.pictures }/${i}" data-preview-src="" data-preview-group="1"  alt="">
												</c:forEach>
											</c:if>
										</div>
										
									</div>
								</div>
							</c:if>
							
						</c:forEach>
						
						
					</div>
			    </c:when>
			    <c:otherwise>
			        
			    </c:otherwise>
			</c:choose>
		<div class="order-check">完成&评价</div>
	</div>

	</body>
	<script src="js/mui.min.js"></script>
	<script src="js/mui.zoom.js"></script>
	<script src="js/mui.previewimage.js"></script>
	<script>
		mui.previewImage();

		$('.user-list-col').on('tap', function() {
			//			tab切换
			$(this).addClass('cur').siblings().removeClass('cur');

			//			内容切换
			var index = $(this).index()
			$('.feedback-col').eq(index).show().siblings().hide();
		})
	</script>

</html>
