package com.cos.service.topicManage;


public interface IFileUploadService {

public String uploadSimplePic(Long topicId, String imagedata,Integer width,Integer height,String imgType) throws Exception;

public String uploadTopicPic(Long topicId, String imagedata, Integer width, Integer height, String imgType) throws Exception;

public String addEntryPic(Long id, String imagedata, Integer width, Integer height, String imgType, String title,
		String desc, Long topic,Long accountId) throws Exception ;

public String updateEntryPic(Long id, String imagedata, Integer width, Integer height, String imgType, String title,
		String desc, Long topic, Long accountId) throws Exception;

public String uploadUserPic(Long userId, String imagedata, Integer width, Integer height, String imgType) throws Exception;

public void delRemoteFile(String imgPath);
}
