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
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>${user.nickName }</title>
		<link href="css/mui.min.css" rel="stylesheet" />
		<link href="css/mui.imageviewer.css" rel="stylesheet" />
		<link href="css/chat.css" rel="stylesheet" />
	</head>

	<body contextmenu="return false;">
		<pre id="h"></pre>
		<div class="mui-content">
			<div id='msg-list'>
			</div>
		</div>
		<footer>
			<div class="footer-left">
				<i id='msg-image' class="mui-icon mui-icon-camera" style="font-size: 28px;"></i>
			</div>
			<div class="footer-center">
				<textarea id='msg-text' type="text" class='input-text'></textarea>
				<button id='msg-sound' type="button" class='input-sound' style="display: none;">按住说话</button>
			</div>
			<label for="" class="footer-right">
				<i id='msg-type' class="mui-icon mui-icon-mic"></i> 
			</label>
		</footer>
		<div id='sound-alert' class="rprogress">
			<div class="rschedule"></div>
			<div class="r-sigh">!</div>
			<div id="audio_tips" class="rsalert">手指上滑，取消发送</div>
		</div>
		<script src="js/mui.min.js"></script>
		<script src="js/mui.imageViewer.js"></script>
		<script src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.min.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src="js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript" charset="utf-8">
			//存放聊天消息
			var fromuserid = ${fromuserid};
			var touserid = ${touserid};		
			var currentPage = 0;
			var onePageNums = 10;
			var URL = window.location.href.split('#')[0]; //获取当前页面的url
			URL = encodeURIComponent(URL);
			
			var appid,nonceStr,signature,timestamp;
			//ajax同步更新全局变量，异步无法更新
			$.ajax({
			    url: "/wx/sign?url="+URL,
			    success: function(result){
			    	appid = result.appId;
			    	timestamp=result.timestamp;
			    	nonceStr=result.nonceStr;
			    	signature=result.signature;
			    },
			  	dataType: "json",
			  	async:false
			});
			
			wx.config({
			    debug: false,
			    appId: appid,
			    timestamp: timestamp,
			    nonceStr: nonceStr,
			    signature: signature,
			    jsApiList: [
			      'checkJsApi',
			      'translateVoice',
			      'startRecord',
			      'stopRecord',
			      'onVoiceRecordEnd',
			      'playVoice',
			      'onVoicePlayEnd',
			      'pauseVoice',
			      'stopVoice',
			      'uploadVoice',
			      'downloadVoice',
			      'chooseImage',
			      'previewImage',
			      'uploadImage',
			      'downloadImage',
			      
			    ]
			});
			
		</script>
		<script type="text/javascript" charset="utf-8">
			(function($, doc) {
				wx.ready(function () {
					//页面加载时调用
					/*  wx.checkJsApi({
					    jsApiList: [
					      'chooseImage',
					      'previewImage',
					      'uploadImage',
					      'startRecord',
					      'translateVoice',
					      'downloadVoice'
					    ],
					    success: function (res) {
					      alert(JSON.stringify(res));
					    }
					 });   */
					 loaddata(currentPage, onePageNums);
					 bindMsgList();
				});		
			
				$.init({
					gestureConfig: {
						tap: true, //默认为true
						doubletap: true, //默认为false
						longtap: true, //默认为false
						swipe: true, //默认为true
						drag: true, //默认为true
						hold: true, //默认为false，不监听
						release: true //默认为false，不监听
					}
					
				});
				
				
			
				//关闭默认转义,模板引擎默认数据包含的 HTML 字符进行转义以避免 XSS 漏洞
				//template.config('escape', false);
				
				
				var ui = {
					body: doc.querySelector('body'),
					footer: doc.querySelector('footer'),
					footerRight: doc.querySelector('.footer-right'),
					footerLeft: doc.querySelector('.footer-left'),
					btnMsgType: doc.querySelector('#msg-type'),
					boxMsgText: doc.querySelector('#msg-text'),
					boxMsgSound: doc.querySelector('#msg-sound'),
					btnMsgImage: doc.querySelector('#msg-image'),
					areaMsgList: doc.querySelector('#msg-list'),
					boxSoundAlert: doc.querySelector('#sound-alert'),
					h: doc.querySelector('#h'),
					content: doc.querySelector('.mui-content')
				};
				
				
				var imageViewer = new $.ImageViewer('.msg-content-image', {
					dbl: false
				});
				
				var headImgUrl = "";
				var GetHeadUrl = function(userId){
					$.ajax({
					    url: "user/getHeadUrl?userId=" + userId,
					    success: function(result){
					    	var json = eval("(" + result + ")");
							headImgUrl = json.url;
					    },
					  	dataType: "text",
					  	async:false
					});
				} 
				
				GetHeadUrl(fromuserid);
				var websocket;
				var domain = window.location.host;
				var initWebSocket = function() {
					if ('WebSocket' in window) {  
						//mui.alert('连接方式：WebSocket', '系统消息', function() {});
						websocket = new WebSocket("ws://" + domain + "/echo?fromuserid=" + fromuserid + "&touserid=" + touserid);
					} else if ('MozWebSocket' in window) {
						//mui.alert('连接方式：MozWebSocket', '系统消息', function() {});
						websocket = new MozWebSocket("ws://echo?fromuserid=" + fromuserid + "&touserid=" + touserid);
					} else {
						//mui.alert('连接方式：SockJS', '系统消息', function() {});
						websocket = new SockJS("http://" + domain + "/sockjs/echo?fromuserid=" + fromuserid + "&touserid=" + touserid);
					}
				}
				
				initWebSocket();
				websocket.onopen = function(evnt) {
					/* ui.areaMsgList.innerHTML = template('msg-template', {
						"record": record
					}); */
					//mui.alert('连接服务器成功!' + evnt.data, '系统消息', function() {});
				};
				
				var bindMsgList = function() {
					//绑定数据:
					/* ui.areaMsgList.innerHTML = template('msg-template', {
						"record": record
					}); */
					var msgItems = ui.areaMsgList.querySelectorAll('.msg-item');
					[].forEach.call(msgItems, function(item, index) {
					
						var msgType = item.getAttribute('msg-type');
						var msgContent = item.getAttribute('msg-content');
						
						if(msgType == 'image' ) {
							//var content = item.querySelectorAll('.msg-content-image')[0].src;
							//var URL = window.location.href.split('#')[0]; //获取当前页面的url
							wx.downloadImage({
							    serverId: msgContent, // 需要下载的图片的服务器端ID，由uploadImage接口获得
							    isShowProgressTips: 0, // 默认为1，显示进度提示
							    success: function (res) {
							   	 	var localId = res.localId;
							    	item.querySelectorAll('.msg-content-image')[0].src=localId;
							   		
							   		ui.areaMsgList.scrollTop = ui.areaMsgList.scrollHeight + ui.areaMsgList.offsetHeight;
							   		
							    }
							});
							
							
						}
					
						item.addEventListener('tap', function(event) {
							
							msgItemTap(item, event);
						}, false);
					});
					imageViewer.findAllImage();
					ui.areaMsgList.scrollTop = ui.areaMsgList.scrollHeight + ui.areaMsgList.offsetHeight;
				};
				
				
				var bindSendMsgList = function() {
					var msgItems = ui.areaMsgList.querySelectorAll('.msg-item');
					[].forEach.call(msgItems, function(item, index) {
					
						item.addEventListener('tap', function(event) {
							
							msgItemTap(item, event);
						}, false);
					});
					imageViewer.findAllImage();
					ui.areaMsgList.scrollTop = ui.areaMsgList.scrollHeight + ui.areaMsgList.offsetHeight;
				};
				
				websocket.onmessage = function(evnt) {
					
					//mui.alert('收到消息：' + evnt.data, '提示', function() {});
					var str = evnt.data;
					
					var obj = eval('(' + str + ')');
					//alert(obj.message_detail);
					//接收消息
					/* 
					[{
						"sender":"echo","nick_name":"Mr Ren","to_user_id":3,"from_user_id":47,"message_nature_type":0,"message_status":1,"message_detail":"哈你妹","psot_time":1476851319000,"head_img_url":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM6IWnDSHLFAcvTHI8gTFyxQ9tGzZHzUicewrjWQpXooHpQ8XOjEL8eJcYdAfEtk4IqkQfnE1pMDrMw/0","message_id":2,"message_type":1
					},
					{
						"sender":"self","nick_name":"Echo","to_user_id":47,"from_user_id":3,"message_nature_type":0,"message_status":1,"message_detail":"哈哈哈","psot_time":1476851166000,"head_img_url":"http://wx.qlogo.cn/mmopen/nBcYE97sU44fEficHaianAEHRf7G1psw2NkT2PNEibqGt6IfkHQPv63KVjUH9nBRUYCxliaL4oFV0mutYoNsVusu6ttLfZictk9oY/0","message_id":1,"message_type":1
					},
					{
						"sender":"self","nick_name":"Echo","to_user_id":47,"from_user_id":3,"message_nature_type":0,"message_status":1,"message_detail":"你好","psot_time":1476765027000,"head_img_url":"http://wx.qlogo.cn/mmopen/nBcYE97sU44fEficHaianAEHRf7G1psw2NkT2PNEibqGt6IfkHQPv63KVjUH9nBRUYCxliaL4oFV0mutYoNsVusu6ttLfZictk9oY/0","message_id":3,"message_type":1
					}]
					 */ 
					 
				
					 
					try{
						for(var i = 0;i<obj.length;i++){
							
							var json = {
								sender: obj[i].sender,
								type: obj[i].message_type,
								headImgUrl: obj[i].head_img_url,
								content: obj[i].message_detail
							};
							record.push(json);
							
							
						}
					}catch(e){
					}
					bindMsgList();	
					
				};
				
				
				websocket.onerror = function(evnt) {
					mui.alert('服务器出错了-》' + evnt.data, '系统消息', function() {});
				};
				websocket.onclose = function(evnt) {
					mui.alert('与服务器断开了链接!', '系统消息', function() {});
				}
				
				
				
				
				ui.h.style.width = ui.boxMsgText.offsetWidth + 'px';
				//alert(ui.boxMsgText.offsetWidth );
			
				//  智能接口
				var voice = {
				    localId: '',
				    serverId: ''
				};
			
				var footerPadding = ui.footer.offsetHeight - ui.boxMsgText.offsetHeight;
				var msgItemTap = function(msgItem, event) {
					var msgType = msgItem.getAttribute('msg-type');
					var msgContent = msgItem.getAttribute('msg-content');
					if(msgType == 'sound') {
						var playState = msgItem.querySelector('.play-state');
						playState.innerText = '正在播放...';
						wx.downloadImage({
						    serverId: msgContent, // 需要下载的图片的服务器端ID，由uploadImage接口获得
						    isShowProgressTips: 0, // 默认为1，显示进度提示
						    success: function (res) {
						   	 	wx.playVoice({
							      localId: res.localId
							    });
						    	
						    }
						});
						
					    
					    //  停止播放音频
					   /*  wx.stopVoice({
						     localId: voice.localId
						}); */
					    
					    //  监听录音播放停止
						wx.onVoicePlayEnd({
						    complete: function (res) {
						      playState.innerText = '点击播放';
						    }
						});
					}
				};
				
				var send = function(msg) {
					//发送消息
					/* 
					{
						sender: 'self',
						type: 'image',
						headImgUrl: headImgUrl,
						content: localId,
						serverId :res.serverId
					} 
					 */ 
					if (websocket.readyState != 1) {
						//websocket session 失效后，重新建立链接
						websocket.close();
						initWebSocket();
					} 
					/* else {
						mui.alert('未与服务器链接!', '系统消息', function() {});
					} */
					var html = "";
					if(msg.sender == "self"){
						html += "<div class='msg-item  msg-item-self' msg-type='" + msg.type + "' msg-content='" + msg.content + "'>"
						 + "<i class='msg-user mui-icon'><img class='msg-user-img' src='" + msg.headImgUrl + "' alt='' /></i>";
					}else{
						html += "<div class='msg-item' msg-type='" + msg.type + "' msg-content='" + msg.content + "'>"
						 + "<img class='msg-user-img' src='" + msg.headImgUrl + "' alt='' />";
					}
					html += "<div class='msg-content'><div class='msg-content-inner'>";
					if(msg.type == 'text'){
						html += ( msg.content || '&nbsp;&nbsp;');
					}else if(msg.type == 'image'){
						html += "<img class='msg-content-image' src='" + msg.content + "' style='max-width: 100px;' />";
					}else if(msg.type == 'sound'){
						html += "<span class='mui-icon mui-icon-mic' style='font-size: 18px;font-weight: bold;'></span>"
							+ "<span class='play-state'>点击播放</span>";
					}
					
					html += "</div><div class='msg-content-arrow'></div></div><div class='mui-item-clear'></div></div>";
					var newNode = document.createElement("div"); 
					newNode.innerHTML = html; 
					document.getElementById("msg-list").appendChild(newNode);
					bindSendMsgList();
					
					var message_content = "";		
					if(msg.type == "sound"){
						message_content = '{"from_user_id":"' + fromuserid
							+ '","to_user_id":"' + touserid + '","message_type":"sound"'
							+ ',"message_detail":"'
							+ msg.serverId + '"}';
					}else if(msg.type == "image"){
						message_content = '{"from_user_id":"' + fromuserid
							+ '","to_user_id":"' + touserid + '","message_type":"image"'
							+ ',"message_detail":"'
							+ msg.serverId + '"}';
					}else{
						message_content = '{"from_user_id":"' + fromuserid
							+ '","to_user_id":"' + touserid + '","message_type":"text"'
							+ ',"message_detail":"'
							+ msg.content + '"}';
					}
							
					setTimeout(function() {
						websocket.send(message_content);
			        }, 200);
					
				};
				

				function msgTextFocus() {
					ui.boxMsgText.focus();
					setTimeout(function() {
						ui.boxMsgText.focus();
					}, 150);
				}
				//解决长按“发送”按钮，导致键盘关闭的问题；
				ui.footerRight.addEventListener('touchstart', function(event) {
					if(ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
						msgTextFocus();
						event.preventDefault();
					}
				});
				//解决长按“发送”按钮，导致键盘关闭的问题；
				ui.footerRight.addEventListener('touchmove', function(event) {
					if(ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
						msgTextFocus();
						event.preventDefault();
					}
				});
				ui.footerRight.addEventListener('touchcancel', function(event) {
					if (ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
						msgTextFocus();
						event.preventDefault();
					}
				});
				ui.footerRight.addEventListener('touchend', function(event) {
					if (ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
						msgTextFocus();
						event.preventDefault();
					}
				});
				ui.footerRight.addEventListener('release', function(event) {
					if(ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
						//showKeyboard();
						ui.boxMsgText.focus();
						setTimeout(function() {
							ui.boxMsgText.focus();
						}, 150);
						//							event.detail.gesture.preventDefault();
						if(headImgUrl == ""){
								headImgUrl = GetHeadUrl(fromuserid);	
						}
						
						send({
							sender: 'self',
							type: 'text',
							headImgUrl: headImgUrl,
							content: ui.boxMsgText.value.replace(new RegExp('\n', 'gm'), '<br/>')
						});
						ui.boxMsgText.value = '';
						$.trigger(ui.boxMsgText, 'input', null);
					} else if(ui.btnMsgType.classList.contains('mui-icon-mic')) {
						ui.btnMsgType.classList.add('mui-icon-compose');
						ui.btnMsgType.classList.remove('mui-icon-mic');
						ui.boxMsgText.style.display = 'none';
						ui.boxMsgSound.style.display = 'block';
						ui.boxMsgText.blur();
						document.body.focus();
					} else if(ui.btnMsgType.classList.contains('mui-icon-compose')) {
						ui.btnMsgType.classList.add('mui-icon-mic');
						ui.btnMsgType.classList.remove('mui-icon-compose');
						ui.boxMsgSound.style.display = 'none';
						ui.boxMsgText.style.display = 'block';
						//--
						//showKeyboard();
						ui.boxMsgText.focus();
						setTimeout(function() {  
							ui.boxMsgText.focus();
						}, 150);
					}
				}, false);
				ui.footerLeft.addEventListener('tap', function(event) {
					
					var localId;
					wx.chooseImage({
					    count: 1, // 默认9
					    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
					    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
					    success: function (res) {
					        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					        
					        localId=localIds[0];//默认取一张
					        if(headImgUrl == ""){
								headImgUrl = GetHeadUrl(fromuserid);	
							}
					        //上传到微信服务器
					        wx.uploadImage({
							    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
							    isShowProgressTips: 0, // 默认为1，显示进度提示
							    success: function (res) {
							        
							        send({
										sender: 'self',
										type: 'image',
										headImgUrl: headImgUrl,
										content: localId,
										serverId :res.serverId
									})
							    }
							}); 
					        
					    }
					});
					
					
				}, false);
				var setSoundAlertVisable = function(show) {
					if(show) {
						ui.boxSoundAlert.style.display = 'block';
						ui.boxSoundAlert.style.opacity = 1;
					} else {
						ui.boxSoundAlert.style.opacity = 0;
						//fadeOut 完成再真正隐藏
						setTimeout(function() {
							ui.boxSoundAlert.style.display = 'none';
						}, 200);
					}
				};
				var recordCancel = false;
				var audio_tips = document.getElementById("audio_tips");
				var startTimestamp = null;
				var stopTimestamp = null;
				var stopTimer = null;
				//录音最小间隔
				var MIN_SOUND_TIME = 800;
				ui.boxMsgSound.addEventListener('hold', function(event) {
					recordCancel = false;
					if(stopTimer) clearTimeout(stopTimer);
					audio_tips.innerHTML = "手指上划，取消发送";
					ui.boxSoundAlert.classList.remove('rprogress-sigh');
					setSoundAlertVisable(true);
					//开始录音
					wx.startRecord({
					    cancel: function () {
					   	  mui.alert('用户拒绝授权录音!', '提示', function() {});
					    }
					});
					startTimestamp = (new Date()).getTime();
					
				}, false);
				ui.body.addEventListener('drag', function(event) {
					//console.log('drag');
					if(Math.abs(event.detail.deltaY) > 50) {
						if(!recordCancel) {
							recordCancel = true;
							if(!audio_tips.classList.contains("cancel")) {
								audio_tips.classList.add("cancel");
							}
							audio_tips.innerHTML = "松开手指，取消发送";
						}
					} else {
						if(recordCancel) {
							recordCancel = false;
							if(audio_tips.classList.contains("cancel")) {
								audio_tips.classList.remove("cancel");
							}
							audio_tips.innerHTML = "手指上划，取消发送";
						}
					}
				}, false);
				ui.boxMsgSound.addEventListener('release', function(event) {
					//console.log('release');
					if(audio_tips.classList.contains("cancel")) {
						audio_tips.classList.remove("cancel");
						audio_tips.innerHTML = "手指上划，取消发送";
					}
					//
					stopTimestamp = (new Date()).getTime(); 
					if(stopTimestamp - startTimestamp < MIN_SOUND_TIME) {
						audio_tips.innerHTML = "录音时间太短";
						ui.boxSoundAlert.classList.add('rprogress-sigh');
						recordCancel = true;
						stopTimer = setTimeout(function() {
							setSoundAlertVisable(false);
						}, 800);
					} else {
						setSoundAlertVisable(false);
					}
					wx.stopRecord({
				      success: function (res) {
				        voice.localId = res.localId;
				        if(!recordCancel){
				        	if(headImgUrl == ""){
								headImgUrl = GetHeadUrl(fromuserid);	
							}
							
							wx.uploadVoice({
							    localId: res.localId, // 需要上传的音频的本地ID，由stopRecord接口获得
							    isShowProgressTips: 0, // 默认为1，显示进度提示
							        success: function (res) {
							        voice.serverId = res.serverId; // 返回音频的服务器端ID
								    send({
										sender: 'self',
										type: 'sound',
										headImgUrl: headImgUrl,
										content: voice.localId,
										serverId :res.serverId
									})
							    }
							});
				        	
				        }
				        
				      },
				      fail: function (res) {
				        alert(JSON.stringify(res));
				      }
				    });
				}, false);
				
				//  监听录音自动停止
				wx.onVoiceRecordEnd({
				    complete: function (res) {
				      voice.localId = res.localId;
				      audio_tips.classList.remove("cancel");
					  audio_tips.innerHTML = "录音时间不得超过一分钟";
					  stopTimer = setTimeout(function() {
							setSoundAlertVisable(false);
					  }, 800);
					  if(headImgUrl == ""){
						   headImgUrl = GetHeadUrl(fromuserid);	
					  }
					  wx.uploadVoice({
					    localId: res.localId, // 需要上传的音频的本地ID，由stopRecord接口获得
					    isShowProgressTips: 0, // 默认为1，显示进度提示
					        success: function (res) {
					        voice.serverId = res.serverId; // 返回音频的服务器端ID
						        send({
									sender: 'self',
									type: 'sound',
									headImgUrl: headImgUrl,
									content: voice.localId,
									serverId :res.serverId
								})
						    }
						});
				    }
				});
				
				ui.boxMsgSound.addEventListener("touchstart", function(e) {
					//console.log("start....");
					e.preventDefault();
				});
				ui.boxMsgText.addEventListener('input', function(event) {
					ui.btnMsgType.classList[ui.boxMsgText.value == '' ? 'remove' : 'add']('mui-icon-paperplane');
					ui.btnMsgType.setAttribute("for", ui.boxMsgText.value == '' ? '' : 'msg-text');
					ui.h.innerText = ui.boxMsgText.value.replace(new RegExp('\n', 'gm'), '\n-') || '-';
					ui.footer.style.height = (ui.h.offsetHeight + footerPadding) + 'px';
					ui.content.style.paddingBottom = ui.footer.style.height;
				});
				var focus = false;
				ui.boxMsgText.addEventListener('tap', function(event) {
					ui.boxMsgText.focus();
					setTimeout(function() {
						ui.boxMsgText.focus();
					}, 0);
					focus = true;
					setTimeout(function() {
						focus = false;
					}, 1000);
					event.detail.gesture.preventDefault();
				}, false);
				//点击消息列表，关闭键盘
				ui.areaMsgList.addEventListener('tap', function(event) {
					if(!focus) {
						ui.boxMsgText.blur();
					}
				})

				function loaddata(startIndex, endIndex) {
				
					$.ajax({
						url : 'chat/getChatHistoryMsg',
						data : {
							startIndex : startIndex,
							endIndex : endIndex,
							fromUserID : fromuserid,
							toUserID :touserid
						},
						async : false,
						type : 'post',
						dataType : 'json',
						success : function(data) {
							
							for (var i = 0; i < data.length; i++) {
							
								var html = "";
								if(data[i].sender == "self"){
									html += "<div class='msg-item  msg-item-self' msg-type='" + data[i].message_type + "' msg-content='" + data[i].message_detail + "'>"
									 + "<i class='msg-user mui-icon'><img class='msg-user-img' src='" + data[i].head_img_url + "' alt='' /></i>";
								}else{
									html += "<div class='msg-item' msg-type='" + data[i].message_type + "' msg-content='" + data[i].message_detail + "'>"
									 + "<img class='msg-user-img' src='" + data[i].head_img_url + "' alt='' />";
								}
								html += "<div class='msg-content'><div class='msg-content-inner'>";
								if(data[i].message_type == 'text'){
									html += ( data[i].message_detail || '&nbsp;&nbsp;');
								}else if(data[i].message_type == 'image'){
									html += "<img class='msg-content-image' src='' style='max-width: 100px;' />";
								}else if(data[i].message_type == 'sound'){
									html += "<span class='mui-icon mui-icon-mic' style='font-size: 18px;font-weight: bold;'></span>"
										+ "<span class='play-state'>点击播放</span>";
								}
								
								html += "</div><div class='msg-content-arrow'></div></div><div class='mui-item-clear'></div></div>";
								var newNode = document.createElement("div"); 
								newNode.innerHTML = html; 
								var reforeNode = document.getElementById("msg-list").firstChild; 
								document.getElementById("msg-list"). insertBefore(newNode,reforeNode );
								
								
							}
							/* $('.task-col').on('tap', function() {
								window.location.href = "mission/task-info/"
												+ $(this).attr("data-m");
							}) */
						}
					})
				}
			}(mui, document));
			
			
			
			
		</script>
	</body>

</html>