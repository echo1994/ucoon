import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

import com.cn.ucoon.listener.OnlineCountListener;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.util.WeixinUtil;



public class test {

	public static void main(String[] args) throws UnsupportedEncodingException {

		//开发者识别参数
		String state = "echo";
		
		//重定向的地址
		String redirect_url = "http://wx.ucoon.cn/ucoon/wx/oauth";
		
		redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
		
		//公众号appid
		String appid = "wx14236620e0b8201e";
		
		//作用域-》snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。
		String scope = "snsapi_userinfo";
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + redirect_url + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect";
	
		
		System.out.println(url);
		
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx14236620e0b8201e&redirect_uri=http%3A%2F%2Fdq7kesuqpw.proxy.qqbrowser.cc%2Fucoon%2Fwx%2Foauth&response_type=code&scope=snsapi_userinfo&state=echo#wechat_redirect
		System.out.println(OnlineCountListener.getSum());
		
		
		System.out.println(strToDateLong("20141030133525"));
	}
	@Test
	public void getCount(){
		
		System.out.println(WeixinUtil.customSend("ogF_wvuJ_E4axtC729eTozgyyJTM", "text", "空小二", "哈哈哈"));
		Mission mission = new Mission();
		
	
	}
	 /**
	  * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	  * 
	  * @param strDate
	  * @return
	  */
	 public static String strToDateLong(String strDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = formatter.parse(strDate, pos);
	  SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	  String dateString = formatter2.format(strtodate);
	  return dateString;
	 }
	

	
}
