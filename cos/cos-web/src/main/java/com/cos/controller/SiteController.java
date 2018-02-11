package com.cos.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.controller.base.BaseController;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.ImgFile;
import com.cos.model.Topic;
import com.cos.service.sys.IFileService;
import com.cos.service.topic.ITopicService;

@RestController
public class SiteController  extends BaseController{

	@Resource
	private ITopicService topicService;
	@Resource
	private IFileService fileService;

	@RequestMapping(value="/miss.html")
	public ModelAndView toNotFound(){
		ModelAndView view=new ModelAndView("common/miss");
		return view;
	}
	@RequestMapping(value="/stay.html")
	public ModelAndView toError(){
		ModelAndView view=new ModelAndView("common/stay");
		return view;
	}
	@RequestMapping(value="/header.html")
	public ModelAndView toHeader() throws Exception{
		ModelAndView view=new ModelAndView("common/header");
		view.addObject("nickname", this.getAccount().getNickname());
		return view;
	}
	@RequestMapping(value="/500.html")
	public ModelAndView to500() throws Exception{
		ModelAndView view=new ModelAndView("error/500");
		return view;
	}
	@RequestMapping(value="/nologin.html")
	public ModelAndView toNoLogin() throws Exception{
		ModelAndView view=new ModelAndView("error/302");
		return view;
	}
	@RequestMapping(value="/statement.html")
	public ModelAndView toStatement() throws Exception{
		ModelAndView view=new ModelAndView("help/statement");
		view.addObject("loginuser", this.getAccount());
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		return view;
	}
	@RequestMapping(value="/message.html")
	public ModelAndView toLeaveMessage() throws Exception{
		ModelAndView view=new ModelAndView("help/leave-message");
		view.addObject("loginuser", this.getAccount());
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		return view;
	}
	@RequestMapping(value="/index.html")
	public ModelAndView toSiteIndex() throws Exception{
		ModelAndView view=new ModelAndView("index");
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		view.addObject("loginuser", this.getAccount());
		Map<String,List<Topic>> map=topicService.queryIndexTopTopics();
		view.addObject("topicmaps", map);
		List<ImgFile> files= fileService.queryIndexFiles();
		view.addObject("images", files);
		return view;
	}
}
