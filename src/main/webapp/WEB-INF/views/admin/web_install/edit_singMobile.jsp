<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<link rel="stylesheet" href="css/colorpicker.css" type="text/css" />
   		<link rel="stylesheet" media="screen" type="text/css" href="css/layout.css" />
		<style>
			.hid{display:none}
		</style>
	</head>
	<body >
		<div class="listbox">
			<div class="pagetitle">模板消息编辑
				<a href="admin/show_singserviceMessageMapping">>>返回列表</a>
			</div>
				<table class="table table-bordered table-condensed">
					<tr>
						<td colspan="2">模板消息编辑</td>
					</tr>
					<tr>
						<td class="item_title">发送对象</td>
						<td class="item_input">
								<input name="openId" id="openId" value="${openId}" class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title">选择模板</td>
						<td class="item_input">
							<select id="mobiles">
								<c:forEach items="${mobiles}" var="mobile">
									<option value="${mobile.mobileId}">${mobile.mobileTitle}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<c:forEach items="${mobiles}" var="mobile">
						<c:forEach items="${mobile.contents}" var="content" varStatus="status">
							<tr class="hid a" mobileId="${mobile.mobileId}" head="${mobile.mobileFirstParam}" foot="${mobile.mobileRemark}">
								<td class="item_title">${content}</td>
								<td class="item_input">
									<input name="keyword" type="text" id="keyword${status.index+1}" style="display:inline-block;width:30%"class="form-control input-sm">
									<span>&nbsp;选择颜色：</span><span class="span" inp="color${status.index+1}" style="border:1px solid blue;width:25px;height:20px;display:inline-block;"></span>
									<p style="display:none" class="color${status.index+1}"> </p>
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
					<tr>
							<td class="item_title">头部数据</td>
							<td class="item_input">
								<input name="mobileFirstParam" id="mobileFirstParam" value="${mobile.mobileFirstParam}" class="form-control input-sm">
							</td>
					</tr>
					
					<tr>
							<td class="item_title">地部数据</td>
							<td class="item_input">
								<input name="mobileRemark" id="mobileRemark" value="${mobile.mobileRemark}" class="form-control input-sm">
							</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<p class="btn btn-primary btn-sm" onclick="editUp()">发送客服消息</p>	
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>  <!-- 
<script type="text/javascript" src="js/jquery.js"></script> -->
<script type="text/javascript" src="js/colorpicker.js"></script>
<script type="text/javascript" src="js/eye.js"></script>
<script type="text/javascript" src="js/utils.js"></script>
<script type="text/javascript" src="js/layout.js?ver=1.0.2"></script>
<script type="text/javascript">
		//设置可以选择颜色
		colorSet();
		document.getElementsByTagName("option")[0].setAttribute("checked","checked");
		var mobileid = $("#mobiles").find('option:checked').val();
	 	express(mobileid)
	 	$("#mobiles").change(function(){
	 	var mobileid = $(this).find('option:checked').val();
	 	express(mobileid)
	 })
	//发送模板消息内容
	var keywords = [];
	var colors = [];
function editUp(){ 
	 colors = [];
	 keywords = [];
	 	$("tr").each(function(){
			if($(this).attr("class")=="a"){
				keywords.push($(this).find("input").val())
				colors.push($(this).find("p").html());
				}
		}) 
		var mobileFirstParam = $("#mobileFirstParam").val();
		var mobileRemark =  $("#mobileRemark").val();
		var mobileId = $("#mobiles").find('option:checked').val();
		var openId = $("#openId").val();
	//	alert(JSON.stringify({"keywords":keywords,"colors":colors,"mobileFirstParam":mobileFirstParam,"mobileRemark":mobileRemark,"mobileId":mobileId}))
		$.ajax({
				type:"post",
				url:"admin/send_mobile",
				data:{"keywords":keywords,"colors":colors,"mobileFirstParam":mobileFirstParam,"mobileRemark":mobileRemark,"mobileId":mobileId,"openId":openId},
				success:function(result){ 
				alert(result)
					/* if(result=="success"){
					//发送成功跳到 单发查看页面
						//window.location.href="admin/show_singserviceMessageMapping"
						alert("发送成功")
					}else{
						alert("发送失败")
					} */
				}
			}); 
			return false;
  }
	function express(mobileid){
			$(".a").each(function(){
	 		if($(this).attr("mobileId")==mobileid){
	 			$("#mobileFirstParam").val($(this).attr("head"));
	 			$("#mobileRemark").val($(this).attr("foot"));
	 			$(this).attr("class","a");
	 		}
	 		else $(this).attr("class","hid a");
	 	})
	}
	function colorSet(){
		$('.span').each(function(){
				var spans = $(this)
				$(this).ColorPicker({
				color: '#0000ff',
				onShow: function (colpkr) {
					$(colpkr).fadeIn(500);
					return false;
				},
				onHide: function (colpkr) {
					$(colpkr).fadeOut(500);
					return false;
				},
				onChange: function (hsb, hex, rgb) {
					spans.css('backgroundColor', '#' + hex);
						$("tr").each(function(){
						if($(this).attr("class")=="a"){
							$(this).find('.'+spans.attr("inp")).html(hex);
						}
					}) 
				//	$('.'+spans.attr("inp")).html(hex);
				}
				});	
			})
	}
</script>
