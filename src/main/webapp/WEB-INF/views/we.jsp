<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<script src="js/mui.min.js"></script>
		<link href="css/mui.min.css" rel="stylesheet" />
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/iconfont.css" rel="stylesheet" />
	</head>
	<body>
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable mui-scalable">
			<!--侧滑菜单部分-->
			<aside id="offCanvasSide" class="mui-off-canvas-right mui-transitioning">
				<div id="offCanvasSideScroll" class="mui-scroll-wrapper scroll-wrapper-bg">
					<div class="mui-scroll">
						<div class="basic-mes">
							<!--头像-->
							<img src="images/home_pic2.jpg">
							<div class="ucoon-user">满血复活的大魔王<i class="mui-icon iconfont icon-man"></i></div>
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
							<p class="user-talk">陪吃配喝陪睡觉，有钱样样都行</p>
							<!--财富情况-->
							<div class="treasure">
								<span class=""><i class="mui-icon iconfont icon-qian"></i>58.9</span>
								<span class=""><i class="mui-icon iconfont icon-love"></i>66</span>
							</div>
							<!--侧滑菜单列表-->
							<ul class="aside-menu">
								<li><i class="mui-icon iconfont icon-wode"></i>我发布的</li>
								<li><i class="mui-icon iconfont icon-plane"></i>我发布的</li>
								<li><i class="mui-icon iconfont icon-service"></i>我服务的</li>
								<li><i class="mui-icon mui-icon-help"></i>帮助联系</li>
								<li><i class="mui-icon mui-icon-info"></i>关于我们</li>
							</ul>		
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
							<input type="search" class="mui-input-clear" placeholder="搜索内容、地点">
						</div>
					</form>
					<!--筛选导航-->
							<div class="we-select">
								<div class="we-select-col mui-pull-left"><span class="title-active">全部</span></div>
								<div class="we-select-col mui-pull-left"><span>时间</span></div>
								<div class="we-select-col mui-pull-left"><span>附近</span></div>
							</div>
				</header>
				<!--底部导航菜-->
				<nav class="mui-bar mui-bar-tab" id="nav-tap-bar">
					<a class="mui-tab-item " id="ucoon-me" href="index">
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
				<button class="mui-btn mui-fab mui-btn-primary mui-btn-outlined" style="position: absolute;bottom: 80px;left:70%; z-index: 10;">发布</button>
				<!--主界面中间区域-->
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
						<div class="mui-scroll ">
							
						<!--主内容切换（全部&附近）-->
						<div class="we-task-select">
							<!--任务信息-->
							<ul class="task">
								<li class="we-task-col clearfix">
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
								</li>
								<li class="we-task-col clearfix">
									<a href="#">
										<div class="mui-pull-left we-task-col-left">
											<img class="mui-pull-left" src="images/home_pic3.jpg">
											<p class="ucoon-user">Toad</p>
										</div>
										
										<div class="task-price mui-pull-right">
											<button><i class="mui-icon mui-icon-plusempty"></i>加入</button>
										</div>
										<div class="we-task-detail">
											<p class="task-title">求跑步</p>
											<p class="task-description">万人操场，求男神陪锻炼</p>
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
								</li>
								<li class="we-task-col clearfix">
									<a href="#">
										<div class="mui-pull-left we-task-col-left">
											<img class="mui-pull-left" src="images/home_pic4.jpg">
											<p class="ucoon-user">Toad</p>
										</div>
										
										<div class="task-price mui-pull-right">
											<button><i class="mui-icon mui-icon-plusempty"></i>加入</button>
										</div>
										<div class="we-task-detail">
											<p class="task-title">图书馆复习</p>
											<p class="task-description">本人美女，求男神陪读</p>
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
								</li>
								<li class="we-task-col clearfix">
									<a href="#">
										<div class="mui-pull-left we-task-col-left">
											<img class="mui-pull-left" src="images/fido-face.jpg">
											<p class="ucoon-user">Toad</p>
										</div>
										
										<div class="task-price mui-pull-right">
											<button><i class="mui-icon mui-icon-plusempty"></i>加入</button>
										</div>
										<div class="we-task-detail">
											<p class="task-title">聊创业</p>
											<p class="task-description">SAMPLE诚毅店，一起分享你的项目</p>
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
								</li>
								<li class="we-task-col clearfix">
									<a href="#">
										<div class="mui-pull-left we-task-col-left">
											<img class="mui-pull-left" src="images/bg_logo.png">
											<p class="ucoon-user">Toad</p>
										</div>
										
										<div class="task-price mui-pull-right">
											<button><i class="mui-icon mui-icon-plusempty"></i>加入</button>
										</div>
										<div class="we-task-detail">
											<p class="task-title">羽毛球</p>
											<p class="task-description">水平中等，求轻虐</p>
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
								</li>
								<li class="we-task-col clearfix">
									<a href="#">
										<div class="mui-pull-left we-task-col-left">
											<img class="mui-pull-left" src="images/home_pic2.jpg">
											<p class="ucoon-user">Toad</p>
										</div>
										
										<div class="task-price mui-pull-right">
											<button><i class="mui-icon mui-icon-plusempty"></i>加入</button>
										</div>
										<div class="we-task-detail">
											<p class="task-title">聊心</p>
											<p class="task-description">刚失恋，求聊天散心</p>
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
								</li>
								
								
								
							</ul>
						</div>
					</div>
				</div>
				
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
	

		<script>
			mui.init();
			//主界面和侧滑菜单界面均支持区域滚动；
			mui('#offCanvasSideScroll').scroll();
			mui('#offCanvasContentScroll').scroll();
			//实现ios平台原生侧滑关闭页面；
			if (mui.os.plus && mui.os.ios) {
				mui.plusReady(function() { //5+ iOS暂时无法屏蔽popGesture时传递touch事件，故该demo直接屏蔽popGesture功能
					plus.webview.currentWebview().setStyle({
						'popGesture': 'none'
					});
				});
			}
			//图片轮播定时轮播 
			var slider = mui("#slider");
			slider.slider({
				interval: 5000
			});
			mui('#offCanvasSideScroll').scroll(function(){
				console.log('1');
			});
			mui('#offCanvasContentScroll').scroll(function(){
				console.log('1');
			});
			
			
           //页面跳转
         mui('#nav-tap-bar').on('tap','a',function() {
          	var id = this.getAttribute('href');
				var href = this.href;
          mui.openWindow({
			url: href,
			id: id
			});
			});
			
		</script>

	</body>

</html>