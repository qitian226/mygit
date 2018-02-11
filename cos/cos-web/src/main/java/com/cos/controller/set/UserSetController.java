package com.cos.controller.set;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.HeaderTokenizer.Token;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.authority.AuthorityUtil;
import com.cos.common.utils.AESUtil;
import com.cos.common.utils.RegularUtil;
import com.cos.controller.base.BaseController;
import com.cos.model.CityCode;
import com.cos.model.ProvinceCode;
import com.cos.model.SysAccount;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.userSet.IUserSetService;

@RestController
public class UserSetController extends BaseController {

	@Resource
	private IUserSetService userSetService;
	@Resource
	private ISysAccountService sysAccountService;
	
	@RequestMapping(value="/user/set")
	public ModelAndView toUserSetPage() throws Exception{
		ModelAndView view=new ModelAndView("set/user_info");
		List<ProvinceCode> provinces =userSetService.queryProvince();
		view.addObject("provinces", provinces);
		SysAccount account=this.getAccount();
		String mobile=account.getMobile();
		String first=mobile.substring(0, 3);
		String end=mobile.substring(7, 11);
		account.setMobile(first+"****"+end);
		view.addObject("user", account);
		return view;
	}
	@RequestMapping(value="/user/queryarea")
	public ModelAndView queryCity(Integer code){
		ModelAndView view=new ModelAndView("set/city");
		List<CityCode> citys =userSetService.queryCityCode(code);
		view.addObject("citys", citys);
		return view;
	}
	@RequestMapping(value="/user/up")
	public String updateUserInfo(HttpServletResponse response,String nickname,short sex,String email,String qq,String wechat,Integer province,Integer city,Integer area, String desc) throws Exception{
		SysAccount sysAccount=new SysAccount();
		StringBuffer msg=new StringBuffer();
		/*if(StringUtils.isBlank(nickname)){
			msg.append("昵称是必须的哦!<br>");
		}else{
			if(nickname.length()>10 || nickname.length()<2 ){
				msg.append("昵称长度在2和10之间哦!<br>");
			}
			if(!RegularUtil.isMatch(nickname,RegularUtil.INCLUDE_ENGLISH_CHINESE)){
				msg.append("昵称只是中英文哦!<br>");
			}
		}*/
		List<Integer> citys=new ArrayList<Integer>();
		if(province==null){
			msg.append("省是必须的哦!<br>");
		}else{
			citys.add(province);
		}
		if(city==null){
			msg.append("城市是必须的哦!<br>");
		}else{
			citys.add(city);
		}
		
		if(area!=null){
			citys.add(area);
		}
		if(StringUtils.isNotBlank(email)){
			if(email.length()>30){
				msg.append("邮箱长度不能超出30哦!<br>");
			}	
		if(!RegularUtil.isMatch(email,RegularUtil.EMAIL_REGEX)){
			msg.append("邮箱格式错误哦!<br>");
		}
		}
		if(StringUtils.isNotBlank(qq)){
		if(qq.length()>20){
			msg.append("QQ号码长度不能超出20哦!<br>");
		}
		if(!RegularUtil.isMatch(qq,RegularUtil.QQ_CODE)){
			msg.append("QQ号码格式错误哦!<br>");
		}
		}
		if(StringUtils.isNotBlank(wechat)){
			if(wechat.length()>20){
				msg.append("微信号码长度不能超出20哦!<br>");
			}
		if(!RegularUtil.isMatch(wechat,RegularUtil.WeChat_CODE)){
			msg.append("微信号码格式错误哦!<br>");
		 }
		}
		if(StringUtils.isBlank(desc)){
			msg.append("个人介绍是必须的哦!<br>");
		}else{
			if(desc.length()>300){
				msg.append("个人介绍长度不能超出300哦!<br>");
			}
			desc=RegularUtil.replaceSpecStr(desc,RegularUtil.SPECIAL_CODE);
		}
		if(msg.length()>0){
			 return "{\"success\":\"fail\",\"warn\":\""+msg.toString()+"\"}";
		}
		sysAccount.setId(this.getAccount().getId());
		sysAccount.setSex(sex);
		sysAccount.setEmail(email);
		sysAccount.setQq(qq);
		sysAccount.setWechat(wechat);
		sysAccount.setProvince(province);
		sysAccount.setCity(city);
		sysAccount.setArea(area);
		sysAccount.setDesc(desc);
		String out=userSetService.updateUserInfo(sysAccount,citys);
		return out;
	}
	@RequestMapping(value="/user/header")
	public ModelAndView toHeaderEdit(Long userid){
		ModelAndView view=new ModelAndView("set/header_edit");
		SysAccount account=sysAccountService.querySimpleAccount(userid);
		view.addObject("imgpath", account.getImgPath());
		return view;
	}
	@RequestMapping(value = "user/upload",produces = "text/plain;charset=UTF-8")  
 	@ResponseBody
    public String fileUploadSimpleFile(String imagedata) throws IllegalStateException, IOException {
		String out=userSetService.updateUserImg(imagedata,this.getAccount().getId());
        return out;
    }
	@RequestMapping(value = "user/uppass",produces = "text/plain;charset=UTF-8")  
 	@ResponseBody
    public String updatePassWord(HttpServletRequest request,String os,String reg) throws Exception {
		StringBuffer msg=new StringBuffer();
		Object token=null;
		if(StringUtils.isBlank(reg)){
			msg.append("验证码不能为空!");
		}
		else{
			token=request.getSession().getAttribute("captchaToken");
			if(null==token||!token.toString().toLowerCase().equals(reg.toLowerCase())){
				msg.append("验证码错误!");
			}
		}
		String info=new AESUtil().decryptString(os, token.toString());
		String[] infos=info.split(";");
		String oldpass=infos[0];
		String newpass=infos[1];
		if(StringUtils.isBlank(os)){
			msg.append("原密码不能为空!");
		}
		if(!RegularUtil.isMatch(newpass, RegularUtil.PASSWORD_REGEX)){
			msg.append("新密码格式错误,密码格式为头尾字母或数字,中间可包含特殊字符!");
		}
		 
		String out=userSetService.updateUserPassWord(this.getAccount().getId(),this.getAccount().getMobile(),oldpass,newpass);
        return out;
    }
}
