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
		<link rel="stylesheet" href="css/list.css" type="text/css" />	
	</head>
	<body >
		<div class="listbox">
			<div class="pagetitle">会员列表</div>
			<div class="buttonz">
				<a href="admin/add_User" class="btn btn-success btn-sm">增加会员</a>
				<a href="javascript:;" class="btn btn-danger btn-sm" onclick="delall_admin();">批量删除</a>
			</div>
			<table class="table table-bordered table-condensed">
			 <thead>
				<tr>
					<th width="1%"><input type="checkbox" id="checkall"></th>
					<th width="5%">用户ID</th>
					<th width="5%">微信名</th>
					<th width="5%">微信号</th>
					<th width="5%">性别</th>
					<th width="5%">个性签名</th>
					<th width="5%">生日</th>
					<th width="5%">手机号码</th>
					<th width="5%">真实姓名</th>
					<th width="5%">头像</th>
					<th width="5%">个人简介</th>
					<th width="5%">评分</th>
					<th width="5%">注册时间</th>
					<th width="5%">国家</th>
					<th width="5%">省份</th>
					<th width="5%">城市</th>
					<th width="5%">状态</th>
					<th width="5%">纬度</th>
					<th width="5%">经度</th>
					<th width="5%">操作</th>
				</tr>
			 </thead>
			 <tbody id="tb1">
					
			</tbody>		
			</table>
			<div class="paging">
			  <ul class="pagination">
			    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">‹</span></a></li>
			    <li class="active"><a href="#">1</a></li>
			    <li><a href="#">2</a></li>								    
			    <li><a href="#" aria-label="Next"><span aria-hidden="true">›</span></a></li>
			    <li><span>1/1022 页</span></li>
			    <li><input type="text" class="form-control"></li>
				<li><a href="javascript:;">Go</a></li>
				<li><span>30655 条记录</span></li>
			  </ul>
			</div>
		</div>
	</body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			type:"post",
			dataType:"json",
			url:"admin/getuser_list",
			success:function(data){
				for(var i=0;i<data.length;i++){
					var s=data[i];
					$("#tb1").append(
						'<tr><td class="text-center"><input type="checkbox" name="a" value="'+s.userId+'"></td><td><center>'
						+s.openId+'</center></td><td>'+s.nickName+'</td><td>'+s.weixinId+'</td><td>'+s.sex+'</td><td>'+s.signature
						+'</td><td>'+s.birthday+'</td><td>'+s.phone+'</td><td>'+s.name+'</td><td>'+s.headImgUrl+'</td><td>'+s.intro
						+'</td><td>'+s.credit+'</td><td>'+s.registTime+'</td><td>'+s.country+'</td><td>'+s.province+'</td><td>'+s.city
						+'</td><td>'+s.state+'</td><td>'+s.latitude+'</td><td>'+s.longitude+'</td><td><a href="admin/editUser?userId='+s.userId+'">修改</td></tr>'
					);
				}
			}
		});
	});
	
</script>