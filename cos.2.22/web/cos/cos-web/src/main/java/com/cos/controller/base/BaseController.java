package  com.cos.controller.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cos.common.utils.ImageUtils;
import com.cos.exception.BusinessException;
import com.cos.model.Entry;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.topicManage.ITopicManageService;

/**
 * 各Controler公用属性
 * 
 * @author QI
 */
@Controller
public class BaseController {
 
	@Resource
	private ISysAccountService sysAccountService;

	/**
	 * 获取项目路径
	 * 
	 * @author
	 */
	public String getPath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	public String getSitePath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort();
		return basePath;
	}
	
	

	/**
	 * 获取用户使用的浏览器
	 * 
	 * @return
	 */
	public String getbrowser() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		String browser = request.getHeader("user-agent");
		if (browser.indexOf("Firefox") > -1) {
			return "Firefox";
		} else if (browser.indexOf("MSIE 8") > -1) {
			return "IE8";
		} else if (browser.indexOf("MSIE 9") > -1) {
			return "IE9";
		} else if (browser.indexOf("MSIE 10") > -1) {
			return "IE10";
		} else if (browser.indexOf("Chrome") > -1) {
			return "Chrome";
		}
		return "";
	}
 
	public SysAccount getAccount() throws UnsupportedEncodingException {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		  if(null!=request.getAttribute("uid_")){ //解密过的 已经登录
			  SysAccount account=sysAccountService.getAccount(Long.parseLong(request.getAttribute("uid_").toString()));
			  return account;
		  }else{
			  return null;
		  }
	}

}
