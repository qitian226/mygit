package com.cos.service.sys;

import java.util.List;
import java.util.Map;

import com.cos.exception.ServiceException;
import com.cos.model.SysAccount;

public interface ISysAccountService {
	/**
	 * 
	 * @param regemail
	 * @param md5pass
	 * @param pwd
	 * @param regcode
	 * @return
	 * @throws BusinessException
	 */
	SysAccount checkLoginUser(String regemail, String md5pass,String pwd, String regcode) throws ServiceException;

	SysAccount getAccount(Long accountId);

	Long getTotalCountNum();

	String[] queryOrgAndPost(String orgId, String postId);

	String addAccount(SysAccount sysAccount);

	String checkUpdateRule(String id, String accountName);

	String checkRule(String accountName);

	String delAccount(String id);

	String updateAccount(SysAccount sysAccount);

	Long queryExistAccount(String where);

	String saveAccountsPost(String accountIds, String postId);

	SysAccount querySimpleAccount(Long accontId);

	SysAccount querySimpleAccountByname(String username);
	
	List<SysAccount> queryGroupSysAccounts(List<Long> accountIds);

	List<SysAccount> querySysUserByPage(int page, int limit);

	String lockUser(String ids);
	
	String unLockUser(String ids);

	List<Map<String,Object>> searchSysUserByPage(String word, int page, int limit);

	Long getSearchTotalCountNum(String word);


	 
	
}
