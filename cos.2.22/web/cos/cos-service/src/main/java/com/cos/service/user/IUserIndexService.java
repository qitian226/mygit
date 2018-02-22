package com.cos.service.user;

import java.util.List;

import com.cos.model.Entry;
import com.cos.model.Topic;

public interface IUserIndexService {

	List<Topic> queryTopTopic(Long accountId,Long loginAccountId);

	List<Topic> queryTopicPage(Long accountId, String page, String topictype, String tag,Long loginAccountId);

	Long queryTopicTotalCount(Long accountId, String topictype, String tag,Long loginAccountId);

	List<Entry> queryEntryPage(Long accountId, String page);

	Long queryEntryTotalCount(Long accountId);

	/**
	 * 查询当前用户所关注的用户
	 * @param accountId
	 * @param page
	 * @param loginAccountId
	 * @return
	 */
	List<Object[]> queryAttentionAccount(Long accountId,int page,Long loginAccountId);

	Long queryAttentionAccountCount(Long id);

	Long queryDynamicTotalCount(Long id);

	List<Topic> queryDynamicByPage(Long id, int page,Long loginAccountId);

	List<Topic> queryDynamicByPage(Long id, int page);

	List<Topic> queryCollectTopics(long accountId, String type);

	List<String> queryCollectTypes(Long accountId);
	/**
	 * 关注用户
	 * @param attentionId 被关注用户
	 * @param accountId   用户
	 * @return
	 */
	String attentionAccount(Long attentionId, Long accountId);

	String cancelAttentionAccount(Long attentionId, Long accountId);

	String collectTopic(Long topicId, Long accountId);

	List<Topic> queryTopicCarousel(String uname);

	List<Entry> queryEntryCarousel(Long accountId);

	List<Topic> queryHotTopic(Long accountId, Long loginAccountId);

	List<Object[]> queryFansAccount(Long accountId, int page, Long loginAccountId);

	List<Topic> queryIndexCollect(Long accountId);

	Long queryFansTotalCount(Long accountId);

	String cancelCollect(Long topicId, Long accountId);

}
