package com.cos.controller;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.utils.JSOUPUtil;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.RegularUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.exception.BusinessException;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.Entry;
import com.cos.model.Topic;
import com.cos.service.edit.ITopicEditService;
import com.cos.service.topic.ITopicService;

@RestController@RequestMapping(value="/edit")
public class TopicEditController extends BaseController {
	@Resource
	private ITopicEditService topicEditService;
	@Resource
	private ITopicService topicService;

	/**
	 * 主题创建页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/topic/create")
	public ModelAndView toTopicCreatePage() throws Exception{
		ModelAndView view=new ModelAndView("edit/topic_create");
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		view.addObject("loginuser", this.getAccount());//当前登录用户
		Long id=PrimaryKeyGenerator.getPrimaryKey("topic");
		view.addObject("topicid", id);
		return view;
	}
	@RequestMapping(value="/topic/loadtags")
	public ModelAndView loadTags(String type) throws Exception{
		ModelAndView view=new ModelAndView("edit/tags");
		List<BaseDictionaryitem> tags=topicService.queryTopicTags(type);
		view.addObject("topictags", tags);
		return view;
	}
	/**
	 * 主题封面创建及修改页面
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/topic/cover/edit/{id}")
	public ModelAndView toTopicCoverEditPage(@PathVariable Long id) throws Exception{
		ModelAndView view=new ModelAndView("edit/topic_cover_edit");
		Topic topic=topicService.querySimpleTopic(id);
		view.addObject("topic", topic);
		return view;
	}

	@RequestMapping(value="/topic/entry/add/{topicid}/{classify}")
	@ResponseBody
	public ModelAndView toEntryAddPage(@PathVariable Long topicid,@PathVariable String classify) throws Exception
	{
		ModelAndView view=new ModelAndView("edit/topic_entry_edit");
		Long id=PrimaryKeyGenerator.getPrimaryKey("entry");
		view.addObject("topicId", topicid);
		view.addObject("entryId", id);
		view.addObject("classify", classify);
		return view;
	}
	@RequestMapping(value="/topic/entry/edit/{topicid}/{entryid}/{classify}")
	@ResponseBody
	public ModelAndView toEntryEditPage(@PathVariable Long topicid,@PathVariable Long entryid,@PathVariable String classify) throws Exception
	{
		ModelAndView view=new ModelAndView("edit/topic_entry_edit");
		Entry entry=topicService.querySimpleEntry(entryid);
		view.addObject("topicId", topicid);
		view.addObject("entry", entry);
		view.addObject("uptoken", "is");
		view.addObject("classify", classify);
		return view;
	}
	@RequestMapping(value="/topic/save")
	@ResponseBody
	public String saveTopic(Long id,String title,String desc,String type,String tag,String smodel)  throws Exception
	{
		if(StringUtils.isBlank(title)){
			return  ReturnJSONUtil.getWarnInfo("标题不可以为空哦!");
		}
		if(title.length()>30){
			return  ReturnJSONUtil.getWarnInfo("标题不能超出30字哦!");
		}
		title=JSOUPUtil.pureText(title);
		title=RegularUtil.replaceSpecStr(title, RegularUtil.SPECIAL_CODE);
        if(StringUtils.isNotBlank(desc)){
		desc=JSOUPUtil.filterBasic(desc);
		if(desc.length()>1000){
			return  ReturnJSONUtil.getWarnInfo("描述不能超出30字哦!");
		}
        }
		if(StringUtils.isBlank(type)){
			return  ReturnJSONUtil.getWarnInfo("没有选择分类哦!");
		}
		if(StringUtils.isBlank(tag)){
			return ReturnJSONUtil.getWarnInfo("没有选择标签哦!");
		}
		if(StringUtils.isBlank(smodel)){
			return ReturnJSONUtil.getWarnInfo("没有选择显示模式哦!");
		}
		String result=topicEditService.addTopic(id,title,desc,type,tag,this.getAccount().getId(),"no",smodel);
		return result;
	}
	@RequestMapping(value="/topic/publish")
	@ResponseBody
	public String publishTopic(Long id)  throws Exception
	{
		String result=topicEditService.publishTopic(id,this.getAccount().getId());
		return result;
	}
	@RequestMapping(value="/topic/update/{id}")
	@ResponseBody
	public String updateTopic(String title,String imgurl,String desc,String type,String tag,@PathVariable Long id,String smodel)  throws Exception
	{

		if(StringUtils.isBlank(title)){
			return  ReturnJSONUtil.getWarnInfo("标题不可以为空哦!");
		}
		if(title.length()>30){
			return  ReturnJSONUtil.getWarnInfo("标题不能超出30字哦!");
		}
		title=JSOUPUtil.pureText(title);
		title=RegularUtil.replaceSpecStr(title, RegularUtil.SPECIAL_CODE);
        if(StringUtils.isNotBlank(desc)){
		desc=JSOUPUtil.filterBasic(desc);
		if(desc.length()>1000){
			return  ReturnJSONUtil.getWarnInfo("描述不能超出30字哦!");
		}
        }
		if(StringUtils.isBlank(type)){
			return  ReturnJSONUtil.getWarnInfo("没有选择分类哦!");
		}
		if(StringUtils.isBlank(tag)){
			return ReturnJSONUtil.getWarnInfo("没有选择标签哦!");
		}
		if(StringUtils.isBlank(smodel)){
			return ReturnJSONUtil.getWarnInfo("没有选择显示模式哦!");
		}
		String result=topicEditService.updateTopic(title,imgurl,desc,type,tag,this.getAccount().getId(),id,smodel);
		return result;
	}
	@RequestMapping(value="/topic/remove/{id}")
	@ResponseBody
	public String removeTopic(@PathVariable Long id) throws Exception
	{
		String result=topicEditService.removeTopic(id,this.getAccount());
		return result;
	}
	@RequestMapping(value="/entry/remove/{id}")
	@ResponseBody
	public String removeEntry(@PathVariable Long id) throws Exception
	{
		String result=topicEditService.removeEntry(id,this.getAccount().getId());
		return result;
	}
	@RequestMapping(value="/entry/refresh")
	public ModelAndView refreshEntry(Long entry)
	{
		ModelAndView view=new ModelAndView("topic/entry");
		Entry model=topicEditService.querySimpleEntry(entry);
		view.addObject("entry", model);
		return view;
	}

	@RequestMapping(value = "/topic/edit/{id}")
	public ModelAndView topicEdit(@PathVariable Long id) throws Exception {
		Topic t=topicEditService.querySimpleTopic(id);
		if(null==t){
			throw new BusinessException("无此主题存在!");
		}
		if(t.getStatus()==0){
			throw new BusinessException("此主题已经被删除!");
		}
		List<Entry> entrys=topicEditService.queryTopicEntrys(id);
		ModelAndView view=new ModelAndView("edit/topic_edit");
		view.addObject("topic",t);
		view.addObject("entrys",entrys);
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		List<BaseDictionaryitem> topictags= topicService.queryTagsByTopicType(t.getType());
		view.addObject("topictags", topictags);
		view.addObject("loginuser", this.getAccount());//当前登录用户
		return view;
	}
	/**
	 * 设置topic轮播
	 * @param topic
	 * @return
	 *//*
	@RequestMapping(value = "/topic/setcarousel")
	@ResponseBody
	public String topicSetCarousel(@RequestParam(required=true)Long topic){
		String result=topicEditService.setCarousel(topic);
		return result;
	}
	*//**
	 * 取消topic轮播
	 * @param topic
	 * @return
	 *//*
	@RequestMapping(value = "/topic/cancelcarousel")
	@ResponseBody
	public String topicCancelCarousel(@RequestParam(required=true)Long topic){
		String result=topicEditService.cancelCarousel(topic);
		return result;
	}
	*//**
	 * 设置entry轮播
	 * @param topic
	 * @return
	 *//*
	@RequestMapping(value = "/entry/setcarousel")
	@ResponseBody
	public String entrySetCarousel(@RequestParam(required=true)Long entry){
		String result=topicEditService.setEntryCarousel(entry);
		return result;
	}
	*//**
	 * 取消entry轮播
	 * @param topic
	 * @return
	 *//*
	@RequestMapping(value = "/entry/cancelcarousel")
	@ResponseBody
	public String entryCancelCarousel(@RequestParam(required=true)Long entry){
		String result=topicEditService.cancelEntryCarousel(entry);
		return result;
	}*/
	@RequestMapping(value = "/topic/uppublic")
	@ResponseBody
	public String updateTopicPublic(@RequestParam(required=true)Long id,@RequestParam(required=true)int status) throws Exception{
		String result=topicEditService.updateTopicPublic(id,status,this.getAccount().getId());
		return result;
	}
	@RequestMapping(value = "/topic/uptop")
	@ResponseBody
	public String updateTopicTop(@RequestParam(required=true)Long id,@RequestParam(required=true)int top) throws Exception{
		String result=topicEditService.updateTopicTop(id,top,this.getAccount().getId());
		return result;
	}
	@RequestMapping(value = "/topic/upcarousel")
	@ResponseBody
	public String updateTopicCarousel(@RequestParam(required=true)Long id,@RequestParam(required=true)int carousel) throws Exception{
		String result=topicEditService.updateTopicCarousel(id,carousel,this.getAccount().getId());
		return result;
	}
}
