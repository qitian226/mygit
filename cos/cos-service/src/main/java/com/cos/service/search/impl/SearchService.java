package com.cos.service.search.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cos.common.utils.JSOUPUtil;
import com.cos.dao.EntryMapper;
import com.cos.dao.FullIndexMapper;
import com.cos.dao.TopicMapper;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.Topic;
import com.cos.model.TopicExample;
import com.cos.service.search.ISearchService;

@Service
public class SearchService implements ISearchService {


	@Resource
	private FullIndexMapper fullIndexDao;
	@Resource
	private EntryMapper entryDao;
	@Resource
	private TopicMapper topicDao;

	@Override
	public List<Map<String,String>> queryTopicAndEntryByPage(String word,int first,int limit) {
		List<Map<String,Long>> maps=fullIndexDao.queryTopicAndEntryByPage(word,first,limit);
		List<Long> entryIds=new ArrayList<Long>();
		List<Long> topicIds=new ArrayList<Long>();
		for(Map<String,Long> item:maps){
			if(item.containsKey("entry_id_")){
				entryIds.add(item.get("entry_id_"));
			}else{
				topicIds.add(item.get("topic_id_"));
			}
		}
		List<Map<String,String>> jsonResults=new ArrayList<Map<String,String>>();
		if(entryIds.size()>0){
		EntryExample eexa=new EntryExample();
		eexa.createCriteria().andIdIn(entryIds);
		List<Entry> entrys=entryDao.selectByExample(eexa);
		for(Entry entry:entrys){
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", entry.getId().toString());
			map.put("topicId", entry.getTopicId().toString());
			map.put("title", entry.getTitle());
			if(StringUtils.isNotBlank(entry.getImgUrl())) {
				String[] urls=entry.getImgUrl().split(";");
				map.put("zoominUrl", urls[1]);
			}
			String desc=JSOUPUtil.pureText(entry.getDesc());
			map.put("desc", desc.length()>350?desc.substring(0, 350):desc);
			map.put("createTime",entry.getCreateTimeString());
			map.put("type", "entry");
			jsonResults.add(map);
		 }
		}
		if(topicIds.size()>0){
			TopicExample texa=new TopicExample();
			texa.createCriteria().andIdIn(topicIds);
			List<Topic> topics=topicDao.selectByExample(texa);
			for(Topic topic:topics){
				Map<String,String> map=new HashMap<String,String>();
				map.put("id", topic.getId().toString());
				map.put("title", topic.getTitle());
				if(StringUtils.isNotBlank(topic.getImgUrl())) {
					String[] urls=topic.getImgUrl().split(";");
					map.put("zoominUrl", urls[1]);
				}
				String desc=JSOUPUtil.pureText(topic.getDesc());
				map.put("desc", desc.length()>350?desc.substring(0, 350):desc);
				map.put("createTime",topic.getCreateTimeString());
				map.put("classify",topic.getClassify());
				map.put("type", "topic");
				jsonResults.add(map);
			}
		 }
		return jsonResults;
	}

	@Override
	public Long queryTopicAndEntryCount(String word) {
		Long count=fullIndexDao.queryTopicAndEntryCount(word);
		return count;
	}
}
