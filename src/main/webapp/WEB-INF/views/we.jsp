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
		
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<link rel="stylesheet" href="css/mui.min.css">
		<link rel="stylesheet" type="text/css" href="css/mui.picker.min.css" />
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/iconfont.css" rel="stylesheet" />
		
		<script src="js/mui.min.js"></script>
		<script src="js/jquery-2.1.4.min.js"></script>
		<script src="js/mui.picker.min.js"></script>
		
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" charset="utf-8">
			
			
			var currentPage = 0;
			var onePageNums = 10;
			var latitude = "";
			var longitude = "";
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
			    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
				/* wx.checkJsApi({
				    jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
				    success: function(res) {
				    
				    	alert(res.checkResult + ";" + res.errMsg);
				        // 以键值对的形式返回，可用的api值true，不可用为false
				        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
				    }
				}); */
				wx.getLocation({
				    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				    success: function (res) {
				        latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				        longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				        var speed = res.speed; // 速度，以米/每秒计
				        var accuracy = res.accuracy; // 位置精度
				       	setTimeout(loaddata(currentPage * onePageNums, (currentPage + 1) * onePageNums - 1, '', true,'all'),1000);
				    }
				});
			
				
				$(".search").bind("keyup",
						function(e) {
							initIndex();
							loaddata(currentPage * onePageNums,
											(currentPage + 1) * onePageNums - 1,
											this.value, true,'all');
							if (e.keyCode == 13) {
								initIndex();
								loaddata(currentPage * onePageNums,
										(currentPage + 1) * onePageNums - 1,
										this.value, true,'all');
							}
						})
				//查看详情
				$(".clearfix").bind(
					"tap",
					function() {
						window.location.href = "mission/task-info/"
								+ $(this).attr("data-m");
				})
				
				
				$('.we-select-col').on('tap', function() {
					var li = this;
					var classList = li.classList;
					if (!classList.contains('title-active')) {
						var active = li.parentNode.querySelector('.title-active');
						active.classList.remove('title-active');
						classList.add('title-active');
						
					}
					initIndex();
					//加载数据
					if(classList.contains('all')){
						loaddata(currentPage * onePageNums,
										(currentPage + 1) * onePageNums - 1,
										this.value, true,'all');
					} else if(classList.contains('time')){
						loaddata(currentPage * onePageNums,
										(currentPage + 1) * onePageNums - 1,
										this.value, true,'time');
					} else{
						loaddata(currentPage * onePageNums,
										(currentPage + 1) * onePageNums - 1,
										this.value, true,'nearby');
					}
				});
								
			
			});
			
			//使用微信内置地图查看位置接口
			/* wx.openLocation({
			    latitude: 0, // 纬度，浮点数，范围为90 ~ -90
			    longitude: 0, // 经度，浮点数，范围为180 ~ -180。
			    name: '', // 位置名
			    address: '', // 地址详情说明
			    scale: 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
			    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
			}); */
						
		
			function initIndex() {
				currentPage = 0;
				onePageNums = 10;
			}
			function loaddata(startIndex, endIndex, keyWord, clearable,type) {
				$
						.ajax({
							url : 'activity/getActivityLimited',
							data : {
								startIndex : startIndex,
								endIndex : endIndex,
								keyWord : keyWord,
								latitude : latitude,
								longitude : longitude,
								type : type
							},
							async : false,
							type : 'post',
							dataType : 'json',
							success : function(data) {
								if (clearable == true) {
		
									$(".task").empty();
								}
								for (var i = 0; i < data.length; i++) {
									$(".task")
											.append("<li class=\"we-task-col clearfix\">"
										 + "<a href=\"#\">"
										 + "<div class=\"mui-pull-left we-task-col-left\">"
										 + "<img class=\"mui-pull-left\" src="+data[i].head_img_url+">"
										 + "<p class=\"ucoon-user\">"+data[i].nick_name+"</p>"
										 + "</div>"
										 + "<div class=\"task-price mui-pull-right\">"
										 + "<button><i class=\"mui-icon mui-icon-plusempty\"></i>加入</button>"
										 + "</div>"
											+ "<div class=\"we-task-detail\">"
												+ "<p class=\"task-title\">"+data[i].activity_name+"</p>"
												+ "<p class=\"task-description\">"+data[i].activity_desc+"</p>"
												+ "<div class=\"time-add clearfix\">"
													+ "<div class=\"mui-pull-left time-add-content\">"
														+ "<i class=\"mui-icon iconfont icon-time\"></i><span class=\"add-task-time\">" + getMonthDay(data[i].activity_time) + "</span>"
													+ "</div>"
													+ "<div class=\"mui-pull-left\">"
														+ "<i class=\"mui-icon mui-icon-location\"></i><span class=\"distance\">"+data[i].distance+"</span>"
													+ "</div>"
												+ "</div>"
											+ "</div>"
										+ "</a>"
									+ "</li>");
		
								}
		
							}
						})
			}
			function getMonthDay(timestamp) {
				var publishTime = timestamp/1000,
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
			
			   if (d_days < 365){
			        return M + '月' + D + '日&nbsp;' + H + ':' + m;
			    }else {
			        return Y + '年' + M + '月' + D + '日&nbsp;' + H + ':' + m;
			    }
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
		</script>
		
		<style>
			.send-btn-we{
				position: absolute;
				width: 50px;
				height: 50px;
				background-color: #C3D94F;
				border-radius: 50%;
				color: #fff;
				opacity: 0.8;
				bottom: 100px;
				right: 40px;
				text-align: center;
				
				z-index: 8;
				overflow: hidden;
			}
			.send-btn-we .iconfont{
				/*display: block;*/
				margin-top: 13px;
				font-size: 24px;
			}
		</style>
	</head>
	<body>
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable mui-scalable">
			<!--侧滑菜单部分-->
			<aside id="offCanvasSide" class="mui-off-canvas-right mui-transitioning">
				<div id="offCanvasSideScroll" class="mui-scroll-wrapper scroll-wrapper-bg">
					<div class="mui-scroll">
						<div class="basic-mes">
							<!--头像-->
							<img src="${user.headImgUrl}">
							<div class="ucoon-user">${user.nickName }
								<c:choose>
									 <c:when test="${user.sex == 2}">
									 <i class="mui-icon iconfont icon-woman"></i>
									 </c:when>
									 <c:otherwise>
										<i class="mui-icon iconfont icon-man"></i>
									 </c:otherwise>
								 </c:choose>
								
							</div>
							<!--五星评分-->
							<div class="user-score">
								<span class="fivestar">
									<i class="mui-icon iconfont icon-star"></i>
									<i class="mui-icon iconfont icon-star"></i>
									<i class="mui-icon iconfont icon-star"></i>
									<i class="mui-icon iconfont icon-star-half"></i>
									<i class="mui-icon iconfont icon-star-empty"></i>
								</span>	
							</div>
							<!--个性签名-->
							<c:choose>
							 <c:when test="${!empty user.signature}">
							 <p class="user-talk">${user.signature }</p>
							 </c:when>
							 <c:otherwise>
							 <p class="user-talk">用一句话介绍自己吧:) 这里加个修改的图片~~~</p>
							 </c:otherwise>
							 </c:choose>
							<!--财富情况-->
							<div class="treasure" id="wealth">
							    <span class=""><i class="mui-icon iconfont icon-qian"></i>${balance }</span>
								<span class=""><i class="mui-icon iconfont icon-love"></i>${credits }</span>
							</div>
							<!--侧滑菜单列表-->
							<ul class="aside-menu">
								<li id="wode"><i class="mui-icon iconfont icon-wode"></i>我的信息</li>
								<li id="mysend"><i class="mui-icon iconfont icon-plane"></i>我发布的</li>
								<li id="myservice"><i class="mui-icon iconfont icon-service"></i>我服务的</li>
								<li id="chat-list"><i class="mui-icon iconfont icon-service"></i>消息中心</li>
								<li id="help"><i class="mui-icon mui-icon-help"></i>帮助联系</li>
								<li id="info"><i class="mui-icon mui-icon-info"></i>关于我们</li>
							</ul>
							<script type="text/javascript">
								$('#wealth').bind('tap',function(){
									window.location.href="wealth/";
								})
								
								$('#wode').bind('tap',function(){
									window.location.href="";
								})
								$('#mysend').bind('tap',function(){
									window.location.href="mysend";
								})
								$('#myservice').bind('tap',function(){
									window.location.href="myservice";
								})
								
								$('#chat-list').bind('tap',function(){
									window.location.href="chat/chat-list";
								})
								
								$('#help').bind('tap',function(){
									window.location.href="feedback";
								})
								
								$('#wode').bind('tap',function(){
									window.location.href="";
								})
								
							</script>	
						</div>	
					</div>
				</div>	
			</aside>
			<!--主界面部分-->
			<div class="mui-inner-wrap mui-transitioning">
				<!--顶部导航栏-->
				<header class="mui-bar mui-bar-nav mui-bar-we" id="header">
					<!--侧滑按钮-->
					<a id="offCanvasBtn" href="#offCanvasSide" class="mui-icon mui-action-menu mui-icon-bars mui-pull-right "></a>
					<!--顶部搜索框-->
					<form action="#">
						<div class="mui-input-row mui-search ">
							<input type="search" class="mui-input-clear search" placeholder="搜索内容、地点">
						</div>
	
					</form>
					<!--筛选导航-->
					<div class="we-select">
						<div class="we-select-col mui-pull-left title-active all"><span>全部</span></div>
						<div class="we-select-col mui-pull-left time"><span>时间</span></div>
						<div class="we-select-col mui-pull-left nearby"><span>附近</span></div>
					</div>
				</header>
				<!--底部导航菜-->
				<nav class="mui-bar mui-bar-tab" id="nav-tap-bar">
					<a class="mui-tab-item " id="ucoon-me" href="">
						<span class="tab-icon tab-me"></span>
						<span class="tab-name mui-tab-label">我有空</span>
					</a>
					<a class="mui-tab-item mui-active" id="ucoon-we" href="we">
						<span class="tab-icon tab-we-cur"></span>
						<span class="tab-name mui-tab-label">都有空</span>
					</a>
					<a class="mui-tab-item" id="ucoon-who" href="who-new">
						<span class="tab-icon tab-who"></span>
						<span class="tab-name mui-tab-label">谁有空</span>
					</a>
				</nav>
				<div class="send-btn-we" id="create"><i class="mui-icon iconfont icon-plane"></i> </div>
				<script type="text/javascript">
						$('#create').bind('tap',function(){
							window.location.href="activity/create";
						})
				</script>
				<!--主界面中间区域-->
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
						<div class="mui-scroll ">
							
						<!--主内容切换（全部&附近）-->
						<div class="we-task-select" id="we-task-select">
							<!--活动信息-->
							<ul class="task">
								<!-- <li class="we-task-col clearfix">
									<a href="#">
										<div class="mui-pull-left we-task-col-left">
											<img class="mui-pull-left" src="images/home_pic2.jpg">
											<p class="ucoon-user">Toad</p>
										</div>
										
										<div class="task-price mui-pull-right">
											<button><i class="mui-icon mui-icon-plusempty"></i>加入</button>
										</div>
										<div class="we-task-detail">
											<p class="task-title">一起打篮球</p>
											<p class="task-description">光前旁篮球场，院队成员，</p>
											<div class="time-add clearfix">
												<div class="mui-pull-left time-add-content">
													<i class="mui-icon iconfont icon-time"></i><span class="add-task-time">07月30日18:30</span>
												</div>
												<div class="mui-pull-left">
													<i class="mui-icon mui-icon-location"></i><span class="distance">1.2公里</span>
												</div>
											</div>
										</div>
									</a>
								</li> -->
								
								
								
								
							</ul>
						</div>
					</div>
				</div>
				
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
		<script>
		
			mui.init({
				pullRefresh: {
					container: '#we-task-select',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					}
				}
			});
			var count = 0;
			/**
			 * 下拉刷新具体业务实现
			 */
			function pulldownRefresh() {
				setTimeout(function() {

//					var table = document.body.querySelector('.mui-table-view');

//					var cells = document.body.querySelectorAll('.mui-table-view-cell');

//					for (var i = cells.length, len = i + 3; i < len; i++) {

//						var li = document.createElement('li');

//						li.className = 'mui-table-view-cell';

//						li.innerHTML = '<a class="mui-navigate-right">Item ' + (i + 1) + '</a>';

//						//下拉刷新，新纪录插到最前面；

//						table.insertBefore(li, table.firstChild);

//					}

					mui('#we-task-select').pullRefresh().endPulldownToRefresh(); //refresh completed

				}, 1500);
			}
			
			/**
			 * 上拉加载具体业务实现
			 */
			function pullupRefresh() {
				setTimeout(function() {
					mui('#we-task-select').pullRefresh().endPullupToRefresh((++count > 10)); //参数为true代表没有更多数据了。
//					var table = document.body.querySelector('.mui-table-view');

//					var cells = document.body.querySelectorAll('.mui-table-view-cell');

//					for (var i = cells.length, len = i + 20; i < len; i++) {

//						var li = document.createElement('li');

//						li.className = 'mui-table-view-cell';

//						li.innerHTML = '<a class="mui-navigate-right">Item ' + (i + 1) + '</a>';

//						table.appendChild(li);

//					}
				}, 1500);
			}
			
			// 主界面和侧滑菜单界面均支持区域滚动；
			mui('#we-task-select').scroll();
			mui('#we-task-select').scroll();
			// 实现ios平台原生侧滑关闭页面；
			if (mui.os.plus && mui.os.ios) {
				mui.plusReady(function() { // 5+
					// iOS暂时无法屏蔽popGesture时传递touch事件，故该demo直接屏蔽popGesture功能
					plus.webview.currentWebview().setStyle({
						'popGesture' : 'none'
					});
				});
			}
			
			// 页面跳转
			mui('#nav-tap-bar').on('tap', 'a', function() {
				var id = this.getAttribute('href');
				var href = this.href;
				mui.openWindow({
					url : href,
					id : id
				});
			});
			
		</script>

	</body>
	<!-- <script src="js/we.js"></script> -->
</html>