package com.cos.service.edit;

import java.util.List;

import com.cos.model.Entry;
import com.cos.model.SysAccount;
import com.cos.model.Topic;

public interface ITopicEditService {


	String addTopic(Long topicId,String title, String desc,String type,String tag ,Long accountId,String istop,String smodel);

	Topic querySimpleTopic(Long id);

	String addEntry(Long entryId,String title, String imgurl, String desc, Long topicId, Long accountId,Integer width, Integer height);

	String addExtendEntry(Long entryId,String title,String desc, Long topicId, Long accountId,Integer width, Integer height,String imgdata)  throws Exception;

	List<Entry> queryTopicEntrys(Long topicId);

	Entry querySimpleEntry(Long id);

	String updateTopic(String title, String imgurl, String desc,String type,String tag, Long accountId, Long topicId,String smodel);

	String removeTopic(Long topicId,SysAccount account);

	String removeEntry(Long entryId,Long accountId);

	String updateEntry(String title, String imgurl, String desc, Long entryId,Long accountId,Integer width, Integer height);

	String setCarousel(Long topic);

	String cancelCarousel(Long topic);

	String setEntryCarousel(Long entry);

	String cancelEntryCarousel(Long entry);

	String publishTopic(Long id,Long accountId);

	String updateTopicPublic(Long id, int status,Long accountId);

	String updateTopicTop(Long id, int istop,Long accountId);

	String updateTopicCarousel(Long id, int carousel, Long id2);

	String addExtendTopic(Long topicId,String title, String desc,String type,String tag ,Long accountId,String istop,String smodel,Integer width, Integer height,String imgdata) throws Exception;

}
