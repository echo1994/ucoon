import java.io.IOException;
import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.util.WeixinUtil;

public class test1 {

	/**
	 *
	 * @author qincd
	 * @date Nov 6, 2014 9:57:54 AM
	 */
	public static void main(String[] args) {
		float rate = 0.08f;
		 BigDecimal singleMonney = new BigDecimal("0.15");
		BigDecimal result = singleMonney .multiply(new BigDecimal((1 - rate))).setScale(2, BigDecimal.ROUND_HALF_UP);
			System.out.println(result);
	}

	/**
	 * 上传多媒体文件到微信
	 *
	 * @author qincd
	 * @date Nov 6, 2014 4:15:14 PM
	 */
	public static JSONObject testUploadMedia(String filePath, String type) {
		try {
			return WeixinUtil.uploadMediaToWX(filePath, type);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 从微信下载多媒体文件
	 *
	 * @author qincd
	 * @date Nov 6, 2014 4:56:25 PM
	 */
	public static String testDownloadMedia(String mediaId,
			String fileSaveDir) {
		try {
			return WeixinUtil.downloadMediaFromWx(mediaId,
					fileSaveDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
