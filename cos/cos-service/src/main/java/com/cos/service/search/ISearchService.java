package com.cos.service.search;

import java.util.List;
import java.util.Map;

public interface ISearchService {

	List<Map<String,String>> queryTopicAndEntryByPage(String word,int first,int limit);

	Long queryTopicAndEntryCount(String word);

}
