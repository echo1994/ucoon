<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>后台管理系统</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="css/cj.css" type="text/css" />
		<link rel="stylesheet" href="css/frame.css" type="text/css"/>
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	    <![endif]-->	
	</head>
	<body>
		<!--头部-->
		<div class="topheader">
			<h1 class="logo"><img src="images/bus_logo.png" width="82"></h1><span class="slogan">后台管理系统</span>
			<div class="pd pull-right">
				欢迎<span>${userName}</span>登录后台管理系统
				<b>|</b>
				<a href="">网站首页</a>
				<b>|</b>
				<a data-toggle="modal" data-target="#edit_password">修改密码</a>
				<b>|</b>
				<a href="admin/logout">退出登录</a>
			</div>
			<br style="clear:both"  />			
		</div>
		<!--导航-->
		<div class="header">
	    	<ul class="headermenu">
	        	<li><a href="index.html?type=index">首页</a></li>
	        	<li><a href="index.html?type=user">会员管理</a></li>
	        	<li><a href="index.html?type=company">企业管理</a></li>
	        	<li><a href="index.html?type=order">订单管理</a></li>
	        	<li><a href="index.html?type=letter">站内信管理</a></li>
			</ul>
	    </div>
	    <div class="main">
	    	<!--左侧导航列表-->
	    	<div class="left_nav" id="if_url">
	    		<ul>
	    			<li class="one_list"><a href="javascript:;" class="tables">一级标题<span class="arrow"></span></a>
	    				<ul>
	    					<li><a href="地址" target="iframeleft">二级标题</a></li>
	    				</ul>
	    			</li>
	    		</ul>
	    	</div>
	    	<!--右侧框架-->
    		<div class="centercontent">
    			<!--tab libox-->
    			<div class="bq_nav" id="ifr_libox">
    				
    			</div>
    			<!--iframe box-->
    			<div id="iframeleft">
    				
    			</div>
    		</div>
	    </div>
	</body>
</html>
<!--修改密码框-->
<div class="modal fade" id="edit_password" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form name="" id="" action="" method="post" class="form-horizontal">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">修改密码</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label">原密码</label>
					    <div class="col-sm-6">
					      <input type="password" name="password"  class="form-control input-sm">
					    </div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label">新密码</label>
					    <div class="col-sm-6">
					      <input type="password" name="new_password"  class="form-control input-sm">
					    </div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label">确认密码</label>
					    <div class="col-sm-6">
					      <input type="password" name="qy_password"  class="form-control input-sm">
					    </div>
					</div>
				</div>
				<div class="modal-footer">
		          <button type="submit" class="btn btn-success btn-sm" onclick="">确定</button>
		        </div>
	        </form>
		</div>
	</div>
</div>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/bootstrap.min.js"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/index_list_ajax.js" type="text/javascript"></script>