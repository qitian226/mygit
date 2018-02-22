package com.cos.service.topicManage;

import com.cos.model.SysAccount;

public interface IFileUploadService {

public String uploadSimplePic(SysAccount user,Long topicId, String imagedata,Integer width,Integer height,String imgType) throws Exception;

public String uploadTopicPic(SysAccount user,Long topicId, String imagedata, Integer width, Integer height, String imgType) throws Exception;

public String addEntryPic(SysAccount user,Long id, String imagedata, Integer width, Integer height, String imgType, String title,
		String desc, Long topic,Long accountId) throws Exception ;

public String updateEntryPic(SysAccount user,Long id, String imagedata, Integer width, Integer height, String imgType, String title,
		String desc, Long topic, Long accountId) throws Exception;

public String uploadUserPic(SysAccount user, String imagedata, Integer width, Integer height, String imgType) throws Exception;

public void delRemoteFile(String imgPath);
}
