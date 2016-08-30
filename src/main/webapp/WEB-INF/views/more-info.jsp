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
<meta charset="utf-8">
<title>查看详情</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<script src="js/jquery-2.1.4.min.js"></script>
<link rel="stylesheet" href="css/mui.min.css" />
<link rel="stylesheet" href="css/style.css" />
<link href="css/iconfont.css" rel="stylesheet" />
<style>
.mysend {
	margin-top: 0;
}

.mysend-col .m-t {
	margin-top: 0;
}
/*.mysend .t-m .read-times{
				color: #C3D94F;
			}*/
.mysend .t-r .task-status {
	color: #999;
}
</style>

</head>
<script type="text/javascript">
	var pmid = ${mid};
	$(function() {
		var isnull = loaddata(0, 0, pmid, true);
		if (isnull == 0) {
			// 显示空界面提示！！！！！！
		}
		$(".fr").bind('tap', function() {
			var key1 = $(this).attr("data-confirm");
			var key2 = $(this).attr("data-result");
			alert(key1 + "," + key2);
			$.ajax({
				url : 'apply/confirmAppliment/' + key1 + '/' + key2,
				data : {
					startIndex : 0,
					endIndex : 0
				},
				async : false,
				type : 'post',
				success : function(data) {
					if (data == "true") {
						alert("操作成功");
					}
					history.go(0);
				}
			});
		});
	})

	function loaddata(startIndex, endIndex, mid, clearable) {
		$
				.ajax({
					url : 'apply/getApByMid',
					data : {
						startIndex : startIndex,
						endIndex : endIndex,
						missionId : mid,
					},
					async : false,
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (clearable == true) {
							$(".mysend").empty();
						}
						if (data.length == 0 || data == null || data == "") {
							return 0;
						} else {
							//隐藏空界面提示！~！！！！！！！！！！！！！
						}
						for (var i = 0; i < data.length; i++) {
							$('.mysend')
									.append(
											"<li class='mysend-col'>"
													+ "<div class='m-t'>"
													+ "<img class='fl' src='"+data[i].head_img_url+"'>"
													+ "<div class='t-r fr'>"
													+ "	<p class='send-time'>"
													+ getMonthDay(data[i].take_time)
													+ "</p>"
													+ "	<p class='task-status'>"
													+ "		距离您<span>1.2km<span></span>"
													+ "	</p>"
													+ "</div>"
													+ "<div class='t-m'>"
													+ "	<p>"
													+ "		"
													+ data[i].nick_name
													+ (data[i].sex == "1" ? "<i class='mui-icon iconfont icon-man'></i>"
															: "<i class='mui-icon iconfont icon-women'></i>")
													+ "	</p>"
													+ "	<p class='read-times'>"
													+ (data[i].note == undefined ? ""
															: data[i].note)
													+ "</p>"
													+ "</div>"
													+ "</div>"
													+ "<div class='m-b'>"
													+ "	<button class='fr'>联系Ta</button>"
													+ "	<button class='fr' data-confirm='"+data[i].apply_id+"' data-result='1'>选Ta</button>"
													+ "	<button class='fr' data-confirm='"+data[i].apply_id+"' data-result='2'>拒绝</button>"
													+ "</div>" + "</li>");
						}
					}
				})
	}
	function getMonthDay(timestamp) {
		var date = new Date(timestamp);
		month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) + "-"
				: date.getMonth() + 1 + "-";
		day = date.getDate() + 1 < 10 ? "0" + date.getDate() + " " : date
				.getDate()
				+ " ";
		hour = date.getHours() < 10 ? "0" + date.getHours() + ":" : date
				.getHours()
				+ ":";
		minute = date.getMinutes() < 10 ? date.getMinutes() : date.getMinutes();
		return month + day + hour + minute;
	}
</script>
<body>
	<div class="mui-content">
		<ul class="mysend">
			<!-- <li class="mysend-col">
				<div class="m-t">
					<img class="fl" src="images/muwu.jpg">
					<div class="t-r fr">
						<p class="send-time">08-08 13:30</p>
						<p class="task-status">
							距离您<span>1.2km<span></span>
						</p>
					</div>
					<div class="t-m">
						<p>
							满血复活大魔王<i class="mui-icon iconfont icon-man"></i>
						</p>
						<p class="read-times">有钱啥都干</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">联系Ta</button>
					<button class="fr">选Ta</button>
					<button class="fr">拒绝</button>
				</div>
			</li>
			<li class="mysend-col">
				<div class="m-t">
					<img class="fl" src="images/muwu.jpg">
					<div class="t-r fr">
						<p class="send-time">08-08 13:30</p>
						<p class="task-status">
							距离您<span>1.2km<span></span>
						</p>

					</div>
					<div class="t-m">
						<p>
							超级女神经<i class="mui-icon iconfont icon-women"></i>
						</p>
						<p class="read-times">美女哦</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">联系Ta</button>
					<button class="fr">选Ta</button>
					<button class="fr">拒绝</button>
				</div>
			</li> -->
		</ul>
	</div>
	<script src="js/mui.min.js"></script>
</body>
</html>
