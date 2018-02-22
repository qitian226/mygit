package com.cos.service.edit.impl;


import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.EntryMapper;
import com.cos.dao.FullIndexMapper;
import com.cos.dao.SysAccountMapper;
import com.cos.dao.TopicMapper;
import com.cos.dao.TopicToTagMapper;
import com.cos.exception.BusinessException;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.FullIndex;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.service.edit.ITopicEditService;

@Service
@Transactional
public class TopicEditService implements ITopicEditService {

    @Resource
	private TopicMapper topicDao;
    @Resource
  	private EntryMapper entryDao;
	@Resource
	private TopicToTagMapper topicToTagDao;
	@Resource
	private SysAccountMapper sysAccountDao;
	@Resource
	private FullIndexMapper fullIndexDao;

	
	/*
	 * (non-Javadoc)
	 * @see com.cos.service.edit.ITopicEditService#addEntry(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	
	@Override
	public String addEntry(Long entryId,String title, String imgurl, String desc, Long topicId, Long accountId,Integer width, Integer height) {
		EntryExample exa=new EntryExample();
		exa.createCriteria().andIdEqualTo(entryId);
		Long count=entryDao.countByExample(exa);
		if(count.compareTo(1L)>=0){ //防重
			return ReturnJSONUtil.getWarnExtendInfo("单片已经创建!", new JsonModel("entry", entryId.toString()));
		}
		Entry entry=new Entry();
		entry.setId(entryId);
		entry.setTitle(title);
		entry.setCreateBy(accountId);
		entry.setDesc(desc);
		entry.setStatus((short) 1);
		entry.setImgUrl(imgurl);
		entry.setImgWidth(width);
		entry.setImgHeight(height);

		entry.setTopicId(topicId);
		entry.setGrade(0);
		entry.setCurrVersion((short)1);
		entry.setCommentNum(0);
		EntryExample example=new EntryExample();
		example.createCriteria().andTopicIdEqualTo(topicId);
		Integer sortId=entryDao.selectSortIdByExample(example);
		entry.setSortId(sortId==null?1:sortId);
		Topic topic=topicDao.selectByPrimaryKey(topicId);
		topic.setEntryNum((null==topic.getEntryNum()?0:topic.getEntryNum())+1);

        /** fullindex **/

		FullIndex fullindex=new FullIndex();
		if(StringUtils.isNotBlank(title)){
		Long id=PrimaryKeyGenerator.getPrimaryKey("fullindex");
		fullindex.setId(id);
		fullindex.setTopicId(topicId);
		fullindex.setEntryId(entryId);
		fullindex.setIndex(title);
		fullindex.setCurrVersion((short)1);
		fullindex.setCreateBy(accountId);
		fullindex.setStatus((short)1);
		}
		try{
			entryDao.insert(entry);
			if(StringUtils.isNotBlank(title)){
			fullIndexDao.insert(fullindex);
			}
			topicDao.updateByPrimaryKey(topic);
			SysAccount account=sysAccountDao.selectByPrimaryKey(accountId);
			sysAccountDao.addEntryCountByPrimaryKey(accountId,account.getCurrVersion());
		}catch(Exception e){
			throw new BusinessException("创建单片失败!", e);
		}
		  return ReturnJSONUtil.getSuccessExtendInfo("创建单片成功!", new JsonModel("id", entryId), new JsonModel("imgurl", imgurl.split(";")[1]));
	}
	
	/**
	 * 
	 */
	@Override
	public String updateEntry(String title, String imgurl, String desc, Long entryId,Long accountId,Integer width, Integer height) {
		Entry entry=entryDao.selectByPrimaryKey(entryId);
		if(null==entry){
			throw new BusinessException("无此单片存在!");
		}
		if(entry.getCreateBy().compareTo(accountId)!=0){
			throw new BusinessException("你无此操作权限!");
		}
		entry.setDesc(desc);
		entry.setTitle(title);
		entry.setImgUrl(imgurl);
		entry.setImgWidth(width);
		entry.setImgHeight(height);
		try{
			entryDao.updateByPrimaryKeySelective(entry);
		}catch(Exception e){
			throw new BusinessException("更新单片失败!", e);
		}
		return ReturnJSONUtil.getSuccessExtendInfo("更新单片成功!", new JsonModel("update", "is"),new JsonModel("id", entryId),new JsonModel("imgurl", imgurl.split(";")[1]));
	}
	
     /**
	 * 
	 */
	@Override
	public String updateTopicCover(Long topicId,String url, Integer width,Integer height) {
		 Topic topic=topicDao.selectByPrimaryKey(topicId);
		 topic.setImgUrl(url);
		 topic.setImgWidth(width);
		 topic.setImgHeight(height);
		 try{
		 topicDao.updateByPrimaryKey(topic); //待加删除图片代码
		 }catch(Exception ex){
			 throw new BusinessException("更新封面失败!", ex);
		 }
		return ReturnJSONUtil.getSuccessExtendInfo("更新主题成功!",new JsonModel("id",topicId), new JsonModel("imgurl",url.split(";")[1]));
	}

}
