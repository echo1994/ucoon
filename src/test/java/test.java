import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

import com.cn.ucoon.listener.OnlineCountListener;



public class test {

	public static void main(String[] args) throws UnsupportedEncodingException {

		//开发者识别参数
		String state = "echo";
		
		//重定向的地址
		String redirect_url = "http://dq7kesuqpw.proxy.qqbrowser.cc/ucoon/wx/oauth";
		
		redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
		
		//公众号appid
		String appid = "wx7687c14ac73b051b";
		
		//作用域-》snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。
		String scope = "snsapi_userinfo";
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + redirect_url + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect";
	
		
		System.out.println(url);
		
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx14236620e0b8201e&redirect_uri=http%3A%2F%2Fdq7kesuqpw.proxy.qqbrowser.cc%2Fucoon%2Fwx%2Foauth&response_type=code&scope=snsapi_userinfo&state=echo#wechat_redirect
		System.out.println(OnlineCountListener.getSum());
	}
	@Test
	public void getCount(){
		
		System.out.println(OnlineCountListener.getSum());
	}
	
	

	
}
