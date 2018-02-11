package com.cos.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.cache.load.CacheLoadPropertiesFile;
import com.cos.common.utils.ImageUtils;
import com.cos.common.utils.JSOUPUtil;
import com.cos.common.utils.RegularUtil;
import com.cos.controller.base.BaseController;
import com.cos.exception.BusinessException;
import com.cos.global.FileGlobal;
import com.cos.model.Entry;
import com.cos.model.ImgFile;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.service.comment.ICommentService;
import com.cos.service.sys.IFileService;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.topicManage.IFileUploadService;
import com.cos.service.topicManage.ITopicManageService;

@RestController
public class ImageController extends BaseController {

	private static final Log logger = LogFactory.getLog(ImageController.class);
	@Resource
	private ICommentService commentService;
	@Resource
	private ITopicManageService topicManageService;
	@Resource
	private ISysAccountService sysAccountService;
	@Resource
	private IFileUploadService fileUploadService;
	@Resource
	private IFileService fileService;

	private final String BASE_FILE_PATH = CacheLoadPropertiesFile.getPropertiesValue("filepath", "uploadpath");
	private Integer topic_width = 260;
	private Integer topic_height = 260;
	private Integer entry_width = 260;
	private Integer entry_height = 260;

	@RequestMapping(value = "/topic/image/{id}")
	public void topicImageLoad(@PathVariable String id, HttpServletResponse response) throws Exception {
		Topic topic = topicManageService.querySimpleTopic(id);
		String imgPath = (null == topic.getImgUrl()) ? FileGlobal.DEFAULT_IMG_URL : topic.getImgUrl();
		String filePath = imgPath;
		File file = new File(filePath);
		if (!file.exists()) {
			logger.debug("path:" + filePath + " ,file doesn't exist");
			file = new File(FileGlobal.DEFAULT_IMG_URL);
		}
		ImageUtils.responseToPage(response, file, id);
	}

	@RequestMapping(value = "/topic/image/s/{id}")
	public void topicImageResizeLoad(@PathVariable String id, HttpServletResponse response) throws Exception {
		Topic topic = topicManageService.querySimpleTopic(id);
		String imgPath = (null == topic.getImgUrl()) ? FileGlobal.DEFAULT_IMG_URL : topic.getImgUrl();
		String filePath = imgPath.split("\\.")[0];
		filePath = filePath + "_s.jpg";
		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("path:" + filePath + " ,file doesn't exist");
			file = new File(FileGlobal.DEFAULT_IMG_URL);
		}
		ImageUtils.responseToPage(response, file, id + "_s");
	}

	@RequestMapping(value = "/entry/image/{id}")
	public void entryImageLoad(@PathVariable String id, HttpServletResponse response) throws Exception {
		Entry entry = topicManageService.querySimpleEntry(id);
		String imgPath = (null == entry.getImgUrl()) ? FileGlobal.DEFAULT_IMG_URL : entry.getImgUrl();
		String filePath = imgPath;
		File file = new File(filePath);
		if (!file.exists()) {
			logger.debug("path:" + filePath + " ,file doesn't exist");
			file = new File(FileGlobal.DEFAULT_IMG_URL);
		}
		ImageUtils.responseToPage(response, file, id);
	}

	@RequestMapping(value = "/entry/image/s/{id}")
	public void entryImageResizeLoad(@PathVariable String id, HttpServletResponse response) throws Exception {
		Entry entry = topicManageService.querySimpleEntry(id);
		String imgPath = (null == entry.getImgUrl()) ? FileGlobal.DEFAULT_IMG_URL : entry.getImgUrl();

		String filePath = imgPath.split("\\.")[0];
		filePath = filePath + "_s.jpg";
		File file = new File(filePath);
		if (!file.exists()) {
			logger.debug("path:" + filePath + " ,file doesn't exist");
			file = new File(FileGlobal.DEFAULT_IMG_URL);
		}
		ImageUtils.responseToPage(response, file, id + "_s");
	}

	@RequestMapping(value = "/entry/fullimg")
	public ModelAndView toFullImage(String url) throws Exception {
		ModelAndView view = new ModelAndView("topic/full_img");
		view.addObject("url", url);
		return view;
	}

	@RequestMapping(value = "/u/image/{id}")
	public void accountImageLoad(@PathVariable Long id, HttpServletResponse response) throws Exception {
		SysAccount account = sysAccountService.querySimpleAccount(id);
		String imgPath = (null == account.getImgPath()) ? FileGlobal.DEFAULT_IMG_URL : account.getImgPath();
		File file = new File(imgPath);
		if (!file.exists()) {
			logger.debug("path:" + imgPath + " ,file doesn't exist");
			file = new File(FileGlobal.DEFAULT_IMG_URL);
		}
		ImageUtils.responseToPage(response, file, id.toString());
	}

	@RequestMapping(value = "/filesimpleupload", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String fileUploadSimpleFile(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			String fileFloderPath = "";
			String filename = request.getParameter("filename");
			String foldername = request.getParameter("foldername");
			fileFloderPath = foldername + "/";
			String path = BASE_FILE_PATH + fileFloderPath;
			File floderPath = new File(path);
			if (!floderPath.exists()) {
				floderPath.mkdirs();
			}
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					File localFile = new File(path, filename);
					file.transferTo(localFile);
				}
			}
		}
		return "{}";
	}

	/**
	 *
	 * @param id
	 *            图片id对应entry id 保持图片唯一
	 * @param imagedata
	 * @param width
	 * @param height
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/entry/imageupload", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String entryImageUpload(Long id, String imagedata, Integer width, Integer height,String title,String desc,Long topic)
			throws IllegalStateException, IOException {
		try {
			if (width.compareTo(height) > 0) {
				width = width * entry_height / height;
				height = entry_height;
			}
			if (height.compareTo(width) > 0) {
				height = height * entry_width / width;
				width = entry_width;
			}

			if (height.equals(width)) {
				height = topic_height;
				width = topic_width;
			}
			if(StringUtils.isNotBlank(title)){
			title=JSOUPUtil.pureText(title);
			title=RegularUtil.replaceSpecStr(title, RegularUtil.SPECIAL_CODE);
			}
			if(StringUtils.isNotBlank(desc)){
			desc=JSOUPUtil.filterBasic(desc);
			}
			String out = fileUploadService.addEntryPic(id, imagedata, width, height, "jpg",title, desc,topic,this.getAccount().getId());
			return out;
		} catch (Exception e) {
			throw new BusinessException("上传图片错误!", e);
		}
	}
	@RequestMapping(value = "/entry/update/imageupload", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String entryImageUpdate(Long id, String imagedata, Integer width, Integer height,String title,String desc,Long topic)
			throws IllegalStateException, IOException {
		try {
			if (width.compareTo(height) > 0) {
				width = width * entry_height / height;
				height = entry_height;
			}
			if (height.compareTo(width) > 0) {
				height = height * entry_width / width;
				width = entry_width;
			}

			if (height.equals(width)) {
				height = topic_height;
				width = topic_width;
			}
			if(StringUtils.isNotBlank(title)){
				title=JSOUPUtil.pureText(title);
				title=RegularUtil.replaceSpecStr(title, RegularUtil.SPECIAL_CODE);
				}
				if(StringUtils.isNotBlank(desc)){
				desc=JSOUPUtil.filterBasic(desc);
				}
			String out = fileUploadService.updateEntryPic(id, imagedata, width, height, "jpg",title, desc,topic,this.getAccount().getId());
			return out;
		} catch (Exception e) {
			throw new BusinessException("上传图片错误!", e);
		}
	}
	/**
	 *
	 * @param id
	 *            图片id对应topic id 保持图片唯一
	 * @param imagedata
	 * @param width
	 * @param height
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 *             配合 tomcat server.xml maxPostSize="" 属性
	 */
	@RequestMapping(value = "/topic/imageupload", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String topicImageUpload(HttpServletRequest req, Long id, String imagedata, Integer width, Integer height)
			throws IllegalStateException, IOException {
		try {

			if (width.compareTo(height) > 0) {
				width = width * topic_height / height;
				height = topic_height;
			}
			if (height.compareTo(width) > 0) {
				height = height * topic_width / width;
				width = topic_width;
			}

			if (height.equals(width)) {
				height = topic_height;
				width = topic_width;
			}
			String out = fileUploadService.uploadTopicPic(id, imagedata, width, height, "jpg");
			return out;
		} catch (Exception e) {
			throw new BusinessException("上传图片失败!", e);
		}
	}

	@RequestMapping(value = "/file/image/{id}")
	public void fileImageLoad(@PathVariable Long id, HttpServletResponse response) throws Exception {
		ImgFile imgFile = fileService.querySimpleFile(id);
		String imgPath = imgFile.getUrl();
		String filePath = imgPath;
		File file = new File(filePath);
		ImageUtils.responseToPage(response, file, id.toString());
	}

}
