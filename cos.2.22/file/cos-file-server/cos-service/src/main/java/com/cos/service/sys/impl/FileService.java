package com.cos.service.sys.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.common.utils.FileLocalUtils;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.ImgFileMapper;
import com.cos.exception.BusinessException;
import com.cos.model.ImgFile;
import com.cos.service.sys.IFileService;

@Service @Transactional
public class FileService implements IFileService {

	@Resource
	private ImgFileMapper imgFileDao;
	@Override
	public String addImgFile(ImgFile ifile) {
		try{
		imgFileDao.insert(ifile);
		}catch(Exception ex){
			throw new BusinessException("insert imgfile error", ex);
		}
		return ReturnJSONUtil.getSuccessInfo("图片发布成功!");
	}
	 
	@Override
	public String delImgFile(Long id) throws  Exception {
		ImgFile file=imgFileDao.selectByPrimaryKey(id);
		imgFileDao.deleteByPrimaryKey(id);
		FileLocalUtils.delFile(file.getUrl());
		return ReturnJSONUtil.getSuccessInfo("图片删除成功!");
	}

}
