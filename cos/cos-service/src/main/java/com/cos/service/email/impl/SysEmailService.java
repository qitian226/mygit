package com.cos.service.email.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.DateTimeUtil;
import com.cos.common.utils.DateUtil;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.FansGroupMapper;
import com.cos.dao.FansMapper;
import com.cos.dao.SysEmailMapper;
import com.cos.enums.PageNumEnum;
import com.cos.exception.BusinessException;
import com.cos.model.Fans;
import com.cos.model.FansExample;
import com.cos.model.FansGroup;
import com.cos.model.FansGroupExample;
import com.cos.model.SysAccount;
import com.cos.model.SysEmail;
import com.cos.model.SysEmailExample;
import com.cos.model.SysEmailExample.Criteria;
import com.cos.service.email.ISysEmailService;
import com.cos.service.sys.ISysAccountService;

@Service @Transactional
public class SysEmailService implements ISysEmailService {

	private final  int EMAIL_PAGE_RANGE=PageNumEnum.Email.getValue();
    @Resource
	private SysEmailMapper sysEmailDao;
    @Resource
    private FansGroupMapper fansGroupDao;
    @Resource
    private FansMapper fansDao;
    @Resource
    ISysAccountService sysAccountService;

	@Override
	public List<SysEmail> queryFromUserEmails(Long formUserid,Long toUserId) {
		if(null==toUserId){
			throw new BusinessException("请登录!");
		}
		SysAccount fromAccount=sysAccountService.querySimpleAccount(formUserid);
		SysAccount toAccount=sysAccountService.querySimpleAccount(toUserId);
		SysEmailExample exa=new SysEmailExample();
		exa.createCriteria().andFromAccountEqualTo(formUserid).andToAccountEqualTo(toUserId).andStatusEqualTo((short) 0);
		exa.setFirstResult(0);
		exa.setMaxResults(EMAIL_PAGE_RANGE);
		List<SysEmail> emails=sysEmailDao.selectByExample(exa);
		List<Long> ids=new ArrayList<Long>();
		for(SysEmail email : emails){
			ids.add(email.getId());
			email.setFromAccountModel(fromAccount);
			email.setToAccountModel(toAccount);
			email.setCreateTimeString(DateTimeUtil.formatDate(email.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		exa.createCriteria().andIdIn(ids);
		sysEmailDao.updateStatusByExample(exa,(short)1);
		return emails;
	}
	@Override
	public  SysEmail sendEmail(Long toUserid, Long fromUserId,String tocken,String message) {
		 SysEmailExample exa=new SysEmailExample();
		 //带时间戳会减少全表扫描时间
		 exa.createCriteria().andTockenEqualTo(tocken);
		 Long count=sysEmailDao.countByExample(exa);
		 if(null!=count && (count.compareTo(1L)>=0)){ //防重
			 throw new BusinessException("这个邮件已经发送了!");
		 }
		 SysAccount fromAccountModel=sysAccountService.querySimpleAccount(fromUserId);
		 SysEmail email=new SysEmail();
		 email.setId(PrimaryKeyGenerator.getPrimaryKey("sysemail"));
		 email.setFromAccount(fromUserId);
     	 email.setFromAccountModel(fromAccountModel);
		 email.setToAccount(toUserid);
		 email.setMessage(message);
		 email.setStatus((short)0);
		 email.setTocken(tocken);
		 email.setCreateTimeString(DateTimeUtil.getCurrentDateString());
		 try{
		 sysEmailDao.insert(email);
		 }catch(Exception ex){
			 throw new BusinessException("发送邮件失败!", ex);
		 }
		return email;
	}
	@Override
	public Object[]  queryHistoryEmailsByPage(Long formUserid, SysAccount toUser,int page) {
		 SysEmailExample exa=new SysEmailExample();
		 exa.createCriteria().andFromAccountEqualTo(formUserid).andToAccountEqualTo(toUser.getId());
		 SysEmailExample exa1=new SysEmailExample();
		 Criteria c1=exa1.createCriteria().andFromAccountEqualTo(toUser.getId()).andToAccountEqualTo(formUserid);
		 exa.setFirstResult(EMAIL_PAGE_RANGE*(page-1));
		 exa.setMaxResults(EMAIL_PAGE_RANGE);
		 exa.or(c1);
		 exa.setOrderByClause("id_");
		 SysAccount fromAccount=sysAccountService.querySimpleAccount(formUserid);
		 List<SysEmail> emails=sysEmailDao.selectPageByExample(exa);
		 for(SysEmail email:emails){
			 email.setFromAccountModel(fromAccount);
			 email.setToAccountModel(toUser);
			 email.setCreateTimeString(DateTimeUtil.formatDate(email.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		 }
		 Long count=sysEmailDao.countByExample(exa);
		 Long nextcount=Long.parseLong(Integer.toString(EMAIL_PAGE_RANGE*page));
		 Boolean isend=false;
		 if(nextcount.compareTo(count)>=0){
			 isend=true;
		 }
		 Object[] r=new Object[]{emails,isend,page+1};
		 return r;
	}
	@Override
	public List<Fans> queryGroupFans(Long gid,Long accountId) {
		FansExample example=new FansExample();
		 example.createCriteria().andGroupIdEqualTo(gid).andAccountIdEqualTo(accountId); //查询粉丝
		 List<Fans> fans=fansDao.selectByExample(example);
		 List<Long> fansIds=new ArrayList<Long>();
		 for(Fans fan:fans){
			 fansIds.add(fan.getFansId());
		 }
		 if(null!=fansIds &&fansIds.size()>0){
			 List<SysAccount> accounts=sysAccountService.queryGroupSysAccounts(fansIds);//查询粉丝用户数据
			 for(Fans fan:fans){
				 for(SysAccount a:accounts){
					 if(fan.getFansId().compareTo(a.getId())==0){
						 fan.setFansName(a.getNickname());
						 fan.setFansAccountName(a.getAccountName());
						 fan.setAccount(a);
						 if(StringUtils.isNotBlank(a.getDesc())){
						 fan.setFansDesc(a.getDesc().length()>12?(a.getDesc().substring(0, 12)+"..."):a.getDesc());
						 }else{
							 fan.setFansDesc("");
						 }
						 break;
					 }
				 }
			 }
		 }
		return fans;
	}

	@Override
	public List<FansGroup> queryFansGroups(Long accountId) {
		FansGroupExample example=new FansGroupExample();
		example.createCriteria().andCreateByEqualTo(accountId).andIdNotEqualTo(0L);
		List<FansGroup>  groups=fansGroupDao.selectByExample(example);
		return groups;
	}

	@Override
	public List<FansGroup> queryFansAllGroups(Long accountId) {
		FansGroupExample example=new FansGroupExample();
		example.createCriteria().andCreateByEqualTo(accountId).andTypeEqualTo((short)1);
		com.cos.model.FansGroupExample.Criteria criteria=example.createCriteria();
		criteria.andTypeEqualTo((short) 0);
		example.or(criteria);
		example.setOrderByClause("id_");
		List<FansGroup>  groups=fansGroupDao.selectByExample(example);
		return groups;
	}

	@Override
	public Map<String,List<SysEmail>> queryHistoryInfos(SysAccount account) {
		Long accountId=account.getId();
		Map<String,List<SysEmail>> infos=new HashMap<String,List<SysEmail>>();
		Date nowDay=DateUtil.getNowDate();
		Date beforeDay=DateUtil.getDayBefore(DateUtil.getNowDate());
		Date monday=DateUtil.getFirstDayOfWeek();

		SysEmailExample exa=new SysEmailExample();
		SysEmailExample exa1=new SysEmailExample();
		exa.createCriteria().andCreateTimeGreaterThanOrEqualTo(nowDay).andFromAccountEqualTo(account.getId());
		exa.or(exa1.createCriteria().andCreateTimeGreaterThanOrEqualTo(nowDay).andToAccountEqualTo(account.getId()));
		List<SysEmail> emails=sysEmailDao.selectByExample(exa);
		infos.put("today", emails);

		exa=new SysEmailExample();
		exa1=new SysEmailExample();
		exa.createCriteria().andFromAccountEqualTo(account.getId()).andCreateTimeGreaterThanOrEqualTo(beforeDay).andCreateTimeLessThan(nowDay);
		exa.or(exa1.createCriteria().andToAccountEqualTo(account.getId()).andCreateTimeGreaterThanOrEqualTo(beforeDay).andCreateTimeLessThan(nowDay));
		emails=sysEmailDao.selectByExample(exa);
		infos.put("preday", emails);


		exa=new SysEmailExample();
		exa1=new SysEmailExample();
		exa.createCriteria().andFromAccountEqualTo(account.getId()).andCreateTimeGreaterThanOrEqualTo(monday).andCreateTimeLessThan(beforeDay);
		exa.or(exa1.createCriteria().andToAccountEqualTo(account.getId()).andCreateTimeGreaterThanOrEqualTo(monday).andCreateTimeLessThan(beforeDay));
		emails=sysEmailDao.selectByExample(exa);
		infos.put("weekday", emails);


		exa=new SysEmailExample();
		exa1=new SysEmailExample();
		exa.createCriteria().andFromAccountEqualTo(account.getId()).andCreateTimeLessThan(monday);
		exa.or(exa1.createCriteria().andToAccountEqualTo(account.getId()).andCreateTimeLessThan(monday));
		exa.setFirstResult(0);
		exa.setMaxResults(EMAIL_PAGE_RANGE);
		exa.setOrderByClause("id_ desc");

		emails=sysEmailDao.selectPageByExample(exa);
		infos.put("weekbefore", emails);

		if(infos.size()==0){
			return null;
		}
		Set<Long> accountIds=new HashSet<Long>();
		for(String key :infos.keySet()){
			List<SysEmail> es=infos.get(key);
			for(SysEmail e:es){
				if(e.getFromAccount().compareTo(accountId)!=0){
					accountIds.add(e.getFromAccount());
				}
				if(e.getToAccount().compareTo(accountId)!=0){
					accountIds.add(e.getToAccount());
				}
			}
		}
		if(accountIds.size()==0){
			return null;
		}
		List<SysAccount> accounts=sysAccountService.queryGroupSysAccounts(new ArrayList(accountIds));

		for(String key :infos.keySet()){
			List<SysEmail> es=infos.get(key);
			for(SysEmail e:es){
				e.setCreateTimeString(DateUtil.getDateFormat(e.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
				if(e.getFromAccount().compareTo(accountId)==0){
					 e.setFromAccountModel(account);
				}else
				{
					for(SysAccount a :accounts){
						if(a.getId().compareTo(e.getFromAccount())==0){
							 e.setFromAccountModel(a);
							 break;
						}
					}
				}

				if(e.getToAccount().compareTo(accountId)==0){
					 e.setToAccountModel(account);
				}else{
					for(SysAccount a :accounts){
						if(a.getId().compareTo(e.getToAccount())==0){
							 e.setToAccountModel(a);
							 break;
						}
					}
				}
			}
		}
		return infos;
	}

	@Override
	public List<SysEmail> queryHistoryDayInfos(int hid) {
		Date nowDay=DateUtil.getNowDate();
		Date beforeDay=DateUtil.getDayBefore(DateUtil.getNowDate());
		Date monday=DateUtil.getFirstDayOfWeek();
		 if(hid==1){ //当天
			    SysEmailExample exa=new SysEmailExample();
				exa.createCriteria().andCreateTimeGreaterThanOrEqualTo(nowDay);
				List<SysEmail> emails=sysEmailDao.selectByExample(exa);
			    return emails;
		  }
		if(hid==2){ //前天
			SysEmailExample	exa=new SysEmailExample();
			exa.createCriteria().andCreateTimeGreaterThanOrEqualTo(beforeDay).andCreateTimeLessThan(nowDay);
			List<SysEmail> emails=sysEmailDao.selectByExample(exa);
			return emails;
		 }
		if(hid==7){
			SysEmailExample exa=new SysEmailExample();
			exa.createCriteria().andCreateTimeGreaterThanOrEqualTo(monday).andCreateTimeLessThan(beforeDay);
			List<SysEmail> emails=sysEmailDao.selectByExample(exa);
			return emails;
		}
		  return null;
	}
	@Override
	public List<SysEmail> queryHistoryWeekInfos(int page) {
		Date monday=DateUtil.getFirstDayOfWeek();
		SysEmailExample  exa=new SysEmailExample();
		exa.setFirstResult(EMAIL_PAGE_RANGE*(page-1));
		exa.setMaxResults(EMAIL_PAGE_RANGE);
		exa.createCriteria().andCreateTimeLessThan(monday);
		List<SysEmail> emails=sysEmailDao.selectPageByExample(exa);
		 Long count=sysEmailDao.countByExample(exa);
		 Long nextcount=Long.parseLong(Integer.toString(EMAIL_PAGE_RANGE*page));
		 Boolean isend=false;
		 if(nextcount.compareTo(count)>=0){
			 isend=true;
		 }
		 Object[] r=new Object[]{emails,isend};
		return null;
	}
	@Override
	public String removeFansGroups(long gid) {
		 FansExample exa=new FansExample();
		 exa.createCriteria().andGroupIdEqualTo(gid);
		 Fans record=new Fans();
		 record.setGroupId(0L);//移动到未分组
		 try{
		 fansDao.updateByExampleSelective(record, exa);
		 fansGroupDao.deleteByPrimaryKey(gid);
		 }catch(Exception e){
			 throw new BusinessException("删除粉丝组失败!", e);
		 }
		return ReturnJSONUtil.getSuccessInfo("新增粉丝分组成功!");
	}
	@Override
	public String addFansGroups(String gname, Long accountId,short type) {
		 FansGroupExample exa=new FansGroupExample();
		 exa.createCriteria().andCreateByEqualTo(accountId).andNameEqualTo(gname);
		 Long count= fansGroupDao.countByExample(exa);
		 if(null!=count && count.compareTo(0L)>0){ //防重
			 return ReturnJSONUtil.getWarnInfo("repeat");
		 }
		 FansGroup group=new FansGroup();
		 Long id=PrimaryKeyGenerator.getPrimaryKey("fansgroup");
		 group.setId(id);
		 group.setType(type);//区分fans attention
		 group.setCreateBy(accountId);
		 group.setName(gname);
		 try{
		 fansGroupDao.insert(group);
		 }catch(Exception e){
			 throw new BusinessException("增加分组失败!", e);
		 }
		return ReturnJSONUtil.getSuccessExtendInfo("新增分组成功!", new JsonModel("id",id));
	}
	@Override
	public String moveToFansGroups(Long fansid, Long gid,Long accountId) {
		 FansExample exa=new FansExample();
		 exa.createCriteria().andAccountIdEqualTo(accountId).andFansIdEqualTo(fansid);
		 List<Fans> fans= fansDao.selectByExample(exa);
		 if(fans.size()>0){
			 Fans fan=fans.get(0);
			 fan.setGroupId(gid);
			 try{
			 fansDao.updateByPrimaryKey(fan);
			 }catch(Exception e){
				 throw new BusinessException("移动分组失败!", e);
			 }
		 }
		 return  ReturnJSONUtil.getSuccessInfo("移动分组成功!");
	}
	@Override
	public List<SysEmail> queryHistoryEmailsByPage(SysAccount account, int page) {
		Date monday=DateUtil.getFirstDayOfWeek();
		SysEmailExample exa=new SysEmailExample();
		exa.setFirstResult(EMAIL_PAGE_RANGE*(page-1));
		exa.setMaxResults(EMAIL_PAGE_RANGE);
		exa.setOrderByClause("id_ desc");
		exa.createCriteria().andCreateTimeLessThan(monday);
		List<SysEmail> emails=sysEmailDao.selectPageByExample(exa);

		Set<Long> accountIds=new HashSet<Long>();
		Long accountId=account.getId();
		for(SysEmail e:emails){
			if(e.getFromAccount().compareTo(accountId)!=0){
				accountIds.add(e.getFromAccount());
			}
			if(e.getToAccount().compareTo(accountId)!=0){
				accountIds.add(e.getToAccount());
			}
		}
		if(accountIds.size()==0){
			return null;
		}
		List<SysAccount> accounts=sysAccountService.queryGroupSysAccounts(new ArrayList(accountIds));
			for(SysEmail e:emails){
				e.setCreateTimeString(DateUtil.getDateFormat(e.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
				if(e.getFromAccount().compareTo(accountId)==0){
					 e.setFromAccountModel(account);
					 e.setFromAccountName(account.getNickname());
				}else
				{
					for(SysAccount a :accounts){
						if(a.getId().compareTo(e.getFromAccount())==0){
							 e.setFromAccountModel(a);
							 e.setFromAccountName(a.getNickname());
							 break;
						}
					}
				}

				if(e.getToAccount().compareTo(accountId)==0){
					 e.setToAccountModel(account);
				}else{
					for(SysAccount a :accounts){
						if(a.getId().compareTo(e.getToAccount())==0){
							 e.setToAccountModel(a);
							 e.setToAccountName(a.getNickname());
							 break;
						}
					}
				}
			}

		return  emails;
	}
	@Override
	public SysEmail querySimpleEmail(Long emailId,SysAccount account) {
		SysEmail email= sysEmailDao.selectByPrimaryKey(emailId);
		email.setCreateTimeString(DateUtil.getDateTimeFormat(email.getCreateTime()));
		if(email.getFromAccount().compareTo(account.getId())==0){
			email.setFromAccountModel(account);
			email.setFromAccountName(account.getNickname());
		}
		if(email.getToAccount().compareTo(account.getId())==0){
			email.setToAccountModel(account);
			email.setToAccountName(account.getNickname());
		}
		if(email.getFromAccount().compareTo(account.getId())!=0){
			SysAccount user=sysAccountService.querySimpleAccount(email.getFromAccount());
			email.setFromAccountModel(user);
			email.setFromAccountName(user.getNickname());
		}
		if(email.getToAccount().compareTo(account.getId())!=0){
			SysAccount user=sysAccountService.querySimpleAccount(email.getToAccount());
			email.setToAccountModel(user);
			email.setToAccountName(user.getNickname());
		}
		return email;
	}
	@Override
	public List<SysEmail> queryReciveEmails(Long accountId) {
		SysEmailExample exa=new SysEmailExample();
		exa.createCriteria().andToAccountEqualTo(accountId).andStatusEqualTo((short) 0);
		List<SysEmail> emails=sysEmailDao.selectByExample(exa);
		List<Long> ids=new ArrayList<Long>();
		for(SysEmail email : emails){
			ids.add(email.getId());
			email.setCreateTimeString(DateTimeUtil.formatDate(email.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		exa.createCriteria().andIdIn(ids);
		sysEmailDao.updateStatusByExample(exa,(short)1);
		return emails;
	}
	@Override
	public List<FansGroup> queryAttentionsAllGroups(Long accountId) {
		FansGroupExample example=new FansGroupExample();
		example.createCriteria().andCreateByEqualTo(accountId).andTypeEqualTo((short)2);
		com.cos.model.FansGroupExample.Criteria criteria=example.createCriteria();
		criteria.andTypeEqualTo((short) 0);
		example.or(criteria);
		example.setOrderByClause("id_");
		List<FansGroup>  groups=fansGroupDao.selectByExample(example);
		return groups;
	}
	@Override
	public List<Fans> queryGroupAttentions(Long gid, Long accountId) {
		FansExample example=new FansExample();
		 example.createCriteria().andGroupIdEqualTo(gid).andFansIdEqualTo(accountId); //查询粉丝
		 List<Fans> attens=fansDao.selectByExample(example);
		 List<Long> attenIds=new ArrayList<Long>();
		 for(Fans fan:attens){
			 attenIds.add(fan.getAccountId());
		 }
		 if(null!=attenIds &&attenIds.size()>0){
			 List<SysAccount> accounts=sysAccountService.queryGroupSysAccounts(attenIds);//查询粉丝用户数据
			 for(Fans fan:attens){
				 for(SysAccount a:accounts){
					 if(fan.getAccountId().compareTo(a.getId())==0){
						 fan.setName(a.getNickname());
						 fan.setAccountName(a.getAccountName());
						 fan.setAccount(a);
						 if(StringUtils.isNotBlank(a.getDesc())){
						 fan.setDesc(a.getDesc().length()>12?(a.getDesc().substring(0, 12)+"..."):a.getDesc());
						 }else{
							 fan.setDesc("");
						 }
						 break;
					 }
				 }
			 }
		 }
		return attens;
	}
	@Override
	public String moveToAttenGroups(Long uid, Long gid, Long accountId) {
		 FansExample exa=new FansExample();
		 exa.createCriteria().andAccountIdEqualTo(uid).andFansIdEqualTo(accountId);
		 List<Fans> fans= fansDao.selectByExample(exa);
		 if(fans.size()>0){
			 Fans fan=fans.get(0);
			 fan.setGroupId(gid);
			 try{
			 fansDao.updateByPrimaryKey(fan);
			 }catch(Exception e){
				 throw new BusinessException("移动分组失败!", e);
			 }
		 }
		 return  ReturnJSONUtil.getSuccessInfo("移动分组成功!");
	}
	@Override
	public String checkFans(Long uid,Long LoginId) {
		if(uid.compareTo(1000000L)==0 || uid.compareTo(2000000L)==0){
			return ReturnJSONUtil.getSuccessExtendInfo("ok", new JsonModel("info","sys"));
		}
		FansExample example=new FansExample();
		example.createCriteria().andAccountIdEqualTo(uid);
		FansExample example1=new FansExample();
		example.or(example1.createCriteria().andAccountIdEqualTo(LoginId));
		Long count= fansDao.countByExample(example);
		if(count.compareTo(0L)==0){
			return ReturnJSONUtil.getWarnInfo("你们还没有建立联系,请互相关注哦!");
		}
		return ReturnJSONUtil.getSuccessInfo("ok");
	}
}
