<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>"> 
    <meta charset="utf-8">
    <title>任务详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="css/mui.min.css">
    <link href="css/style.css" rel="stylesheet" />
    <link href="css/iconfont.css" rel="stylesheet" />
    <style>
        html,
        body{
            background: #fff;
        }
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

    </style>
</head>

<body>
<div class="mui-content">

    <!--弹窗触发，可删除-->
    <button id="btn">点我</button>

    <!--弹窗遮罩层-->
    <div id="bg"></div>
    <div class="pop-cot" id="pop" style="display: none">
        <div class="title">任务凭据</div>
        <textarea name="" id=""  placeholder="输入任务完成情况"></textarea>
        <ul class="addimg-box clearfix">
            <li>
                <img src="images/addimg.png"/>
                <input type="file"  name="imgUpload" />
            </li>
        </ul>
        <p class="tip">（可上传3张）</p>
        <div class="over-btn">
            <button class="fl close-btn">取消</button>
            <button class="fl">提交</button>
        </div>
    </div>

</div>

</body>

<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script src="js/mui.min.js"></script>
<script>

    $(function () {
        $("#btn").click(function () {
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

    });

    /* 多图预览 */
    filecount = 0;
    $(document)
            .ready(
                    function(e) {

                        // 判断浏览器是否有FileReader接口
                        if (typeof FileReader == 'undefined') {
                            $("#destination")
                                    .css({
                                        'background' : 'none'
                                    })
                                    .html(
                                            '亲,您的浏览器还不支持HTML5的FileReader接口,无法使用图片本地预览,请更新浏览器获得最好体验');
                            // 如果浏览器是ie
                            if ($.browser.msie === true) {
                                // ie6直接用file input的value值本地预览
                                if ($.browser.version == 6) {
                                    $("#imgUpload")
                                            .change(
                                                    function(event) {
                                                        // ie6下怎么做图片格式判断?
                                                        var src = event.target.value;
                                                        // var src =
                                                        // document.selection.createRange().text;
                                                        // //选中后 selection对象就产生了
                                                        // 这个对象只适合ie
                                                        var img = '<img src="'
                                                                + src
                                                                + '" width="200px" height="200px" />';
                                                        $("#destination").empty()
                                                                .append(img);
                                                    });
                                }
                                // ie7,8使用滤镜本地预览
                                else if ($.browser.version == 7
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
                                }
                            }
                            // 如果是不支持FileReader接口的低版本firefox 可以用getAsDataURL接口
                            else if ($.browser.mozilla === true) {
                                $("#imgUpload")
                                        .change(
                                                function(event) {
                                                    // firefox2.0没有event.target.files这个属性
                                                    // 就像ie6那样使用value值
                                                    // 但是firefox2.0不支持绝对路径嵌入图片
                                                    // 放弃firefox2.0
                                                    // firefox3.0开始具备event.target.files这个属性
                                                    // 并且开始支持getAsDataURL()这个接口
                                                    // 一直到firefox7.0结束
                                                    // 不过以后都可以用HTML5的FileReader接口了
                                                    if (event.target.files) {
                                                        // console.log(event.target.files);

                                                        for (var i = 0; i < event.target.files.length; i++) {
                                                            var img = '<img src="'
                                                                    + event.target.files
                                                                            .item(i)
                                                                            .getAsDataURL()
                                                                    + '" width="200px" height="200px"/>';
                                                            $("#destination")
                                                                    .empty()
                                                                    .append(img);
                                                        }

                                                    } else {
                                                        // console.log(event.target.value);
                                                        // $("#imgPreview").attr({'src':event.target.value});
                                                    }
                                                });
                            }
                        } else {
                            // 单图上传 version 2
                            $("#imgUpload").change(function(e) {
                                var file = e.target.files[0];
                                var reader = new FileReader();
                                reader.onload = function(e) {
                                    // displayImage($('bd'),e.target.result);
                                    // alert('load');
                                    $("#imgPreview").attr({
                                        'src' : e.target.result
                                    });
                                }
                                reader.readAsDataURL(file);
                            });
                            // 多图上传 input file控件里指定multiple属性 e.target是dom类型
                            $("#imgUpload")
                                    .change(
                                            function(e) {
                                                if (filecount
                                                        + event.target.files.length > 5) {
                                                    alert("上传图片不能超过5张");
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
                                                            var img = '<img src="'
                                                                    + e.target.result
                                                                    + '" width="200px" height="200px"/>';
                                                            var img = '<li><img src="'
                                                                    + e.target.result
                                                                    + '"/></li>';
                                                            $(".addimg")
                                                                    .append(img);
                                                        }
                                                    }
                                                    filecount += e.target.files.length;
                                                }
                                            });

                        }
                    });
    /* 多图预览 */

</script>

</html>
