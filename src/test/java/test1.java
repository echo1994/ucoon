import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.util.WeixinUtil;

public class test1 {

	/**
	 *
	 * @author qincd
	 * @date Nov 6, 2014 9:57:54 AM
	 */
	public static void main(String[] args) {
		String filePath = "C:\\Users\\mlk\\Desktop\\龙腾烟行\\5443472423712b1faf2467e70ee96006.png";
		JSONObject uploadJsonObj = testUploadMedia(filePath, "image");
		if (uploadJsonObj == null) {
			System.out.println("上传图片失败");
			return;
		}

		int errcode = 0;
		if (uploadJsonObj.containsKey("errcode")) {
			errcode = uploadJsonObj.getIntValue("errcode");
		}
		if (errcode == 0) {
			System.out.println("图片上传成功");

			String mediaId = uploadJsonObj.getString("media_id");
			long createAt = uploadJsonObj.getLong("created_at");
			System.out.println("--Details:");
			System.out.println("media_id:" + mediaId);
			System.out.println("created_at:" + createAt);
		} else {
			System.out.println("图片上传失败！");

			String errmsg = uploadJsonObj.getString("errmsg");
			System.out.println("--Details:");
			System.out.println("errcode:" + errcode);
			System.out.println("errmsg:" + errmsg);
		}

		/*String mediaId = "6W-UvSrQ2hkdSdVQJJXShwtFDPLfbGI1qnbNFy8weZyb9Jac2xxxcAUwt8p4sYPH";
		String filepath = testDownloadMedia(mediaId,
				"d:/test");
		System.out.println(filepath);*/
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
