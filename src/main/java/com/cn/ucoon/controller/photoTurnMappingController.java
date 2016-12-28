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
@RequestMapping("/admin")
public class photoTurnMappingController {
	

	public static final String MISSION_IMAGE_LOCATION = "D:/ucoonFileUpload/";

	@RequestMapping("/reqmImage/{filename}/{filetype}")
	public void getMissionImage(@PathVariable("filename") String filename,@PathVariable("filetype") String filetype, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(filename)) {
			filename = "";
		}
		String path = MISSION_IMAGE_LOCATION + filename+"."+filetype;
		FileFilter filefilter = new FileFilter() {
			public boolean accept(File file) {
				if (file.getName()!=null && file.getName().length()>0) {
					System.out.println(file.getName());
					return true;
				}
				return false;
			}
		};
		File file = new File(path);
		
		File tar = file;
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
