package com.cos.service.sys;

import com.cos.model.ImgFile;

public interface IFileService {

	String addImgFile(ImgFile ifile);

	String delImgFile(Long id) throws  Exception;

}
