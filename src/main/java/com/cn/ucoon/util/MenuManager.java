package com.cn.ucoon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ucoon.pojo.wx.AccessToken;
import com.cn.ucoon.pojo.wx.Button;
import com.cn.ucoon.pojo.wx.ComplexButton;
import com.cn.ucoon.pojo.wx.Menu;
import com.cn.ucoon.pojo.wx.ViewButton;

/**
 * 菜单管理器类:直接运行生成菜单 一级菜单<=3 二级菜单<=5
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {

		/**
		 * 测试号
		 */
		//第三方用户唯一凭证
		//String appId = "wx7687c14ac73b051b";
//		 第三方用户唯一凭证密钥
		//String appSecret = "58761104834ed5a2a617443a5b838568";
		
//		 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.appid, WeixinUtil.appsecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		//
//		ViewButton btn11 = new ViewButton();
//		btn11.setName("父亲节");
//		btn11.setType("view");
//		btn11.setUrl("http://m.5read.com/64");
		//
		// CommonButton btn12 = new CommonButton();
		// btn12.setName("擦");
		// btn12.setType("click");
		// btn12.setKey("12");
		//
		// CommonButton btn13 = new CommonButton();
		// btn13.setName("");
		// btn13.setType("click");
		// btn13.setKey("13");
		
		ViewButton btn11 = new ViewButton();
		btn11.setName("Echo");
		btn11.setType("view");
		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx14236620e0b8201e&redirect_uri=http%3A%2F%2Fwx.ucoon.cn%2Fucoon%2F&response_type=code&scope=snsapi_userinfo&state=echo#wechat_redirect");

	
		
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("");
		mainBtn1.setSub_button(new Button[] { btn11 });

//		ComplexButton mainBtn2 = new ComplexButton();
//		mainBtn2.setName("");
//		mainBtn2.setSub_button(new Button[] {btn,btn42,btn43,btn41  });
//
//		ComplexButton mainBtn3 = new ComplexButton();
//		mainBtn3.setName("");
//		mainBtn3.setSub_button(new Button[] { btn21,btn22,btn31,btn32,btn26 });

		/**
		 * 一级菜单直接跳转
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] {btn11});

		return menu;
	}
}
