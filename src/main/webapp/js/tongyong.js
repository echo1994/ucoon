$(function () {
    runtime();
})
function autoheight() {
    var wheight = $(window).height() - 130;
    $("iframe").height(wheight - 35);
    $("#if_url").height(wheight);
}
function runtime() {
    autoheight();
    setTimeout("runtime()", 100);
}

function LoadingBox(msg) {
    if (!msg) msg = "加载";
    var html = "<div id='loadingDiv'>"
	+ "<div style='height: 1325px; display: none; opacity: 0;' class='overlay'></div>"
	+ "<div style='opacity: 0; margin-top: 250px;' id='AjaxLoading' class='showbox'>"
	+ "<div class='loadingWord'>"
	+ "<i></i>" + msg + "中，请稍候…"
	+ "</div>"
	+ "</div>"
	+ "<div style='height: 1200px;'></div>"
	+ "</div>";
    return html;
}
//加载loading动画框
function InitLoading(msg) {
    var loadingDiv = LoadingBox(msg);
    var h = $(document).height();
    $(".overlay").css({ "height": h });
    var div = $("body").find("#loadingDiv");
    div.remove();
    $("body").append($(loadingDiv));
}
//开始显示loading，在ajax执行之前调用
function startLoading(msg) {
    InitLoading(msg);
    $(".overlay").css({ 'display': 'block', 'opacity': '0.2' });
    $(".showbox").stop(true).animate({ 'margin-top': '300px', 'opacity': '1' }, 200);
}
//加载完成后隐藏，在ajax执行完成后（complete）调用
function endLoading() {
    $(".showbox").stop(true).animate({ 'margin-top': '250px', 'opacity': '0' }, 400);
    $(".overlay").css({ 'display': 'none', 'opacity': '0' });
}
//获取地址栏值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
//上传图片
function UploadImage(up) {
    //    console.log(up);
    //    if (up.list != undefined) {
    //        list = up.list;
    //    };
    var $ = jQuery,
        $list = up.list,
        ratio = window.devicePixelRatio || 1, 					// 优化retina, 在retina下这个值是2
        thumbnailWidth = 100 * ratio, thumbnailHeight = 100 * ratio, // 缩略图大小
		uploader; 													// Web Uploader实例		
    allimg = {}, 											// 所有文件的进度信息，key为file id
    // 初始化Web Uploader
	    uploader = WebUploader.create({
	        // 自动上传。
	        auto: true,
	        // swf文件路径
	        swf: '/content/webuploader/Uploader.swf',
	        // 文件接收服务端。
	        server: '/Manager/PicServer/Webuploader',
	        //server: up.s_url,
	        // 选择文件的按钮。可选。
	        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	        pick: up.pick,
	        // 只允许选择文件，可选。
	        accept: {
	            title: 'Images',
	            extensions: 'gif,jpg,jpeg,bmp,png',
	            mimeTypes: 'image/*'
	        },
	        fileNumLimit: up.limit
            //fileSingleSizeLimit:2048
	    });
    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        var $li = $('<li id="' + file.id + '" class="file-item thumbnail"><img><div class="info">' + file.name + '</div></li>'),
        $btns = $('<div class="file-panel"><span class="cancel" title="删除">删除</span><span class="upload" title="重新上传">重新上传</span>').appendTo($li),
        $img = $li.find('img');
        allimg[file.id] = [file.type];
        $list.append($li);
        $li.on('mouseenter', function () {
            $btns.stop().animate({ height: 30 });
        });
        $li.on('mouseleave', function () {
            $btns.stop().animate({ height: 0 });
        });
        $btns.on('click', 'span', function () {
            var index = $(this).index(), deg;
            switch (index) {
                case 0:
                    removeFile(file);
                    return;
                case 1:
                    retry(file);
                    return;
            }
            if (supportTransition) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({ '-webkit-transform': deg, '-mos-transform': deg, '-o-transform': deg, 'transform': deg });
            } else {
                $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~ ~((file.rotation / 90) % 4 + 4) % 4) + ')');
            }
        });
        // 创建缩略图
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }
            $img.attr('src', src);
        }, thumbnailWidth, thumbnailHeight);
    });
    // 负责view的销毁
    function removeFile(file) {
        var $li = $('#' + file.id);
        delete allimg[file.id];
        uploader.removeFile(file);
        $li.off().find('.file-panel').off().end().remove();
    }
    function retry(file) {
        uploader.retry(file);
    }
    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id), $percent = $li.find('.progress span');
        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
        }
        $percent.css('width', percentage * 100 + '%');
    });
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, response) {
        $('#' + file.id).addClass('upload-state-done');
        up.success(response);
    });
    // 文件上传失败，现实上传出错。
    uploader.on('uploadError', function (file) {
        var $li = $('#' + file.id), $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }
        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').remove();
    });
}

