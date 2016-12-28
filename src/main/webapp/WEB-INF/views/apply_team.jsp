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
		<title>执行者联盟</title>
		
		<script src="js/jquery-2.1.4.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="css/feedback.css" />
		<link href="css/mui.picker.css" rel="stylesheet" />
		<link href="css/mui.poppicker.css" rel="stylesheet" />
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
		
.fix-btn {
	position: fixed;
	bottom: 0;
	width: 100%;
	height: 50px;
	font-size: 16px;
	color: #555;
}


.fix-btn input {
	float: right;
	background-color: #C3D94F;
	width: 100%;
	height: 50px;
	font-size: 16px;
	border-radius: 0;
	border: none;
}

	</style>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">申请入口</h1>
		</header>
		<div class="mui-content">
			<form id="myform" action="team/saveApplyteam" method="post" enctype="multipart/form-data">
				
				<div class="row mui-input-row">
					<textarea id='question' name="question" class="mui-input-clear question" placeholder="请输入你的自我评价(选填)"></textarea>
				</div>
				
				<p>&nbsp;&nbsp;技能标签(最多三个)</p>
				<div class="mui-card" style="margin-bottom: 10px">
					<div class="mui-input-row mui-checkbox mui-left">
						<label>咨询</label>
						<input name="checkbox" value="情感、表白、谈" type="checkbox" class="isCheck" >
					</div>
					<div class="mui-input-row mui-checkbox mui-left">
						<label>拿、送、取（外卖、快递等等）</label>
						<input name="checkbox" value="拿、送、取" type="checkbox"  class="isCheck"  >
					</div>
					<div class="mui-input-row mui-checkbox mui-left">
						<label>开发编程</label>
						<input name="checkbox" value="开发、编程" type="checkbox"  class="isCheck" >
					</div>
					<div class="mui-input-row mui-checkbox mui-left">
						<label>代课、辅导</label>
						<input name="checkbox" value="代课、辅导" type="checkbox"  class="isCheck"  >
					</div><div class="mui-input-row mui-checkbox mui-left">
						<label>才艺</label>
						<input name="checkbox" value="唱歌、跳舞" type="checkbox"  class="isCheck" >
					</div>
					
				</div>
				<p>&nbsp;&nbsp;图片(提供身份证正面照或者学生证正面照,最多2张,必填)</p>
				<div class="pop-cot row mui-input-row">
					 <ul class="addimg-box clearfix">
			            <li id="addimg">
			                <img src="images/addimg.png"/>
			                <input type="file" id="imgUpload" name="imgUpload" multiple="multiple"/>
			            </li>
			        </ul>
			    </div>
				<p>&nbsp;&nbsp;姓名</p>
				<div class="mui-input-row">
					<input id='name' name="name" type="text" class="mui-input-clear contact" placeholder="(必填)" />
				</div>
				<p>&nbsp;&nbsp;手机号</p>
				<div class="mui-input-row">
					<input id='phone' name="phone" type="text" class="mui-input-clear contact" placeholder="(必填)" />
				</div>
				<p>&nbsp;&nbsp;学校</p>
				<select class="mui-btn" style="padding: 8px;margin: 0 0 80px 0;" id="school" name="school">
					<option value="0">点击选择</option>
					<option value="集美大学">集美大学</option>
					<option value="集美大学诚毅学院">集美大学诚毅学院</option>
					<option value="华侨大学">华侨大学</option>
					<option value="厦门大学">厦门大学</option>
					<option value="厦门工学院">厦门工学院</option>
					<option value="厦门大学嘉庚学院">厦门大学嘉庚学院</option>
					<option value="厦门理工学院">厦门理工学院</option>
					<option value="厦门华夏学院">厦门华夏学院</option>
					<option value="厦门兴才学院">厦门兴才学院</option>
					<option value="厦门城市职业学院">厦门城市职业学院</option>
					<option value="厦门海洋职业技术学院">厦门海洋职业技术学院</option>
					<option value="厦门中华职业学院">厦门中华职业学院</option>
					<option value="厦门工商旅游学院">厦门工商旅游学院</option>
					<option value="厦门外国语学院">厦门外国语学院</option>
					<option value="厦门英才学院">厦门英才学院</option>
				</select>
			</form>
			<div class="fix-btn clearfix">
				<input type='submit' class="send-btn" id="submit" value="提交" />
			</div>
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
			var i = 0;
			/* 多图预览 */
		    var filecount = 0;
			$(".mui-checkbox").click(function(){
				i = 0;
				$(".isCheck").each(function(){
				
					if($(this).is(':checked')){
						i++;
					}
				
				})
				
				if(i > 3){
					$(this).find(".isCheck").prop("checked",false);
					mui.toast('技能标签最多三个');
				}
				
			});
			
			$("#submit").click(function(){
				var name = $("#name").val();
				var phone = $("#phone").val();
				var school = $("#school").val();
				if(i == 0){
					return mui.toast('请选择技能标签');
				}
				
				if(filecount == 0){
					return mui.toast('请上传身份证或学生证');
				}
				/* alert(i);
				if(i>3){
					return mui.toast('技能标签最多三个');
				} */
				
				var re1 = new RegExp("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$"); 
				if (name == '' || !re1.test(name)) {
					return mui.toast('姓名填写不符合规范');
				}
				var re2 = /^1[34578]\d{9}$/; //验证规则
				
				if (phone == '' || !re2.test(phone)) {
					return mui.toast('手机号填写不符合规范');
				}
				if(school == 0){
					return mui.toast('请选择学校');
				}
				
				mui.alert("感谢申请加入联盟O(∩_∩)O","申请入口","好",function () {});
				$("#myform").submit();
				
				
			});
		</script>
		<script>
		    
		    
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
		                                         if(filecount == 2){
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
		                                                        + event.target.files.length > 2) {
		                                                    mui.alert("上传图片不能超过2张");
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
		                                                    if(filecount == 2){
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