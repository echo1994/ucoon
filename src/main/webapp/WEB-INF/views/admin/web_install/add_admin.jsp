<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
			<div class="pagetitle">新增成员
				<a href="admin/admin_list">>>返回列表</a>
			</div>
<!-- 			<form  name="" id="" action="admin/getadd_admin" method="post" onsubmit="return sub()"> -->
				<table class="table table-bordered table-condensed">
					<tr>
						<td colspan="2">成员基本信息设置</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>账号:</td>
						<td class="item_input">
							<input type="text" id="username" name="username"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>密码:</td>
						<td class="item_input">
							<input type="password" id="password" name="password"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>所属分组:</td>
						<td class="item_input">
							<select class="form-control" id="groupName" name="groupName">
								<option>请选择</option>
						   	</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<button type="submit" class="btn btn-primary btn-sm" style="width:150px;" 
							onclick="sub()">保存</button>
						</td>
					</tr>
				</table>
<!-- 			</form> -->
		</div>
	</body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/bootstrap.min.js" type="text/javascript"></script> 
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
		type:"get",
		dataType:"json",
		url:"admin/getadmingroup_list",
		success:function(data){
			 for(var i=0;i<data.length;i++){
				var s=data[i];
				$("#groupName").append(
					'<option>'+s.groupName+'</option>'
				);
			} 
		}
		});
	});
	function sub(){
		var username=$("#username").val();
		var password=$("#password").val();
		var groupName=$("#groupName")[0].value;
		if(groupName=="请选择"){
			alert("请选择组别");
			return;
		}
		var params={
			"username":username,
			"password":password,
			"groupName":groupName
		}
		var param = JSON.stringify(params);
		$.ajax({
		type:"post",
		url:"admin/getadd_admin",
		data:params,
		success:function(data){
			if(data=="has"){
				alert("该账号已存在");
			}
			else if(data=="true"){
				alert("添加成功");
				$("#username").val("");
				window.location.reload();
			}else{
				alert("添加失败");
			}
		}
		});
}
</script>