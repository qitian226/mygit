package com.cos.authority;


import java.io.IOException;
import java.io.PrintWriter;

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

import com.cos.common.utils.ReturnJSONUtil;
import com.cos.exception.BusinessException;



/**
 * TODO 通用权限过滤器
 * @author qikunlun
 * @date
 *
 */

public class AuthorityFilter implements Filter {

	    private static final Log logger = LogFactory.getLog(AuthorityFilter.class);

		private static final String ERROR_URI = "/nologin.html";

	    public AuthorityFilter() {
	    }
	    
	    private void outPrint( HttpServletResponse response,int status,String info) {
			 response.setStatus(status);
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.println(info);
   			response.flushBuffer();
			} catch (Exception e) {
				logger.error("sys out print error",e);
			}
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

		    	  //过滤未保护URl
		    	  if (authority.getIsPass()==true) {
		    	   Boolean isPass=AuthorityUtil.checkCookies(httpRequest);//校验cookie是否合法
			    	  if(!isPass){
			    		  throw new BusinessException("非法登录!");//非法登录
			    	  }  
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
		    	    	 throw new BusinessException("您需要用户登录!");//非法登录
		    	     }
		    	  }
		    	  
	        }
	        catch (Exception e)
	        {
	        	if (e instanceof BusinessException) {
	        		  outPrint(httpResponse,200,ReturnJSONUtil.getWarnInfo(e.getMessage()));
				} 
	        	else
	        	{
	        		outPrint(httpResponse,200,"<script>window.location.href='"+ERROR_URI+"'</script>");
	        	}
	        	logger.error("login error",e); 
	        }
		}

		/**
		 * @see Filter#init(FilterConfig)
		 */
		@Override
		public void init(FilterConfig fConfig) throws ServletException {

		}

	}
