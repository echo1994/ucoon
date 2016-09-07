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
<script src="js/mui.imageViewer.js"></script>
<link href="css/mui.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />
<link href="css/mui.imageviewer.css" rel="stylesheet" />
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
	var currentPage = 0;
	var onePageNums = 10;
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
				${mdetails.nick_name}<i class="mui-icon iconfont icon-man"></i>
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
				<span>评论</span><span id="discusCount">(0)</span>
				<button class="fr pinglunn-btn" onclick="comment()">评论</button>
			</p>
			<ul class="m-discus">
				<!-- <li class="discus-col">
					<div class="father">
						<img class="fl" src="images/muwu.jpg">
						<div class="f-r fr">
							<p class="discus-time">08-08 13:30</p>
						</div>
						<div class="f-m">
							<p>满血复活大魔王<i class="mui-icon iconfont icon-man"></i></p>
							<p class="discus-content">有钱啥都干</p>
						</div>
					</div>
					<div class="son clearfix">
						<div class="s-r fr">
							<p class="discus-time">08-08 13:36</p>
						</div>
						<div class="s-m fl">
							<p><span>Toad</span>@<span>满血复活大魔王</span></p>
							<p class="discus-content">睡觉干吗？</p>
						</div>
					</div>
				</li>-->
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
