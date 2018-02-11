package com.cos.authority;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cos.exception.BusinessException;



/**
 * TODO 通用权限过滤器
 * @author qikunlun
 * @date
 *
 */

public class AuthorityFilter implements Filter {

	 private static final Log logger = LogFactory.getLog(AuthorityFilter.class);

	    private static final String LOGIN_URI = "/mem/login";

		private static final String ERROR_URI = "/nologin.html";

	    public AuthorityFilter() {
	    }

		/**
		 * @see Filter#destroy()
		 */
		@Override
		public void destroy() {
		}

		/**
		 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
		 */
		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		   HttpServletRequest httpRequest=(HttpServletRequest)request;
		   HttpServletResponse httpResponse=(HttpServletResponse)response;
		    try
	        {

		    	  // 得到用户请求的URI
		    	  String request_uri = httpRequest.getRequestURI();
		    	  // 得到web应用程序的上下文路径
		    	  String ctxPath = httpRequest.getContextPath();
		    	  // 去除上下文路径，得到剩余部分的路径
		    	  String uri = request_uri.substring(ctxPath.length());
		    	  Authority authority=AuthorityUtil.filterUndefendedUrl(uri);

		    	  Boolean isPass=AuthorityUtil.checkCookies(httpRequest);//校验cookie是否合法
		    	  if(!isPass){
		    		  throw new BusinessException("非法登录!");//非法登录
		    	  }
		    	  //过滤未保护URl
		    	  if (authority.getIsPass()==true) {
		    	   chain.doFilter(request, response);
		    	  }
		    	  else {//校验保护URL
		    		  Boolean pass=AuthorityUtil.checkTocken(httpRequest);
		    	   if (pass)
		    	     {
		    	        chain.doFilter(request, response);
		    	     }
		    	     else
		    	     {
		    	    	 httpResponse.sendRedirect(ERROR_URI);
		    	     }
		    	  }
	        }
	        catch (Exception e)
	        {
	        	if(e instanceof BusinessException==false){
	        		logger.error(e.getMessage());
	        	}
	        }
		}

		/**
		 * @see Filter#init(FilterConfig)
		 */
		@Override
		public void init(FilterConfig fConfig) throws ServletException {

		}

	}
