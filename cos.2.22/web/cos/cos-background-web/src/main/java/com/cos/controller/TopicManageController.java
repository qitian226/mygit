package com.cos.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.utils.JSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.Topic;
import com.cos.service.sys.ISysDictionaryService;
import com.cos.service.topicManage.ITopicManageService;

@Controller
public class TopicManageController extends BaseController {

	@Resource
	private ITopicManageService topicManageService;
	@Resource
	private ISysDictionaryService sysDictionaryService;

	@RequestMapping(value="topic/manage.html")
	public ModelAndView toTopicEdit()
	{
		ModelAndView view=new ModelAndView("background/topic_manage");
		List<BaseDictionaryitem> types=sysDictionaryService.queryDictionaryitemByCode("topic_type");
		view.addObject("types", types);
		return view;
	}

	@RequestMapping(value="/topic/query")
	public ModelAndView queryTopic(@RequestParam(value = "page", required = true) Integer page,String params)
	{
		params="["+params+"]";
		List<Topic> topics=topicManageService.queryTopicMeta(page,params);
		ModelAndView view=new ModelAndView("background/topic_list");
		view.addObject("topics", topics);
		return view;
	}
	@RequestMapping(value="/topic/querytags")
	@ResponseBody
	public String queryTags(@RequestParam(value = "type", required = true) String type)
	{
		List<BaseDictionaryitem> items=sysDictionaryService.queryDictionaryitemByCode(type);
		return JSONUtil.outPutObjectJSONData(items);
	}
	@RequestMapping(value="/topic/querycount")
	@ResponseBody
	public String queryTopicCount(String params)
	{
		params="["+params+"]";
		Long count=topicManageService.queryTopicCount(params);
		String result="{\"count\":\""+Long.toString(count)+"\"}";
		return result;
	}
	@RequestMapping(value="/topic/check")
	@ResponseBody
	public String topicCheck(String ctype,Long id,String check)
	{
		String result=topicManageService.topicCheck(ctype,id,check);
		return result;
	}
	@RequestMapping(value="/topic/qcheck")
	@ResponseBody
	public String topicQueryCheck(String ctype,Long id)
	{
		String result=topicManageService.topicQueryCheck(ctype,id);
		return result;
	}
	@RequestMapping(value = "/main.html")
	public ModelAndView layer() throws Exception {
	   ModelAndView view=new ModelAndView("background/main");
	   return view;
	}
}
