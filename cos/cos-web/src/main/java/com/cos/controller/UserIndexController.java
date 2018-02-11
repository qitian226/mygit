package com.cos.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.utils.JSONUtil;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.Entry;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.sys.ISysDictionaryService;
import com.cos.service.topic.ITopicService;
import com.cos.service.user.IUserIndexService;

@RestController
public class UserIndexController extends BaseController {
	@Resource
	private IUserIndexService userIndexService;
	@Resource
	private ITopicService topicService;
	@Resource
	private ISysAccountService sysAccountService;
	@Resource
	private ISysDictionaryService sysDictionaryService;
    /**
     * 登录用户
     * @param username
     * @return
     * @throws Exception
     */
	@RequestMapping(value="u/{username}", produces = "text/html;charset=UTF-8")
	public ModelAndView toUserSiteIndex(@PathVariable String username) throws Exception{
		ModelAndView view=new ModelAndView("user/index");
		view.addObject("pagetype", "topic");
		view.addObject("username", username);
		//当前查看用户
		SysAccount account=sysAccountService.querySimpleAccountByname(username);
		view.addObject("user",account);
		view.addObject("loginuser", this.getAccount());//当前登录用户
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		List<Topic> topics=userIndexService.queryTopTopic(account.getId(),(null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("topTopics",topics);
		List<Topic> hottopics=userIndexService.queryHotTopic(account.getId(),(null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("hotTopics",hottopics);
	    Long total=userIndexService.queryTopicTotalCount(account.getId(),null,null,(null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("total",total);
		List<Object[]> atas=userIndexService.queryAttentionAccount(account.getId(),1,(null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("attentions",atas);
		List<Object[]> fans=userIndexService.queryFansAccount(account.getId(),1,(null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("fans",fans);
		String usrname_=(null==this.getAccount())?"":this.getAccount().getAccountName();
		if(username.equals(usrname_)){
			view.addObject("isowner", "is");
		}else
		{
			view.addObject("isowner", "no");
		}
		Map<String,Object> attention=topicService.queryAttention(account.getId(), (null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("attention",attention);
		List<Topic> dynamic_topics= userIndexService.queryDynamicByPage(account.getId(),1,(null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("dynamic_topics",dynamic_topics);
		List<Topic> collect_topics= userIndexService.queryIndexCollect(account.getId());
		view.addObject("c_topics",collect_topics);
		return view;
	}

	@RequestMapping(value="u/{username}/{pagetype}", produces = "text/html;charset=UTF-8")
	public ModelAndView toUserModelSite(@PathVariable String username,@PathVariable String pagetype) throws Exception{
		ModelAndView view=new ModelAndView("user/"+pagetype);
		view.addObject("pagetype", pagetype);
		view.addObject("username", username);
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
/*		List<BaseDictionaryitem> tags=topicService.queryTopicTags();
		view.addObject("topictags", tags);*/
		view.addObject("loginuser", this.getAccount());//当前登录用户
		SysAccount account=sysAccountService.querySimpleAccountByname(username);//当前查看用户
		String desc=(null==account.getDesc())?"":account.getDesc();
		account.setDesc(desc.length()>108?desc.substring(0, 108):desc);
		view.addObject("user",account);
		Map<String,Object> attention=topicService.queryAttention(account.getId(), (null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("attention",attention);
		String usrname_=(null==this.getAccount())?"":this.getAccount().getAccountName();
		if(username.equals(usrname_)){
			view.addObject("isowner", "is");
		}else
		{
			view.addObject("isowner", "no");
		}

		if(pagetype.equals("fans")){
			Long count=userIndexService.queryFansTotalCount(account.getId());
			view.addObject("total", count);
			return view;
		}
		if(pagetype.equals("attention")){
		    List<Object[]> atas=userIndexService.queryAttentionAccount(account.getId(),1,(null==this.getAccount())?null:this.getAccount().getId());
			view.addObject("attentions",atas);
			Long total=userIndexService.queryAttentionAccountCount(account.getId());
			view.addObject("total",total);
			return view;
		}
		if(pagetype.equals("dynamic")){
		    Long total=userIndexService.queryDynamicTotalCount(account.getId());
			view.addObject("total",total);
			List<Topic> topics= userIndexService.queryDynamicByPage(account.getId(),1);
			view.addObject("topics",topics);
			return view;
		}
		if(pagetype.equals("topic")){
		 Long total=userIndexService.queryTopicTotalCount(account.getId(),null,null,(null==this.getAccount())?null:this.getAccount().getId());
		 view.addObject("total",total);
		 return view;
		}
		if(pagetype.equals("collect")){
			List<String> ts=userIndexService.queryCollectTypes(account.getId());
			StringBuffer buf=new StringBuffer();
			if(ts.size()>0){
			for(String  t: ts){
				buf.append("\""+t+"\"");
				buf.append(",");
			}
			String t=buf.substring(0, buf.length()-1);
			view.addObject("types",t);
			}else{
			view.addObject("types","'no'");
			}
			return view;
		}
		return view;
	}
	@RequestMapping(value="u/topic/carousel", produces = "text/html;charset=UTF-8")
	public String queryTopicCarousel(String uname){
		List<Topic> urls= userIndexService.queryTopicCarousel(uname);
		return JSONUtil.outPutObjectJSONData(urls);
	}
	@RequestMapping(value="u/topic/page", produces = "text/html;charset=UTF-8")
	public ModelAndView queryTopicPage(String username,String page,String type,String tag) throws Exception{
		ModelAndView view=new ModelAndView("user/common/topic");
		SysAccount account=sysAccountService.querySimpleAccountByname(username);
		List<Topic> topics=userIndexService.queryTopicPage(account.getId(),page,type,tag,this.getAccount()==null?null:this.getAccount().getId());
		view.addObject("topics", topics);
		String usrname_=(null==this.getAccount())?"":this.getAccount().getAccountName();
		if(username.equals(usrname_)){
			view.addObject("isowner", "is");
		}else
		{
			view.addObject("isowner", "no");
		}
		return view;
	}
	@RequestMapping(value="u/loadfans/page", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryFansPage(Long userid,int page) throws Exception{
		ModelAndView view=new ModelAndView("user/common/fan");
		List<Object[]> fans=userIndexService.queryFansAccount(userid,page,(null==this.getAccount())?null:this.getAccount().getId());
		view.addObject("fans", fans);
		return view;
	}
	@RequestMapping(value="u/topic/page/count", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryTopicPageCount(String username,String type,String tag) throws Exception{
		SysAccount account=sysAccountService.querySimpleAccountByname(username);
		Long result=userIndexService.queryTopicTotalCount(account.getId(),type,tag,(null==this.getAccount())?null:this.getAccount().getId());
		return ReturnJSONUtil.getSuccessExtendInfo("ok", new JsonModel("count",result));
	}
	@RequestMapping(value="u/entry/page", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryEntryPage(String username,String page){
		SysAccount account=sysAccountService.querySimpleAccountByname(username);
		List<Entry> entrys=userIndexService.queryEntryPage(account.getId(),page);
		return JSONUtil.outPutObjectJSONData(entrys);
	}
	@RequestMapping(value="u/attention/page", produces = "text/html;charset=UTF-8")
	public ModelAndView queryAttentionPage(String userid,String page) throws Exception{
		ModelAndView view=new ModelAndView("user/common/attention");
		List<Object[]> as=userIndexService.queryAttentionAccount(Long.parseLong(userid),Integer.parseInt(page),this.getAccount().getId());
		view.addObject("attentions",as);
		return view;
	}
	/**
	 * 查询最新动态;以主题维度分页
	 * @param userid
	 * @param page
	 * @param prev_date
	 * @return
	 */
	@RequestMapping(value="u/dynamic/page", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryDynamicPage(String userid,String page,String prev_date){
		ModelAndView view=new ModelAndView("user/common/dynamic");
		List<Topic> topics= userIndexService.queryDynamicByPage(Long.parseLong(userid),Integer.parseInt(page));
		view.addObject("topics",topics);
		view.addObject("prev_date",prev_date);
		return view;
	}
	@RequestMapping(value="u/collect", produces = "text/html;charset=UTF-8")
	public ModelAndView queryCollectTopicPage(Long userid,String type){
		ModelAndView view=new ModelAndView("user/common/collect");
		if(type.equals("no")){ //没有采集数据
			view.addObject("topics", null);
		}else{
		List<Topic> topics=userIndexService.queryCollectTopics(userid,type);
		view.addObject("topics", topics);
		BaseDictionaryitem item= sysDictionaryService.queryDicValueByTypeAndName("topic_type",type);
		view.addObject("type", item.getDictitemValue());
		}
		return view;
	}
	/**
	 * 关注用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="attention/u/{id}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String attentionAccount(@PathVariable Long id) throws Exception{
		 String out=userIndexService.attentionAccount(id,this.getAccount().getId());
		return out;
	}
	@RequestMapping(value="cancelattention/u/{id}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String cancelAttentionAccount(@PathVariable Long id) throws Exception{
		 String out=userIndexService.cancelAttentionAccount(id,this.getAccount().getId());
		return out;
	}
	@RequestMapping(value="collect/u/{id}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String collectTopic(@PathVariable Long id) throws Exception{
		String out=userIndexService.collectTopic(id,(null==this.getAccount())?null:this.getAccount().getId());
		return out;
	}
	@RequestMapping(value="cancelcollect/u/{id}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String cancelCollect(@PathVariable Long id) throws Exception{
		String out=userIndexService.cancelCollect(id,(null==this.getAccount())?null:this.getAccount().getId());
		return out;
	}


}
