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
			<div class="pagetitle">编辑成员组
				<a href="admin/admingroup_list">>>返回列表</a>
			</div>
<!-- 			<form  name="" id="" action="" method="get"> -->
				<table class="table table-bordered table-condensed">
					<tr>
						<td colspan="2">分组权限设置</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>分组名称:</td>
						<td class="item_input">
							<input type="text" name="groupname" id="groupname" value="<%=request.getAttribute("groupname") %>" 
							readonly="true" class="form-control input-sm">
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="item_title"><span class="impt">*</span>是否有效:</td> -->
<!-- 						<td class="item_input"> -->
<!-- 							<label class="radio-inline"><input type="radio" name="effect" value="1">有效</label> -->
<!-- 							<label class="radio-inline"><input type="radio" name="effect" value="2">失效</label> -->
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<td class="item_title"><span class="impt">*</span>权限列表:</td>
						<td class="item_input">
							<table class="access_list">
								<tbody id="tb1">
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<button type="submit" class="btn btn-primary btn-sm" style="width:150px;"onclick="sub()">保存</button>
						</td>
					</tr>
				</table>
<!-- 			</form> -->
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/bootstrap.min.js"></script> 
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			type:"post",
			dataType:"json",
			url:"admin/getAllPermission",
			success:function(data){
			var str="";
				for(var i=0;i<data.length;i++){
					var s=data[i];
					if(i%3==0){
						str+=
							'<tr><td class="access_left"><label><input type="checkbox" name="power" value="'
							+s.permission_id+'">'+s.permission_name+'</label></td>'
					}
					else if(i%3==2){
						str+=
							'<label><input type="checkbox" name="power" value="'+s.permission_id+'">'+s.permission_name+'</label></td></tr>';
					}
					else{
						str+=
							'<td><label><input type="checkbox" name="power" value="'+s.permission_id+'">'+s.permission_name+'</label>';
						if(i==data.length-1){
							str+='</td></tr>';
						}
					}
				}
				$("#tb1").append(
					str
				);
			}
		});
	});
	
	function sub(){
		var groupname=$("#groupname").val();
		var a=$("input[name='power']:checked").serialize();
		var params={
			"ab":a,
			"groupname":groupname
		}
		$.ajax({
		type:"post",
		url:"admin/geteditadmingroup",
		data:params,
		success:function(data){
			if(data=="true"){
				alert("保存成功");
				window.location.href="admin/admingroup_list";
			}else{
				alert("保存失败");
			}
		}
	});
	}
</script>