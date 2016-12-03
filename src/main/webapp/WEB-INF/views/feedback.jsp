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
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>问题反馈</title>
		
		<script src="js/jquery-2.1.4.min.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="css/feedback.css" />
		
		<link href="css/iconfont.css" rel="stylesheet" />
	</head>

	<style>
		.pop-cot{
			margin: 10px;
		}
	
		.pop-cot ul{
		    margin: 10px;
		    padding: 0;
		}
		.pop-cot ul li{
		    width: 50px;
		    height: 50px;
		    position: relative;
		    float: left;
		    margin: 5px;
		    list-style: none;
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
		
	</style>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<button id="submit" class="mui-btn mui-btn-blue mui-btn-link mui-pull-right">发送</button>
			<h1 class="mui-title">问题反馈</h1>
		</header>
		<div class="mui-content">
			<form id="myform" action="saveFeedback" method="post" enctype="multipart/form-data">
				<div class="mui-content-padded">
					<div class="mui-inline">问题和意见</div>
					<a class="mui-pull-right mui-inline" href="#popover">
						快捷输入
						<span class="mui-icon mui-icon-arrowdown"></span>
					</a>
					<!--快捷输入具体内容，开发者可自己替换常用语-->
					<div id="popover" class="mui-popover">
						<div class="mui-popover-arrow"></div>
						<div class="mui-scroll-wrapper">
							<div class="mui-scroll">
								<ul class="mui-table-view">
									<!--仅流应用环境下显示-->
									<li class="mui-table-view-cell stream">
										<a href="#">桌面快捷方式创建失败</a>
									</li>
									<li class="mui-table-view-cell"><a href="#">界面显示错乱</a></li>
									<li class="mui-table-view-cell"><a href="#">启动缓慢，卡出翔了</a></li>
									<li class="mui-table-view-cell"><a href="#">偶发性崩溃</a></li>
									<li class="mui-table-view-cell"><a href="#">UI无法直视，丑哭了</a></li>
								</ul>
							</div>
						</div>
	
					</div>
				</div>
				<div class="row mui-input-row">
					<textarea id='question' name="question" class="mui-input-clear question" placeholder="请详细描述你的问题和意见(不少于5个字)"></textarea>
				</div>
				<p>图片(选填,提供问题截图,最多5张)</p>
				<div class="pop-cot row mui-input-row">
					 <ul class="addimg-box clearfix">
			            <li id="addimg">
			                <img src="images/addimg.png"/>
			                <input type="file" id="imgUpload" name="imgUpload" multiple="multiple"/>
			            </li>
			        </ul>
			    </div>
				<p>QQ/邮箱</p>
				<div class="mui-input-row">
					<input id='contact' name="contact" type="text" class="mui-input-clear contact" placeholder="(选填,方便我们联系你 )" />
				</div>
			</form>
		</div>
		<script src="js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init();
			mui('.mui-scroll-wrapper').scroll();
			
			//选择快捷输入
			mui('.mui-popover').on('tap','li',function(e){
			  document.getElementById("question").value = document.getElementById("question").value + this.children[0].innerHTML;
			  mui('.mui-popover').popover('toggle')
			}) 
			
			$("#submit").click(function(){
				var question = $("#question").val();
				var contact = $("#contact").val();
				if (question == '' ||(contact != '' &&
						contact.search(/^(\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+)|([1-9]\d{4,9})$/) != 0)) {
					return mui.toast('信息填写不符合规范');
				}
				if (question.length > 200) {
					return mui.toast('信息超长,请重新填写~');
				}
				if(question.length > 5){
				
					mui.alert("感谢反馈O(∩_∩)O","问题反馈","好",function () {});
					$("#myform").submit();
				}else{
					return mui.toast('字数少于5个字');
				}
				
			});
		</script>
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
		                                         if(filecount == 5){
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
		                                                        + event.target.files.length > 5) {
		                                                    mui.alert("上传图片不能超过5张");
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
		                                                    if(filecount == 5){
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