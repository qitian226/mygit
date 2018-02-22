package com.cos.controller;

import java.io.IOException;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cos.common.utils.JSOUPUtil;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.RegularUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.exception.BusinessException;
import com.cos.service.sys.IFileService;
import com.cos.service.topicManage.IFileUploadService;
import com.cos.service.userSet.IUserSetService;

@RestController
public class FileUplodController extends BaseController {

	private static final Log logger = LogFactory.getLog(FileUplodController.class);
	 
	@Resource
	private IFileUploadService fileUploadService;
	
	@Resource
	private IUserSetService userSetService;
	
	@Resource
	private IFileService fileService;

	 
	private Integer topic_width = 260;
	private Integer topic_height = 260;
	private Integer entry_width = 260;
	private Integer entry_height = 260;

	
	@RequestMapping(value = "/user/upload",produces = "text/plain;charset=UTF-8")  
 	@ResponseBody
    public String fileUploadSimpleFile(String imagedata) throws IllegalStateException, IOException {
		String out=userSetService.updateUserImg(imagedata,this.getAccount().getId());
        return out;
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
			String out = fileUploadService.addEntryPic(this.getAccount(),id, imagedata, width, height, "jpg",title, desc,topic,this.getAccount().getId());
			return out;
		} catch (Exception e) {
			throw new BusinessException("上传图片失败!您可以联系管理员!", e);
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
			String out = fileUploadService.updateEntryPic(this.getAccount(),id, imagedata, width, height, "jpg",title, desc,topic,this.getAccount().getId());
			return out;
		} catch (Exception e) {
			throw new BusinessException("上传图片失败!您可以联系管理员!", e);
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
	 * 配合 tomcat server.xml maxPostSize="" 属性
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
			String out = fileUploadService.uploadTopicPic(this.getAccount(),id, imagedata, width, height, "jpg");
			return out;
		} catch (Exception e) {
			throw new BusinessException("上传图片失败!您可以联系管理员!", e);
		}
	}
	 @RequestMapping(value = "/uploadindexfile",produces = "text/plain;charset=UTF-8")  
	 	@ResponseBody
	    public String fileUploadSimpleFile(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    	Long id=PrimaryKeyGenerator.getPrimaryKey("imgfile");
		   //创建一个通用的多部分解析器  
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        String imgurl = null;
	        if(multipartResolver.isMultipart(request)){ 
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的所有文件名  
	            Iterator<String> iter = multiRequest.getFileNames(); 
	            while(iter.hasNext()){  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());
	                if(file.getBytes().length>1024*1024) {
	                	return ReturnJSONUtil.getWarnInfo("文件超出1M!");
	                }
	                if(file != null){
	                String out=	fileUploadService.uploadIndexFile(file.getBytes(),"index",id.toString(),"jpg",this.getAccount().getId());
	                return out;
	                }
	            }  
	        }  
	         return ReturnJSONUtil.getWarnInfo("上传图片失败!您可以联系管理员!");
	    }
	 @RequestMapping(value="/sys/delimg")
		public String delImg(Long id){
		  try {
			  fileService.delImgFile(id);
		} catch (Exception e) {
			 return ReturnJSONUtil.getWarnInfo("删除图片失败!您可以联系管理员!");
		}
		  return ReturnJSONUtil.getSuccessInfo("删除图片成功!");
		}
}
