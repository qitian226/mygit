package com.cos.authority;

import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.common.utils.DESCoder;
import com.cos.common.utils.HashCodeUtil;
import com.cos.common.utils.PropertiesFileUtil;

public final class AuthorityUtil {
	/**
	 * 格式：/login;/images/**
	 */
	private static List<String> defendedUris=new ArrayList<String>();;
	/**
	 * 格式：/images/**
	 */
	private static List<String> defendedPackgeUris=new ArrayList<String>();

	public static Authority filterUndefendedUrl(String uri) throws Exception {
		if(defendedUris==null||defendedPackgeUris==null||
				defendedUris.size()==0||defendedPackgeUris.size()==0){
			initFilterCfg();
		}
		Authority authority=new Authority();
		String extendFileName=null;
		if(uri.indexOf(".")!=-1){
			extendFileName=uri.substring(uri.indexOf("."), uri.length());
		    switch (extendFileName) {
			case "jsp":
				authority.setIsURI(true);
				break;
			case "html":
				authority.setIsURI(true);
				break;
			default:
				authority.setIsFile(true);
				break;
			}
		}else{
			authority.setIsURI(true);
		}

		String[] uris = uri.split("/");
		String checkUri = null;
		authority.setIsPass(true);
		if (uris.length > 2) { //校验包权限
			checkUri = uris[1];
			for (String url : defendedPackgeUris) {
				if (url.equals(checkUri)) {
					authority.setIsPass(false);
					return authority;
				}
			}
		}
			checkUri = uri;
			if(checkUri.startsWith("/")){
			checkUri=checkUri.substring(1, checkUri.length());
			}
			for (String url : defendedUris) {
				if (url.equals(checkUri)) {
					authority.setIsPass(false);
					return authority;
				}

			}
		return authority;
	}
	/**
     * 加载所有过滤方法
     * @return
     * @throws Exception
     */
    private static void initFilterCfg() throws Exception
    {
    	defendedUris.clear();
    	defendedPackgeUris.clear();
        ResourceBundle localResource = PropertiesFileUtil.getPropertiesLocale("authority-url");
        String undefendedUri= localResource.getString("defendeduri");
        String undefendedPackgeUri=localResource.getString("defendedpackageuri");
        String[] uris=undefendedUri.split(";");
        for(String uri:uris){
        	defendedUris.add(uri);
        }
        String[] packageUris=undefendedPackgeUri.split(";");
        for(String uri:packageUris){
        	defendedPackgeUris.add(uri);
        }
    }

   public static String encodeUri(String uri) throws Exception{

    	return URLEncoder.encode(uri, "UTF-8");
   }

   public static String dencodeUri(String uri) throws Exception{
	   return new String(uri.getBytes("ISO8859-1"),"UTF-8");
   }
   /**
    * 校验tocken合法
    * @param request
    * @return
 * @throws InvalidKeySpecException
 * @throws NoSuchAlgorithmException
    * @throws GeneralSecurityException
    */
   public static Boolean checkTocken(HttpServletRequest request) throws Exception{
	   String ip=getIpAddr(request);
	   Cookie[] cookies=request.getCookies();
	   if(cookies==null){
		   return false;
	   }
	   int length=cookies.length;
	   String cookie_tocken=null;
	   String cookie_uid=null;
	   for(int i=0;i<length;i++){
		   if(cookies[i].getName().equals("tocken_")){
			   cookie_tocken=cookies[i].getValue();
		   }
		   if(cookies[i].getName().equals("uid_")){
			   cookie_uid=cookies[i].getValue();

		   }
	   }
	   if(ip==null||cookie_uid==null||cookie_tocken==null){
		   return false;
	   }
		try {
			DESCoder des = new DESCoder(ip);
			String uuid=des.decrypt(cookie_uid);
			request.setAttribute("uid_", uuid);//传递UUID
			if(HashCodeUtil.validatePassword(uuid+"@"+ip, cookie_tocken)){
				   return true;
			   }
		} catch (Exception e) {
			throw new RuntimeException("登录异常!", e);
		}
	   return false;
   }

   public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

   public static void addCookie(HttpServletResponse response,String path,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath(path);
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
   public static void removeCookie(HttpServletResponse response,String path,String name){
	    Cookie cookie = new Cookie(name,null);
	    cookie.setPath(path);
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);
	}
public static Boolean checkCookies(HttpServletRequest request) {
	 Cookie[] cookies=request.getCookies();
	 if(cookies==null){
		   return true;
	   }
	 String ip=getIpAddr(request);
	 int length=cookies.length;
	   String cookie_tocken=null;
	   String cookie_uid=null;
	   for(int i=0;i<length;i++){
		   if(cookies[i].getName().equals("tocken_")){
			   cookie_tocken=cookies[i].getValue();
		   }
		   if(cookies[i].getName().equals("uid_")){
			   cookie_uid=cookies[i].getValue();
		   }
	   }
	   if(null!=cookie_tocken && null!=cookie_uid){
		   try {
				DESCoder des = new DESCoder(ip);
				String uuid=des.decrypt(cookie_uid);
				request.setAttribute("uid_", uuid);//传递UUID
				if(HashCodeUtil.validatePassword(uuid+"@"+ip, cookie_tocken)){
					   return true; //合法
				   }else{
					   return false;
				   }
			} catch (Exception e) {
				throw new RuntimeException("登录异常", e);
			}
	   }
	return true;
}
}