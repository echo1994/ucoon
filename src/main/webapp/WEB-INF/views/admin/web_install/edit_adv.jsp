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
			<div class="pagetitle">广告编辑
				<a href="adv_list.html">>>返回列表</a>
			</div>
			<form  name="" id="" action="" method="get">
				<table class="table table-bordered table-condensed">
					<tr>
						<td colspan="2">广告信息</td>
					</tr>
					<tr>
						<td class="item_title">广告名称:</td>
						<td class="item_input"><input type="text" name="advname"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">广告所属:</td>
						<td class="item_input">兼职简历幻灯片广告1</td>
					</tr>
					<tr>
						<td class="item_title">是否有效:</td>
						<td class="item_input">
							<label class="radio-inline"><input type="radio" name="radio" value="1">有效</label>
							<label class="radio-inline"><input type="radio" name="radio" value="2">失效</label>
						</td>
					</tr>
					<tr>
						<td class="item_title">广告代码:</td>
						<td class="item_input">
							<textarea id="editor_id" name="content" style="width:400px;height:300px;">
							
							</textarea>
						</td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<button type="submit" class="btn btn-primary btn-sm" style="width:150px;">保存</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="editor/kindeditor.js"></script>
<script src="editor/lang/zh_CN.js"></script>
<script>
    KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
            	uploadJson : '111.json',
                allowFileManager : true
            });
    });
</script>
