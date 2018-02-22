package com.cos.common.utils;


import java.io.FileOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.cos.common.cache.load.CacheLoadPropertiesFile;
 

public class FileLocalUtils {
	private static final Log logger = LogFactory.getLog(FileLocalUtils.class);
	private final static String FILE_PATH = CacheLoadPropertiesFile.getPropertiesValue("filepath", "filepath");
	
 public static String uploadFiles(byte[] fileBytes,String fileDir,String filename,String fileExtendType) throws Exception {
	 FolderUtils.mkdirs(FILE_PATH+"/"+fileDir);
	 String filePath = fileDir +"/"+filename+"."+ fileExtendType;
	 String fullPath=FILE_PATH+"/"+filePath;
	 FileOutputStream out = new FileOutputStream(fullPath); // 新建一个文件输出器，并为它指定输出位置imgFilePath
		out.write(fileBytes); // 利用文件输出器将二进制格式decodedBytes输出
		out.close(); // 关闭文件输出器
		return  filePath;
 }
public static void delFile(String filePath) throws Exception {
	FileUtil.delete(filePath);
}
}
