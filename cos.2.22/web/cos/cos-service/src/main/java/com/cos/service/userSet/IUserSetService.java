package com.cos.service.userSet;


import java.util.List;
import com.cos.model.CityCode;
import com.cos.model.ProvinceCode;
import com.cos.model.SysAccount;



public interface IUserSetService {

	List<ProvinceCode> queryProvince();

	List<CityCode> queryCityCode(Integer pAreaCode);

	String updateUserInfo(SysAccount sysAccount, List<Integer> citys);

	String updateUserImg(String imgdata, Long userid);

	String updateUserPassWord(Long userid, String mobile, String old_pass, String new_pass) throws Exception;

}
