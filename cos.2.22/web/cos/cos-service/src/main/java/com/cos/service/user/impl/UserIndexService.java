package com.cos.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.CollectFolderMapper;
import com.cos.dao.EntryMapper;
import com.cos.dao.FansMapper;
import com.cos.dao.SysAccountMapper;
import com.cos.dao.TopicMapper;
import com.cos.enums.PageNumEnum;
import com.cos.exception.BusinessException;
import com.cos.model.CollectFolder;
import com.cos.model.CollectFolderExample;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.Fans;
import com.cos.model.FansExample;
import com.cos.model.SysAccount;
import com.cos.model.SysAccountExample;
import com.cos.model.Topic;
import com.cos.model.TopicExample;
import com.cos.service.topic.ITopicService;
import com.cos.service.user.IUserIndexService;
@Service
@Transactional
public class UserIndexService implements IUserIndexService {
	private final  int BABY_PAGE_RANGE=PageNumEnum.Topic.getValue();
	private final  int ATTENTION_PAGE_RANGE=PageNumEnum.Atttention.getValue();
	private final  int DYNAMIC_PAGE_RANGE=PageNumEnum.Dynamic.getValue();
	@Resource
	private TopicMapper topicDao;
	@Resource
	private EntryMapper entryDao;
	@Resource
	private SysAccountMapper sysAccountDao;
	@Resource
	private ITopicService topicService;
	@Resource
	private FansMapper fansDao;
	@Resource
	private CollectFolderMapper collectFolderDao;
	/**
	 * 我的主题 置顶主题
	 */
	@Override
	public List<Topic> queryTopTopic(Long accountId,Long loginAccountId) {
		TopicExample exa=new TopicExample();
		if(null!=loginAccountId && accountId.compareTo(loginAccountId)==0){
			//登录用户查看自己
			exa.createCriteria().andStatusGreaterThan((short)0).andCreateByEqualTo(accountId).andIsTopEqualTo((short) 1);
		}else{
			exa.createCriteria().andStatusEqualTo((short)1).andCreateByEqualTo(accountId).andIsTopEqualTo((short) 1);
		}
		exa.setOrderByClause("id_ desc");

		List<Topic> tss=topicDao.selectByExample(exa);
		if(tss.size()>0){
			List<Topic> ts= topicService.initTopics(tss,236,loginAccountId);
			return ts;
			}
			return tss;
	}
	@Override
	public List<Topic> queryHotTopic(Long accountId, Long loginAccountId) {
		TopicExample example=new TopicExample();
		example.setFirstResult(0);
		example.setMaxResults(30);
		example.setOrderByClause("id_ desc");
		TopicExample.Criteria exa=example.createCriteria();

		if(null!=loginAccountId && accountId.compareTo(loginAccountId)==0){
			//登录用户查看自己
			exa.andStatusGreaterThan((short)0).andCreateByEqualTo(accountId).andIsTopEqualTo((short) 0);
		}else{
			exa.andStatusEqualTo((short)1).andCreateByEqualTo(accountId).andIsTopEqualTo((short) 0);
		}

		List<Topic> tss=topicDao.selectPageByExample(example);
		if(tss.size()>0){
			List<Topic> ts= topicService.initTopics(tss,236,loginAccountId);
			return ts;
			}
			return tss;
	}
	@Override
	public List<Topic> queryTopicPage(Long accountId, String page, String topictype, String tag,Long loginAccountId) {
		TopicExample example=new TopicExample();
		example.setFirstResult(BABY_PAGE_RANGE*(Integer.parseInt(page)-1));
		example.setMaxResults(BABY_PAGE_RANGE);
		example.setOrderByClause("id_ desc");

		TopicExample.Criteria c=example.createCriteria();
		if(null!=loginAccountId && accountId.compareTo(loginAccountId)==0){
		c.andCreateByEqualTo(accountId).andStatusGreaterThanOrEqualTo((short) 1).andIsTopEqualTo((short) 0);
		}else{
		c.andCreateByEqualTo(accountId).andStatusEqualTo((short) 1).andIsTopEqualTo((short) 0);
		}
		if(StringUtils.isNotBlank(topictype)){
			c.andTypeEqualTo(topictype);
		}
		if(StringUtils.isNotBlank(tag)){
			c.andTagsEqualTo(tag);
		}
		List<Topic> tss=topicDao.selectPageByExample(example);
		if(tss.size()>0){
		List<Topic> ts= topicService.initTopics(tss,topictype,236,loginAccountId);
		return ts;
		}
		return tss;
	}
	@Override
	public Long queryTopicTotalCount(Long accountId, String topictype, String tag,Long loginAccountId) {
		TopicExample example=new TopicExample();

		TopicExample.Criteria c=example.createCriteria();
		if(null!=loginAccountId && accountId.compareTo(loginAccountId)==0){
			c.andCreateByEqualTo(accountId).andStatusGreaterThanOrEqualTo((short) 1).andIsTopEqualTo((short) 0);
			}else{
			c.andCreateByEqualTo(accountId).andStatusEqualTo((short) 1).andIsTopEqualTo((short) 0);
			}
		if(StringUtils.isNotBlank(topictype)){
			c.andTypeEqualTo(topictype);
		}
		if(StringUtils.isNotBlank(tag)){
			c.andTypeEqualTo(topictype);
		}
		Long count=topicDao.countByExample(example);
		if(null==count){
			return 0L;
		}
		return count;
	}
	@Override
	public List<Entry> queryEntryPage(Long accountId, String page) {
	    EntryExample example=new EntryExample();
	    example.setFirstResult(BABY_PAGE_RANGE*(Integer.parseInt(page)-1));
		example.setMaxResults(BABY_PAGE_RANGE);
		example.setOrderByClause("id_ desc");

		EntryExample.Criteria c= example.createCriteria();
		c.andCreateByEqualTo(accountId).andStatusEqualTo((short) 1);
		List<Entry> tss=entryDao.selectPageByExample(example);
		if(tss.size()>0){
			List<Entry> ts= topicService.initEntrys(tss,232);
			return ts;
			}
			return tss;
	}
	@Override
	public Long queryEntryTotalCount(Long accountId) {
		EntryExample example=new EntryExample();

		example.createCriteria().andCreateByEqualTo(accountId);
		Long count=entryDao.countByExample(example);
		if(null==count){
			return 0L;
		}
		return count;
	}

	@Override
	public List<Object[]> queryAttentionAccount(Long fansId,int page,Long loginAccountId) {
		FansExample example=new FansExample();

		example.createCriteria().andFansIdEqualTo(fansId); //当前查看用户的关注用户
		example.setFirstResult(ATTENTION_PAGE_RANGE*(page-1));
		example.setMaxResults(ATTENTION_PAGE_RANGE);
		List<Fans> atoas= fansDao.selectPageByExample(example);
		List<Long> accountIds=new ArrayList<Long>();
		for(Fans a :atoas){
			accountIds.add(a.getAccountId());
		}
		SysAccountExample exa=new SysAccountExample();
		exa.createCriteria().andIdIn(accountIds);
		if(accountIds.size()==0){
			return null;
		}
		List<SysAccount> accounts= sysAccountDao.selectByExample(exa);//当前查看用户的关注用户的用户信息
		List<Topic> topics=topicDao.selectAttentionTopicByPage(accountIds, 4," id_ desc");//4 是查询数目
		List<Fans> atas=null;
		if(null!=loginAccountId){
		example=new FansExample();
		example.createCriteria().andFansIdEqualTo(loginAccountId);
		atas= fansDao.selectByExample(example); //当前登录用户的关注用户
		}
		List<Object[]> obs=new ArrayList<Object[]>();
		for(SysAccount account:accounts){ //当前查看用户的关注用户
		 List<Topic> ts=new ArrayList<Topic>();
		 int is=0;
		 for(Topic t:topics){
			if(t.getCreateBy().compareTo(account.getId())==0){
				ts.add(t);
			}
		  }
		  if(null!=loginAccountId){
			  //判断关注按钮是否显示
			  for(Fans a:atas){ //当前用户关注人和查看用户关注人相同，不再关注
				  if(account.getId().compareTo(a.getAccountId())==0){
					  is=1;
				  }
			  }
		  if(account.getId().compareTo(loginAccountId)==0){ //当前登录用户 自己不用关注
			  is=2;
		  }
		  }
		 String desc=(null==account.getDesc())?"":account.getDesc();
		 account.setDesc(desc.length()>54?desc.substring(0, 54):desc);
		 Object[] os=new Object[]{account,topicService.initTopics(ts, 360),is};
		 obs.add(os);
		}
		return  obs;
	}
	@Override
	public Long queryAttentionAccountCount(Long accountId) {
		FansExample example=new FansExample();
		example.createCriteria().andAccountIdEqualTo(accountId);
		Long count=fansDao.countByExample(example);
		return null==count?0L:count;
	}
	@Override
	public Long queryDynamicTotalCount(Long accountId) {
		TopicExample example=new TopicExample();
		example.createCriteria().andCreateByEqualTo(accountId).andStatusEqualTo((short) 1);
		Long count=topicDao.countByExample(example);
		return null==count?0L:count;
	}
	@Override
	public List<Topic> queryDynamicByPage(Long accountId, int page,Long loginAccountId) {
		 TopicExample example=new TopicExample();
		 example.setFirstResult(DYNAMIC_PAGE_RANGE*(page-1));
	     example.setMaxResults(DYNAMIC_PAGE_RANGE);
		 example.setOrderByClause("id_ desc");
		 if(null!=loginAccountId && accountId.compareTo(loginAccountId)==0){ //关注用户的动态
			 FansExample fansExa=new FansExample();
			 fansExa.createCriteria().andFansIdEqualTo(loginAccountId);
			 List<Fans> fans=fansDao.selectByExample(fansExa);
			 if(fans.size()==0){
				 return null;
			 }
			 List<Long> ids=new ArrayList<Long>();
			 for(Fans fan : fans){
				 ids.add(fan.getAccountId());
			 }
			 example.createCriteria().andCreateByIn(ids).andStatusEqualTo((short) 1);
		 }else{
			 example.createCriteria().andCreateByEqualTo(accountId).andStatusEqualTo((short) 1);
		 }
		 List<Topic> tss=topicDao.selectPageByExample(example);
		 if(tss.size()>0){
			List<Topic> ts= topicService.initDynamicTopics(tss);
			return ts;
			}
			return tss;
	}
	@Override
	public List<Topic> queryDynamicByPage(Long accountId, int page) {
		 TopicExample example=new TopicExample();
		 example.setFirstResult(DYNAMIC_PAGE_RANGE*(page-1));
	     example.setMaxResults(DYNAMIC_PAGE_RANGE);
		 example.setOrderByClause("id_ desc");
	     example.createCriteria().andCreateByEqualTo(accountId).andStatusEqualTo((short) 1);

		 List<Topic> tss=topicDao.selectPageByExample(example);
		 if(tss.size()>0){
			List<Topic> ts= topicService.initDynamicTopics(tss);
			return ts;
			}
			return tss;
	}
	@Override
	public List<Topic> queryCollectTopics(long accountId, String folderType) {
		List<Long> topicIds= collectFolderDao.selectCollectTopicByType(folderType, accountId);

		TopicExample example=new TopicExample();
		example.createCriteria().andIdIn(topicIds);
		example.setOrderByClause("id_ desc");
		List<Topic> tss=topicDao.selectByExample(example);
		tss=topicService.initTopics(tss, 230);
		return tss;
	}
	@Override
	public List<String> queryCollectTypes(Long accountId) {
		List<String> types=collectFolderDao.selectCollectType(accountId);
		return types;
	}
	@Override
	public String attentionAccount(Long attentionId, Long accountId) {
		if(attentionId.compareTo(accountId)==0){
			throw new BusinessException("用户不能关注自己!");
		}
		FansExample example=new FansExample();
		example.createCriteria().andAccountIdEqualTo(attentionId).andFansIdEqualTo(accountId);
		Long count= fansDao.countByExample(example);
		if(count.compareTo(1L)>0){  //防重
			return  ReturnJSONUtil.getWarnInfo("此用户已经关注!");
		}

        Fans fan=new Fans();
        fan.setId(PrimaryKeyGenerator.getPrimaryKey("fans"));
        fan.setAccountId(attentionId);
        fan.setFansId(accountId);
        fan.setGroupId(0L);
        fan.setCreateBy(accountId);
        //被关注fans加一
        SysAccount a_account=sysAccountDao.selectByPrimaryKey(attentionId);
		int fansnum=(null==a_account.getFans()?0:a_account.getFans());
		a_account.setFans(fansnum+1);

		//关注用户加一
        SysAccount l_account=sysAccountDao.selectByPrimaryKey(accountId);
		int attentionnum=(null==l_account.getAttention()?0:l_account.getAttention());
		l_account.setAttention(attentionnum+1);

		try{
        fansDao.insert(fan);
        sysAccountDao.updateByPrimaryKeySelective(a_account);
        sysAccountDao.updateByPrimaryKeySelective(l_account);
		}catch(Exception ex){
			throw new BusinessException("添加粉丝错误!", ex);
		}
		return ReturnJSONUtil.getSuccessInfo("关注用户成功!");
	}
	@Override
	public String cancelAttentionAccount(Long attentionId, Long accountId) {
		FansExample exa=new FansExample();
		exa.createCriteria().andFansIdEqualTo(accountId).andAccountIdEqualTo(attentionId);
		List<Fans> atas=fansDao.selectByExample(exa);
		if(atas.size()>0){
			 Long id=atas.get(0).getId();
			 SysAccount a_account=sysAccountDao.selectByPrimaryKey(attentionId); //被关注人
			 int fans=(null==a_account.getFans()?0:a_account.getFans());
			 if(fans>0){
			 a_account.setFans(fans-1);
			 }
			 SysAccount l_account=sysAccountDao.selectByPrimaryKey(accountId);
			 int attention=(null==l_account.getAttention()?0:l_account.getAttention());
			 if(attention>0){
			 l_account.setAttention(attention-1);
			 }
			 try{
				 fansDao.deleteByPrimaryKey(id);
				 sysAccountDao.updateByPrimaryKeySelective(a_account);
				 sysAccountDao.updateByPrimaryKeySelective(l_account);
			 }catch(Exception ex){
				 throw new BusinessException("删除粉丝错误!", ex);
			 }
			 return ReturnJSONUtil.getSuccessInfo("取消关注用户成功!");
		}
		return ReturnJSONUtil.getWarnInfo("取消关注用户失败!");
	}
	@Override
	public String collectTopic(Long topicId, Long accountId) {
		if(null==accountId){
			return  ReturnJSONUtil.getWarnInfo("登录用户不能为空!");
		}
		CollectFolderExample exa=new CollectFolderExample();
		exa.createCriteria().andCreateByEqualTo(accountId).andTopicIdEqualTo(topicId);
		Long count=collectFolderDao.countByExample(exa);
		if(null!=count && count>=1){ //防重
			return ReturnJSONUtil.getWarnInfo("当前主题已经收藏!");
		}
		Topic topic=topicDao.selectByPrimaryKey(topicId);
		if(null==topic){
			throw new BusinessException("没有这个主题");
		}
		CollectFolder record=new CollectFolder();
		record.setId(PrimaryKeyGenerator.getPrimaryKey("collect_topic"));
		record.setTopicId(topic.getId());
		record.setFolderType(topic.getType());
		record.setCreateBy(accountId);
		topic.setCollect(null==topic.getCollect()?1L:topic.getCollect()+1L);
		try{
			collectFolderDao.insert(record);
			topicDao.updateByPrimaryKey(topic);
		}catch(Exception ex){
			throw new BusinessException("收藏主题失败!",ex);
		}
		return ReturnJSONUtil.getSuccessExtendInfo("收藏主题成功!",new JsonModel("num",topic.getCollect()));
	}
	@Override
	public String cancelCollect(Long topicId, Long accountId) {
		if(null==accountId){
			return  ReturnJSONUtil.getWarnInfo("登录用户不能为空!");
		}
		Topic topic=topicDao.selectByPrimaryKey(topicId);
		if(null==topic){
			throw new BusinessException("没有这个主题");
		}
		CollectFolderExample exa=new CollectFolderExample();
		exa.createCriteria().andCreateByEqualTo(accountId).andTopicIdEqualTo(topicId);
		Long count=collectFolderDao.countByExample(exa);
		if(null==count || count==0){ //防重
			return ReturnJSONUtil.getWarnInfo("当前主题已经取消关注!");
		}
		topic.setCollect(topic.getCollect()-1L);
		try{
			collectFolderDao.deleteByExample(exa);
			topicDao.updateByPrimaryKey(topic);
		}catch(Exception ex){
			throw new BusinessException("取消收藏主题失败!",ex);
		}
		return ReturnJSONUtil.getSuccessExtendInfo("取消收藏主题成功!",new JsonModel("num",topic.getCollect()));
	}
	@Override
	public List<Topic> queryTopicCarousel(String uname) {
		SysAccountExample exa=new SysAccountExample();
		exa.createCriteria().andAccountNameEqualTo(uname);
		List<SysAccount> accounts=sysAccountDao.selectByExample(exa);
		if(accounts.size()>0){
		Long accountId=	accounts.get(0).getId();
		TopicExample example=new TopicExample();
		example.setFirstResult(0);
		example.setMaxResults(15);
		example.createCriteria().andCreateByEqualTo(accountId).andIsCarouselEqualTo((short)1).andStatusEqualTo((short)1);
		List<Topic> ts= topicDao.selectPageByExample(example);
		List<Topic> tts=new ArrayList<Topic>();
		for(Topic t:ts){
			Topic nt=new Topic();
			nt.setId(t.getId());
			nt.setTitle(t.getTitle());
			nt.setClassify(t.getClassify());
			tts.add(nt);
		 }
		return tts;
		}
		return null;
	}
	@Override
	public List<Entry> queryEntryCarousel(Long accountId) {
		 EntryExample exa=new EntryExample();
		 exa.setFirstResult(0);
		 exa.setMaxResults(15);
		 exa.createCriteria().andCreateByEqualTo(accountId).andIsCarouselEqualTo((short)1).andStatusEqualTo((short)1);
		 List<Entry> entrys= entryDao.selectPageByExample(exa);
		return entrys;
	}
	@Override
	public List<Object[]> queryFansAccount(Long accountId, int page, Long loginAccountId) {
		FansExample example=new FansExample();
		example.createCriteria().andAccountIdEqualTo(accountId); //查看用户的所有fans
		example.setFirstResult(ATTENTION_PAGE_RANGE*(page-1));
		example.setMaxResults(ATTENTION_PAGE_RANGE);
		List<Fans> atoas= fansDao.selectPageByExample(example);
		List<Long> accountIds=new ArrayList<Long>();
		for(Fans a :atoas){
			accountIds.add(a.getFansId());
		}
		SysAccountExample exa=new SysAccountExample();
		exa.createCriteria().andIdIn(accountIds);
		if(accountIds.size()==0){
			return null;
		}
		List<SysAccount> fans= sysAccountDao.selectByExample(exa);//查看所有fans 用户信息

		List<Fans> atas=null;
		if(null!=loginAccountId){
		example=new FansExample();
		example.createCriteria().andFansIdEqualTo(loginAccountId);
		atas= fansDao.selectByExample(example); //去重， 当前登录用户关注的用户
		}
		List<Object[]> obs=new ArrayList<Object[]>();
		for(SysAccount fan:fans){ //遍历fans
		 int is=0; //可以关注

		  if(null!=loginAccountId){
			  //判断关注按钮是否显示
			  for(Fans a:atas){ //当前fans和当前用户关注人相同，不再关注
				  if(fan.getId().compareTo(a.getAccountId())==0){
					  is=1;
				  }
			  }
		  if(fan.getId().compareTo(loginAccountId)==0){ //当前登录用户 自己不用关注
			  is=2;
		  }
		  }
		 String desc=(null==fan.getDesc())?"":fan.getDesc();
		 fan.setDesc(desc.length()>50?desc.substring(0, 50):desc);
		 Object[] os=new Object[]{fan,is};
		 obs.add(os);
		}
		return  obs;
	}
	@Override
	public List<Topic> queryIndexCollect(Long accountId) {
		CollectFolderExample  exa=new CollectFolderExample();
		exa.setFirstResult(0);
		exa.setMaxResults(20);
		exa.setOrderByClause("id_ desc");
		exa.createCriteria().andCreateByEqualTo(accountId);
        List<Long> cids= collectFolderDao.selectCollectTopicByPage(exa);

		TopicExample example=new TopicExample();
		example.createCriteria().andIdIn(cids);
        if(cids.size()>0){
        	List<Topic> tss=topicDao.selectByExample(example);
        	for(Topic t:tss) {
        		if(StringUtils.isNotBlank(t.getImgUrl())){
         			String[] urls=t.getImgUrl().split(";");
         			if(urls.length==2) {
         				t.setImgUrl(urls[0]);
         				t.setZoominUrl(urls[1]);
         			}
         		}
        	}
    		return tss;
        }
		return null;
	}
	@Override
	public Long queryFansTotalCount(Long accountId) {
		FansExample exa=new FansExample();
		exa.createCriteria().andAccountIdEqualTo(accountId);
		Long count=fansDao.countByExample(exa);
		return count;
	}


}
