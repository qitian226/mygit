package com.cos.service.topicManage.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.cache.load.CacheLoadPropertiesFile;
import com.cos.common.utils.FastDfsUtil;
import com.cos.common.utils.ImageUtils;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.exception.BusinessException;
import com.cos.service.edit.ITopicEditService;
import com.cos.service.topic.ITopicService;
import com.cos.service.topicManage.IFileUploadService;

import sun.misc.BASE64Decoder;

@Service@Transactional
public class FileUploadService implements IFileUploadService {
	private final String IMG_PATH = CacheLoadPropertiesFile.getPropertiesValue("filepath", "imagepath");
	private final String HEAD_PATH = CacheLoadPropertiesFile.getPropertiesValue("filepath", "headpath");
    private final int MAX_FILE_LENGTH=1;

    @Resource
    private ITopicService topicService;
    @Resource
	private ITopicEditService topicEditService;

    @Override
	public String uploadUserPic(Long userId, String imagedata,Integer width,Integer height,String imgType) throws Exception {
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
		
		String imgid=FastDfsUtil.uploadFiles(out.toByteArray(), imgType);
		return  imgid;
	}
	@Override
	public String uploadSimplePic(Long topicId, String imagedata,Integer width,Integer height,String imgType) throws Exception {
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
		
		String imgid=FastDfsUtil.uploadFiles(decodedBytes, imgType);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in=ImageUtils.byte2Input(decodedBytes);
		
		ImageUtils.resize(in, out,width.intValue(),height.intValue()); //保存时调整尺寸
		String zoomin_imgid=FastDfsUtil.uploadFiles(out.toByteArray(), imgType);
		return  imgid+";"+zoomin_imgid;
	}

	@Override
	public String uploadTopicPic(Long topicId, String imagedata, Integer width, Integer height, String imgType) throws Exception {
		String url=uploadSimplePic(topicId,imagedata,width,height,imgType);
		return topicService.updateTopicCover(topicId,url,width,height);
	}

	@Override
	public String addEntryPic(Long entryId, String imagedata, Integer width, Integer height, String imgType, String title,
			String desc, Long topicId,Long accountId) throws Exception {
		String url=uploadSimplePic(entryId,imagedata,width,height,imgType);
		return topicEditService.addEntry(entryId, title, url, desc, topicId, accountId,width,height);

	}

	@Override
	public String updateEntryPic(Long entryId, String imagedata, Integer width, Integer height, String imgType, String title,
			String desc, Long topic, Long accountId) throws Exception {
		String url=uploadSimplePic(entryId,imagedata,width,height,imgType);
		return topicEditService.updateEntry(title, url, desc, entryId,accountId,width,height);
	}
	@Override
	public void delRemoteFile(String imgId) {
		try {
			FastDfsUtil.delFile(imgId);
		} catch (Exception e) {
			throw new BusinessException("remove file :"+imgId + " error", e);
		}
	}
}
