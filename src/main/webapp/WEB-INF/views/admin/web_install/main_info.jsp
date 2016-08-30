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
		<link rel="stylesheet" href="webuploader/webuploader.css" type="text/css" >
	</head>
	<body>
		<div class="listbox">
			<div class="pagetitle">信息设置</div>
			<form  name="" id="" action="" method="get">
				<table class="table table-bordered table-condensed">
					<tr>
						<td class="item_title">ICP备案号:</td>
						<td class="item_input"><input type="text" name="ICP"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">客服在线时间:</td>
						<td class="item_input"><input type="text" name="worktime"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">客服电话1:</td>
						<td class="item_input"><input type="text" name="kefutel1"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">客服电话2:</td>
						<td class="item_input"><input type="text" name="kefutel2"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">客服QQ:</td>
						<td class="item_input"><input type="text" name="kefuQQ"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">测试行内input:</td>
						<td class="item_input sanji">
							<input type="text" name="kefuQQ"  class="form-control input-sm">
							<input type="text" name="kefuQQ"  class="form-control input-sm">
							<select class="form-control input-sm" id="s_province" name="s_province">
						   			<option value="0">--省份--</option>
					   		</select>
						</td>
					</tr>
					<tr>
						<td class="item_title">上传文件:</td>
						<td class="item_input">
							<div id="uploader" class="wu-example">
							    <!--用来存放文件信息-->
							    <div id="thelist" class="uploader-list"></div>
							    <div class="btns">
							        <div id="picker" class="fl-mr10">选择文件</div>
							        <button id="ctlBtn" type="button" class="btn btn-info btn-sm fl-mr10">开始上传</button>
							        <button id="delBtn" type="button" class="btn btn-default btn-sm fl-mr10">撤销文件</button>
							    </div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<div class="form-inline">
								<input type="text" class="form-control" id="" placeholder="行" style="width:50px">
								<span style="margin:0px 5px">X</span>
								<input type="text" class="form-control" id="" placeholder="列" style="width:50px">
							  	<button type="button" class="btn btn-success btn-sm" onclick="table.add_table()">添加表格</button>
								<button type="button" class="btn btn-default btn-sm" onclick="table.del_table()">删除表格</button>
							</div>
							
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
<script src="script/bootstrap.min.js"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="webuploader/webuploader.js" type="text/javascript"></script>
<script>
var table={}


// 文件上传
$(function() {
    var $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        $del=$("#delBtn"),
        state = 'pending',
        uploader;

    uploader = WebUploader.create({
        // 不压缩image
        resize: false,
        // swf文件路径
        swf: '/js/Uploader.swf',
        // 文件接收服务端。
        server: '',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        fileNumLimit:1
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
		//console.log(file);
        $list.append( '<div id="' + file.id + '" class="item"><h4 class="info">' + file.name + '</h4><p class="state">等待上传...</p></div>');
        //删除按钮
	    $del.on( 'click', function() {
	        removeFile( file );
	    });
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');
        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
              '</div>' +
            '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');

        $percent.css( 'width', percentage * 100 + '%' );
    });

    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).find('p.state').text('已上传');
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });
    //上传按钮
    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
    function removeFile(file){
    	//delete allimg[ file.id ];
    	uploader.removeFile( file );
    	$list.find('div').remove();
    	//console.log(file);
    	
    }
});
</script>