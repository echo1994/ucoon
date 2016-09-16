$(document).ready(function(e) {
	
});

mui.init();
// 主界面和侧滑菜单界面均支持区域滚动；
mui('#offCanvasSideScroll').scroll();
mui('#offCanvasContentScroll').scroll();
// 实现ios平台原生侧滑关闭页面；
if (mui.os.plus && mui.os.ios) {
	mui.plusReady(function() { // 5+
		// iOS暂时无法屏蔽popGesture时传递touch事件，故该demo直接屏蔽popGesture功能
		plus.webview.currentWebview().setStyle({
			'popGesture' : 'none'
		});
	});
}

// 页面跳转
mui('#nav-tap-bar').on('tap', 'a', function() {
	var id = this.getAttribute('href');
	var href = this.href;
	mui.openWindow({
		url : href,
		id : id
	});
});


