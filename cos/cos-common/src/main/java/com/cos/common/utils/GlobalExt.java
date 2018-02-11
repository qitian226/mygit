package com.cos.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

import com.cos.common.cache.load.CacheLoadPropertiesFile;

public class GlobalExt implements WebRenderExt{
	private final String IMG_URL = CacheLoadPropertiesFile.getPropertiesValue("filepath", "imageurl");
	private final String HEAD_URL = CacheLoadPropertiesFile.getPropertiesValue("filepath", "headurl");
	private final String INDEX_URL = CacheLoadPropertiesFile.getPropertiesValue("filepath", "indexurl");
	//static long version = System.currentTimeMillis();
	static String sitePath;
	@Override
	public void modify(Template template, GroupTemplate arg1, HttpServletRequest request, HttpServletResponse response) {
		response.setBufferSize(1024*24);
		//js,css 的版本编号
		template.binding("version",System.currentTimeMillis());
		String path = request.getContextPath();
		String sitePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path+"/";
		template.binding("sitePath",sitePath);
		template.binding("imgUrl",IMG_URL);
		template.binding("headUrl",HEAD_URL);
		template.binding("indexUrl",INDEX_URL);
		template.binding("siteDesc","_评尽天下美女_最美云集");
	}
}
