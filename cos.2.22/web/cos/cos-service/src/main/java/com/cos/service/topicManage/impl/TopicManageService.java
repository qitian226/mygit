package com.cos.service.topicManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.DateTimeUtil;
import com.cos.common.utils.DateUtil;
import com.cos.common.utils.JSONUtil;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.EntryMapper;
import com.cos.dao.TopicMapper;
import com.cos.exception.BusinessException;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.Topic;
import com.cos.model.TopicExample;
import com.cos.model.TopicExample.Criteria;
import com.cos.service.topic.ITopicService;
import com.cos.service.topicManage.ITopicManageService;
import com.google.gson.internal.LinkedTreeMap;

@Service
@Transactional
public class TopicManageService implements ITopicManageService {
    private final  int BABY_PAGE_RANGE=12;
    @Resource
	private TopicMapper topicDao;
    @Resource
  	private EntryMapper entryDao;
    @Resource
    private ITopicService topicService;
	@Override
	public List<Topic> queryTopicMeta(Integer page, String params) {
		TopicExample example=new TopicExample();
		example.setFirstResult(BABY_PAGE_RANGE*(page-1));
		example.setMaxResults(BABY_PAGE_RANGE);
		example.setOrderByClause("id_ desc");
		Criteria criteria=example.createCriteria();

		List<LinkedTreeMap> ps= JSONUtil.parseJsonArrayWithGson(params, LinkedTreeMap.class);
		for(LinkedTreeMap map :ps){
			if(map.get("name").equals("title_")){
				criteria.andTitleLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("from_url_")){
				criteria.andFromUrlLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("img_url_")){
				criteria.andImgUrlLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("desc_")){
				criteria.andDescLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("classify_")){
				criteria.andClassifyEqualTo(map.get("value").toString());
				continue;
			}
			if(map.get("name").equals("type_")){
				criteria.andTypeEqualTo(map.get("value").toString());
				continue;
			}
			if(map.get("name").equals("tags_")){
				criteria.andTagsEqualTo(map.get("value").toString());
				continue;
			}
			if(map.get("name").equals("is_carousel_")){
				criteria.andIsCarouselEqualTo(Short.parseShort(map.get("value").toString()));
				continue;
			}
			if(map.get("name").equals("is_top_")){
				criteria.andIsTopEqualTo(Short.parseShort(map.get("value").toString()));
				continue;
			}
			if(map.get("name").equals("grade_")){
				criteria.andGradeGreaterThanOrEqualTo(Integer.parseInt(map.get("min-value").toString())).andGradeLessThanOrEqualTo(Integer.parseInt(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("share_")){
				criteria.andShareGreaterThanOrEqualTo(Long.parseLong(map.get("min-value").toString())).andShareLessThanOrEqualTo(Long.parseLong(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("collect_")){
				criteria.andCollectGreaterThanOrEqualTo(Long.parseLong(map.get("min-value").toString())).andCollectLessThanOrEqualTo(Long.parseLong(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("entry_num_")){
				criteria.andEntryNumGreaterThanOrEqualTo(Integer.parseInt(map.get("min-value").toString())).andEntryNumLessThanOrEqualTo(Integer.parseInt(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("status_")){
				criteria.andStatusEqualTo(Short.parseShort(map.get("value").toString()));
				continue;
			}
			if(map.get("name").equals("create_time_")){
				criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getDateFormat(map.get("min-value").toString())).andCreateTimeLessThanOrEqualTo(DateUtil.getDateFormat(map.get("max-value").toString()));
				continue;
			}
		}
		List<Topic> topics=topicDao.selectPageByExample(example);
		List<Topic> ts=topicService.initTopics(topics,"topic_type",250,null);
		return ts;
	}
	@Override
	public Long queryTopicCount(String params) {
		List<LinkedTreeMap> ps= JSONUtil.parseJsonArrayWithGson(params, LinkedTreeMap.class);
		TopicExample example=new TopicExample();
		Criteria criteria=example.createCriteria();
		for(LinkedTreeMap map :ps){
			if(map.get("name").equals("title_")){
				criteria.andTitleLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("from_url_")){
				criteria.andFromUrlLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("img_url_")){
				criteria.andImgUrlLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("desc_")){
				criteria.andDescLike("%"+map.get("value")+"%");
				continue;
			}
			if(map.get("name").equals("classify_")){
				criteria.andClassifyEqualTo(map.get("value").toString());
				continue;
			}
			if(map.get("name").equals("type_")){
				criteria.andTypeEqualTo(map.get("value").toString());
				continue;
			}
			if(map.get("name").equals("tags_")){
				criteria.andTagsEqualTo(map.get("value").toString());
				continue;
			}
			if(map.get("name").equals("is_carousel_")){
				criteria.andIsCarouselEqualTo(Short.parseShort(map.get("value").toString()));
				continue;
			}
			if(map.get("name").equals("is_top_")){
				criteria.andIsTopEqualTo(Short.parseShort(map.get("value").toString()));
				continue;
			}
			if(map.get("name").equals("grade_")){
				criteria.andGradeGreaterThanOrEqualTo(Integer.parseInt(map.get("min-value").toString())).andGradeLessThanOrEqualTo(Integer.parseInt(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("share_")){
				criteria.andShareGreaterThanOrEqualTo(Long.parseLong(map.get("min-value").toString())).andShareLessThanOrEqualTo(Long.parseLong(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("collect_")){
				criteria.andCollectGreaterThanOrEqualTo(Long.parseLong(map.get("min-value").toString())).andCollectLessThanOrEqualTo(Long.parseLong(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("entry_num_")){
				criteria.andEntryNumGreaterThanOrEqualTo(Integer.parseInt(map.get("min-value").toString())).andEntryNumLessThanOrEqualTo(Integer.parseInt(map.get("max-value").toString()));
				continue;
			}
			if(map.get("name").equals("status_")){
				criteria.andStatusEqualTo(Short.parseShort(map.get("value").toString()));
				continue;
			}
			if(map.get("name").equals("create_time_")){
				criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getDateFormat(map.get("min-value").toString())).andCreateTimeLessThanOrEqualTo(DateUtil.getDateFormat(map.get("max-value").toString()));
				continue;
			}
		}
		Long count= topicDao.countByExample(example);
		return count;
	}
	@Override
	public String addTopic(String title, String imgurl, String desc, Long accountId) {
		Topic topic=new Topic();
		Long id=PrimaryKeyGenerator.getPrimaryKey("topic");
		topic.setId(id);
		topic.setCreateBy(accountId);
		topic.setTitle(title);
		topic.setDesc(desc);
		topic.setIsTop((short) 0);
		topic.setImgUrl(imgurl);
		topic.setCurrVersion((short)1);
		topic.setStatus((short) 1);
		topic.setGrade(0);
		try{
		topicDao.insert(topic);
		}catch(Exception e){
			throw new BusinessException("新增主题错误!", e);
		}
		return ReturnJSONUtil.getSuccessExtendInfo("创建主题成功!", new JsonModel("topic",id));
	}
	@Override
	public Topic querySimpleTopic(String id) {
	 Topic topic=topicDao.selectByPrimaryKey(Long.parseLong(id));
	 return topic;
	}
	@Override
	public String addEntry(String title, String imgurl, String desc, String topic, Long accountId) {
		Entry entry=new Entry();
		Long id=PrimaryKeyGenerator.getPrimaryKey("entry");
		entry.setId(id);
		entry.setTitle(title);
		entry.setCreateBy(accountId);
		entry.setDesc(desc);
		entry.setStatus((short) 1);
		entry.setImgUrl(imgurl);
		entry.setTopicId(Long.parseLong(topic));
		EntryExample example=new EntryExample();
		example.createCriteria().andTopicIdEqualTo(Long.parseLong(topic));
		Integer sortId=entryDao.selectSortIdByExample(example);
		entry.setSortId(sortId==null?1:sortId);
		try{
			entryDao.insert(entry);
		}catch(Exception e){
			throw new BusinessException("新增单片错误!", e);
		}
		return ReturnJSONUtil.getSuccessExtendInfo("创建片段成功!", new JsonModel("entry",id));
	}
	@Override
	public List<Entry> queryTopicEntrys(String topicId) {
		EntryExample example=new EntryExample();
		example.createCriteria().andTopicIdEqualTo(Long.parseLong(topicId)).andStatusEqualTo((short) 1);
		List<Entry> entrys=entryDao.selectByExample(example);
		return entrys;
	}
	@Override
	public Entry querySimpleEntry(String id) {
	    Entry entry=entryDao.selectByPrimaryKey(Long.parseLong(id));
		return entry;
	}
	@Override
	public String updateTopic(String title, String imgurl, String desc, String topicId, Long accountId) {
		Topic topic=topicDao.selectByPrimaryKey(Long.parseLong(topicId));
		topic.setTitle(title);
		topic.setDesc(desc);
		topic.setImgUrl(imgurl);
		topic.setCreateTime(DateTimeUtil.GetCurrentDate());
		try{
		topicDao.updateByPrimaryKeySelective(topic);
		}catch(Exception e){
			throw new BusinessException("更新主题失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("更新主题成功!");
	}
	@Override
	public String virtualRemoveTopic(String topicId) {
		Topic topic=topicDao.selectByPrimaryKey(Long.parseLong(topicId));
		topic.setStatus((short) 0);
		topic.setCreateTime(DateTimeUtil.GetCurrentDate());
		try{
		topicDao.updateByPrimaryKeySelective(topic);
		}catch(Exception e){
			throw new BusinessException("虚拟删除主题失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("删除主题成功!");
	}
	@Override
	public String virtualRemoveEntry(String entryId) {
		Entry entry=entryDao.selectByPrimaryKey(Long.parseLong(entryId));
		entry.setStatus((short) 0);
		entry.setCreateTime(DateTimeUtil.GetCurrentDate());
		try{
			entryDao.updateByPrimaryKeySelective(entry);
		}catch(Exception e){
			throw new BusinessException("虚拟删除单片失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("删除碎片成功!");
	}
	@Override
	public String topicCheck(String ctype, Long id, String check) {
		if(!ctype.equals("topic") && !ctype.equals("entry")){
			throw new BusinessException("不存在这个类型!");
		}
		short status=1;
		if(ctype.equals("topic")){
		  Topic topic=topicDao.selectByPrimaryKey(id);
          if(StringUtils.isNotBlank(check)){
		    status=3;
		 }else{
			status=2;
		 }
         topic.setStatus(status);
 		 topic.setRemark(check);
 		 topicDao.updateByPrimaryKey(topic);
		}
		if(ctype.equals("entry")){
			 Entry entry=entryDao.selectByPrimaryKey(id);
			if(StringUtils.isNotBlank(check)){
			 status=3;
		   }else
		   {
			  status=2;
		   }
			 entry.setStatus(status);
			 entry.setRemark(check);
			 entryDao.updateByPrimaryKey(entry);
		}
		return ReturnJSONUtil.getSuccessInfo("status");
	}
	@Override
	public String topicQueryCheck(String ctype, Long id) {
		if(!ctype.equals("topic") && !ctype.equals("entry")){
			throw new BusinessException("no this check type");
		}
		String msg="";
		if(ctype.equals("topic")){
			  Topic topic=topicDao.selectByPrimaryKey(id);
			  msg=(null==topic.getRemark())?"":topic.getRemark();
		}
		if(ctype.equals("entry")){
			 Entry entry=entryDao.selectByPrimaryKey(id);
			 msg=(null==entry.getRemark())?"":entry.getRemark();
		}
		return ReturnJSONUtil.getSuccessInfo(msg);
	}
}
