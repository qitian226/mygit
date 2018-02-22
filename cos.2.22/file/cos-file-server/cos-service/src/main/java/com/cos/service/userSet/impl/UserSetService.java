package com.cos.service.userSet.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.HashCodeUtil;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.exception.BusinessException;
import com.cos.model.SysAccount;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.topicManage.IFileUploadService;
import com.cos.service.userSet.IUserSetService;

@Transactional
@Service
public class UserSetService implements IUserSetService {

	@Resource
	private ISysAccountService sysAccountService;
	@Resource
	private IFileUploadService fileUploadService;

	
	@Override
	public String updateUserImg(String imagedata, Long userid) {
		String url=null;
        try{
        SysAccount sysAccount=sysAccountService.querySimpleAccount(userid);
        url=fileUploadService.uploadUserPic(sysAccount, imagedata, 120, 120, "jpg");
        sysAccount.setImgPath(url);
		sysAccountService.updateAccount(sysAccount);
        }catch(Exception ex){
        	throw new BusinessException("上传照片失败!", ex);
        }
		return  ReturnJSONUtil.getSuccessExtendInfo("上传照片成功!",new JsonModel("url",url));
	}

	

}
