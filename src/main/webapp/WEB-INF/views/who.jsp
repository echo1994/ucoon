<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title></title>
<link rel="stylesheet" type="text/css" href="css/mui.picker.min.css" />
<link href="css/mui.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />


<script src="js/mui.min.js"></script>
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/mui.picker.min.js"></script>

<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=5tvfGzOQjpNsnVNXhUZ0xkxDCK6sDpRF"></script>
<script type="text/javascript"
	src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
</head>

<body>
	<div id="offCanvasWrapper"
		class="mui-off-canvas-wrap mui-draggable mui-scalable">
		<!--侧滑菜单部分-->
		<aside id="offCanvasSide"
			class="mui-off-canvas-right mui-transitioning">
			<div id="offCanvasSideScroll"
				class="mui-scroll-wrapper scroll-wrapper-bg">
				<div class="mui-scroll">
					<div class="basic-mes">
						<!--头像-->
						<img src="images/home_pic2.jpg">
						<div class="ucoon-user">
							满血复活的大魔王<i class="mui-icon iconfont icon-man"></i>
						</div>
						<!--五星评分-->
						<div class="user-score">
							<span class="fivestar"> <i
								class="mui-icon iconfont icon-star"></i> <i
								class="mui-icon iconfont icon-star"></i> <i
								class="mui-icon iconfont icon-star"></i> <i
								class="mui-icon iconfont icon-star-half"></i> <i
								class="mui-icon iconfont icon-star-empty"></i>
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
							<li><i class="mui-icon iconfont icon-wode"></i>我的消息</li>
							<li id="mysend"><i class="mui-icon iconfont icon-plane"></i>我发布的</li>
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
			<header class="mui-bar mui-bar-nav nav-bar-who" id="header">
				<!--侧滑按钮-->
				<a id="offCanvasBtn" href="#offCanvasSide"
					class="mui-icon mui-action-menu mui-icon-bars mui-pull-right "></a>
				<h1 class="mui-title">发布活动</h1>
			</header>
			<!--底部导航菜-->
			<nav class="mui-bar mui-bar-tab send-btn" id="send-btn">
				发布
				<!--<a  class="mui-tab-item ">
						<span class="mui-icon iconfont icon-me"></span>
						<span class="mui-tab-label">我有空</span>
					</a>
					<a class="mui-tab-item">
						<span class="mui-icon iconfont icon-we"></span>
						<span class="mui-tab-label">都有空</span>
					</a>
					<a class="mui-tab-item mui-active">
						<span class="mui-icon iconfont icon-who"></span>
						<span class="mui-tab-label">谁有空</span>
					</a>-->
			</nav>
			<!--主界面中间区域-->
			<div id="offCanvasContentScroll"
				class="mui-content mui-scroll-wrapper">
				<form class="mui-input-group" action="mission/add-mission"
					enctype="multipart/form-data">
					<div class="mui-input-row who-form">
						<label>标题</label> <input type="text" name="missionTitle"
							placeholder="如领快递，买盒饭等">
					</div>
					<div class="publish-des">
						<textarea type="text" name="missionDescribe"
							placeholder="选填，添加详细描述，有助于快速被接单哦！"></textarea>
						<ul class="addimg">
							<li style="position: relative;"><img id="addimgCo"
								src="images/addimg.png" /> <input type="file" id="imgUpload"
								name="imgUpload" draggable="true"
								style="position:absolute ;top:0;left:0;width: 100%;height: 100%;visibility: hidden;"
								multiple="multiple" /></li>
						</ul>
					</div>
					<div class="mui-input-row who-form">
						<label>售价</label> <input type="text" name="missionPrice"
							placeholder="">
					</div>
					<div class="mui-input-row who-form">
						<label>需要人数</label> <input type="text" name="peopleCount"
							placeholder="1">
					</div>
					<div class="mui-input-row who-form">
						<label>活动地点</label> <input type="text" name="place"
							placeholder="目前仅限厦门地区" id="suggestId">
					</div>
					<div class="mui-input-row who-form">
						<label>截止时间</label>
						<button id='result'
							data-options='{"value":"2016-08-08 10:10","beginYear":2016,"endYear":2020}'
							class="btn mui-btn mui-btn-block ui-alert">2016-08-08
							10:10</button>
					</div>
					<div class="mui-input-row who-form">
						<label>联系电话</label> <input type="tel" placeholder="13074852391">
					</div>
				</form>
			</div>
			<!-- off-canvas backdrop -->
			<div class="mui-off-canvas-backdrop"></div>
			<div id="l-map" style="display: none"></div>
		</div>
	</div>
	<div id="searchResultPanel"
		style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
</body>
<script src="js/who.js"></script>
</html>
