package com.cos.service.topicManage;

import java.util.List;

import com.cos.model.Entry;
import com.cos.model.Topic;

public interface ITopicManageService {

	List<Topic> queryTopicMeta(Integer page,String params);

	Long queryTopicCount(String params);

	String addTopic(String title, String imgurl, String desc, Long accountId);

	Topic querySimpleTopic(String id);

	String addEntry(String title, String imgurl, String desc, String topicId, Long accountId);

	List<Entry> queryTopicEntrys(String topicId);

	Entry querySimpleEntry(String id);

	String updateTopic(String title, String imgurl, String desc, String topicId, Long accountId);

	String virtualRemoveTopic(String topicId);

	String virtualRemoveEntry(String entryId);

	String topicCheck(String ctype, Long id, String check);

	String topicQueryCheck(String ctype, Long id);
}
