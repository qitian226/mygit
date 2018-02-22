package com.cos.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.controller.base.BaseController;
import com.cos.model.BaseDictionaryitem;
import com.cos.service.search.ISearchService;
import com.cos.service.topic.ITopicService;
@RestController
public class SearchController extends BaseController  {
 
	private static final int page_limit = 20;
	@Resource
	private ISearchService searchService;
	@Resource
	private ITopicService topicService;
	
	@RequestMapping(value = "/beauty")
	public ModelAndView queryTopicAndEntry(String word) throws Exception{
		ModelAndView m=new ModelAndView("search/topic_s");
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		m.addObject("topictypes", types);
		List<Map<String,String>> jsonResults=searchService.queryTopicAndEntryByPage(word,0,20);
		Long count=searchService.queryTopicAndEntryCount(word);
		m.addObject("totalCount", count);
		m.addObject("word", word);
		m.addObject("result", jsonResults);
		m.addObject("loginuser", this.getAccount());
		return m;
	}
	@RequestMapping(value="ts/qcount/{word}")
	@ResponseBody
	public String queryTopicAndEntryCount(@PathVariable String word) {
		Long count=searchService.queryTopicAndEntryCount(word);
		return "{\"count\":\""+count+"\"}";
	}
	@RequestMapping(value = "/qs/{page}/{word}")
	public ModelAndView queryTopicAndEntryByPage(@PathVariable int page ,@PathVariable String word) throws Exception{
		ModelAndView m=new ModelAndView("search/s_comment");
		List<Map<String,String>> jsonResults=searchService.queryTopicAndEntryByPage(word,page*page_limit,20);
		m.addObject("result", jsonResults);
		return m;
	}
}
