package com.cos.service.sys;

import java.util.List;
import com.cos.model.ImgFile;

public interface IFileService {

	String addImgFile(ImgFile ifile);

	List<ImgFile> queryIndexFiles();

	ImgFile querySimpleFile(Long id);

	String delImgFile(Long id) throws  Exception;

}
