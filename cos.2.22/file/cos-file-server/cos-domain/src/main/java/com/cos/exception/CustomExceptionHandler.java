package  com.cos.exception;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.utils.ReturnJSONUtil;




/**
 * TODO 异常集中处理器  controller范围内的异常在此集中处理，JSON返回调用端处理
 * @author 齐昆仑
 * @date   2013年11月5日-下午8:36:47
 * 
 */
    public class CustomExceptionHandler implements HandlerExceptionResolver {
        private static final Log logger = LogFactory.getLog(CustomExceptionHandler.class);
        @Override
        public ModelAndView resolveException(HttpServletRequest request,
                HttpServletResponse response, Object object, Exception exception) {
        	     logger.error("sys error",exception);
        		 response.setStatus(200);
                 response.setContentType("text/html;charset=UTF-8");
                 response.setCharacterEncoding("UTF-8");
                 PrintWriter writer = null;
    			try {
    				writer = response.getWriter();
    				if(null!=exception.getMessage()){
    				writer.println(ReturnJSONUtil.getWarnInfo(exception.getMessage()));
        			response.flushBuffer();
    				}
    			} catch (Exception e) {
    				logger.error("sys error",e);
    			}
                 return null;
    		    
        }
      
    }

