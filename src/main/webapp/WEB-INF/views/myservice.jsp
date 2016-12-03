<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>我服务的</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet" href="css/mui.min.css">
<link rel="stylesheet" href="css/style.css">
<link href="css/iconfont.css" rel="stylesheet" />
<script src="js/jquery-2.1.4.min.js"></script>
<style>
 #bg{
    background-color:#000;
    position:fixed;
    z-index:99;
    left:0;
    top:0;
    display:none;
    width:100%;
    height:100%;
    opacity:0.3;
    filter: alpha(opacity=30);
    -moz-opacity: 0.3;
}
.pop-cot{
    position: fixed;
    text-align: center;
    z-index: 9999;
    top:50%;
    left: 50%;
    width: 270px;
    margin-left: -135px;
    margin-top: -150px;
    height: 280px;
    background: #fff;
    border-radius: 10px;
}
.pop-cot .title{
    margin: 20px auto 10px auto;
    color: #c3d94f;
    text-align: center;

}
.pop-cot textarea{
    width: 200px;
    height: 80px;
    padding: 5px;
    margin-bottom: 0;
    font-size: 14px;
    color: #555;
}
.pop-cot ul{
    margin-left: 35px;
    margin-top: 10px;
    margin-bottom: 5px;
}
.pop-cot ul li{
    width: 50px;
    height: 50px;
    position: relative;
    float: left;
    margin-right: 10px;
}
.pop-cot ul li img{
    width: 50px;
    height: 50px;
}
.pop-cot ul li input{
    position: absolute;
    display: block;
    top: 0;
    left: 0;
    width: 50px;
    height: 50px;
    font-size: 0;
    opacity: 0;

}
.pop-cot .tip{
    font-size: 10px;
    color: #999;
    text-align: left;
    padding-left: 35px;
}
.pop-cot .over-btn{
    position: absolute;
    bottom: 15px;
    width: 200px;
    left: 50%;
    margin-left: -100px;
}
.pop-cot .over-btn button{
    width: 80px;
    margin: 0 10px;
    background: #c3d94f;
    color: #fff;
}

.mysend {
	margin-top: 0;
}

.mysend-col .m-t {
	margin-top: 0;
}

.mysend .t-m .read-times {
	color: #C3D94F;
}
</style>

</head>
<script type="text/javascript">
	($(function() {
		loaddata(0, 9, true);
		
		
		$(".cancelorder").on('tap',function(){
			var btnArray = ['否', '是'];
		    mui.confirm('是否取消订单，确认？', '有空ucoon', btnArray, function(e) 
		    {
		        if (e.index == 1) {
					$.ajax({
						url : 'applyOrders/cancelorder/' + $(".cancelorder").attr("data-m"),
						data : {},
						async : false,
						type : 'post',
						dataType : 'text',
						success : function(data) {
							alert(data);
							window.history.go(0);
						}
					})
				} else {
		            
		        }
		    })
		});
		
			
		$(".donesure").on('tap',function(){
			
			var btnArray = ['还未完成', '我已完成'];
		    mui.confirm('请确认您的任务已完成', '有空ucoon', btnArray, function(e) 
		    {
		        if (e.index == 1) {
		        	$("#applyID").val($(".done").attr("data-m"));
		        	$("#myform").submit();
				} else {
		            
		        }
		    })
		});
		$(".done").click(function () {
            $("#bg").css({

                display: "block", height: $(document).height()
            });
            $('#pop').show();
//            $('.close-bg').show();
        });
        $('.close-btn').click(function () {
            $('#pop').hide();
            $('#bg').hide();
        })
		
		$(".evaluate").on('tap',function(){
			window.location.href = "applyOrders/evaluate/"
							+ $(this).attr("data-m");
		});
		
		$(".order").on('tap',function(){
			window.location.href = "applyOrders/myservice-task-info/"
							+ $(this).attr("data-m");
		});
		
		
	}))
	function loaddata(startIndex, endIndex, clearable) {
		$
				.ajax({
					url : 'applyOrders/getOrdersLimited',
					data : {
						startIndex : 0,
						endIndex : 9
					},
					async : false,
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (clearable == true) {
							$(".mysend").empty();
						}
						for (var i = 0; i < data.length; i++) {
							var status = '';
							var handle = '';
							switch (data[i].take_state) {
								case 0:
									status = '待确认';
									handle = "<button class='fr cancelorder' data-m='"+data[i].apply_id+"'>取消任务</button><button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr contact'>联系Ta</button>";
									break;
								case 1:
								
									status = '可以开始执行任务';
									handle = "<button class='fr done' data-m='"+data[i].apply_id+"'>完成任务</button><button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr contact'>联系Ta</button>";
									/* if(data[i].selectpeople == data[i].people_count){
										status = '可以开始执行任务';
										handle = "<button class='fr done' data-m='"+data[i].apply_id+"'>完成任务</button><button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr contact'>联系Ta</button>";
									}else{
										status = '发布人已确认，等待通知任务开始';
										handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr contact'>联系Ta</button>";
									
									} */
									break;
								case 2:
									if(data[i].isEvaluate > 0){
										status = '已完成';
										handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									}else{
										status = '待评价';
										handle = "<button class='fr evaluate' data-m='"+data[i].mission_id+"'>评价</button><button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									}								
									//判断是否已评价
									
									break;
								case 3:
									status = '已取消';
									handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									
									break;
								case 4:
									status = '已发送给雇主，等待雇主审核通过';
									handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									
									break;
								case 5:
									status = '被拒绝';
									handle = "<button class='fr order' data-m='"+data[i].mission_id+"'>查看任务</button><button class='fr'>联系Ta</button>";
									
									break;
							}
							$(".mysend").append("<li class='mysend-col'>"
													+ "<div class='m-t'>"
													+ "<img class='fl' src='"+data[i].head_img_url+"'>"
													+ "<div class='t-r fr'>"
													+ "<p class='task-status'>"
													+ status
													+ "</p>"
													+ "<p class='send-time'>"
													+ getMonthDay(data[i].take_time)
													+ "</p>"
													+ "</div>"
													+ "<div class='t-m'>"
													+ "	<p class='task-title'>"
													+ data[i].mission_title
													+ "</p>"
													+ "	<p class='read-times'>"
													+ "		<i class='mui-icon iconfont icon-qian'></i>"
													+ data[i].mission_price
													+ "	</p>"
													+ "	</div>"
													+ "</div>"
													+ "<div class='m-b'>"
													+ handle
													+ "</div></li>");
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
						<p class="task-status">正在服务</p>
						<p class="send-time">08-08 13:30</p>
					</div>
					<div class="t-m">
						<p class="task-title">求代课</p>
						<p class="read-times">
							<i class="mui-icon iconfont icon-qian"></i>520
						</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">查看订单</button>
					<button class="fr">联系Ta</button>
				</div>
			</li>
			<li class="mysend-col">
				<div class="m-t">
					<img class="fl" src="images/logo.png">
					<div class="t-r fr">
						<p class="task-status">已完成</p>
						<p class="send-time">08-08 13:30</p>
					</div>
					<div class="t-m">
						<p class="task-title">带领快递</p>
						<p class="read-times">
							<i class="mui-icon iconfont icon-qian"></i>520
						</p>
					</div>
				</div>
				<div class="m-b">
					<button class="fr">查看订单</button>
					<button class="fr">联系Ta</button>
				</div>
			</li> -->
		</ul>
	</div>
	<!--弹窗遮罩层-->
    <div id="bg"></div>
    <div class="pop-cot" id="pop" style="display: none">
        <div class="title">任务凭据</div>
        <form id="myform" action="applyOrders/finishOrder" method="post" enctype="multipart/form-data">
	        <textarea name="missionDoneDetail"   placeholder="输入任务完成情况"></textarea>
	        <input type="hidden" name="applyId" id="applyID">
	        <ul class="addimg-box clearfix">
	            <li id="addimg">
	                <img src="images/addimg.png"/>
	                <input type="file" id="imgUpload" name="imgUpload" multiple="multiple"/>
	            </li>
	        </ul>
	        <p class="tip">（最多可上传3张）</p>
	    </form>
        <div class="over-btn">
            <button class="fl close-btn">取消</button>
            <button class="fl donesure">提交</button>
        </div>
        
    </div>
	<script src="js/mui.min.js"></script>
	<script>

    /* 多图预览 */
    filecount = 0;
    
    $(document)
            .ready(
                    function(e) {

                        // 判断浏览器是否有FileReader接口
                        if (typeof FileReader == 'undefined') {
                            $(".addimg-box")
                                    .css({
                                        'background' : 'none'
                                    })
                                    .html(
                                            '亲,您的浏览器还不支持HTML5的FileReader接口,无法使用图片本地预览,请更新浏览器获得最好体验');
                            // 如果浏览器是ie
                            
                            if ($.browser.msie === true) {
                                if ($.browser.version == 7
                                        || $.browser.version == 8) {
                                    $("#imgUpload")
                                            .change(
                                                    function(event) {
                                                        $(event.target).select();
                                                        var src = document.selection
                                                                .createRange().text;
                                                        var dom = document
                                                                .getElementById('destination');
                                                        // 使用滤镜 成功率高
                                                        dom.filters
                                                                .item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
                                                        dom.innerHTML = '';
                                                        // 使用和ie6相同的方式 设置src为绝对路径的方式
                                                        // 有些图片无法显示 效果没有使用滤镜好
                                                        /*
                                                         * var img = '<img
                                                         * src="'+src+'"
                                                         * width="200px"
                                                         * height="200px" />';
                                                         * $("#destination").empty().append(img);
                                                         */
                                                    });
                                         if(filecount == 3){
                                         	$("#addimg").remove();
                                         }           
                                }
                            }
                           
                        } else {
                            // 多图上传 input file控件里指定multiple属性 e.target是dom类型
                            $("#imgUpload")
                                    .change(
                                            function(e) {
                                            
                                            	
                                            
                                                if (filecount
                                                        + event.target.files.length > 3) {
                                                    mui.alert("上传图片不能超过3张");
                                                } else {
                                                    for (var i = 0; i < e.target.files.length; i++) {
                                                        var file = e.target.files
                                                                .item(i);
                                                        // 允许文件MIME类型
                                                        // 也可以在input标签中指定accept属性
                                                        // console.log(/^image\/.*$/i.test(file.type));
                                                        if (!(/^image\/.*$/i
                                                                        .test(file.type))) {
                                                            continue; // 不是图片
                                                            // 就跳出这一次循环
                                                        }

                                                        // 实例化FileReader API
                                                        var freader = new FileReader();
                                                        freader.readAsDataURL(file);
                                                        freader.onload = function(e) {
                                                            var img = '<li><img src="'
                                                                    + e.target.result
                                                                    + '"/></li>';
                                                            $(".addimg-box")
                                                                    .prepend(img);
                                                        }
                                                    }
                                                    filecount += e.target.files.length;
                                                    if(filecount == 3){
			                                         	$("#addimg").remove();
			                                         }   
                                                }
                                            });

                        }
                    });
    /* 多图预览 */

</script>
	
</body>
</html>