package com.cos.service.topic.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.FileLocalUtils;
import com.cos.common.utils.JSOUPUtil;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.BaseDictionaryitemMapper;
import com.cos.dao.CollectFolderMapper;
import com.cos.dao.CommentMapper;
import com.cos.dao.EntryMapper;
import com.cos.dao.FansMapper;
import com.cos.dao.GradeMapper;
import com.cos.dao.ReplyMapper;
import com.cos.dao.TopicMapper;
import com.cos.dao.TopicToTagMapper;
import com.cos.enums.PageNumEnum;
import com.cos.exception.BusinessException;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.BaseDictionaryitemExample;
import com.cos.model.CollectFolder;
import com.cos.model.CollectFolderExample;
import com.cos.model.Comment;
import com.cos.model.CommentExample;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.FansExample;
import com.cos.model.Grade;
import com.cos.model.GradeExample;
import com.cos.model.Reply;
import com.cos.model.ReplyExample;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.model.TopicExample;
import com.cos.model.TopicToTag;
import com.cos.model.TopicToTagExample;
import com.cos.page.PageModel;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.sys.ISysDictionaryService;
import com.cos.service.topic.ITopicService;
import com.cos.service.topicManage.IFileUploadService;



@Service
@Transactional
public class TopicService implements ITopicService {
    private final  int BABY_PAGE_RANGE=PageNumEnum.Topic.getValue();
	@Resource
	private TopicMapper topicDao;
	@Resource
	private EntryMapper entryDao;
	@Resource
	private CommentMapper commentDao;
	@Resource
	private ReplyMapper replyDao;
	//@Resource
	//private SysAccountMapper sysAccountDao;
	@Resource
	private GradeMapper gradeDao;
	@Resource
	private TopicToTagMapper topicToTagDao;
	@Resource
	private BaseDictionaryitemMapper baseDictionaryitemDao;
	@Resource
	private FansMapper fansDao;
	@Resource
	private IFileUploadService fileUploadService;
	@Resource
	private CollectFolderMapper collectFolderDao;
	@Resource
	private ISysDictionaryService sysDictionaryService;
	@Resource
	private ISysAccountService sysAccountService;

	@Override
	public List<Topic> queryTopTopic(String topicType) {
		TopicExample example=new TopicExample();
		example.setOrderByClause("sort_id_");
		example.createCriteria().andTypeEqualTo(topicType).andIsTopEqualTo((short) 1).andStatusEqualTo((short) 1);
		List<Topic> tss=topicDao.selectByExample(example);
		if(tss.size()>0){
		List<Topic> ts= initTopics(tss,topicType,236,null);
		return ts;
		}
		return tss;
	}

	@Override
	public List<Topic> queryTopicByPage(int pageNum,String topicType) {
		TopicExample example=new TopicExample();
		example.setFirstResult(BABY_PAGE_RANGE*(pageNum-1));
		example.setMaxResults(BABY_PAGE_RANGE);
		example.setOrderByClause("id_ desc");
		example.createCriteria().andTypeEqualTo(topicType).andStatusEqualTo((short) 1).andIsTopEqualTo((short)0);
		List<Topic> tss=topicDao.selectPageByExample(example);
		if(tss.size()>0){
		List<Topic> ts= initTopics(tss,topicType,236,null);
		return ts;
		}
		return tss;
	}


	@Override
	public Topic querySimpleTopic(Long topicId) {
		Topic topic=topicDao.selectByPrimaryKey(topicId);
	    SysAccount account=sysAccountService.querySimpleAccount(topic.getCreateBy());
	    topic.setAuthor(account);
	    TopicToTagExample example=new TopicToTagExample();
		 example.createCriteria().andTopicIdEqualTo(topicId);
		 List<TopicToTag> ttt= topicToTagDao.selectByExample(example);
		 List<String> tags=new ArrayList<String>();
		 for(TopicToTag t2t :ttt){
			 tags.add(t2t.getTagName());
		 }
		 if(tags.size()>0){
	     List<BaseDictionaryitem> items=sysDictionaryService.queryDictionaryitemByCodes(topic.getType(),tags);
	     List<String> tagNames=new ArrayList<String>();
	     for(BaseDictionaryitem i:items){
	    	 tagNames.add(i.getDictitemValue());
	     }
	     topic.setTagNames(tagNames);
		 }
	     BaseDictionaryitem item= sysDictionaryService.queryDicValueByTypeAndName("topic_type",topic.getType());
		 topic.setTypeName(item.getDictitemValue());
		 if(StringUtils.isNotBlank(topic.getImgUrl())){
  			String[] urls=topic.getImgUrl().split(";");
  			if(urls.length==2) {
  				topic.setImgUrl(urls[0]);
  				topic.setZoominUrl(urls[1]);
  			}
  		}
		 return topic;
		}

    private List<Comment> initComments(List<Comment> comments){
				String reids="";
				String authors="";
				for(Comment c :comments){
					if(StringUtils.isNotBlank(c.getReplyIds())){
						reids=reids+c.getReplyIds()+",";
					}
					authors=authors+c.getCreateBy()+ ",";
				}

				List<Long> rids=new ArrayList<Long>();
				if(reids.length()>0){
					String[] repids=reids.split(",");
					for(String id:repids){
						rids.add(Long.parseLong(id));
					}
				}
				List<Reply> qreplys = new ArrayList<Reply>();
				if(rids.size()>0){
				ReplyExample rexa=new ReplyExample();
				rexa.createCriteria().andIdIn(rids);
				qreplys=replyDao.selectByExample(rexa);
				}

				if(qreplys!=null&&qreplys.size()>0){
					for(Reply r:qreplys){
						authors=authors+r.getCreateBy()+ ",";
					}
				}

				List<Long> aids=new ArrayList<Long>();
				 String[] auids=authors.split(",");
					for(String id:auids){
						aids.add(Long.parseLong(id));
					}

				List<SysAccount> accounts=new ArrayList<SysAccount>();
				List<Reply> replys=new ArrayList<Reply>();
				if(aids.size()>0){
				    accounts= sysAccountService.queryGroupSysAccounts(aids);
				}
				if(qreplys.size()>0){
				 for(Reply r:qreplys){
					 for(SysAccount a: accounts){
							if(a.getId().compareTo(r.getCreateBy())==0){
								r.setAuthor(a);
								replys.add(r);
							}
						}
				  }
				}

				 	for(Comment c:comments){
						List<Reply> rs=new ArrayList<Reply>();
						for(Reply r:replys){
						if(r.getCommentId().compareTo(c.getId())==0){
							rs.add(r);
						 }
						}
						for(SysAccount a: accounts){
							if(a.getId().compareTo(c.getCreateBy())==0){
								c.setAuthor(a);
							}
						}
						if(rs.size()>0){
							c.setReplys(rs);
						}
					}

				return comments;
    }
	@Override
	public List<Entry> queryListEntrys(Long id) {
		EntryExample example=new EntryExample();
		example.setOrderByClause("id_");
		example.createCriteria().andTopicIdEqualTo(id).andStatusEqualTo((short)1);
		List<Entry> entrys=entryDao.selectByExample(example);
		for(Entry entry:entrys) {
			 if(StringUtils.isNotBlank(entry.getImgUrl())){
		  			String[] urls=entry.getImgUrl().split(";");
		  			if(urls.length==2) {
		  				entry.setImgUrl(urls[0]);
		  				entry.setZoominUrl(urls[1]);
		  			}
		  		}
		}
		return entrys;
	}

	@Override
	public Long queryTotalCount(String type) {
	    TopicExample example=new TopicExample();
		example.createCriteria().andStatusEqualTo((short) 1).andTypeEqualTo(type).andIsTopEqualTo((short) 0);
		Long count=topicDao.countByExample(example);
		return count;
	}

	@Override
	public List<Comment> queryCommentsByTargetId(Long targetId,int pageNum) {
		CommentExample example=new CommentExample();
		example.setFirstResult(PageNumEnum.Comment.getValue()*(pageNum-1));
		example.setMaxResults(PageNumEnum.Comment.getValue());
		example.setOrderByClause("id_");
		example.createCriteria().andTargetIdEqualTo(targetId).andStatusNotEqualTo((short) 0);
		List<Comment> comments= commentDao.selectPageByExample(example);

		example=new CommentExample();
		example.createCriteria().andTargetIdEqualTo(targetId).andStatusNotEqualTo((short) 0);
		example.setFirstResult(0);
		example.setMaxResults(1);
		example.setOrderByClause("id_ desc");

		List<Comment> cs= commentDao.selectPageByExample(example);

		if(pageNum==1 && comments.size()>0){
			if(cs.size()>0){
				comments.add(0, cs.get(0));
			}
		comments=initComments(comments);
		}
		return comments;
	}

	@Override
	public Entry querySimpleEntry(Long entryId) {
	  Entry entry=entryDao.selectByPrimaryKey(entryId);
	  SysAccount a= sysAccountService.querySimpleAccount(entry.getCreateBy());
	  entry.setAuthor(a.getNickname());
	  Long cpCount = 0L;//评价人数
	  CommentExample example=new CommentExample();
	  example.createCriteria().andTargetIdEqualTo(entryId);
	  cpCount=commentDao.countByExample(example);
	  entry.setCpCount(cpCount);
		 if(StringUtils.isNotBlank(entry.getImgUrl())){
	  			String[] urls=entry.getImgUrl().split(";");
	  			if(urls.length==2) {
	  				entry.setImgUrl(urls[0]);
	  				entry.setZoominUrl(urls[1]);
	  			}
	  		}
	  
	  return entry;
	}

	@Override
	public int queryGradeByTargetId(Long accountId, Long entryId) {
		if(null==accountId){
			return 0;
		}
		GradeExample example=new  GradeExample();
		example.createCriteria().andTagetIdEqualTo(entryId).andCreateByEqualTo(accountId);
		List<Grade> gs=gradeDao.selectByExample(example);
		if(gs.size()==0){
			return 0;
		}
		return gs.get(0).getGrade();
	}

	@Override
	public Long queryTotalPageNumByTargetId(Long entryId) {
		CommentExample exa=new CommentExample();
		exa.createCriteria().andTargetIdEqualTo(entryId).andStatusEqualTo((short) 1);
		Double count=(double) commentDao.countByExample(exa);
		return (long) Math.ceil(count/PageNumEnum.Comment.getValue());
	}

	@Override
	public List<BaseDictionaryitem> queryTagsByTopic(Long topicId) {
		TopicToTagExample exa=new TopicToTagExample();
		exa.createCriteria().andTopicIdEqualTo(topicId);
		List<TopicToTag> ts=topicToTagDao.selectByExample(exa);
		List<String> tags=new ArrayList<String>();
		for(TopicToTag t:ts){
			tags.add(t.getTagName());
		}
		List<BaseDictionaryitem> items=new ArrayList<BaseDictionaryitem>();
		if(tags.size()>0){
		BaseDictionaryitemExample bexa=new BaseDictionaryitemExample();
		bexa.createCriteria().andDictitemNameIn(tags);
		items=baseDictionaryitemDao.selectByExample(bexa);
		}
		return items;
	}

	@Override
	public Map<String, Object> queryAttention(Long attentionId, Long accountId) {
		Map<String, Object> r=new HashMap<String, Object>();
		if(null!=accountId){
			FansExample exa=new FansExample();
			exa.createCriteria().andFansIdEqualTo(accountId).andAccountIdEqualTo(attentionId);
			Long count=fansDao.countByExample(exa);
			if(count.compareTo(0L)>0){
				 r.put("isAttention", true);
			}else{
				 r.put("isAttention", false);
			}
		}
		SysAccount account= sysAccountService.querySimpleAccount(attentionId); //被关注用户
		int fans=account.getFans()==null?0:account.getFans();
		r.put("fans", fans);
		return r;
	}

	@Override
	public List<BaseDictionaryitem> queryTopicType() {
		BaseDictionaryitemExample example=new BaseDictionaryitemExample();
		example.createCriteria().andDictCodeEqualTo("topic_type");
		List<BaseDictionaryitem> items=baseDictionaryitemDao.selectByExample(example);
		return items;
	}

	@Override
	public List<BaseDictionaryitem> queryTopicTags(String type) {
		BaseDictionaryitemExample example=new BaseDictionaryitemExample();
		example.createCriteria().andDictCodeEqualTo(type);
		List<BaseDictionaryitem> items=baseDictionaryitemDao.selectByExample(example);
		return items;
	}

	@Override
	public List<Topic> queryTopicByAccount(Long accountId,String q) {
		TopicExample exa=new  TopicExample();
		exa.createCriteria().andCreateByEqualTo(accountId).andTitleLike("%"+q+"%");
		List<Topic> ts=topicDao.selectByExample(exa);
		return ts;
	}

	/*@Override
	public String addEntry(Entry entry,String imgdata,Integer width,Integer height,int isCarousel) {
		try{
			String id=PrimaryKeyGenerator.getPrimaryKey("img").toString();
			String url=fileUploadService.uploadSimplePic(Long.parseLong(id), imgdata,width,height,"jpg");
			EntryExample example=new EntryExample();
			example.createCriteria().andTopicIdEqualTo(entry.getTopicId());
			Integer sortid=entryDao.selectSortIdByExample(example);
			entry.setImgUrl(url);
			entry.setImgWidth(width);
			entry.setImgHeight(height);
			entry.setSortId(null==sortid?1:sortid);
			entry.setIsCarousel((short)isCarousel);
			entryDao.insert(entry);
		}catch(Exception e){
			throw new BusinessException("创建单片失败!", e);
		}
		return ReturnJSONUtil.getSuccessInfo("创建单片成功!");
	}
*/
	@Override
	public String extendAddTopic(Topic topic, String tags,Long accountId) {
		TopicExample exa=new TopicExample();
		exa.createCriteria().andTitleEqualTo(topic.getTitle()).andCreateByEqualTo(accountId);
		Long count=topicDao.countByExample(exa);
		if(null!=count && count.compareTo(0L)>0){
			return ReturnJSONUtil.getWarnInfo("同名主题已经存在了哦!");
		}
		try{
		 topicDao.insert(topic);
		 String[] ts=tags.split(";");
		 for(String t:ts){
			TopicToTag t2t=new TopicToTag();
			Long id=PrimaryKeyGenerator.getPrimaryKey("topictotag");
			t2t.setId(id);
			t2t.setTagName(t);
			t2t.setTopicId(topic.getId());
			t2t.setCreateBy(accountId);
			topicToTagDao.insert(t2t);
		 }
		}catch(Exception ex){
			throw new BusinessException("插入主题错误!", ex);
		}
		return  ReturnJSONUtil.getSuccessExtendInfo("创建单片成功!", new JsonModel("title", topic.getTitle()),new JsonModel("id",topic.getId()));
	}

	@Override
	public int queryTopTopicsCount() {
		TopicExample example=new TopicExample();
		example.setOrderByClause("grade_ desc limit 0, 30");
		List<Topic> ts=topicDao.selectByExample(example);
		if(ts.size()>0){
			return ts.size();
		}
		return 0;
	}


	@Override
	public int queryTopEntrysCount() {
	    EntryExample example=new EntryExample();
	    example.setOrderByClause("grade_ desc limit 0, 30");
	    List<Entry> es =entryDao.selectByExample(example);
	    if(es.size()>0){
	    	return es.size();
	    }
	    return 0;
	}

	@Override
	public List<Entry> queryIndexTopEntrys(String page) {
		EntryExample example=new EntryExample();
		example.setFirstResult(BABY_PAGE_RANGE*(Integer.parseInt(page)-1));
		example.setMaxResults(BABY_PAGE_RANGE);
		example.setOrderByClause("grade_ desc");
		List<Entry> tss=entryDao.selectPageByExample(example);
		 if(tss.size()>0){
		List<Entry> ts= initEntrys(tss,250);
		return ts;
		}
		return tss;
	}

	@Override
	public List<Entry> initEntrys(List<Entry> entrys,int width) {
		List<Long> ids=new ArrayList<Long>();
		List<Entry> ts=new ArrayList<Entry>();

		 for(Entry t:entrys){
			ids.add(t.getCreateBy());
		}

		List<SysAccount> as= sysAccountService.queryGroupSysAccounts(ids);
		for(Entry t:entrys){
			Entry enrty=new Entry();
			enrty.setId(t.getId());
			enrty.setTitle(t.getTitle());
			enrty.setTopicId(t.getTopicId());
			enrty.setDesc(t.getDesc());
			enrty.setGrade(t.getGrade()==null?0:t.getGrade());
			enrty.setCreateTimeString(t.getCreateTimeString());
/*			enrty.setImgWidth(t.getImgWidth()); */
			enrty.setImgHeight(t.getImgHeight()*width/t.getImgWidth());
			enrty.setCreateBy(t.getCreateBy());

			for(SysAccount a:as ){
				if(a.getId().compareTo(t.getCreateBy())==0){
					enrty.setAuthor(a.getNickname());
					enrty.setAccountName(a.getAccountName());
					break;
				}
			}
			ts.add(enrty);
		}
		return ts;
	}
	@Override
	public List<Topic> initDynamicTopics(List<Topic> topics) {
		List<Long> ids=new ArrayList<Long>();
		List<Long> topicIds=new ArrayList<Long>();
		List<Topic> ts=new ArrayList<Topic>();

		for(Topic t:topics){
			ids.add(t.getCreateBy());
			topicIds.add(t.getId());
		}
		List<SysAccount> as=sysAccountService.queryGroupSysAccounts(ids);
		List<Entry> entrys=entryDao.selectDynamicEntryByPage(topicIds, 9, "id_");
		for(Topic t:topics){
			Topic nTopic=new Topic();
			nTopic.setId(t.getId());
			String title=(null==t.getTitle())?"":t.getTitle();
			nTopic.setTitle(title.length()>16?t.getTitle().substring(0, 16):title);
			nTopic.setGrade(t.getGrade());
     		nTopic.setCreateBy(t.getCreateBy());
     		nTopic.setClassify(t.getClassify());
     		nTopic.setGrade(t.getGrade()==null?0:t.getGrade());
     		nTopic.setShare(t.getShare()==null?0L:t.getShare());
     		nTopic.setEntryNum(t.getEntryNum()==null?0:t.getEntryNum());
     		String desc=t.getDesc();
     		if(StringUtils.isNotBlank(desc)){
     			desc=JSOUPUtil.pureText(desc);
     			nTopic.setDesc(desc.length()>60?desc.substring(0, 60):desc);
     		}
     		nTopic.setCreateTime(t.getCreateTime());
			nTopic.setType(t.getType());
			for(SysAccount a:as ){
				if(a.getId().compareTo(t.getCreateBy())==0){
					nTopic.setAuthor(a);
					break;
				}
			}
			List<Entry> es=new ArrayList<Entry>();
			for(Entry entry:entrys){
				 if(entry.getTopicId().compareTo(t.getId())==0){
					 if(StringUtils.isNotBlank(entry.getImgUrl())){
			     			String[] urls=entry.getImgUrl().split(";");
			     			if(urls.length==2) {
			     				entry.setImgUrl(urls[0]);
			     				entry.setZoominUrl(urls[1]);
			     			}
			     		}
					 es.add(entry);
				 }
			}
			nTopic.setEntrys(es);
			ts.add(nTopic);
		}
		return ts;
	}

	@Override
	public List<Topic> initTopics(List<Topic> topics, int width, Long loginAccountId) {
		return baseInitTopics(topics,width,loginAccountId,null);
	}

	private List<Topic> baseInitTopics(List<Topic> topics, int width, Long loginAccountId,String typeName){
		List<Topic> ts=new ArrayList<Topic>();
		List<Long> ids=new ArrayList<Long>();
		List<Long> tids=new ArrayList<Long>();
		List<BaseDictionaryitem> types=null;
		if(StringUtils.isBlank(typeName)){
		BaseDictionaryitemExample exa=new BaseDictionaryitemExample();
		exa.createCriteria().andDictCodeEqualTo("topic_type");
		 types= baseDictionaryitemDao.selectByExample(exa);
		}
		 for(Topic t:topics){
			ids.add(t.getCreateBy());
			tids.add(t.getId());
		}
		List<SysAccount> as=sysAccountService.queryGroupSysAccounts(ids);
		List<CollectFolder> cs=null;

		if(null!=loginAccountId){
		CollectFolderExample cexa=new CollectFolderExample();
		cexa.createCriteria().andCreateByEqualTo(loginAccountId).andTopicIdIn(tids);
	    cs=collectFolderDao.selectByExample(cexa);
		}
		for(Topic t:topics){
			Topic nTopic=new Topic();
			nTopic.setId(t.getId());
			String title=(null==t.getTitle())?"":t.getTitle();
			nTopic.setTitle(title.length()>16?t.getTitle().substring(0, 16):title);
			nTopic.setGrade(t.getGrade()==null?0:t.getGrade());
			nTopic.setEntryNum(null==t.getEntryNum()?0:t.getEntryNum());
			nTopic.setShare(null==t.getShare()?0L:t.getShare());
			nTopic.setCollect(null==t.getCollect()?0L:t.getCollect());
			nTopic.setCreateTime(t.getCreateTime());
			nTopic.setType(t.getType());
			nTopic.setCurrVersion(t.getCurrVersion());
			nTopic.setClassify(t.getClassify());
			String desc=t.getDesc();
     		if(StringUtils.isNotBlank(desc)){
     			desc=JSOUPUtil.pureText(desc);
     			nTopic.setDesc(desc.length()>60?desc.substring(0, 60):desc);
     		}
     		if(StringUtils.isNotBlank(t.getImgUrl())){
     			String[] urls=t.getImgUrl().split(";");
     			if(urls.length==2) {
     				nTopic.setImgUrl(urls[0]);
     				nTopic.setZoominUrl(urls[1]);
     			}
     		}
			if(StringUtils.isNotBlank(typeName)){
				nTopic.setTypeName(typeName);
			}else{
				for(BaseDictionaryitem d:types){
					if(d.getDictitemName().equals(t.getType())){
						nTopic.setTypeName(d.getDictitemValue());
						break;
					}
				}
			}
			nTopic.setStatus(t.getStatus());
			if(null!=t.getImgWidth() && null!=t.getImgHeight()){
			int w=t.getImgWidth();
			int height=t.getImgHeight();
			nTopic.setImgHeight(height*width/w);
			}
			nTopic.setCreateBy(t.getCreateBy());

			for(SysAccount a:as ){
				if(a.getId().compareTo(t.getCreateBy())==0){
					nTopic.setAuthor(a);
					nTopic.setAccountName(a.getAccountName());
					nTopic.setAccountNickName(a.getNickname());
					nTopic.setAccountImgUrl(a.getImgPath());
					nTopic.setAccountCurrVersion(a.getCurrVersion());
					break;
				}
			}
			if(null!=cs){//已经登录
			nTopic.setIsCollect(false);
			for(CollectFolder c:cs){
				if(c.getTopicId().compareTo(t.getId())==0){
					nTopic.setIsCollect(true);
                    break;
				}
			}
			}
			ts.add(nTopic);
		}
		return ts;
	}
	  @Override
		public List<Topic> initTopics(List<Topic> topics,String type,int width,Long accountId){
			BaseDictionaryitemExample exa=new BaseDictionaryitemExample();
			exa.createCriteria().andDictitemNameEqualTo(type);
			List<BaseDictionaryitem> items= baseDictionaryitemDao.selectByExample(exa);
			String typeName="";
			if(items.size()>0){
				typeName=items.get(0).getDictitemValue();
			}
			List<Topic> ts=	baseInitTopics(topics,width,accountId,typeName);
			return ts;
		}
	  @Override
		public List<Topic> initTopics(List<Topic> topics,int width){
		  if(topics.size()==0){
			  return null;
		  }
			List<Long> ids=new ArrayList<Long>();
			List<Topic> ts=new ArrayList<Topic>();
			BaseDictionaryitemExample exa=new BaseDictionaryitemExample();
			exa.createCriteria().andDictCodeEqualTo("topic_type");
			List<BaseDictionaryitem> types= baseDictionaryitemDao.selectByExample(exa);
			 for(Topic t:topics){
				ids.add(t.getCreateBy());
			}

			List<SysAccount> as=sysAccountService.queryGroupSysAccounts(ids);

			for(Topic t:topics){
				Topic nTopic=new Topic();
				nTopic.setId(t.getId());
				String title=(null==t.getTitle())?"":t.getTitle();
				nTopic.setTitle(title.length()>16?t.getTitle().substring(0, 16):title);
				String desc=t.getDesc();
	     		if(StringUtils.isNotBlank(desc)){
	     			desc=JSOUPUtil.pureText(desc);
	     			nTopic.setDesc(desc.length()>60?desc.substring(0, 60):desc);
	     		}
				nTopic.setGrade(null==t.getGrade()?0:t.getGrade());
				nTopic.setType(t.getType());
				nTopic.setClassify(t.getClassify());
				nTopic.setCreateTime(t.getCreateTime());
				nTopic.setCurrVersion(t.getCurrVersion());
				if(StringUtils.isNotBlank(t.getImgUrl())){
	     			String[] urls=t.getImgUrl().split(";");
	     			if(urls.length==2) {
	     				nTopic.setImgUrl(urls[0]);
	     				nTopic.setZoominUrl(urls[1]);
	     			}
	     		}
				for(BaseDictionaryitem d:types){
					if(d.getDictitemName().equals(t.getType())){
						nTopic.setTypeName(d.getDictitemValue());
					}
				}
				if(null!=t.getImgWidth() && null!=t.getImgHeight()){
				int w=t.getImgWidth();
				int height=t.getImgHeight();
				nTopic.setImgHeight(height*width/w);
				}
				if(StringUtils.isNotBlank(t.getImgUrl())){
	     			String[] urls=t.getImgUrl().split(";");
	     			if(urls.length==2) {
	     				nTopic.setImgUrl(urls[0]);
	     				nTopic.setZoominUrl(urls[1]);
	     			}
	     		}
				nTopic.setCreateBy(t.getCreateBy());
				for(SysAccount a:as ){
					if(a.getId().compareTo(t.getCreateBy())==0){
						nTopic.setAccountName(a.getAccountName());
						nTopic.setAccountNickName(a.getNickname());
						nTopic.setAccountImgUrl(a.getImgPath());
						nTopic.setAccountCurrVersion(a.getCurrVersion());
						break;
					}
				}
				ts.add(nTopic);
			}
			return ts;
		}

	@Override
	public String updateTopicCover(Long topicId,String url, Integer width,Integer height) {
		 Topic topic=topicDao.selectByPrimaryKey(topicId);
		/* if(StringUtils.isNotBlank(topic.getImgUrl())) {
			 try {
				 String[] ids=topic.getImgUrl().split(";");
				 if(ids.length==2) {
					 FileLocalUtils.delFile(ids[0]);
					 FileLocalUtils.delFile(ids[1]);
				 }
			} catch (Exception e) {
				 throw new BusinessException("remote del file:"+topic.getImgUrl() +" error", e);
			}
		 }*/
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

	@Override
	public Map<String,List<Topic>> queryIndexTopTopics() {
		BaseDictionaryitemExample exa=new BaseDictionaryitemExample();
		exa.createCriteria().andDictCodeEqualTo("topic_type");
		List<BaseDictionaryitem> types= baseDictionaryitemDao.selectByExample(exa);
		List<String> typess=new ArrayList<String>();
		for(BaseDictionaryitem item:types){
			typess.add(item.getDictitemName());
		}
		List<Topic> topics=topicDao.queryTopTopic(typess,30);
		if(topics.size()>0){
		List<Topic> tss= initTopics(topics,"topic_type",260,null);
		Map<String,List<Topic>> map=new HashMap<String,List<Topic>>();
			for(BaseDictionaryitem item:types){
				List<Topic> ts=new ArrayList<Topic>();
				for(Topic topic :tss){
				if(item.getDictitemName().equals(topic.getType())){
					ts.add(topic);
				  }
				}
				map.put(item.getDictitemValue(), ts);
			}

		return map;
		}
		return null;
	}

	@Override
	public List<PageModel> queryModelTopicByPage(String topicType, int pageNum, int column) {
		if(column!=5 ||column!=6){
			column=5;
		}
		TopicExample example=new TopicExample();
		example.setFirstResult(BABY_PAGE_RANGE*(pageNum-1));
		example.setMaxResults(BABY_PAGE_RANGE);
		example.setOrderByClause("id_ desc");
		example.createCriteria().andTypeEqualTo(topicType).andStatusEqualTo((short) 1);
		List<Topic> tss=topicDao.selectPageByExample(example);
		Stack<Topic> sortTopics=new Stack<Topic>();
		if(tss.size()>0){
		List<Topic> ts= initTopics(tss,topicType,232,null);
		for(Topic t:ts){
			sortTopics.push(t);
		 }
		}
		List<PageModel> list = new ArrayList<PageModel>();//容器集合
    	for(int i=0;i<column;i++){
    		list.add(new PageModel(0));
    	}
    	int length=sortTopics.size();
    	for(int i=0;i<length;i++){
    		Collections.sort(list);
    		list.get(0).addList(sortTopics.pop()); //计算高度，收集对象
    	}
		return list;
	}

	@Override
	public Long queryTotalPageSize(String type) {
		Long totalNum= this.queryTotalCount(type);
		Long pageSize= (totalNum+BABY_PAGE_RANGE-1L)/BABY_PAGE_RANGE;
		return pageSize;
	}

	@Override
	public List<BaseDictionaryitem> queryTagsByTopicType(String type) {
		BaseDictionaryitemExample example=new BaseDictionaryitemExample();
		example.createCriteria().andDictCodeEqualTo(type);
		List<BaseDictionaryitem> items= baseDictionaryitemDao.selectByExample(example);
		return items;
	}

	@Override
	public void updateSingleTopic(Topic topic) {
		topicDao.updateByPrimaryKey(topic);
	}

	@Override
	public Topic querySingleTopic(Long topicId) {
		return topicDao.selectByPrimaryKey(topicId);
	}

	@Override
	public List<Entry> queryListEntrys(EntryExample exam) {
		return entryDao.selectByExample(exam);
	}

	@Override
	public Entry querySingleEntry(Long entryId) {
		return entryDao.selectByPrimaryKey(entryId);
	}

	@Override
	public void updateSingleEntry(Entry entry) {
		entryDao.updateByPrimaryKey(entry);
	}

	@Override
	public Long queryTotalCount(String type, String tag) {
		 TopicExample example=new TopicExample();
			example.createCriteria().andStatusEqualTo((short) 1).andTypeEqualTo(type);
			Long count=topicDao.countByExample(example);
			return count;
	}

	@Override
	public List<PageModel> queryModelTopicByPage(String topicType, String tag, int pageNum, Integer column) {
		if(column!=5 ||column!=6){
			column=5;
		}
		TopicExample example=new TopicExample();

		example.setFirstResult(BABY_PAGE_RANGE*(pageNum-1));
		example.setMaxResults(BABY_PAGE_RANGE);
		example.setOrderByClause("id_ desc");
		example.createCriteria().andTypeEqualTo(topicType).andTagsEqualTo(tag).andStatusEqualTo((short) 1);
		List<Topic> tss=topicDao.selectPageByExample(example);
		Stack<Topic> sortTopics=new Stack<Topic>();

		if(tss.size()>0){
		List<Topic> ts= initTopics(tss,topicType,232,null);
		for(Topic t:ts){
			sortTopics.push(t);
		}
		}
		List<PageModel> list = new ArrayList<PageModel>();//容器集合
    	for(int i=0;i<column;i++){
    		list.add(new PageModel(0));
    	}
    	int length=sortTopics.size();
    	for(int i=0;i<length;i++){
    		Collections.sort(list);
    		list.get(0).addList(sortTopics.pop()); //计算高度，收集对象
    	}
		return list;
	}

	@Override
	public int queryIsCollect(Long topicId, SysAccount account) {
		if(null==account){
			return 0;
		}
	    CollectFolderExample example=new CollectFolderExample();
	    example.createCriteria().andCreateByEqualTo(account.getId()).andTopicIdEqualTo(topicId);
		List<CollectFolder> folders= collectFolderDao.selectByExample(example);
		return folders.size()>0?1:0;
	}

	@Override
	public Long queryTotalTopicCountByTag(String tag) {
		   TopicExample example=new TopicExample();
			example.createCriteria().andTagsEqualTo(tag);
			Long count=topicDao.countByExample(example);
			return count;
	}

}
