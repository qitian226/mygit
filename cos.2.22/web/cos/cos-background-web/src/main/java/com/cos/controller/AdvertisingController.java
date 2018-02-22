package com.cos.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import com.cos.common.cache.load.CacheLoadPropertiesFile;
import com.cos.common.utils.FileLocalUtils;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.enums.FileTypeEnum;
import com.cos.model.ImgFile;
import com.cos.service.sys.IFileService;
@RestController
public class AdvertisingController extends BaseController {
	
	@Resource
	private IFileService fileService;
	
	@RequestMapping(value="/sys/advertising.html")
	public ModelAndView toUser(){
	  ModelAndView view=new ModelAndView("background/advertising");
	  List<ImgFile> files= fileService.queryIndexFiles();
	  view.addObject("files", files);
	  return view;
	}
	@RequestMapping(value="/sys/delimg")
	public String delImg(Long id){
	  try {
		  fileService.delImgFile(id);
	} catch (Exception e) {
		 return ReturnJSONUtil.getWarnInfo("删除图片失败!");
	}
	  return ReturnJSONUtil.getSuccessInfo("删除图片成功!");
	}
	 @RequestMapping(value = "/uploadindexfile",produces = "text/plain;charset=UTF-8")  
	 	@ResponseBody
	    public String fileUploadSimpleFile(HttpServletRequest request,HttpServletResponse response) throws  Exception {
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
	                	imgurl=FileLocalUtils.uploadFiles(file.getBytes(),"index",id.toString(),"jpg");
	                }
	            }  
	            ImgFile ifile=new ImgFile();
	            ifile.setId(id);
	            ifile.setCreateBy(this.getAccount().getId());
	            ifile.setType(FileTypeEnum.Index.getName());
	            ifile.setUrl(imgurl);
	            ifile.setCurrVersion((short) 1);
	            String out= fileService.addImgFile(ifile);
	            return out;
	        }  
	         return ReturnJSONUtil.getWarnInfo("图片发布失败!");
	    }
	 
}
