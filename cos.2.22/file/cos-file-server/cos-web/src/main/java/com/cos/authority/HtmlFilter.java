
package  com.cos.authority;

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

/**
 * TODO 通用权限过滤器
 * @author tiantian
 * @date   2014年4月14日-下午4:57:29
 *
 */

public class HtmlFilter implements Filter {
    private static final Log logger = LogFactory.getLog(HtmlFilter.class);
    private static final String ERROR_URI = "/error";
    public HtmlFilter() {
    
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}
	private static String getExtensionName(String filename) {   
	        if ((filename != null) && (filename.length() > 0)) {   
	            int dot = filename.lastIndexOf('.');   
	            if ((dot >-1) && (dot < (filename.length() - 1))) {   
	                return filename.substring(dot + 1);   
	            }   
	        }   
	        return filename;   
	    } 
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	   HttpServletRequest httpRequest=(HttpServletRequest)request;
	   HttpServletResponse httpResponse=(HttpServletResponse)response;
	   // 得到用户请求的URI
 	    String request_uri = httpRequest.getRequestURI();
 	   // 得到web应用程序的上下文路径
 	    String ctxPath = httpRequest.getContextPath();
	    try
        {
	    	  // 去除上下文路径，得到剩余部分的路径
	    	  String uri = request_uri.substring(ctxPath.length());
	    	  String extendFileName=getExtensionName(uri);
	    	  if(extendFileName.equals("html")||extendFileName.equals("htm")){
	    		  chain.doFilter(request, response);
	    	  }
        }
        catch (Exception e)
        {
            httpResponse.sendRedirect(ctxPath+ERROR_URI);
            logger.error("AuthorityFilter Error:"+e.toString());
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
