package com.cos.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.utils.JSONUtil;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.controller.base.BaseController;
import com.cos.dao.SysAccountMapper;
import com.cos.model.BaseDictionary;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.SysAccount;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.sys.ISysDictionaryService;

@RestController
public class SysManageController extends BaseController {
	
	@Resource
	private ISysAccountService sysAccountService;
	
	@Resource
	private ISysDictionaryService sysDictionaryService;
	
	@RequestMapping(value="/sys/user.html")
	public ModelAndView toUser(){
	  ModelAndView view=new ModelAndView("background/sys_account");
	  return view;
	}
	
	@RequestMapping(value="/sys/dictionary.html")
	public ModelAndView toDictionary(){
	  ModelAndView view=new ModelAndView("background/sys_dictionary");
	  return view;
	}
	
	@RequestMapping(value="/sys/tags.html")
	public ModelAndView toTags(){
	  ModelAndView view=new ModelAndView("background/tags");
	  return view;
	}
	@RequestMapping(value="/sys/dic")
	public ModelAndView toDic(){
	  ModelAndView view=new ModelAndView("background/new_dic");
	  return view;
	}
	@RequestMapping(value="/sys/updic")
	public ModelAndView toUpdateDic(String code){
	  ModelAndView view=new ModelAndView("background/update_dic");
	  BaseDictionary dic=sysDictionaryService.queryDictionaryByCode(code);
	  view.addObject("dic", dic);
	  return view;
	}
	@RequestMapping(value="/sys/adddic")
	public String addDic(String code,String name,String desc) throws Exception{
	  BaseDictionary dic=new BaseDictionary();
	  dic.setId(PrimaryKeyGenerator.getPrimaryKey("basedictionary"));
	  dic.setDictCode(code.toLowerCase());
	  dic.setDictName(name);
	  dic.setDictDesc(desc);
	  dic.setCreatedBy(this.getAccount().getId());
	  dic.setCurrVersion((short) 1);
	  dic.setStatus((short) 1);
	  String out= sysDictionaryService.addDic(dic);
	  return out;
	}
	@RequestMapping(value="/sys/updatedic")
	public String updateDic(Long id, String code,String name,String desc) throws Exception{
	  BaseDictionary dic=sysDictionaryService.queryDictionaryById(id);
	  dic.setDictCode(code.toLowerCase());
	  dic.setDictName(name);
	  dic.setDictDesc(desc);
	  dic.setCreatedBy(this.getAccount().getId());
	  String out= sysDictionaryService.updateDic(dic);
	  return out;
	}
	@RequestMapping(value="/sys/deldic")
	public String delDic(String code) throws Exception{
	  String out= sysDictionaryService.virtualrRemoveDic(code);
	  return out;
	}
	@RequestMapping(value="/sys/dicitem")
	public ModelAndView toDicitem(String code){
	  ModelAndView view=new ModelAndView("background/new_dicitem");
	  view.addObject("code", code);
	  return view;
	}
	@RequestMapping(value="/sys/updicitem")
	public ModelAndView toUpdateDicitem(Long id){
	  ModelAndView view=new ModelAndView("background/update_dicitem");
	  BaseDictionaryitem dic=sysDictionaryService.queryDictionaryitemById(id);
	  view.addObject("item", dic);
	  return view;
	}
	@RequestMapping(value="/sys/adddicitem")
	public String addDicitem(String code,String name,String value,String desc) throws Exception{
	  BaseDictionaryitem dic=new BaseDictionaryitem();
	  dic.setId(PrimaryKeyGenerator.getPrimaryKey("basedictionary"));
	  dic.setDictCode(code.toLowerCase());
	  dic.setDictitemName(name);
	  dic.setDictitemValue(value);
	  dic.setSortId(0);
	  dic.setDictitemDesc(desc);
	  dic.setCreatedBy(this.getAccount().getId());
	  dic.setCurrVersion((short) 1);
	  dic.setStatus((short) 1);
	  String out= sysDictionaryService.addDictionary(dic);
	  return out;
	}
	@RequestMapping(value="/sys/updatedicitem")
	public String updateDicitem(Long id, String code,String name,String value,String desc) throws Exception{
	  BaseDictionaryitem dic=sysDictionaryService.queryDictionaryitemById(id);
	  dic.setDictitemName(name);
	  dic.setDictitemDesc(desc);
	  dic.setDictitemValue(value);
	  dic.setCreatedBy(this.getAccount().getId());
	  String out= sysDictionaryService.updateDictionary(dic);
	  return out;
	}
	@RequestMapping(value="/sys/deldicitem")
	public String delDicitem(Long id) throws Exception{
	  String out= sysDictionaryService.virtualrRemoveDicitem(id);
	  return out;
	}

	@RequestMapping(value="/sys/quser")
	@ResponseBody
	public String querySysUser(int page,int limit){
		List<SysAccount> sysAccounts= sysAccountService.querySysUserByPage(page,limit);
		Long count=sysAccountService.getTotalCountNum();
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("code", 0);
		map.put("msg", "success");
		map.put("count", count);
		map.put("data", sysAccounts);
		return JSONUtil.outPutObjectJSONData(map);
	}
	
	@RequestMapping(value="/sys/qdic")
	@ResponseBody
	public String queryDictionary(String word,int page,int limit){
		List<Map<String,Object>> maps=sysDictionaryService.queryDictionary(word,page,limit);
		int count=sysDictionaryService.getDicTotalCountNum();
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("code", 0);
		map.put("msg", "success");
		map.put("count", count);
		map.put("data", maps);
		return JSONUtil.outPutObjectJSONData(map);
	}
	@RequestMapping(value="/sys/qdicitem")
	@ResponseBody
	public String queryDictionaryItem(String code,int page,int limit){
		List<Map<String,Object>> maps=sysDictionaryService.queryDictionaryItem(code);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("code", 0);
		map.put("msg", "success");
		map.put("count", 0);
		map.put("data", maps);
		return JSONUtil.outPutObjectJSONData(map);
	}
	/**
	 * 查封用户
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/sys/lockuser")
	@ResponseBody
	public String lockUser(String ids){
		String out= sysAccountService.lockUser(ids); 
		return out;
	}
	@RequestMapping(value="/sys/unlockuser")
	@ResponseBody
	public String unlockUser(String ids){
		String out= sysAccountService.unLockUser(ids); 
		return out;
	}
	@RequestMapping(value="/sys/search")
	@ResponseBody
	public String searchSysUser(String word,int page,int limit){
		List<Map<String,Object>> maps= sysAccountService.searchSysUserByPage(word,page,limit);
		Long count=sysAccountService.getSearchTotalCountNum(word);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("code", 0);
		map.put("msg", "success");
		map.put("count", count);
		map.put("data", maps);
		return JSONUtil.outPutObjectJSONData(map);
	}
	
}
