package com.cn.ucoon.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {
	//"D:/ucoon/mission_image/";
	//"/data/wwwroot/wx.ucoon.cn/mission_image/"
	public static final String MISSION_IMAGE_LOCATION = "D:/ucoon/mission_image/";

	@RequestMapping("/reqmImage/{category}/{num}")
	public void getMissionImage(@PathVariable("category") String category,
			@PathVariable("num") final Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(category)) {
			category = "";
		}
		String path = MISSION_IMAGE_LOCATION + category;
		FileFilter filefilter = new FileFilter() {
			public boolean accept(File file) {
				if (file.getName().startsWith(num.toString())) {
					System.out.println(file.getName());
					return true;

				}
				return false;
			}
		};
		File file = new File(path);
		System.out.println(path);
		File[] files = file.listFiles(filefilter);
		File tar = null;
		if (files.length > 0) {
			tar = files[0];
		}
		response.setContentType("image/png");
		try {
			FileInputStream inputStream = new FileInputStream(tar);
			byte[] data = new byte[(int) tar.length()];
			int length = inputStream.read(data);
			inputStream.close();

			response.setContentType("image/png");

			OutputStream stream = response.getOutputStream();
			stream.write(data);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
