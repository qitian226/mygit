package com.cos.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csource.common.MyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.FastDfsUtil;
import com.cos.common.utils.FileUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.ImgFileMapper;
import com.cos.enums.FileTypeEnum;
import com.cos.exception.BusinessException;
import com.cos.model.ImgFile;
import com.cos.model.ImgFileExample;
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
	public List<ImgFile> queryIndexFiles() {
		ImgFileExample example=new ImgFileExample();
		example.createCriteria().andTypeEqualTo(FileTypeEnum.Index.getName());
		List<ImgFile> files=imgFileDao.selectByExample(example);
		return files;
	}
	@Override
	public ImgFile querySimpleFile(Long id) {
		ImgFile file=imgFileDao.selectByPrimaryKey(id);
		return file;
	}
	@Override
	public String delImgFile(Long id) throws MyException, Exception {
		ImgFile file=imgFileDao.selectByPrimaryKey(id);
		String url=file.getUrl();
		imgFileDao.deleteByPrimaryKey(id);
		FastDfsUtil.delFile(file.getUrl());
		return ReturnJSONUtil.getSuccessInfo("图片删除成功!");
	}

}
