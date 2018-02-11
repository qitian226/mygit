package com.cos.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.utils.JSONUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.Comment;
import com.cos.model.Entry;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.topic.ITopicService;

@RestController
public class TopicController extends BaseController {
	@Resource
	private ITopicService topicService;
	@Resource
	private ISysAccountService sysAccountService;

	/**
	 * 主题分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/topic/page")
	@ResponseBody
	public String queryTopicPage(String page,String type,String top){
		 List<Topic> topics=new ArrayList<Topic>();
		if(StringUtils.isNoneBlank(page)){
	     topics=topicService.queryTopicByPage(Integer.parseInt(page),type);
		}else{
		 topics=topicService.queryTopTopic(type);
		}
		return JSONUtil.outPutObjectJSONData(topics);
	}

	/**
	 * 主题
	 * @param id  topic id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/topic/{type}/{id}")
	public ModelAndView toTopic(@PathVariable Long id,Long e,@PathVariable String type) throws Exception{
		ModelAndView view=null;
		if(type.equals("photo")){
			view=new ModelAndView("topic/photo/flat_topic");
		}
		if(type.equals("article")){
			view=new ModelAndView("topic/flat_topic");
		}

		Topic topic=topicService.querySimpleTopic(id);
		view.addObject("topic",topic);
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		List<BaseDictionaryitem> tags=topicService.queryTagsByTopic(id);
		view.addObject("tags",tags);
		Map<String,Object> attention=topicService.queryAttention(topic.getCreateBy(),null==this.getAccount()?null:this.getAccount().getId());
		view.addObject("attention",attention);
		List<Entry> entrys=topicService.queryListEntrys(id);
		view.addObject("entrys",entrys);
		int grade=topicService.queryGradeByTargetId(null==this.getAccount()?null:this.getAccount().getId(),id);
		view.addObject("accountgrade", grade);//当前用户评价分数
		view.addObject("account",topic.getAccountName());
		SysAccount account=sysAccountService.querySimpleAccount(topic.getCreateBy());
		view.addObject("user",account);
		if(null!=e){
			view.addObject("entryid",e);
		}
		if(null!=this.getAccount() && topic.getCreateBy().compareTo(this.getAccount().getId())==0){
			view.addObject("isowner", "is");
		}else
		{
			view.addObject("isowner", "no");
		}
		int is= topicService.queryIsCollect(id,this.getAccount());
		view.addObject("iscollect", is);
		return view;
	}
	@RequestMapping(value="/entry/photo/{id}")
	public ModelAndView toEntry(@PathVariable Long id) throws Exception{
		ModelAndView view=new ModelAndView("topic/photo/flat_entry");
		Entry entry=topicService.querySimpleEntry(id);
		view.addObject("entry", entry);
		return view;
	}
	/**
	 * 碎片单条
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/entry/{id}")
	public ModelAndView toClistFirst(@PathVariable Long id) throws Exception{
		ModelAndView view=new ModelAndView("topic/flat_entry");
		Entry entry=topicService.querySimpleEntry(id);
		List<Comment> comments=topicService.queryCommentsByTargetId(id,1);
		Long totalPageNum=topicService.queryTotalPageNumByTargetId(id);
		view.addObject("comments", comments);
		view.addObject("entry", entry);
		view.addObject("page", 1);
		view.addObject("totalPageNum", totalPageNum);
		view.addObject("login", this.getAccount()==null?0:this.getAccount().getId());
		return view;
	}
	/**
	 * 获取碎片评论分页数据
	 * @param id
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/entry/clist/{id}/{page}")
	public ModelAndView toClistPage(@PathVariable Long id,@PathVariable int page) throws Exception{
		ModelAndView view=new ModelAndView("topic/clist_page");
		List<Comment> comments=topicService.queryCommentsByTargetId(id,page);
		view.addObject("comments", comments);
		view.addObject("page",page);
		view.addObject("entryId", id);
		Long totalPageNum=topicService.queryTotalPageNumByTargetId(id);
		view.addObject("totalPageNum", totalPageNum);
		view.addObject("login", this.getAccount()==null?0:this.getAccount().getId());
		return view;
	}
	@RequestMapping(value="/closedown/{id}")
	public String closeDownTopic(@PathVariable Long id){
		Topic topic=topicService.querySimpleTopic(id);
		short s=topic.getStatus()==(short)3?(short)2:(short)3;
		topic.setStatus(s);
		topicService.updateSingleTopic(topic);
		return ReturnJSONUtil.getSuccessInfo("查封成功!");
	}
}
