package com.cos.service.topic;

import java.util.List;
import java.util.Map;

import com.cos.model.BaseDictionaryitem;
import com.cos.model.Comment;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.page.PageModel;

public interface ITopicService {

	List<Topic> queryTopTopic(String TopicType);

	List<Topic> queryTopicByPage(int pageNum,String type);

	Topic querySimpleTopic(Long id);

	List<Entry> queryListEntrys(Long id);

	Long queryTotalCount(String type);

	List<Comment> queryCommentsByTargetId(Long entryId,int pageNum);

	Entry querySimpleEntry(Long entryId);

	int queryGradeByTargetId(Long accountId, Long entryId);

	Long queryTotalPageNumByTargetId(Long entryId);

	List<BaseDictionaryitem> queryTagsByTopic(Long id);

	Map<String,Object> queryAttention(Long attentionId, Long accountId);

	List<BaseDictionaryitem> queryTopicType();

	List<BaseDictionaryitem> queryTopicTags(String type);

	List<Topic> queryTopicByAccount(Long accountId, String q);

	//String addEntry(Entry entry,String imgdata,Integer width,Integer height,int iscarousel);

	String extendAddTopic(Topic topic, String tags,Long accountId);

	List<Topic> initTopics(List<Topic> topics, String type,int width,Long accountId);

	List<Topic> initTopics(List<Topic> topics,int width);

	List<Entry> initEntrys(List<Entry> entrys,int width);

	int queryTopTopicsCount();

	Map<String,List<Topic>> queryIndexTopTopics();

	int queryTopEntrysCount();

	List<Entry> queryIndexTopEntrys(String page);

	/**
	 * 初始化动态主题
	 * @param tss
	 * @return
	 */
	List<Topic> initDynamicTopics(List<Topic> tss);

	List<Topic> initTopics(List<Topic> tss, int width, Long accountId);

	String updateTopicCover(Long topicId,String url, Integer width,Integer height);

	/**
	 * 翻页模式 翻页
	 * @param type
	 * @param page
	 * @return
	 */
	List<PageModel> queryModelTopicByPage(String type, int page,int column);

	Long queryTotalPageSize(String type);

	List<BaseDictionaryitem> queryTagsByTopicType(String type);

	void updateSingleTopic(Topic topic);

	Topic querySingleTopic(Long topicId);

	List<Entry> queryListEntrys(EntryExample exam);

	Entry querySingleEntry(Long entryId);

	void updateSingleEntry(Entry entry);

	Long queryTotalCount(String type, String tag);

	List<PageModel> queryModelTopicByPage(String type, String tag, int page, Integer column);

	int queryIsCollect(Long topicId, SysAccount account);

	Long queryTotalTopicCountByTag(String tag);

}
