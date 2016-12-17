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
<title>我的任务</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/mui.min.js"></script>
<script src="js/mui.imageViewer.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<link href="css/mui.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />
<link href="css/mui.imageviewer.css" rel="stylesheet" />
</head>
<style>

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
.pop-cot{
    position: fixed;
    text-align: center;
    z-index: 9999;
    top:50%;
    left: 50%;
    width: 270px;
    margin-left: -135px;
    margin-top: -150px;
    height: 280px;
    background: #fff;
    border-radius: 10px;
}
.pop-cot .title{
    margin: 20px auto 10px auto;
    color: #c3d94f;
    text-align: center;

}
.pop-cot textarea{
    width: 200px;
    height: 80px;
    padding: 5px;
    margin-bottom: 0;
    font-size: 14px;
    color: #555;
}
.pop-cot ul{
    margin-left: 35px;
    margin-top: 10px;
    margin-bottom: 5px;
}
.pop-cot ul li{
    width: 50px;
    height: 50px;
    position: relative;
    float: left;
    margin-right: 10px;
}
.pop-cot ul li img{
    width: 50px;
    height: 50px;
}
.pop-cot ul li input{
    position: absolute;
    display: block;
    top: 0;
    left: 0;
    width: 50px;
    height: 50px;
    font-size: 0;
    opacity: 0;

}
.pop-cot .tip{
    font-size: 10px;
    color: #999;
    text-align: left;
    padding-left: 35px;
}
.pop-cot .over-btn{
    position: absolute;
    bottom: 15px;
    width: 200px;
    left: 50%;
    margin-left: -100px;
}
.pop-cot .over-btn button{
    width: 80px;
    margin: 0 10px;
    background: #c3d94f;
    color: #fff;
}

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
	.assessment{
		padding: 0 20px;
		font-size: 12px;
		color: #999;
	}

</style>
<script type="text/javascript">
	var URL = window.location.href.split('#')[0]; //获取当前页面的url
	URL = encodeURIComponent(URL);
	var appid,nonceStr,signature,timestamp;
	//ajax同步更新全局变量，异步无法更新
	$.ajax({
	    url: "wx/sign?url="+URL,
	    success: function(result){
	    	appid = result.appId;
	    	timestamp=result.timestamp;
	    	nonceStr=result.nonceStr;
	    	signature=result.signature;
	    },
	  	dataType: "json",
	  	async:false
	});
	
	wx.config({
	    debug: false,
	    appId: appid,
	    timestamp: timestamp,
	    nonceStr: nonceStr,
	    signature: signature,
	    jsApiList: [
	      'checkJsApi',
	      'openLocation',
	      'getLocation'
	    ]
	});
	wx.ready(function(){
	
	/* 	wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		        latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度
		       	setTimeout(loaddata(currentPage * onePageNums, (currentPage + 1) * onePageNums - 1, '', true,'all'),1000);
		    }
		});  */
	});
	

	var currentPage = 0;
	var onePageNums = 10;
	$(function() {
		var pt = getMonthDay1("${mdetails.publish_time}");
		var st = getMonthDay2("${mdetails.start_time}");
		var et = getMonthDay2("${mdetails.end_time}");
		$("#pt").html('${mdetails.publish_time}'.substring(0,'${mdetails.publish_time}'.indexOf(".")));
		$("#jz").html('${mdetails.end_time}'.substring(0,'${mdetails.end_time}'.indexOf(".")));
		$("#st").html('${ou.take_time}'.substring(0,'${ou.take_time}'.indexOf(".")));
		$("#wc").html('${ou.finish_time}' == "" ? "未完成" : '${ou.finish_time}'.substring(0,'${ou.finish_time}'.indexOf(".")));
		$("#note").html('${ou.note}' == ""? "<button onclick=\"note()\">留言</button>" : '${ou.note}');
		
		if('${evaluate.executorScore}' == null || '${evaluate.executorScore}' == ""){
			$(".user-score").html("未评价");
		}else{
			$(".fivestar").html('${evaluate.executorScore}');
			$(".assessment").html('${evaluate.executorEvaluate}' == "" ? "无":'${evaluate.executorEvaluate}');
		}
		
		
		var state = ${ou.take_state};
		var missionState = ${mdetails.mission_status};
		var handle="";
		switch (state) {
			case 0:
			
				if(missionState == 6){
					$("#zt").html("未被选上");
					handle = "<button class=\"fl chat\" data-m='"+${ou.apply_id}+"'>联系ta</button>";
				
				}else{
					$("#zt").html("正在审核");
					handle = "<button class=\"fl chat\" data-m='"+${ou.apply_id}+"'>联系ta</button><button class=\"fl cur cancelorder\" data-m='"+${ou.apply_id}+"'>取消任务</button>";
				}
				break;
			case 1:
				if(missionState == 6){
					$("#zt").html("正在进行");
					handle = "<button class=\"fl chat\">联系ta</button><button class=\"fl cur done\" data-m='"+${ou.apply_id}+"'>完成任务</button>";
				
				}else{
					$("#zt").html("等待系统通知任务开始");
					handle = "<button class=\"fl cur chat\" style=\"width:100%;\">联系ta</button>";
				
				}
				break;
			case 2:
				if(${ou.isEvaluate} > 0){
					$("#zt").html("已完成");
					handle = "<button class=\"fl cur chat\" style=\"width:100%\">联系ta</button>";
				}else{
					$("#zt").html("待评价");
					handle = "<button class=\"fl chat\">联系ta</button><button class=\"fl cur evaluate\" data-m='"+${ou.mission_id}+"'>评价</button>";
				}								
				//判断是否已评价
				break;
			case 3:
				$("#zt").html("已取消");
				handle = "<button class=\"fl cur chat\"  style=\"width:100%;\">联系ta</button>";
				break;
			case 4:
				$("#zt").html("已发送给雇主，等待雇主审核通过");
				handle = "<button class='fl cur chat'  style=\"width:100%;\">联系Ta</button>";
				
				break;
		}
		$(".fix-btn").html(handle);
		
		
		
		
		$.ajax({
			url : 'applyOrders/getOrdersCountByM',
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
		
		var imageViewer = new mui.ImageViewer('.content-image', {
			dbl: false //单击放大，true双击放大
		});
		
		var max = ${mdetails.pic_count};
		for (var i = 0; i < max; i++) {
			$("#imgbox").append(
					"<img class='content-image' src='reqmImage/${mdetails.pictures}/"+i+"' style=\"max-width: 50px;margin:5px;\" ><br>");

		}
		imageViewer.findAllImage();
		setTimeout(loadcommentdata(0, 10, ${mdetails.mission_id}), 1000);
		
		$("#missionplace").click(function(){
		
			wx.openLocation({
			    latitude: ${mdetails.mission_lat}, // 纬度，浮点数，范围为90 ~ -90
			    longitude: ${mdetails.mission_lng}, // 经度，浮点数，范围为180 ~ -180。
			    name: '${mdetails.mission_title}', // 位置名
			    address: '${mdetails.mission_describe}', // 地址详情说明
			    scale: 15, // 地图缩放级别,整形值,范围从1~28。默认为最大
			    infoUrl: 'http://wx.ucoon.cn' // 更多信息
			});
		
		});
		
		commentsItemClick();
	})


	function commentsItemClick(){
 		//绑定元素点击后 ajax执行后可执行$(document).on('click',"",function(){})
 		
 		
 		
		$(document).on('click',"ul li.discus-col div.father",function(){
			var item = $(this);
			var userId =item.find(".userId").val();
			var commentId = item.find(".commentsId").val();
			var missionId = ${mdetails.mission_id};
			
			$.ajax({
				url : 'comment/checkComments',
				data : {
					commentId : commentId,
					userId : userId
				},
				async : true,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if(data.result == "delete"){
						var btnArray = ['否', '是'];
						mui.confirm('确认要删除该条评论？', '提示', btnArray, function(e) {
							if (e.index == 1) {
								
								$.ajax({
									url : 'comment/deleteComments',
									data : {
										commentId : data.msg
									},
									async : false,
									type : 'post',
									dataType : 'json',
									success : function(data) {
										if(data.result != "success"){
											mui.toast(data.msg);
											return;
										}
										$(item).parent().fadeOut('slow');
										$(item).parent().remove();
										mui.toast('删除成功');
									}
								});
								
								
							} 
						})
						return;
					}
					var btnArray = ['取消', '确定'];
					mui.prompt('请输入你对' + data.user.nickName + '的回复：', '回复内容', '${user.nickName}', btnArray, function(e) {
						if (e.index == 1) {
							if(trimStr(e.value) == ""){
								mui.alert('回复不能为空!', '提示', function() {});
								return false; 
							}
							 
							$.ajax({
								url : 'comment/saveCommentsChild',
								data : {
									content : e.value,
									commentId:commentId,
									toUserId:userId
								},
								async : false,
								type : 'post',
								dataType : 'json',
								success : function(data) {
									if(data.result == "error"){
										mui.alert(data.msg, '提示', function() {});
										return;
									}
									
									var html =  "<div class=\"son clearfix\">"
										+"<div class=\"s-r fr\">"
										+"<p class=\"discus-time\">"  + getDateDiff(data.msg.commentschild.commentTime) + "</p>"
										+"</div>"
										+"<div class=\"s-m fl\">"
										+"<p><span>"  + data.msg.fromuser.nickName + "</span>@<span>"  + data.msg.touser.nickName + "</span></p>"
										+"<p class=\"discus-content\">"  + data.msg.commentschild.content + "</p>"
										+"</div>"
										+"<input type=\"hidden\" class=\"fromUserId\" value=\""  + data.msg.fromuser.userId + "\" \>"
										+"<input type=\"hidden\" class=\"commentsChildId\" value=\""  + data.msg.commentschild.commentChildId + "\" \>"
										+"<input type=\"hidden\" class=\"commentsId\" value=\""  + data.msg.commentschild.commentId + "\" \>"
										+"</div>";
									
									$(html).hide().appendTo($(item).parent()).show('slow');	
								}
							})
						} 
					})
					
					
				}
			})
		});
		
		$(document).on('click',"ul li.discus-col div.son",function(){
			var item = $(this);
			var fromUserId = item.find(".fromUserId").val();
			var commentsChildId = item.find(".commentsChildId").val();
			var commentsId = item.find(".commentsId").val();
			var missionId = ${mdetails.mission_id};
			
			$.ajax({
				url : 'comment/checkComments',
				data : {
					commentId : commentsChildId,
					userId : fromUserId
				},
				async : true,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if(data.result == "delete"){
						var btnArray = ['否', '是'];
						mui.confirm('确认要删除该条评论？', '提示', btnArray, function(e) {
							if (e.index == 1) {
								
								$.ajax({
									url : 'comment/deleteCommentsChild',
									data : {
										commentChildId : data.msg
									},
									async : false,
									type : 'post',
									dataType : 'json',
									success : function(data) {
										if(data.result != "success"){
											mui.toast(data.msg);
											return;
										}
										$(item).fadeOut('slow');
										$(item).remove();
										mui.toast('删除成功');
									}
								});
								
								
							} 
						})
						return;
					}
					var btnArray = ['取消', '确定'];
					mui.prompt('请输入你对' + data.user.nickName + '的回复：', '回复内容', '${user.nickName}', btnArray, function(e) {
						if (e.index == 1) {
							if(trimStr(e.value) == ""){
								mui.alert('回复不能为空!', '提示', function() {});
								return false; 
							}
							 
							$.ajax({
								url : 'comment/saveCommentsChild',
								data : {
									content : e.value,
									commentId:commentsId,
									toUserId:fromUserId
								},
								async : false,
								type : 'post',
								dataType : 'json',
								success : function(data) {
									if(data.result == "error"){
										mui.alert(data.msg, '提示', function() {});
										return;
									}
									
									var html =  "<div class=\"son clearfix\">"
										+"<div class=\"s-r fr\">"
										+"<p class=\"discus-time\">"  + getDateDiff(data.msg.commentschild.commentTime) + "</p>"
										+"</div>"
										+"<div class=\"s-m fl\">"
										+"<p><span>"  + data.msg.fromuser.nickName + "</span>@<span>"  + data.msg.touser.nickName + "</span></p>"
										+"<p class=\"discus-content\">"  + data.msg.commentschild.content + "</p>"
										+"</div>"
										+"<input type=\"hidden\" class=\"fromUserId\" value=\""  + data.msg.fromuser.userId + "\" \>"
										+"<input type=\"hidden\" class=\"commentsChildId\" value=\""  + data.msg.commentschild.commentChildId + "\" \>"
										+"<input type=\"hidden\" class=\"commentsId\" value=\""  + data.msg.commentschild.commentId + "\" \>"
										+"</div>";
									
									$(html).hide().appendTo($(item).parent()).show('slow');	
								}
							})
						} 
					})
					
					
				}
			})
		}); 
		
		$(".chat").on('tap',function(){
			window.location.href="chat/api-1/" + ${user.userId} + "/" + ${mdetails.user_id};
		});
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
		
		$(".done").click(function () {
            $("#bg").css({

                display: "block", height: $(document).height()
            });
            $('#pop').show();
//            $('.close-bg').show();
        });
        $('.close-btn').click(function () {
            $('#pop').hide();
            $('#bg').hide();
        })
		$(".donesure").on('tap',function(){
			
			var btnArray = ['还未完成', '我已完成'];
		    mui.confirm('请确认您的任务已完成', '有空ucoon', btnArray, function(e) 
		    {
		        if (e.index == 1) {
		        	$("#applyID").val($(".done").attr("data-m"));
		        	$("#myform").submit();
				} else {
		            
		        }
		    })
		});	
		
		
		$(".evaluate").on('tap',function(){
			window.location.href = "applyOrders/evaluate/"
							+ $(this).attr("data-m");
		});
	}

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
		return new Date(parseInt(timestamp) /1000).toLocaleString().replace(/:\d{1,2}$/,' ');  
	}
	
	function format(date){
	    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDay();
	}
	
	function loadcommentdata(startIndex, endIndex, missionId){
		$.ajax({
			url : 'comment/getCommentsLimited',
			data : {
				startIndex : startIndex,
				endIndex : endIndex,
				missionId : missionId,
			},
			async : false,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				$("#discusCount").html("(" + data.length + ")");
				
				
				
				for (var i = 0; i < data.length; i++) {
					if(data[i].sex == "1"){
						data[i].sex = "icon-man";
					}else{
					    data[i].sex = "icon-women";
					}
					
					var html = "<li class=\"discus-col\">"
						+"<div class=\"father\">"
						+"<img class=\"fl\" src=\""  + data[i].head_img_url + "\">"
						+"<div class=\"f-r fr\">"
						+"<p class=\"discus-time\">"  + getDateDiff(data[i].comment_time) + "</p>"
						+"</div>"
						+"<div class=\"f-m\">"
						+"<p>"  + data[i].nick_name + "<i class=\"mui-icon iconfont " + data[i].sex + "\"></i></p>"
						+"<p class=\"discus-content\">"  + data[i].content + "</p>"
						+"</div>"
						+"<input type=\"hidden\" class=\"userId\" value=\""  + data[i].user_id + "\" \>"
						+"<input type=\"hidden\" class=\"commentsId\" value=\""  + data[i].comment_id + "\" \>"
						+"</div>";
					
					for(var j = 0;j<data[i].child.length;j++){
						html +=  "<div class=\"son clearfix\">"
							+"<div class=\"s-r fr\">"
							+"<p class=\"discus-time\">"  + getDateDiff(data[i].child[j].comment_time) + "</p>"
							+"</div>"
							+"<div class=\"s-m fl\">"
							+"<p><span>"  + data[i].child[j].from_name + "</span>@<span>"  + data[i].child[j].to_name + "</span></p>"
							+"<p class=\"discus-content\">"  + data[i].child[j].content + "</p>"
							+"</div>"
							+"<input type=\"hidden\" class=\"fromUserId\" value=\""  + data[i].child[j].from_id + "\" \>"
							+"<input type=\"hidden\" class=\"commentsChildId\" value=\""  + data[i].child[j].comment_child_id + "\" \>"
							+"<input type=\"hidden\" class=\"commentsId\" value=\""  + data[i].child[j].comment_id + "\" \>"
							+"</div>";
						
					
					}
					html+="</li>";
					
					$(".m-discus").append(html);

				}

			}
		})
	}
	
	function comment(){
		var btnArray = ['取消', '确定'];
		mui.prompt('请输入评论的内容：', '内容', '${user.nickName}', btnArray, function(e) {
			if (e.index == 1) {
				if(trimStr(e.value) == ""){
					mui.alert('评论不能为空!', '提示', function() {});
					return false; 
				}
				 
				$.ajax({
					url : 'comment/saveComments',
					data : {
						content : e.value,
						missionId:${mdetails.mission_id}
					},
					async : true,
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if(data.result == "error"){
							mui.alert(data.msg, '提示', function() {});
							return;
						}
						
						if(data.msg.user.sex == "1"){
							data.msg.user.sex = "icon-man";
						}else{
						    data.msg.user.sex = "icon-women";
						}
						
						var html = "<li class=\"discus-col\">"
							+"<div class=\"father\">"
							+"<img class=\"fl\" src=\""  + data.msg.user.headImgUrl + "\">"
							+"<div class=\"f-r fr\">"
							+"<p class=\"discus-time\">"  + getDateDiff(data.msg.comments.commentTime) + "</p>"
							+"</div>"
							+"<div class=\"f-m\">"
							+"<p>"  + data.msg.user.nickName + "<i class=\"mui-icon iconfont " + data.msg.user.sex + "\"></i></p>"
							+"<p class=\"discus-content\">"  + data.msg.comments.content + "</p>"
							+"</div>"
							+"<input type=\"hidden\" class=\"userId\" value=\""  + data.msg.user.userId + "\" \>"
							+"<input type=\"hidden\" class=\"commentsId\" value=\""  + data.msg.comments.commentId + "\" \>"
							+"</div>";
						html+="</li>";
						$(html).hide().prependTo(".m-discus").show('slow');	
					}
				})
			} 
		})
	
	}
	
	function note(){
		var btnArray = ['取消', '确定'];
		mui.prompt('请输入留言的内容：', '内容', '有空ucoon', btnArray, function(e) {
			if (e.index == 1) {
				if(trimStr(e.value) == ""){
					mui.alert('留言不能为空!', '提示', function() {});
					return false; 
				}
				 
				$.ajax({
					url : 'applyOrders/saveNote',
					data : {
						content : e.value,
						applyId:${ou.apply_id}
					},
					async : true,
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if(data.result == "error"){
							mui.alert(data.msg, '提示', function() {});
							return;
						}
						mui.alert(data.msg, '提示', function() {});
						$("#note").html(e.value);
					
					}
				})
			} 
		})
	
	}
	
	
	function getDateDiff (dateStr) {
	    var publishTime = dateStr/1000,
	        d_seconds,
	        d_minutes,
	        d_hours,
	        d_days,
	        timeNow = parseInt(new Date().getTime()/1000),
	        d,
	
	        date = new Date(publishTime*1000),
	        Y = date.getFullYear(),
	        M = date.getMonth() + 1,
	        D = date.getDate(),
	        H = date.getHours(),
	        m = date.getMinutes(),
	        s = date.getSeconds();
	        //小于10的在前面补0
	        if (M < 10) {
	            M = '0' + M;
	        }
	        if (D < 10) {
	            D = '0' + D;
	        }
	        if (H < 10) {
	            H = '0' + H;
	        }
	        if (m < 10) {
	            m = '0' + m;
	        }
	        if (s < 10) {
	            s = '0' + s;
	        }
	
	    d = timeNow - publishTime;
	    d_days = parseInt(d/86400);
	    d_hours = parseInt(d/3600);
	    d_minutes = parseInt(d/60);
	    d_seconds = parseInt(d);
	
	    if(d_days > 0 && d_days < 3){
	        return d_days + '天前';
	    }else if(d_days <= 0 && d_hours > 0){
	        return d_hours + '小时前';
	    }else if(d_hours <= 0 && d_minutes > 0){
	        return d_minutes + '分钟前';
	    }else if (d_seconds < 60) {
	        if (d_seconds <= 0) {
	            return '刚刚发表';
	        }else {
	            return d_seconds + '秒前';
	        }
	    }else if (d_days >= 3 && d_days < 30){
	        return M + '-' + D + '&nbsp;' + H + ':' + m;
	    }else if (d_days >= 30) {
	        return Y + '-' + M + '-' + D + '&nbsp;' + H + ':' + m + ':' + s;
	    }
	}    
	
	function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}
	
	
	function report(mission_id,userId){
		var btnArray = ['取消', '确定'];
		mui.prompt('请输入举报的内容：', '内容', '${user.nickName}', btnArray, function(e) {
			if (e.index == 1) {
				if(trimStr(e.value) == ""){
					mui.alert('内容不能为空!', '提示', function() {});
					return false; 
				}
				 
				$.ajax({
					url : 'report/saveMissionReport',
					data : {
						content : e.value,
						missionId:mission_id,
						userId:userId
					},
					async : true,
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if(data.result == "fail"){
							mui.toast(data.msg);
							return;
						}
						mui.toast(data.msg);
					}
				})
			} 
		})
	
	}
</script>
<body>
	<div class="mui-content">
		<div class="basic-mes">
			<!--头像-->
			<img src="${mdetails.head_img_url}">
			<div class="ucoon-user">
				${mdetails.nick_name}
				<c:choose>
				    <c:when test="${mdetails.sex == 2}">
				       <i class="mui-icon iconfont icon-women"></i>
				    </c:when>
				    <c:otherwise>
				        <i class="mui-icon iconfont icon-man"></i>
				    </c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<div class="task-info-sec1">
			<div class="top clearfix">
				<span class="task-name fl">${mdetails.mission_title}</span>
				<button class="task-name fr" onclick="report(${mdetails.mission_id},${user.userId});">举报</button>
			</div>
			<p>
				<i class="mui-icon iconfont icon-qian"></i>${mdetails.mission_price}
			</p>
			<div class="bottom clearfix">
				<span id="remain">剩余-个名额</span><span>${mdetails.view_count}次浏览</span><span
					id="pt">00月00日</span>
			</div>
		</div>
		<div class="task-info-sec2">
			<p>
				<i class="mui-icon  mui-icon-person "></i><span>人数</span><span
					class="innertxt">${mdetails.people_count}人</span>
			</p>
			<p>
				<i class="mui-icon  mui-icon-location "></i><span>地点</span><span
					class="innertxt" id="missionplace">${mdetails.place}</span>
			</p>
			<p>
				<i class="mui-icon iconfont icon-time"></i><span>截止</span><span
					class="innertxt" id="jz">00-00 00:00</span>
			</p>
			<p>
				<i class="mui-icon iconfont icon-time"></i><span>开始</span><span
					class="innertxt" id="st">00-00 00:00</span>
			</p>
			<p>
				<i class="mui-icon iconfont icon-time"></i><span>完成</span><span
					class="innertxt" id="wc">00-00 00:00</span>
			</p>
			<p>
				<i class="mui-icon iconfont icon-time"></i><span>状态</span><span
					class="innertxt" id="zt"></span>
			</p>
		</div>
		<div class="task-info-description">
			<p>详细描述:</p>
			<p>${mdetails.mission_describe}</p>
			<div class="imgbox" id="imgbox"></div>

		</div>
		<div class="task-info-description">
			<p>我的留言:</p>
			<p id="note"></p>

		</div>
		<div class="task-info-description">
			<p>我对发布者的评价:</p>
			<!--五星评分-->
			<div class="user-score">
				<span>评分：</span>	<span class="fivestar">
										<!-- <i class="mui-icon iconfont icon-star"></i>
										<i class="mui-icon iconfont icon-star"></i>
										<i class="mui-icon iconfont icon-star"></i>
										<i class="mui-icon iconfont icon-star-half"></i>
										<i class="mui-icon iconfont icon-star-empty"></i> -->
									</span>
			</div>
			<p class="assessment">
				
			</p>

		</div>
		<br /> <br /> <br /> <br />

		<div class="fix-btn">
			<button class="fl"></button>
			<button class="fl cur"></button>
		</div>

	</div>
	<!--弹窗遮罩层-->
    <div id="bg"></div>
    <div class="pop-cot" id="pop" style="display: none">
        <div class="title">任务凭据</div>
        <form id="myform" action="applyOrders/finishOrder" method="post" enctype="multipart/form-data">
	        <textarea name="missionDoneDetail"   placeholder="输入任务完成情况"></textarea>
	        <input type="hidden" name="applyId" id="applyID">
	        <ul class="addimg-box clearfix">
	            <li id="addimg">
	                <img src="images/addimg.png"/>
	                <input type="file" id="imgUpload" name="imgUpload" multiple="multiple"/>
	            </li>
	        </ul>
	        <p class="tip">（最多可上传3张）</p>
	    </form>
        <div class="over-btn">
            <button class="fl close-btn">取消</button>
            <button class="fl donesure">提交</button>
        </div>
        
    </div>
	<script>

    /* 多图预览 */
    filecount = 0;
    
    $(document)
            .ready(
                    function(e) {

                        // 判断浏览器是否有FileReader接口
                        if (typeof FileReader == 'undefined') {
                            $(".addimg-box")
                                    .css({
                                        'background' : 'none'
                                    })
                                    .html(
                                            '亲,您的浏览器还不支持HTML5的FileReader接口,无法使用图片本地预览,请更新浏览器获得最好体验');
                            // 如果浏览器是ie
                            
                            if ($.browser.msie === true) {
                                if ($.browser.version == 7
                                        || $.browser.version == 8) {
                                    $("#imgUpload")
                                            .change(
                                                    function(event) {
                                                        $(event.target).select();
                                                        var src = document.selection
                                                                .createRange().text;
                                                        var dom = document
                                                                .getElementById('destination');
                                                        // 使用滤镜 成功率高
                                                        dom.filters
                                                                .item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
                                                        dom.innerHTML = '';
                                                        // 使用和ie6相同的方式 设置src为绝对路径的方式
                                                        // 有些图片无法显示 效果没有使用滤镜好
                                                        /*
                                                         * var img = '<img
                                                         * src="'+src+'"
                                                         * width="200px"
                                                         * height="200px" />';
                                                         * $("#destination").empty().append(img);
                                                         */
                                                    });
                                         if(filecount == 3){
                                         	$("#addimg").remove();
                                         }           
                                }
                            }
                           
                        } else {
                            // 多图上传 input file控件里指定multiple属性 e.target是dom类型
                            $("#imgUpload")
                                    .change(
                                            function(e) {
                                            
                                            	
                                            
                                                if (filecount
                                                        + event.target.files.length > 3) {
                                                    mui.alert("上传图片不能超过3张");
                                                } else {
                                                    for (var i = 0; i < e.target.files.length; i++) {
                                                        var file = e.target.files
                                                                .item(i);
                                                        // 允许文件MIME类型
                                                        // 也可以在input标签中指定accept属性
                                                        // console.log(/^image\/.*$/i.test(file.type));
                                                        if (!(/^image\/.*$/i
                                                                        .test(file.type))) {
                                                            continue; // 不是图片
                                                            // 就跳出这一次循环
                                                        }

                                                        // 实例化FileReader API
                                                        var freader = new FileReader();
                                                        freader.readAsDataURL(file);
                                                        freader.onload = function(e) {
                                                            var img = '<li><img src="'
                                                                    + e.target.result
                                                                    + '"/></li>';
                                                            $(".addimg-box")
                                                                    .prepend(img);
                                                        }
                                                    }
                                                    filecount += e.target.files.length;
                                                    if(filecount == 3){
			                                         	$("#addimg").remove();
			                                         }   
                                                }
                                            });

                        }
                    });
    /* 多图预览 */

</script>
</body>

</html>
