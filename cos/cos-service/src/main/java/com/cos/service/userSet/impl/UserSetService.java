package com.cos.service.userSet.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.HashCodeUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.CityCodeMapper;
import com.cos.dao.ProvinceCodeMapper;
import com.cos.exception.BusinessException;
import com.cos.model.CityCode;
import com.cos.model.CityCodeExample;
import com.cos.model.ProvinceCode;
import com.cos.model.ProvinceCodeExample;
import com.cos.model.SysAccount;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.topicManage.IFileUploadService;
import com.cos.service.userSet.IUserSetService;

@Transactional
@Service
public class UserSetService implements IUserSetService {

	@Resource
	private ProvinceCodeMapper provinceCodeDao;
	@Resource
	private CityCodeMapper  cityCodeDao;
	@Resource
	private ISysAccountService sysAccountService;

	@Resource
	private IFileUploadService fileUploadService;

	@Override
	public List<ProvinceCode> queryProvince() {
		ProvinceCodeExample example=new ProvinceCodeExample();
		example.createCriteria().andCityCodeIsNotNull();
		List<ProvinceCode> codes=provinceCodeDao.selectByExample(example);
		return codes;
	}

	@Override
	public List<CityCode> queryCityCode(Integer pAreaCode) {
		CityCodeExample example=new CityCodeExample();
		example.createCriteria().andPAreaCodeEqualTo(pAreaCode);
		List<CityCode>  codes=cityCodeDao.selectByExample(example);
		return codes;
	}

	@Override
	public String updateUserInfo(SysAccount sysAccount, List<Integer> citys) {
		CityCodeExample exa=new CityCodeExample();
		exa.createCriteria().andAreaCodeIn(citys);
		List<CityCode> codes=cityCodeDao.selectByExample(exa);
		if(codes.size()==2){
			sysAccount.setProviceName(codes.get(0).getAreaName());
			sysAccount.setCityName(codes.get(1).getAreaName());
		}
		if(codes.size()==3){
			sysAccount.setProviceName(codes.get(0).getAreaName());
			sysAccount.setCityName(codes.get(1).getAreaName());
			sysAccount.setAreaName(codes.get(2).getAreaName());
		}
        try{
       String out= sysAccountService.updateAccount(sysAccount);
       return out;
        }catch(Exception ex){
        	throw new BusinessException("保存用户信息失败!", ex);
        }
	}

	@Override
	public String updateUserImg(String imagedata, Long userid) {
        try{
        SysAccount sysAccount=sysAccountService.querySimpleAccount(userid);
        if(StringUtils.isNotBlank(sysAccount.getImgPath())) {
        	fileUploadService.delRemoteFile(sysAccount.getImgPath());
        }
        String url=fileUploadService.uploadUserPic(userid, imagedata, 120, 120, "jpg");
        sysAccount.setImgPath(url);
		sysAccountService.updateAccount(sysAccount);
        }catch(Exception ex){
        	throw new BusinessException("上传照片失败!", ex);
        }
		return  ReturnJSONUtil.getSuccessInfo("上传照片成功!");
	}

	@Override
	public String updateUserPassWord(Long userid, String mobile, String old_pass, String new_pass) throws Exception {
		 SysAccount sysAccount=sysAccountService.querySimpleAccount(userid);
		 if(null==sysAccount){
			 return ReturnJSONUtil.getWarnInfo("无此用户!");
		 }
			String pass=sysAccount.getPassword();//hash值
		 String privatekey=HashCodeUtil.md5Encode(old_pass+mobile);//私钥
			if(!HashCodeUtil.validatePassword(privatekey, pass)){ //校验hash散列
				throw new BusinessException("原密码错误!") ;
			}
			privatekey=HashCodeUtil.md5Encode(new_pass+mobile);
			sysAccount.setPassword(HashCodeUtil.createHash(privatekey));
			sysAccountService.updateAccount(sysAccount);
		return ReturnJSONUtil.getSuccessInfo("修改密码成功!");
	}

}
