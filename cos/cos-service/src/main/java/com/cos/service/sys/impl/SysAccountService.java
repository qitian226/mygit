package com.cos.service.sys.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.HashCodeUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.SysAccountMapper;
import com.cos.exception.BusinessException;
import com.cos.exception.ServiceException;
import com.cos.model.SysAccount;
import com.cos.model.SysAccountExample;
import com.cos.service.sys.ISysAccountService;

@Service
@Transactional
public class SysAccountService implements ISysAccountService {
	private static final Log logger = LogFactory.getLog(SysAccountService.class);
	@Resource
	private SysAccountMapper sysAccountDao;
	private final String DEFAULT_PASSWORD = "bunny";

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public SysAccount checkLoginUser(String regemail, String md5pass, String pwd, String regcode)
			throws ServiceException {
		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andEmailEqualTo(regemail).andStatusEqualTo((short) 0);
		List<SysAccount> accounts = sysAccountDao.selectByExample(example);
		if (accounts.size() == 0) {
			throw new ServiceException("帐号密码不匹配!");
		}
		SysAccount member = accounts.get(0);
		String email = member.getEmail();
		String pass = member.getPassword();// hash值

		String localMd5pass = null;
		String privatekey = null;
		try {
			localMd5pass = HashCodeUtil.md5Encode(pwd + regcode);
			privatekey = HashCodeUtil.md5Encode(pwd + email);// 私钥
		} catch (Exception e) {
			throw new ServiceException("登录异常!",e);
		} // 校验链路安全
		if (!localMd5pass.equals(md5pass)) {
			throw new ServiceException("非法登录!");
		}
		try {
			if (!HashCodeUtil.validatePassword(privatekey, pass)) { // 校验hash散列
				throw new ServiceException("帐号密码不匹配!");
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new ServiceException("登录异常!",e);
		}

		return member;
	}

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public SysAccount getAccount(Long accountId) {
		SysAccount account = sysAccountDao.selectByPrimaryKey(accountId);
		if (null == account) {
			throw new BusinessException("无此用户!");// 非法登录
		}
		return account;
	}

	@Override
	public Long getTotalCountNum() {
		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andIdIsNotNull();
		Long count = sysAccountDao.countByExample(example);
		return count;
	}

	@Override
	public String addAccount(SysAccount sysAccount) {
		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andEmailEqualTo(sysAccount.getEmail());
		Long count = sysAccountDao.countByExample(example);
		// 验证用户是否存在
		if (null != count && count.compareTo(1L) > 0) {
			return  ReturnJSONUtil.getWarnInfo("此邮箱已经被注册!");
		}
		example = new SysAccountExample();
		example.createCriteria().andAccountNameEqualTo(sysAccount.getAccountName());
		count = sysAccountDao.countByExample(example);

		// 验证用户是否存在
		if (count > 0) {
			return  ReturnJSONUtil.getWarnInfo("此用户已经被注册!");
		}
		try {
			String privatekey = HashCodeUtil
					.md5Encode(HashCodeUtil.md5Encode(DEFAULT_PASSWORD) + sysAccount.getEmail());
			sysAccount.setPassword(HashCodeUtil.createHash(privatekey));
			sysAccountDao.insert(sysAccount);
		} catch (Exception e) {
			throw new ServiceException("用户添加失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("用户添加成功!");
	}

	@Override
	public String checkUpdateRule(String id, String accountName) {
		String out = "";
		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andIdNotEqualTo(Long.parseLong(id)).andAccountNameEqualTo(accountName);
		Long count = sysAccountDao.countByExample(example);
		// 验证用户是否存在
		if (null != count && count.compareTo(1L) > 0) {
			out += "此用户名称已经存在!";
		}
		return out;
	}

	@Override
	public String checkRule(String accountName) {
		String out = "";
		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andAccountNameEqualTo(accountName);
		Long count = sysAccountDao.countByExample(example);
		// 验证用户是否存在
		if (null != count && count.compareTo(1L) > 0) {
			out += "此用户名称已经存在!";
		}
		return out;
	}

	@Override
	public String delAccount(String id) {
		try {
			sysAccountDao.deleteByPrimaryKey(Long.parseLong(id));
		} catch (Exception e) {
			throw new ServiceException("用户删除失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("用户删除成功!");
	}

	@Override
	public String updateAccount(SysAccount sysAccount) {
		try {
			sysAccountDao.updateByPrimaryKeySelective(sysAccount);
		} catch (Exception e) {
			throw new ServiceException("修改会员资料失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("保存会员资料成功!");
	}

	@Override
	public Long queryExistAccount(String where) {
		SysAccountExample example = new SysAccountExample();
		/*
		 * com.hw.bunny.base.domain.model.SysAccountExample.Criteria c =
		 * example.createCriteria(); if (where != null && where != "") {
		 * String[] infos = where.split(","); if (!infos[0].equals("-1")) {
		 * c.andOrganizationIdEqualTo(Long.parseLong(infos[0])); } if
		 * (!infos[1].equals("-1")) {
		 * c.andPostIdEqualTo(Long.parseLong(infos[1])); } if
		 * (!infos[2].equals("-1")) { c.andAccountNameLike("%" + infos[2] +
		 * "%"); } }
		 */
		Long count = sysAccountDao.countByExample(example);
		return count;
	}

	@Override
	public String saveAccountsPost(String accountIds, String postId) {
		SysAccount account = new SysAccount();
		account.setPostId(Long.parseLong(postId));
		List<Long> aids = new ArrayList<Long>();
		for (String id : accountIds.split("\\|")) {
			aids.add(Long.parseLong(id));
		}

		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andIdIn(aids);
		sysAccountDao.updateByExampleSelective(account, example);
		return ReturnJSONUtil.getSuccessInfo("设置用户角色成功!");
	}

	@Override
	public String[] queryOrgAndPost(String orgId, String postId) {

		return null;
	}

	@Override
	public SysAccount querySimpleAccount(Long id) {
		SysAccount account = sysAccountDao.selectByPrimaryKey(id);
		return account;
	}

	@Override
	public SysAccount querySimpleAccountByname(String username) {
		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andAccountNameEqualTo(username);
		List<SysAccount> accounts = sysAccountDao.selectByExample(example);
		if (accounts.size() > 0) {
			return accounts.get(0);
		}
		throw new BusinessException("没有这个用户!");
	}

	@Override
	public List<SysAccount> queryGroupSysAccounts(List<Long> accountIds) {
		SysAccountExample example = new SysAccountExample();
		example.createCriteria().andIdIn(accountIds);
		List<SysAccount> accounts = sysAccountDao.selectByExample(example);
		return accounts;
	}

	@Override
	public List<SysAccount> querySysUserByPage(int page, int limit) {
		SysAccountExample example = new SysAccountExample();
		example.setDistinct(true);
		example.setFirstResult((page - 1) * limit);
		example.setMaxResults(limit);
		List<SysAccount> sysAccounts = sysAccountDao.selectPageByExample(example);
		return sysAccounts;
	}

	@Override
	public String lockUser(String ids) {
		String[] idss = ids.split(",");
		if (idss.length > 0) {
			List<Long> accountIds = new ArrayList<Long>();
			for (String id : idss) {
				accountIds.add(Long.parseLong(id));
			}
			sysAccountDao.updateLockByIds("is", accountIds);
		}
		return  ReturnJSONUtil.getSuccessInfo("用户锁定成功!");
	}

	@Override
	public List<Map<String, Object>> searchSysUserByPage(String word, int page, int limit) {
		List<Map<String, Object>> maps = sysAccountDao.searchAccountByPage(word, (page - 1) * limit, limit);
		return maps;
	}

	@Override
	public Long getSearchTotalCountNum(String word) {
		Long count = sysAccountDao.searchAccountTotalCount(word);
		return count;
	}

	@Override
	public String unLockUser(String ids) {
		String[] idss = ids.split(",");
		if (idss.length > 0) {
			List<Long> accountIds = new ArrayList<Long>();
			for (String id : idss) {
				accountIds.add(Long.parseLong(id));
			}
			sysAccountDao.updateLockByIds("no", accountIds);
		}
		return  ReturnJSONUtil.getSuccessInfo("用户解锁成功!");
	}

}
