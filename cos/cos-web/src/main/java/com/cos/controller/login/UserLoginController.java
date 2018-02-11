
package com.cos.controller.login;

import java.awt.Color;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cos.authority.AuthorityUtil;
import com.cos.common.utils.AESUtil;
import com.cos.common.utils.DESCoder;
import com.cos.common.utils.HashCodeUtil;
import com.cos.common.utils.RegularUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.exception.BusinessException;
import com.cos.model.SysAccount;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.user.IRegAccountService;




/**
 * TODO What the class does
 *
 * @author qikunlun
 * @date 2015年11月16日-下午10:52:10
 *
 */
@Controller
public class UserLoginController extends BaseController {

	@Resource
	private IRegAccountService regAccountService;
	@Resource
	private ISysAccountService sysAccountService;

	private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
	private static Random random = new Random();
	static {
		cs.setColorFactory(new ColorFactory() {
			@Override
			public Color getColor(int x) {
				int[] c = new int[3];
				int i = random.nextInt(c.length);
				for (int fi = 0; fi < c.length; fi++) {
					if (fi == i) {
						c[fi] = random.nextInt(71);
					} else {
						c[fi] = random.nextInt(256);
					}
				}
				return new Color(c[0], c[1], c[2]);
			}
		});
		RandomWordFactory wf = new RandomWordFactory();
		wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
		wf.setMaxLength(4);
		wf.setMinLength(4);
		cs.setWordFactory(wf);
	}
	@RequestMapping(value = "/login")
	public String toLogin(HttpServletRequest request) throws IOException {
		return "login/login";
	}
	@RequestMapping(value = "/loginout")
	public void loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
		AuthorityUtil.removeCookie(response, "/","tocken_");
		AuthorityUtil.removeCookie(response, "/","uid_");
		AuthorityUtil.removeCookie(response, "/","nname_");
		AuthorityUtil.removeCookie(response, "/","u_");
		AuthorityUtil.removeCookie(response, "/","post_");
		response.sendRedirect(this.getSitePath());
	}
	@RequestMapping(value = "/register")
	public String toRegiter(HttpServletRequest request) throws IOException {
		return "login/register";
	}

	@RequestMapping(value = "/loginmem", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String loginMem(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String regcode=request.getParameter("rc").toLowerCase();
		String pass=request.getParameter("s");//对称校验传输值 (mobile;password)regcode加密
		Object token=null;

	 	if(StringUtils.isBlank(regcode)){
	 		return ReturnJSONUtil.getWarnInfo("验证码不能为空!");
		}
		else{
			token=request.getSession().getAttribute("captchaToken");
			if(null==token||!token.toString().toLowerCase().equals(regcode)){
				return ReturnJSONUtil.getWarnInfo("验证码错误!");
			}
		}
		String info=new AESUtil().decryptString(pass, token.toString().toLowerCase());
		String[] infos=info.split(";");
		String 	mobile=infos[0];
	    String password=infos[1];
		if(StringUtils.isBlank(mobile)){
			    return ReturnJSONUtil.getWarnInfo("手机号码是必须的哦!");
			}else{
				if(!RegularUtil.isMatch(mobile,RegularUtil.MOBILE_CODE)){
					  return ReturnJSONUtil.getWarnInfo("手机号码格式错误!");
				}
		  }
		if(StringUtils.isBlank(password)){
			 return ReturnJSONUtil.getWarnInfo("密码是必须的哦!");
		}
		 SysAccount account=regAccountService.checkLoginUser(mobile,password);
		  try {
			String  privateKey=AuthorityUtil.getIpAddr(request);
			String tocken=HashCodeUtil.createHash(account.getId().toString()+"@"+privateKey);
		    AuthorityUtil.addCookie(response, "/","tocken_", tocken, 20*60*24);
		    DESCoder des = new DESCoder(privateKey);
		    String uuid=des.encrypt(account.getId().toString());
		    AuthorityUtil.addCookie(response, "/","uid_", uuid, 20*60*24);
		    AuthorityUtil.addCookie(response, "/","u_", account.getAccountName(), 20*60*24);
		    AuthorityUtil.addCookie(response, "/","nname_", URLEncoder.encode(null==account.getNickname()?"小白":account.getNickname(), "UTF-8"), 20*60*24);
		    if(null!=account.getVip()){
		    AuthorityUtil.addCookie(response, "/","vlevel_", account.getVip().toString(), 20*60*24);
		    }
		    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
		    	 return ReturnJSONUtil.getWarnInfo("非法登录!");
		}
		return ReturnJSONUtil.getSuccessInfo("登录成功!");
	}
	/** register
	 * @throws Exception **/
	@RequestMapping(value = "/reguser", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String regAccount(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String regcode=request.getParameter("regcode");
		String nickname=request.getParameter("nickname");
		String reginfo=request.getParameter("s_");//客户端组合加密 用户名; 密码;手机号;;
        String agreement=request.getParameter("agreement");

        if(Boolean.parseBoolean(agreement)==false){
        	return ReturnJSONUtil.getWarnInfo("您不同意协议，不能提供注册!");
        }
		if(StringUtils.isBlank(regcode)){
			return ReturnJSONUtil.getWarnInfo("验证码不能为空!");
		}
		else{
			Object token=request.getSession().getAttribute("captchaToken");
			if(null==token||!token.toString().toLowerCase().equals(regcode.toLowerCase())){
				return ReturnJSONUtil.getWarnInfo("验证码错误!");
			}
		}
		if(StringUtils.isBlank(nickname)){
			return ReturnJSONUtil.getWarnInfo("昵称是必须的哦!");
		}else{
			if(nickname.length()>10 || nickname.length()<2){
				return ReturnJSONUtil.getWarnInfo("昵称长度在2和10之间!");
			}
			if(!RegularUtil.isMatch(nickname,RegularUtil.INCLUDE_ENGLISH_CHINESE)){
				return ReturnJSONUtil.getWarnInfo("昵称只是中英文哦!");
			}
		}
		if(StringUtils.isBlank(reginfo)){
			return ReturnJSONUtil.getWarnInfo("注册用户失败!");
		}
		String info=new AESUtil().decryptString(reginfo, regcode.toLowerCase());
		String[] infos=info.split(";");
		String reguser=infos[0];
		String password=infos[1];
		String mobile=infos[2];
		if(StringUtils.isBlank(reguser)){
			return ReturnJSONUtil.getWarnInfo("用户名不能为空!");
		}
		else{
			reguser=reguser.toLowerCase();
			if(reguser.length()<4||reguser.length()>12){
				return ReturnJSONUtil.getWarnInfo("用户名只能4到12位之间!");
			}
		if(!RegularUtil.isMatch(reguser, RegularUtil.LETTER_AND_NUMBER_REGEX)){
			return ReturnJSONUtil.getWarnInfo("用户名只能为字母和数字!");
			}
		}
		if(StringUtils.isBlank(mobile)){
			return ReturnJSONUtil.getWarnInfo("手机号码是必须的哦!");

		}else{
			if(!RegularUtil.isMatch(mobile,RegularUtil.MOBILE_CODE)){
				return ReturnJSONUtil.getWarnInfo("手机号码格式错误!");
			}
		}
		if(StringUtils.isBlank(password)){
			return ReturnJSONUtil.getWarnInfo("密码不能为空!");
		}
		if(!RegularUtil.isMatch(password, RegularUtil.PASSWORD_REGEX)){
			return ReturnJSONUtil.getWarnInfo("密码格式为头尾字母或数字,中间可以是特殊字符!");
		}
        String result=regAccountService.regAccount(reguser.toLowerCase(),password,mobile,nickname.toLowerCase(),"s");
        return result;
	}
	@RequestMapping(value="/reginfo")
   public ModelAndView regAccountInfo(Long uid) throws Exception{
		ModelAndView view=new ModelAndView("login/register_info");
		SysAccount account= sysAccountService.querySimpleAccount(uid);
		if(null==account){
			throw new BusinessException("错误请求参数!请刷新");
		}
		view.addObject("user", account);
		return view;
   }
	/* 验证码 */
	@RequestMapping(value="/securitycode")
	public void crimg(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		switch (random.nextInt(5)) {
		case 0:
			cs.setFilterFactory(new CurvesRippleFilterFactory(cs
					.getColorFactory()));
			break;
		case 1:
			cs.setFilterFactory(new MarbleRippleFilterFactory());
			break;
		case 2:
			cs.setFilterFactory(new DoubleRippleFilterFactory());
			break;
		case 3:
			cs.setFilterFactory(new WobbleRippleFilterFactory());
			break;
		case 4:
			cs.setFilterFactory(new DiffuseRippleFilterFactory());
			break;
		}
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		setResponseHeaders(response);
		String token = EncoderHelper.getChallangeAndWriteImage(cs, "png",
				response.getOutputStream());
		session.setAttribute("captchaToken", token);

	}

	protected void setResponseHeaders(HttpServletResponse response) {
		response.setContentType("image/png");
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		long time = System.currentTimeMillis();
		response.setDateHeader("Last-Modified", time);
		response.setDateHeader("Date", time);
		response.setDateHeader("Expires", time);
	}
}
