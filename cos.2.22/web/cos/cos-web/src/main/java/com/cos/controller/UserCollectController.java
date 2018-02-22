package com.cos.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.common.utils.JSONUtil;
import com.cos.common.utils.JSOUPUtil;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.RegularUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.Topic;
import com.cos.service.edit.ITopicEditService;
import com.cos.service.topic.ITopicService;

@Controller
public class UserCollectController extends BaseController {
	@Resource
	private ITopicService topicService;
	@Resource
	private ITopicEditService topicEditService;
	private Integer entry_width = 260;
	private Integer entry_height = 260;
	@RequestMapping(value="cc/tags", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getTopicTypeTag(String type){
		if(StringUtils.isNotBlank(type)){
			List<Map<String,String>> tagsMap=new ArrayList<Map<String,String>>();
			List<BaseDictionaryitem> tags=topicService.queryTagsByTopicType(type);
			for(BaseDictionaryitem item :tags){
				Map<String,String> filterMap=new HashMap<String,String>();
				filterMap.put("name", item.getDictitemName());
				filterMap.put("value", item.getDictitemValue());
				tagsMap.add(filterMap);
			}
			Map<String,List<Map<String,String>>> outs=new HashMap<String,List<Map<String,String>>>();
		     outs.put("tags", tagsMap);
			return JSONUtil.outPutObjectJSONData(outs);
		}
	 	List<BaseDictionaryitem> types= topicService.queryTopicType();
	    List<Map<String,String>> typeMap=new ArrayList<Map<String,String>>();
		for(BaseDictionaryitem item :types){
			Map<String,String> filterMap=new HashMap<String,String>();
			filterMap.put("name", item.getDictitemName());
			filterMap.put("value", item.getDictitemValue());
			typeMap.add(filterMap);
		}
		Map<String,List<Map<String,String>>> outs=new HashMap<String,List<Map<String,String>>>();
		outs.put("type", typeMap);
		return JSONUtil.outPutObjectJSONData(outs);
	}
	/**
	 * 插件收集图片
	 * @param imageData
	 * @param topicid
	 * @param fromurl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="cc/extendup", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uplaodCollectImg(String imageData,Long topicid) throws Exception{
		HashMap img= JSONUtil.changeFormString(imageData, HashMap.class);
		String width= img.get("width").toString();
		String height= img.get("height").toString();
		String w= width.lastIndexOf(".")!=-1?(width.substring(0,width.lastIndexOf("."))):width;
		String h= height.lastIndexOf(".")!=-1?(height.substring(0,height.toString().lastIndexOf("."))):height;
		Long entryId=PrimaryKeyGenerator.getPrimaryKey("entry");
		String title=URLDecoder.decode(img.get("title").toString(),"utf-8");
		String desc=URLDecoder.decode(img.get("desc").toString(),"utf-8");
		int width_=Integer.parseInt(w);
		int height_=Integer.parseInt(h);
		if (width_>height_) {
			width_ = width_ * entry_height / height_;
			height_ = entry_height;
		}
		if (height_>width_) {
			height_ = height_ * entry_width / width_;
			width_ = entry_width;
		}
        try {
		String out=topicEditService.addExtendEntry(this.getAccount(), entryId, title,desc, topicid, width_, height_,img.get("data").toString());
		return out;
        }catch(Exception e) {
        	return ReturnJSONUtil.getWarnInfo("当前图片上传错误!");
        }
		
	}
	@RequestMapping(value="cc/extendaddtopic", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String extendAddTopic(String tags,String type,String title,String token,String desc,String width,String height,String data) throws Exception{
		title=URLDecoder.decode(title,"utf-8");
		if(StringUtils.isBlank(title)){
			return  ReturnJSONUtil.getWarnInfo("标题不可以为空哦!");
		}
		if(title.length()>30){
			return  ReturnJSONUtil.getWarnInfo("标题不能超出30字哦!");
		}
		if(StringUtils.isNoneBlank(desc)){
			desc=URLDecoder.decode(desc,"utf-8");
		}else
		{
			desc="";
		}
		title=JSOUPUtil.pureText(title);
		title=RegularUtil.replaceSpecStr(title, RegularUtil.SPECIAL_CODE);

		if(StringUtils.isBlank(type)){
			return  ReturnJSONUtil.getWarnInfo("没有选择分类哦!");
		}
		if(StringUtils.isBlank(tags)){
			return ReturnJSONUtil.getWarnInfo("没有选择标签哦!");
		}

		Long id=PrimaryKeyGenerator.getPrimaryKey("topic");
		String w= width.lastIndexOf(".")!=-1?(width.substring(0,width.lastIndexOf("."))):width;
		String h= height.lastIndexOf(".")!=-1?(height.substring(0,height.toString().lastIndexOf("."))):height;
		String result=topicEditService.addExtendTopic(this.getAccount(), id,title,desc,type,tags,"no","article",Integer.parseInt(w),Integer.parseInt(h),data);
		return result;
	}
	@RequestMapping(value="cc/extendquerytopic", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String extendQueryTopic(String q) throws Exception{
		q = q.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		q=URLDecoder.decode(q, "UTF-8");
		if(StringUtils.isBlank(q)){
			return "[]";
		}
		List<Topic> ts= topicService.queryTopicByAccount(this.getAccount().getId(),q);
		List<Map<String,String>> filterMaps=new ArrayList<Map<String,String>>();
		for(Topic t:ts){
			Map<String,String> filterMap=new HashMap<String,String>();
			filterMap.put("title", t.getTitle());
			filterMap.put("id", t.getId().toString());
			filterMaps.add(filterMap);
		}
		return JSONUtil.outPutObjectJSONData(filterMaps);
	}
}
