
package com.cos.service.user;


import com.cos.model.SysAccount;



/**
 * TODO What the class does
 * @author qikunlun
 * @date   2015年11月18日-下午5:47:39
 *
 */
public interface IRegAccountService {

	/**
	 * @param reguser
	 * @param regpass
	 * @param regemail
	 * @return
	 */
	String regAccount(String reguser, String password, String mobile,String nickname,String usertype);

	/**
	 * @param regemail
	 * @param regpass
	 * @param regcode
	 * @return
	 */
	SysAccount checkLoginUser(String mobile, String password) throws Exception;

}
