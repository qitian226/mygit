package com.cos.common.utils;


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

 

public class FastDfsUtil {
	private static final Log logger = LogFactory.getLog(FastDfsUtil.class);
	static {
		 ClassPathResource resource=new ClassPathResource("fdfs_client.conf");
		  try {
			ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		} catch (IOException | MyException e) {
			logger.error(e.getMessage());
		}
	}
 public static String uploadFiles(byte[] fileBytes,String fileExtendType) throws Exception, MyException {
      String file_id;
      TrackerClient tracker = new TrackerClient();
      TrackerServer trackerServer = tracker.getConnection();
      StorageServer storageServer = null;
      StorageClient1 client = new StorageClient1(trackerServer, storageServer);
      NameValuePair[] meta_list;
      meta_list = new NameValuePair[1];
      meta_list[0] = new NameValuePair("size",Integer.toString(fileBytes.length));
      file_id = client.upload_file1(fileBytes, fileExtendType, meta_list);
	return file_id;
 }
public static void delFile(String file_id) throws Exception, MyException {
	TrackerClient tracker = new TrackerClient();
    TrackerServer trackerServer = tracker.getConnection();
    StorageServer storageServer = null;
    StorageClient1 client = new StorageClient1(trackerServer, storageServer);
    client.delete_file1(file_id);	
}  
}
