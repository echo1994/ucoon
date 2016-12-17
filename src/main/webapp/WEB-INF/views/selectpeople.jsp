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
<title>选人</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="css/mui.min.css">
<link href="css/style.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />
<style>
html,
		body{
			background: #fff;
		}
			.mui-content{
				background: #fff;
			}

		.title-who{
			border:none;
			padding-bottom: 5px;
		}
		h3{
			text-align: center;
			font-size: 14px;
			padding-bottom: 10px;
			border-bottom: 1px solid #eee;
			color: #999;
		}
		h3 span{
			color: #C3D94F;
		}
		.selet-num span{
			color: #C3D94F;
		}
		.select-people{
			margin: 10px;
		}
		.select-people .sp-tit{
			color: #555;
			font-size: 14px;
			line-height: 61px;
			margin-right: 5px;
		}
		.select-people ul{
			width: 240px;
			margin: 0;
		}
		.select-people li{

			float: left;
			width: 60px;
			margin-bottom: 10px;
		}
		.select-people li img{
			display: block;
			width: 40px;
			height: 40px;
			margin: 0 auto;
			border-radius: 50%;
		}

		.select-people li p{
			color: #999;
			font-size: 12px;
			text-align: center;
			overflow : hidden;
			text-overflow: ellipsis;

		}
		.joinin{
			border-bottom: 1px dashed #eee;
			margin-bottom: 10px;
		}


		.payment_time_mask{
			display: none;
		}

		.payment_time_mask{
			z-index: 999;
			width: 90%;
			height: 272px;
			position: absolute;
			left: 5%;
			top: 115px;
			border-radius: 10px;
			overflow: hidden;
			text-align: center;
		}
		#slider{
			z-index: 999;
			width: 100%;
			/*margin: 0 10px;*/
			/*height: 272px;*/
			position: absolute;
			/*left: 5%;*/
			top: 100px;
		}

		#bg{
			background-color:#000;
			position:fixed;
			z-index:99;
			left:0;
			top:0;
			display:none;
			width:100%;
			height:100%;
			opacity:0.3;
			filter: alpha(opacity=30);
			-moz-opacity: 0.3;
		}
		.mui-slider .mui-slider-group .mui-slider-item {
				width:240px;
				background: #fff;
				height: 300px;
			border-radius: 20px;
			margin: 0 10px;
			}
		.mui-slider .mui-slider-group .mui-slider-item img{
			width: 60px;
			height: 60px;
		}
		.basic-mes{
			margin-top:20px;;
		}
			.total-task{
				color: #999;
				text-align: center;
				font-size: 12px;
			}
		.total-task span{
			color: #c3d94f;
		}
			.link-way{
				text-align: center;
				color: #c3d94f
			}

			.link-way i{
				font-size: 16px;
				margin: 0 5px;
			}
			.ly{
				margin: 0 20px;
			}
			.ly .tit{
				font-size: 12px;
				color: #555;

			}
			.ly .cot{
				border: 1px solid #ccc;
				padding: 5px;
				font-size: 12px;
				color: #999;
				line-height: 15px;
			}
			.choose-btn{
				display: block;
				text-align: center;
				background: #c3d94f;
				width: 100px;
				height: 30px;
				color: #fff;
				margin: 10px auto 0 auto;
			}
			.close-bg{
				display: none;
				position: fixed;
				z-index: 999;
				bottom:40px;
				left:50%;
				margin-left: -20px;


			}
			.close-bg .mui-icon{
				font-size: 40px;
				color: #fff;
			}

			#slider{
				padding: 0 20%;
			}
</style>
</head>
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/mui.min.js"></script>
<script>

	var missionId = '${mdetails.mission_id }';
	//
	$(document).on('tap',".detail",function(){

		
		$.ajax({
		    url: "mission/selectpeopledetail/" + missionId,
		    success: function(result){
			    $(".mui-slider-group").html("");
			    for(var i = 0;i<result.length;i++){
			    	var sexHtml = result[i].sex == 2?"<i class=\"mui-icon iconfont icon-woman\"></i>":"<i class=\"mui-icon iconfont icon-man\"></i>";
			   	 	var note = result[i].note == null?"无": result[i].note;
			    	$(".mui-slider-group").append("<div class=\"mui-slider-item\">"
						+"<div class=\"basic-mes\" id=\"" + result[i].apply_id + "\">"
							+"<img src=\"" + result[i].head_img_url + "\">"
							+"<div class=\"ucoon-user\">"
								+ result[i].nick_name
								+ sexHtml
							+"</div>"
							+"<div class=\"user-score\">"
								+"<span class=\"fivestar\"> "
									/* +"<c:forEach varStatus="i" begin="1" end="${info.all }">"
										+"<i class="ui-icon iconfont icon-star"></i>"
									+"</c:forEach>"
									+"<c:forEach varStatus="i" begin="1" end="${info.blank }">"
										+"<i class="mui-icon iconfont icon-star-empty"></i>"
									+"</c:forEach>"
									+"<c:if test="${info.half == true}">"
										+"<i class="mui-icon iconfont icon-star-half"></i> "
									+"</c:if>" */
								+"</span>"
							+"</div>"
						+"</div>"
						+"<p class=\"total-task\">"
							+"已帮助他人 <span>" + result[i].helpCount + "</span>次"
						+"</p>"
						+"<div class=\"link-way\">"
							+"<a href=\"user/detailWithEvaluate/" + result[i].apply_id + "\">查看详情</a>"
						+"</div>"
						+"<div class=\"ly\">"
							+"<p class=\"tit\">他的留言:</p>"
							+"<p class=\"cot\">" + note + "</p>"
						+"</div>"
						+"<input type=\"hidden\" value=\"" + result[i].head_img_url + "\" class=\"headimgurl\" />"
						+"<input type=\"hidden\" value=\"" + result[i].nick_name + "\" class=\"nickname\" />"
						+"<input type=\"hidden\" value=\"" + result[i].apply_id + "\" class=\"applyId\" />"
						+"<button class=\"choose-btn\">选Ta</button>"
					+"</div>");
				    
			    	
			    
			    
			    }
			    
			   
			    mui('#slider').slider();
			    //		点击头像跳转到user-info.html
				var del = document.getElementsByClassName("basic-mes");
				for(var i=0; i<del.length; i++){
					del[i].addEventListener('tap', function() {
						mui.openWindow({
							url: "user/detailWithEvaluate/" + this.id
						});
					});
				}
				
		    },
		  	dataType: "json",
		  	async:true
		});
		
		$("#bg").css({
			display: "block", height: $(document).height()
		});
		$('#slider').show();
		$('.close-bg').show();
	
	})
	

	
	
			
	
	$(document).on('tap',".choose-btn",function(){
		$(this).parent().remove();
		
		var i = 0;
		var p = '${mdetails.people_count }';
		$(".deletepeople").each(function(index,item){
			i++;
		
		});
		
		if(i>=p){
			alert("已经达到人数要求，不可再选人");
			$('#slider').hide();
			$('#bg').hide();
			$(this).hide();
			return;
		
		}
		
		var headimgurl = $(this).parent().find('.headimgurl').val();
		var nickname = $(this).parent().find('.nickname').val();
		var applyId = $(this).parent().find('.applyId').val();
		$.ajax({
		    url: "applyOrders/chosePeople/"+applyId,
		    success: function(result){
		    	if(result == "success"){
		    		
		    		$("#select").append("<li class=\"deletepeople\">" 
											+ "<img src=\"" + headimgurl + "\" />"
											+ "<p>" + nickname + "</p>"
											+ "<input type=\"hidden\" value=\"" + applyId + "\" class=\"applyIdVal\">"
											+ "<input type=\"hidden\" value=\"" + headimgurl + "\" class=\"headimgUrlVal\">"
											+ "<input type=\"hidden\" value=\"" + nickname + "\" class=\"nickNameVal\">"
										+ "</li>");
					$("#" + applyId).remove();	
		    	}else{
		    		alert(result);
		    	}
		    	
		    },
		  	dataType: "test",
		  	async:false
		});
		
		$("#select").append("<li class=\"deletepeople\">" 
											+ "<img src=\"" + headimgurl + "\" />"
											+ "<p>" + nickname + "</p>"
											+ "<input type=\"hidden\" value=\"" + applyId + "\" class=\"applyIdVal\">"
											+ "<input type=\"hidden\" value=\"" + headimgurl + "\" class=\"headimgUrlVal\">"
											+ "<input type=\"hidden\" value=\"" + nickname + "\" class=\"nickNameVal\">"
										+ "</li>");
		$("#" + applyId).remove();	
	})

</script>
<body>
	<div class=" mui-draggable" id="offCanvasWrapper"
		class="mui-off-canvas-wrap mui-draggable mui-scalable">
		<h2 class="title-who">
			<i class="mui-icon mui-icon-download"></i>筛选服务者
		</h2>
		<h3>
			共需 <span>${mdetails.people_count }</span>人
		</h3>
		<div class="select-people" id="offCanvasContentScroll">
			<div class="joinin clearfix">
				<div class="sp-tit fl">未选择:</div>
				<ul class="clearfix fl" id="unselect">
					<c:forEach items="${list }" var="info"> 
						<li  id="${info.apply_id }" class="detail">
							<img src="${info.head_img_url }" />
							<p>${info.nick_name }</p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="selected clearfix">
				<div class="sp-tit fl">已选择（点击取消）:</div>
					<ul class="clearfix fl" id="select">
						<c:forEach items="${list2 }" var="info">
							<li class="deletepeople">
								<img src="${info.head_img_url }" />
								<p>${info.nick_name }</p>
								<input type="hidden" value="${info.apply_id }" class="applyIdVal">
								<input type="hidden" value="${info.head_img_url }" class="headimgUrlVal">
								<input type="hidden" value="${info.nick_name }" class="nickNameVal">
							</li>
						</c:forEach>
	
					</ul>
					 
			</div>
		</div>
		<div id="footer">
			<button class="send-btn" id="done">完成</button>
		</div>
		

		<!--弹窗遮罩层-->
		<div id="bg"></div>
		<!--选人滑动模块-->
		<div id="slider" class="mui-slider" style="display: none;">
			<div class="mui-slider-group">


			

			</div>

		</div>
		<div class="close-bg">
			<i class="mui-icon mui-icon-close"></i>
		</div>

	</div>
	
</body>



<script>

		//图片轮播定时轮播--不允许自动滚动
		var slider = mui("#slider");
		slider.slider({
			interval: 0
		});
		$(function () {
			$('.close-bg').click(function () {
				$('#slider').hide();
				$('#bg').hide();
				$(this).hide();
			})
			$('#done').click(function () {
				var peopleCount = ${mdetails.people_count };
				var selectPeopleCount = 0;
				$(".deletepeople").each(function(index,item){
					selectPeopleCount++;
				});
				
				if(selectPeopleCount <= 0){
					alert("选择人数必须大于1");
					return;
				}
				
				if(selectPeopleCount > peopleCount){
					alert("选择的人数不能大于需要的人数");
					return;
				}
				
				var tip = '当前你已选择' + selectPeopleCount + '人，确认后将无法更改!';
				if(selectPeopleCount < peopleCount){
					tip = '当前你已选择' + selectPeopleCount + '人，确认后将无法更改，余下金额将退款余额中！';
				}
				
				
				var btnArray = ['取消', '确认'];
                mui.confirm(tip, '有空ucoon', btnArray, function(e) {
                    if (e.index == 1) {
                        $.ajax({
						    url: "mission/missionDoing/"+${mdetails.mission_id} ,
						    dataType: "text",
						    async:true,
						    success: function(result){
						    	if(result != "success"){
						    		alert(result);
						    		return;
						    	
						    	}
						    	window.location.href = "mission/order-info/" + ${mdetails.mission_id};
						    	
						    	
						    }
						});
                    } 
                })
			})
			
			$(document).on('tap',".deletepeople",function(){
				var applyId = $(this).find(".applyIdVal").val();
				var headimgUrlVal = $(this).find(".headimgUrlVal").val();
				var nickNameVal = $(this).find(".nickNameVal").val();
				var bq = $(this);
				$.ajax({
				    url: "applyOrders/cancelPeople/"+applyId,
				    success: function(result){
				    	if(result == 'success'){
							bq.remove();
							$("#unselect").append("<li  id=\"" + applyId + "\" class=\"detail\">"
										+ "<img src=\"" + headimgUrlVal + "\" />"
										+ "<p>" + nickNameVal + "</p>"
									+ "</li>");
				    	}else if(result == 'error'){
				    		alert("选人失败");
				    	}else{
				    		alert(result);
				    	}
				    },
				  	dataType: "text",
				  	async:true
				});
				
			})
		});
	
		
			
			
</script>

</html>
