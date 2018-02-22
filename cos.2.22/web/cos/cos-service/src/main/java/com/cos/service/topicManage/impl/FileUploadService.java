package com.cos.service.topicManage.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.common.utils.FileLocalUtils;
import com.cos.common.utils.ImageUtils;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.exception.BusinessException;
import com.cos.model.SysAccount;
import com.cos.service.edit.ITopicEditService;
import com.cos.service.topic.ITopicService;
import com.cos.service.topicManage.IFileUploadService;

import sun.misc.BASE64Decoder;

@Service@Transactional
public class FileUploadService implements IFileUploadService {
	
	
    private final int MAX_FILE_LENGTH=1;

    @Resource
    private ITopicService topicService;
    @Resource
	private ITopicEditService topicEditService;

    @Override
	public String uploadUserPic(SysAccount user, String imagedata,Integer width,Integer height,String imgType) throws Exception {
		// 去掉头部
		if(imagedata.split(",").length==2){
			imagedata=imagedata.split(",")[1];
		};
		// 写入磁盘
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(imagedata); // 将字符串格式的image转为二进制流（biye[])的decodedBytes
		int fileSize= decodedBytes.length/(1024*1024);
		if(fileSize>=MAX_FILE_LENGTH){
			return ReturnJSONUtil.getWarnInfo("上传图片失败,图片不能超过1M哦!");
		}
	
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in=ImageUtils.byte2Input(decodedBytes);
		ImageUtils.resize(in, out,width.intValue(),height.intValue()); //保存时调整尺寸
		String fileDir=user.getAccountName();
		String filePath=FileLocalUtils.uploadFiles(out.toByteArray(),fileDir,user.getId().toString(), imgType);
		return  filePath;
	}
	@Override
	public String uploadSimplePic(SysAccount user,Long fileId, String imagedata,Integer width,Integer height,String imgType) throws Exception {
		// 去掉头部 
		if(imagedata.split(",").length==2){
			imagedata=imagedata.split(",")[1];
		};
		// 写入磁盘
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(imagedata); // 将字符串格式的image转为二进制流（biye[])的decodedBytes
		int fileSize= decodedBytes.length/(1024*1024);
		if(fileSize>=MAX_FILE_LENGTH){
			return ReturnJSONUtil.getWarnInfo("上传图片失败,图片不能超过1M哦!");
		}
		String fileDir=user.getAccountName();
		
		String filePath=FileLocalUtils.uploadFiles(decodedBytes,fileDir,fileId.toString(), imgType);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in=ImageUtils.byte2Input(decodedBytes);
		
		ImageUtils.resize(in, out,width.intValue(),height.intValue()); //保存时调整尺寸
		 
		String zoomin_filePath=FileLocalUtils.uploadFiles(out.toByteArray(),fileDir,fileId.toString()+"_s", imgType);
		return  filePath+";"+zoomin_filePath;
	}

	@Override
	public String uploadTopicPic(SysAccount user,Long topicId, String imagedata, Integer width, Integer height, String imgType) throws Exception {
		String url=uploadSimplePic(user,topicId,imagedata,width,height,imgType);
		return topicService.updateTopicCover(topicId,url,width,height);
	}

	@Override
	public String addEntryPic(SysAccount user,Long entryId, String imagedata, Integer width, Integer height, String imgType, String title,
			String desc, Long topicId,Long accountId) throws Exception {
		String url=uploadSimplePic(user,entryId,imagedata,width,height,imgType);
		return topicEditService.addEntry(entryId, title, url, desc, topicId, accountId,width,height);
	}

	@Override
	public String updateEntryPic(SysAccount user,Long entryId, String imagedata, Integer width, Integer height, String imgType, String title,
			String desc, Long topic, Long accountId) throws Exception {
		String url=uploadSimplePic(user,entryId,imagedata,width,height,imgType);
		return topicEditService.updateEntry(title, url, desc, entryId,accountId,width,height);
	}
	@Override
	public void delRemoteFile(String imgId) {
		try {
			FileLocalUtils.delFile(imgId);
		} catch (Exception e) {
			throw new BusinessException("remove file :"+imgId + " error", e);
		}
	}
}
