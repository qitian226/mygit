package com.cos.service.edit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.common.utils.DateTimeUtil;
import com.cos.common.utils.FileLocalUtils;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.EntryMapper;
import com.cos.dao.FullIndexMapper;
import com.cos.dao.SysAccountMapper;
import com.cos.dao.TopicMapper;
import com.cos.dao.TopicToTagMapper;
import com.cos.enums.TopicGlobalEnum;
import com.cos.exception.BusinessException;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.FullIndex;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.model.TopicExample;
import com.cos.model.TopicToTag;
import com.cos.model.TopicToTagExample;
import com.cos.service.edit.ITopicEditService;
import com.cos.service.sys.ISysDictionaryService;
import com.cos.service.topicManage.IFileUploadService;

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
	private ISysDictionaryService sysDictionaryService;
	@Resource
	private FullIndexMapper fullIndexDao;
	@Resource
	private IFileUploadService fileUploadService;
	@Override
	public String addTopic(Long topicId,String title, String desc, String type,String tag,Long accountId,String istop,String smodel) {
		TopicExample exa=new TopicExample();
		exa.createCriteria().andIdEqualTo(topicId);
		Long count=topicDao.countByExample(exa);
		if(count.compareTo(1L)>=0){ //防重
			return ReturnJSONUtil.getWarnInfo("主题已经创建!");
		}
        if(null==sysDictionaryService.queryDicValueByTypeAndName("topic_type", type)){
        	throw new BusinessException("非法类型，无此类型定义!");
        }
		BaseDictionaryitem item= sysDictionaryService.queryDicValueByTypeAndName(type,tag);
		if(null==item){
			throw new BusinessException("非法参数，无此标签定义!");
		}
		if(!smodel.equals("article") && !smodel.equals("photo")){
			throw new BusinessException("非法显示模式，无此模式定义!");
		}
		Topic topic=new Topic();
		topic.setId(topicId);
		topic.setCreateBy(accountId);
		topic.setTitle(title);
		topic.setTags(tag);
		topic.setDesc(desc);
		topic.setType(type);
		topic.setClassify(smodel);
		short is_top=istop.equals("is")?(short)1:(short)0;
		topic.setIsTop(is_top);
		topic.setCurrVersion((short)1);
		topic.setStatus((short) 2);//预发布
		topic.setGrade(0);
		topic.setCollect(0L);
		topic.setShare(0L);
		topic.setEntryNum(0);
		topic.setIsCarousel((short)0);
		/** fullindex **/

		FullIndex fullindex=new FullIndex();
		Long id=PrimaryKeyGenerator.getPrimaryKey("fullindex");
		fullindex.setId(id);
		fullindex.setTopicId(topicId);
		fullindex.setEntryId(null);
		fullindex.setIndex(title+" "+topic.getTags());
		fullindex.setCurrVersion((short)1);
		fullindex.setCreateBy(accountId);
		fullindex.setStatus((short)1);
		try{
		topicDao.insert(topic);
		fullIndexDao.insert(fullindex);

		if(StringUtils.isNotBlank(tag)){
			TopicToTag t2t=new TopicToTag();
			Long tid=PrimaryKeyGenerator.getPrimaryKey("topictotag");
			t2t.setId(tid);
			t2t.setTagName(tag);
			t2t.setTopicId(topicId);
			t2t.setCreateBy(accountId);
			topicToTagDao.insert(t2t);
		}
		SysAccount account=sysAccountDao.selectByPrimaryKey(accountId);
		sysAccountDao.addTopicCountByPrimaryKey(accountId,account.getCurrVersion());//用户加主题数1
		}catch(Exception e){
			throw new BusinessException("创建主题失败", e);
		}
		return  ReturnJSONUtil.getSuccessExtendInfo("创建主题成功!", new JsonModel("id",topicId), new JsonModel("title",title));
	}
	@Override
	public String addExtendTopic(SysAccount account,Long topicId,String title, String desc, String type,String tag,String istop,String smodel,Integer width,Integer height,String data) throws Exception {
		TopicExample exa=new TopicExample();
		exa.createCriteria().andIdEqualTo(topicId);
		Long count=topicDao.countByExample(exa);
		if(count.compareTo(1L)>=0){ //防重
			return ReturnJSONUtil.getWarnInfo("主题已经创建!");
		}
        if(null==sysDictionaryService.queryDicValueByTypeAndName("topic_type", type)){
        	throw new BusinessException("非法类型，无此类型定义!");
        }
		BaseDictionaryitem item= sysDictionaryService.queryDicValueByTypeAndName(type,tag);
		if(null==item){
			throw new BusinessException("非法参数，无此标签定义!");
		}
		if(!smodel.equals("article") && !smodel.equals("photo")){
			throw new BusinessException("非法显示模式，无此模式定义!");
		}
		String imgurl=fileUploadService.uploadSimplePic(account,topicId, data,width,height,"jpg");

		Topic topic=new Topic();
		topic.setId(topicId);
		topic.setCreateBy(account.getId());
		topic.setTitle(title);
		topic.setTags(tag);
		topic.setDesc(desc);
		topic.setType(type);
		topic.setClassify(smodel);
		short is_top=istop.equals("is")?(short)1:(short)0;
		topic.setIsTop(is_top);
		topic.setCurrVersion((short)1);
		topic.setStatus((short) 2);//预发布
		topic.setGrade(0);
		topic.setCollect(0L);
		topic.setShare(0L);
		topic.setEntryNum(0);
		topic.setIsCarousel((short)0);
        topic.setImgWidth(width);
        topic.setImgHeight(height);
        topic.setImgUrl(imgurl);
		/** fullindex **/

		FullIndex fullindex=new FullIndex();
		Long id=PrimaryKeyGenerator.getPrimaryKey("fullindex");
		fullindex.setId(id);
		fullindex.setTopicId(topicId);
		fullindex.setEntryId(null);
		fullindex.setIndex(title+" "+topic.getTags());
		fullindex.setCurrVersion((short)1);
		fullindex.setCreateBy(account.getId());
		fullindex.setStatus((short)1);
		try{
		topicDao.insert(topic);
		fullIndexDao.insert(fullindex);

		if(StringUtils.isNotBlank(tag)){
			TopicToTag t2t=new TopicToTag();
			Long tid=PrimaryKeyGenerator.getPrimaryKey("topictotag");
			t2t.setId(tid);
			t2t.setTagName(tag);
			t2t.setTopicId(topicId);
			t2t.setCreateBy(account.getId());
			topicToTagDao.insert(t2t);
		}
		
		sysAccountDao.addTopicCountByPrimaryKey(account.getId(),account.getCurrVersion());//用户加主题数1
		}catch(Exception e){
			throw new BusinessException("创建主题失败", e);
		}
		return  ReturnJSONUtil.getSuccessExtendInfo("创建主题成功!", new JsonModel("id",topicId), new JsonModel("title",title));
	}
	@Override
	public Topic querySimpleTopic(Long topicId) {
	 Topic topic=topicDao.selectByPrimaryKey(topicId);
	 TopicToTagExample example=new TopicToTagExample();
	 example.createCriteria().andTopicIdEqualTo(topicId);
	 List<TopicToTag> ttt= topicToTagDao.selectByExample(example);
	 if(ttt.size()>0){
		 topic.setTags(ttt.get(0).getTagName());
	 }
	 BaseDictionaryitem item= sysDictionaryService.queryDicValueByTypeAndName("topic_type",topic.getType());
	 topic.setTypeName(item.getDictitemValue());
	 
	 if(StringUtils.isNotBlank(topic.getImgUrl())) {
		 String[] urls=topic.getImgUrl().split(";");
		 topic.setImgUrl(urls[0]);
		 topic.setZoominUrl(urls[1]);
	 }
	 return topic;
	}
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
	@Override
	public String addExtendEntry(SysAccount account,Long entryId, String title,String desc, Long topicId,
			Integer width, Integer height, String imgdata) throws Exception {
		String imgurl=fileUploadService.uploadSimplePic(account,entryId, imgdata,width,height,"jpg");
		return addEntry(entryId,title,imgurl,desc,topicId,account.getId(),width,height);
	}
	@Override
	public List<Entry> queryTopicEntrys(Long topicId) {
		EntryExample example=new EntryExample();
		example.setOrderByClause("id_");
		example.createCriteria().andTopicIdEqualTo(topicId).andStatusEqualTo((short) 1);
		List<Entry> entrys=entryDao.selectByExample(example);
		for(Entry entry:entrys) {
			if(StringUtils.isNotBlank(entry.getImgUrl())) {
				String[] urls=entry.getImgUrl().split(";");
				entry.setImgUrl(urls[0]);
				entry.setZoominUrl(urls[1]);
			}
		}
		return entrys;
	}
	@Override
	public Entry querySimpleEntry(Long id) {
	    Entry entry=entryDao.selectByPrimaryKey(id);
		return entry;
	}
	@Override
	public String updateTopic(String title, String imgurl, String desc,String type,String tag, Long accountId, Long topicId,String smodel) {
		Topic topic=topicDao.selectByPrimaryKey(topicId);
		if(null==topic){
			throw new BusinessException("无此主题存在!");
		}
		if(topic.getCreateBy().compareTo(accountId)!=0){
			throw new BusinessException("你无此操作权限!");
		}
		BaseDictionaryitem item= sysDictionaryService.queryDicValueByTypeAndName(type,tag);
		if(null==item){
			throw new BusinessException("非法参数，无此标签");
		}
		topic.setTitle(title);
		topic.setDesc(desc);
		topic.setImgUrl(imgurl);
		topic.setTags(tag);
		topic.setType(type);
		topic.setClassify(smodel);
		topic.setCreateTime(DateTimeUtil.GetCurrentDate());
		try{
		topicDao.updateByPrimaryKeySelective(topic);
		if(StringUtils.isNotBlank(tag)){
			TopicToTagExample example=new TopicToTagExample();
			example.createCriteria().andTopicIdEqualTo(topicId);
			topicToTagDao.deleteByExample(example);
			TopicToTag t2t=new TopicToTag();
			Long tid=PrimaryKeyGenerator.getPrimaryKey("topictotag");
			t2t.setId(tid);
			t2t.setTagName(tag);
			t2t.setTopicId(topicId);
			t2t.setCreateBy(accountId);
			topicToTagDao.insert(t2t);
		}
		}catch(Exception e){
			throw new BusinessException("更新主题失败!", e);
		}
		return ReturnJSONUtil.getSuccessExtendInfo ("更新主题成功",new JsonModel("topic",topicId));
	}
	 
	@Override
	public String removeTopic(Long topicId,SysAccount account) {
		Topic topic=topicDao.selectByPrimaryKey(topicId);
		if(null==topic){
			throw new BusinessException("无此主题存在!");
		}
		if(topic.getStatus()==0){
			throw new BusinessException("此主题已经被删除!");
		}
		if(topic.getCreateBy().compareTo(account.getId())!=0){
			throw new BusinessException("你无此操作权限!");
		}
		try{
			topic.setStatus((short)0);
			topicDao.updateByPrimaryKey(topic);
			sysAccountDao.delTopicCountByPrimaryKey(account.getId(), account.getCurrVersion());
			fullIndexDao.removeStatusByTopic(topicId);
			EntryExample example=new EntryExample();
			example.createCriteria().andTopicIdEqualTo(topicId);
			Long count= entryDao.countByExample(example);
			account=sysAccountDao.selectByPrimaryKey(account.getId());
			sysAccountDao.updateEntryCount(count,account.getId(), account.getCurrVersion());
		}catch(Exception e){
				throw new BusinessException("删除主题失败!", e);
			}
			return ReturnJSONUtil.getSuccessInfo("删除主题成功!");
	}
	@Override
	public String removeEntry(Long entryId,Long accountId) {
		Entry entry=entryDao.selectByPrimaryKey(entryId);
		if(null==entry){
			throw new BusinessException("无此图片存在!");
		}
		if(entry.getCreateBy().compareTo(accountId)!=0){
			throw new BusinessException("你无此操作权限!");
		}
		try{
			entry.setStatus((short)0);
			entryDao.updateByPrimaryKey(entry);
			SysAccount account=sysAccountDao.selectByPrimaryKey(accountId);
			sysAccountDao.delEntryCountByPrimaryKey(accountId,account.getCurrVersion());
			Topic topic=topicDao.selectByPrimaryKey(entry.getTopicId());
			topic.setEntryNum(topic.getEntryNum()-1);
			topicDao.updateByPrimaryKey(topic);
		}catch(Exception e){
			throw new BusinessException("删除图片失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("删除图片成功!");
	}
	@Override
	public String updateEntry(String title, String imgurl, String desc, Long entryId,Long accountId,Integer width, Integer height) {
		Entry entry=entryDao.selectByPrimaryKey(entryId);
		if(null==entry){
			throw new BusinessException("无此单片存在!");
		}
		if(entry.getCreateBy().compareTo(accountId)!=0){
			throw new BusinessException("你无此操作权限!");
		}
	/*	if(StringUtils.isNotBlank(entry.getImgUrl())) {
			try {
			String[] ids=entry.getImgUrl().split(";");
			if(ids.length==2) {
				 FileLocalUtils.delFile(ids[0]);
				 FileLocalUtils.delFile(ids[1]);
			}
			} catch (Exception e) {
				 throw new BusinessException("remote del file:"+entry.getImgUrl() +" error", e);
			}
		}
		*/
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
	@Override
	public String setCarousel(Long topicId) {
		 Topic topic=topicDao.selectByPrimaryKey(topicId);
		 if(null==topic){
				throw new BusinessException("无此主题存在!");
			}
		 topic.setStatus((short)2);
		 try{
		 topicDao.updateByPrimaryKeySelective(topic);
		 }catch(Exception e){
				throw new BusinessException("设置轮播错误!", e);
			}
		 return  ReturnJSONUtil.getSuccessInfo("设置轮播成功!");
	}
	@Override
	public String cancelCarousel(Long topicId) {
		Topic topic=topicDao.selectByPrimaryKey(topicId);
		if(null==topic){
			throw new BusinessException("无此主题存在!");
		}
		 topic.setStatus((short)1);
		 try{
		 topicDao.updateByPrimaryKeySelective(topic);
		 }catch(Exception e){
				throw new BusinessException("取消轮播错误!", e);
			}
		 return  ReturnJSONUtil.getSuccessInfo("取消轮播成功!");
	}
	@Override
	public String setEntryCarousel(Long entryId) {
		Entry entry=entryDao.selectByPrimaryKey(entryId);
		if(null==entry){
			throw new BusinessException("无此单片存在!");
		}
		entry.setStatus((short)2);
		 try{
			 entryDao.updateByPrimaryKeySelective(entry);
		 }catch(Exception e){
				throw new BusinessException("更新轮播失败!", e);
			}
		 return ReturnJSONUtil.getSuccessInfo("设置轮播成功!");
	}
	@Override
	public String cancelEntryCarousel(Long entryId) {
		Entry entry=entryDao.selectByPrimaryKey(entryId);
		if(null==entry){
			throw new BusinessException("无此单片存在!");
		}
		entry.setStatus((short)1);
		 try{
			 entryDao.updateByPrimaryKeySelective(entry);
		 }catch(Exception e){
				throw new BusinessException("更新轮播失败!", e);
			}
		 return ReturnJSONUtil.getSuccessInfo("取消轮播成功!");
	}
	@Override
	public String publishTopic(Long id,Long accountId) {
		Topic topic=topicDao.selectByPrimaryKey(id);
		if(null==topic){
			throw new BusinessException("无此主题存在!");
		}
		if(topic.getCreateBy().compareTo(accountId)!=0){
			throw new BusinessException("你无此操作权限!");
		}
		if(null==topic){
			return ReturnJSONUtil.getWarnInfo("主题不存在,请先创建哦!");
		}
		if(StringUtils.isBlank(topic.getImgUrl())){
			return ReturnJSONUtil.getWarnInfo("没有创建封面,封面是必须的哦!");
		}
		if(topic.getStatus()==1){
			return ReturnJSONUtil.getWarnInfo("主题已经发布成功!");
		}
		topic.setStatus((short)1);
		 try{
			 topicDao.updateByPrimaryKeySelective(topic);
		 }catch(Exception e){
				throw new BusinessException("主题更新错误!", e);
		 }
		 return ReturnJSONUtil.getSuccessInfo("主题发布成功!");
	 }
	@Override
	public String updateTopicPublic(Long id, int status,Long accountId) {
		if(status!=1 && status !=2){
			return ReturnJSONUtil.getWarnExtendInfo("没有这个状态!",new JsonModel("status",status==1?2:1));
		}
		Topic topic=topicDao.selectByPrimaryKey(id);
		if(null==topic){
			return ReturnJSONUtil.getWarnExtendInfo("主题不存在,请先创建哦!",new JsonModel("status",status==1?2:1));
		}
		if(topic.getCreateBy().compareTo(accountId)!=0){
			return ReturnJSONUtil.getWarnExtendInfo("你无此操作权限!",new JsonModel("status",status==1?2:1));
		}
       if(status==1){
    	    if(StringUtils.isBlank(topic.getImgUrl())){
   			 return ReturnJSONUtil.getWarnExtendInfo("没有创建封面,封面是必须的哦!",new JsonModel("status",status==1?2:1));
   		     }
	   		if(topic.getStatus()==1){
	   			return ReturnJSONUtil.getWarnExtendInfo("主题已经发布成功!",new JsonModel("status",status==1?2:1));
	   		 }
		}
		topic.setStatus((short)status);
		topicDao.updateByPrimaryKey(topic);
		return ReturnJSONUtil.getSuccessInfo("状态修改成功!");
	}
	@Override
	public String updateTopicTop(Long id, int istop,Long accountId) {
		if(istop!=0 && istop !=1){
			 return ReturnJSONUtil.getWarnExtendInfo("没有这个状态!",new JsonModel("top",istop==1?0:1));
		}
		TopicExample example=new TopicExample();
		example.createCriteria().andCreateByEqualTo(accountId).andIsTopEqualTo((short)1);
		Long count=topicDao.countByExample(example);
		if(count.compareTo(TopicGlobalEnum.Top.getValue())>0){
			return ReturnJSONUtil.getWarnExtendInfo("置顶主题不能超过"+TopicGlobalEnum.Top.getName()+"个!",new JsonModel("top",istop==1?0:1));
		}
		Topic topic=topicDao.selectByPrimaryKey(id);
		if(null==topic){
			return ReturnJSONUtil.getWarnExtendInfo("主题不存在,请先创建哦!",new JsonModel("top",istop==1?0:1));
		}
		if(topic.getCreateBy().compareTo(accountId)!=0){
			 return ReturnJSONUtil.getWarnExtendInfo("你无此操作权限!",new JsonModel("top",istop==1?0:1));
		}

		topic.setIsTop((short)istop);
		topicDao.updateByPrimaryKey(topic);
		return ReturnJSONUtil.getSuccessInfo("置顶状态设置成功!");
	}
	@Override
	public String updateTopicCarousel(Long id, int carousel, Long accountId) {
		if(carousel!=0 && carousel !=1){
			 return ReturnJSONUtil.getWarnExtendInfo("没有这个状态!",new JsonModel("carousel",carousel==1?0:1));
		}
		TopicExample example=new TopicExample();
		example.createCriteria().andCreateByEqualTo(accountId).andIsCarouselEqualTo((short)1);
		Long count=topicDao.countByExample(example);
		if(count.compareTo(TopicGlobalEnum.Carousel.getValue())>0){
			return ReturnJSONUtil.getWarnExtendInfo("轮播主题不能超过"+TopicGlobalEnum.Carousel.getName()+"个!",new JsonModel("carousel",carousel==1?0:1));
		}
		Topic topic=topicDao.selectByPrimaryKey(id);
		if(null==topic){
			return ReturnJSONUtil.getWarnExtendInfo("主题不存在,请先创建哦!",new JsonModel("carousel",carousel==1?0:1));
		}
		if(topic.getCreateBy().compareTo(accountId)!=0){
			 return ReturnJSONUtil.getWarnExtendInfo("你无此操作权限!",new JsonModel("carousel",carousel==1?0:1));
		}
		topic.setIsCarousel((short)carousel);
		topicDao.updateByPrimaryKey(topic);
		return ReturnJSONUtil.getSuccessInfo("轮播状态设置成功!");

	}


}
