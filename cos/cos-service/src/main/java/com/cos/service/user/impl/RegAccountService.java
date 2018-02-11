
package com.cos.service.user.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.DateTimeUtil;
import com.cos.common.utils.HashCodeUtil;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.common.utils.SendMailUtil;
import com.cos.dao.SysAccountMapper;
import com.cos.enums.SiteUserTypeEnum;
import com.cos.exception.BusinessException;
import com.cos.exception.ServiceException;
import com.cos.model.SysAccount;
import com.cos.model.SysAccountExample;
import com.cos.service.user.IRegAccountService;



/**
 * TODO What the class does
 * @author qikunlun
 * @date   2015年11月18日-下午5:48:22
 *
 */
@Service @Transactional
public class RegAccountService implements IRegAccountService{
	@Resource
     private SysAccountMapper sysAccountDao;

	/* (non-Javadoc)
	 * @see com.pyfriend.portal.site.service.IRegAccountService#addAccount(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String regAccount(String reguser, String password, String mobile,String nickname,String usertype) {
		SysAccountExample exa=new SysAccountExample();
		exa.createCriteria().andMobileEqualTo(mobile);
    	Long count=sysAccountDao.countByExample(exa);
        //验证用户是否存在
        if(null!=count&&count.compareTo(1L)>=0) {
        	    return ReturnJSONUtil.getWarnInfo("此手机号码已经被注册!");
        }

        exa=new SysAccountExample();
        exa.createCriteria().andAccountNameEqualTo(reguser);
        count=sysAccountDao.countByExample(exa);
        //验证用户是否存在
        if(count>0) {
        	   return ReturnJSONUtil.getWarnInfo("此用户已经被注册!");
        }
        exa=new SysAccountExample();
        exa.createCriteria().andNicknameEqualTo(nickname);
        count=sysAccountDao.countByExample(exa);
        //验证昵称是否存在
        if(count>0) {
        	     return ReturnJSONUtil.getWarnInfo("此昵称已经被使用!");
        }
		SysAccount t=new SysAccount();
		try {
			t.setId(PrimaryKeyGenerator.getPrimaryKey("sysaccount"));
			t.setAccountName(reguser);
			t.setNickname(nickname);
			String real_pass=HashCodeUtil.md5Encode(password+mobile);//组合密码
			t.setPassword(HashCodeUtil.createHash(real_pass));//密码存放的是校验值 实际密码未存储
			t.setType(SiteUserTypeEnum.getName(4));
			t.setStatus((short) 1);
			t.setIslocked("no");
			t.setMobile(mobile);
			t.setSex((short)1);
			t.setCreatedTime(DateTimeUtil.GetCurrentDate());
			t.setLastUpdatedTime(DateTimeUtil.GetCurrentDate());
			t.setCurrVersion((short)1);
			t.setTopicCount(0);
			t.setEntryCount(0);
			t.setAttention(0);
			t.setFans(0);
			t.setType(usertype);
			t.setVip((short)0);
            t.setLevel((short) 0);
			sysAccountDao.insert(t);
		} catch (Exception e) {
		    throw new ServiceException("注册用户失败",e);
		}
		return ReturnJSONUtil.getWarnExtendInfo("注册用户成功!", new JsonModel("uid",t.getId()));
	}

    private void sendEmail(SysAccount user,String siteurl) throws MessagingException{
    	///邮件的内容
        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
        sb.append("<a href=\""+siteurl+"/activate?email=");
        sb.append(user.getEmail());
        sb.append("&validatecode=");
        sb.append(user.getValidatecode());
        sb.append("\">激活最美注册用户</a>");
        //发送邮件
        SendMailUtil.send(user.getEmail(), sb.toString());
    }
    /**
     * 处理激活
     * @throws ServiceException
     */
      ///传递激活码和email过来
    public void regAccountActivate(String email , String validateCode)throws ServiceException{
         //数据访问层，通过email获取用户信息
    	SysAccountExample exa=new SysAccountExample();
    	exa.createCriteria().andEmailEqualTo(email);
    	List<SysAccount> users=sysAccountDao.selectByExample(exa);

        //验证用户是否存在
        if(users.size()>0) {
        	SysAccount user=users.get(0);
            //验证用户激活状态
            if(user.getStatus()==0) {
                ///没激活
                Date currentTime = new Date();//获取当前时间
                //验证链接是否过期
                if(currentTime.before(user.getCreatedTime())) {
                    //验证激活码是否正确
                    if(validateCode.equals(user.getValidatecode())) {
                        //激活成功， //并更新用户的激活状态，为已激活
                        user.setStatus((short) 0);//把状态改为激活
                        user.setLastUpdatedTime(DateTimeUtil.GetCurrentDate());
                        sysAccountDao.updateByPrimaryKey(user);
                    } else {
                       throw new ServiceException("激活码不正确");
                    }
                } else { throw new ServiceException("激活码已过期！");
                }
            } else {
               throw new ServiceException("邮箱已激活，请登录！");
            }
        } else {
            throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
        }

    }

	/* (non-Javadoc)
	 * @see com.pyfriend.portal.user.service.IRegAccountService#checkLoginUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SysAccount checkLoginUser(String mobile, String password) throws Exception {
		SysAccountExample exa=new SysAccountExample();
		exa.createCriteria().andMobileEqualTo(mobile);
		List<SysAccount> accounts= sysAccountDao.selectByExample(exa);

		if(accounts.size()==0){
			throw new BusinessException("帐号或密码错误!") ;
		}
		if(accounts.size()==1 && (accounts.get(0).getIslocked().equals("is"))){
			throw new BusinessException("帐号被锁定!") ;
		}
		if(accounts.size()==1 && (accounts.get(0).getStatus()==0)){
			throw new BusinessException("帐号已经失效!") ;
		}
		SysAccount member=accounts.get(0);

		String pass=member.getPassword();//hash值
	    String privatekey=HashCodeUtil.md5Encode(password+mobile);//私钥
		if(!HashCodeUtil.validatePassword(privatekey, pass)){ //校验hash散列
			throw new BusinessException("帐号或密码错误!") ;
		}
		return  member;
	}
}
