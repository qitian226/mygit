package com.cos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cos.common.utils.JSONUtil;
import com.cos.common.utils.JsonModel;
import com.cos.common.utils.RegularUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.exception.BusinessException;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.Comment;
import com.cos.model.Entry;
import com.cos.model.Reply;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.page.PageModel;
import com.cos.service.comment.ICommentService;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.topic.ITopicService;


@RestController
public class TopicController extends BaseController {
	@Resource
	private ITopicService topicService;
	@Resource
	private ISysAccountService sysAccountService;
	@Resource
	private ICommentService commentService;
	/**
	 * topic主页 未分页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/topic/{type}/index")
	public ModelAndView toTopicTypeIndex(@PathVariable String type,Integer column) throws Exception{
		ModelAndView view=new ModelAndView("topic/index");
		view.addObject("topictype", type);
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		for(BaseDictionaryitem item:types){
			if(item.getDictitemName().equals(type)){
				view.addObject("topicTypeTitle", item.getDictitemValue());
				break;
			}
		}
		view.addObject("loginuser", this.getAccount());//当前登录用户
		List<BaseDictionaryitem> tags=topicService.queryTagsByTopicType(type);
		view.addObject("tags", tags);
	    Long total=topicService.queryTotalCount(type);
		view.addObject("total",total);
		    if(null==column){
		    	column=5;
		    }
			List<PageModel> list= topicService.queryModelTopicByPage(type,1,column);
			view.addObject("lists",list);
		return view;
	}
	@RequestMapping(value="/topic/{type}/{tag}/index")
	public ModelAndView toTopicTagIndex(@PathVariable String type,@PathVariable String tag,Integer column) throws Exception{
		ModelAndView view=new ModelAndView("topic/index");
		view.addObject("topictype", type);
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		view.addObject("tag", tag);
		for(BaseDictionaryitem item:types){
			if(item.getDictitemName().equals(type)){
				view.addObject("topicTypeTitle", item.getDictitemValue());
				break;
			}
		}
		view.addObject("loginuser", this.getAccount());//当前登录用户
		List<BaseDictionaryitem> tags=topicService.queryTagsByTopicType(type);
		view.addObject("tags", tags);
	    Long total=topicService.queryTotalCount(type,tag);
		view.addObject("total",total);
		    if(null==column){
		    	column=5;
		    }
			List<PageModel> list= topicService.queryModelTopicByPage(type,tag,1,column);
			view.addObject("lists",list);
		return view;
	}
	/**
	 * topic 主页 分页模式
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/topic/{type}/p/index")
	public ModelAndView toTopicPageIndex(@PathVariable String type,Integer pageno,Integer column) throws Exception{
		ModelAndView view=new ModelAndView("topic/page_index");
		view.addObject("topictype", type);
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);
		for(BaseDictionaryitem item:types){
			if(item.getDictitemName().equals(type)){
				view.addObject("topicTypeTitle", item.getDictitemValue());
				break;
			}
		}
		List<BaseDictionaryitem> tags=topicService.queryTagsByTopicType(type);
		view.addObject("tags", tags);
		view.addObject("loginuser", this.getAccount());//当前登录用户
	    if(null==pageno){
	    	pageno=1;
	    }
	    if(null==column){
	    	column=5;
	    }
		List<PageModel> list= topicService.queryModelTopicByPage(type,pageno,column);
		view.addObject("lists",list);

		Long totalPage =topicService.queryTotalPageSize(type);
		view.addObject("pageNo",pageno);
		view.addObject("column",column);
		view.addObject("showPages",10);//显示页数
		view.addObject("totalPage",totalPage);
		view.addObject("url","topic/erciyuan/p/index.html");
		return view;
	}
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
	@RequestMapping(value="/topic/auto/{id}")
	public ModelAndView toTopic(@PathVariable Long id,Long e) throws Exception{
		ModelAndView view=null;
		Topic topic=topicService.querySimpleTopic(id);
		String type=topic.getClassify();
		if(type.equals("photo")){
			view=new ModelAndView("topic/photo/flat_topic");
		}
		if(type.equals("article")){
			view=new ModelAndView("topic/flat_topic");
		}

		view.addObject("topic",topic);
		List<BaseDictionaryitem> types= topicService.queryTopicType();
		view.addObject("topictypes", types);

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
	/**
	 *点赞
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/comment/praise")
	@ResponseBody
	public String praiseComment(String id) throws Exception{
		String out=commentService.praiseComment(id,this.getAccount().getId());
		return out;
	}
	/**
	 *删除评论
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/comment/remove/{id}")
	@ResponseBody
	public String removeComment(@PathVariable Long id) throws Exception{
		String out=commentService.removeComment(id,this.getAccount().getId());
		return out;
	}
	/**
	 * 针对评论新发表回复
	 * @param foucsuser //回复用户
	 * @param c
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="comment/submitreply")
	public ModelAndView submitReply(Long foucsuser,String c,Long id,String tocken) throws Exception{
		ModelAndView view=new ModelAndView("topic/clist_reply");
		if(StringUtils.isBlank(c)){
			throw new BusinessException("评论不可以为空哦!");
		}else{
			if(c.length()>200){
				throw new BusinessException("评论长度不能超出300!");
			}
			c=RegularUtil.replaceSpecStr(c,RegularUtil.SPECIAL_CODE);
		}
		Reply r=commentService.submitReply(foucsuser,c,id,this.getAccount(),tocken);
		if(null==r){
			return null;
		}
		view.addObject("reply",r);
		view.addObject("commentId",id);
		return view;
	}
	/**
	 * 针对碎片新发表评论
	 * @param c
	 * @param id
	 * @param g
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="entry/submitcomment")
	public String submitComment(String c,Long id,String g,String tocken) throws Exception{

		if(StringUtils.isBlank(c)){
			throw new BusinessException("评论不可以为空哦!");
		}else{
			if(c.length()>200){
				throw new BusinessException("评论长度不能超出300!");
			}
			c=RegularUtil.replaceSpecStr(c,RegularUtil.SPECIAL_CODE);
		}
		int count=commentService.submitComment(c,id,this.getAccount(),g,tocken);
		if(count==0){
			  return ReturnJSONUtil.getWarnInfo("重复提交!");
		}
		return ReturnJSONUtil.getSuccessExtendInfo("发表评论成功!", new JsonModel("cnum",count));
	}
	/**
	 * 刷新评价人数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="entry/getnum", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getGrade(Long id) throws Exception{
		Entry entry=topicService.querySimpleEntry(id);
		return ReturnJSONUtil.getSuccessExtendInfo("刷新分数成功!", new JsonModel("cnum",entry.getCommentNum()));
	}


	@RequestMapping(value="/index/top/entry/page", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryIndexTopEntryPage(String page){
		List<Entry> ts= topicService.queryIndexTopEntrys(page);
		return JSONUtil.outPutObjectJSONData(ts);
	}

	@RequestMapping(value="/page")
	public ModelAndView toPage(int pageno) throws Exception{
		ModelAndView view=new ModelAndView("common/pagination");
		view.addObject("pageNo",pageno);
		view.addObject("showPages",10);
		view.addObject("totalPage",8);
		view.addObject("url","page");
		return view;
	}

}
