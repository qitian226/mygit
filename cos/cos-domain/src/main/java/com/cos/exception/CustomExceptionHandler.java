package  com.cos.exception;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;




/**
 * TODO 异常集中处理器
 * @author 齐昆仑
 * @date   2013年11月5日-下午8:36:47
 *
 */
    public class CustomExceptionHandler implements HandlerExceptionResolver {
        private static final Log logger = LogFactory.getLog(CustomExceptionHandler.class);
        @Override
        public ModelAndView resolveException(HttpServletRequest request,
                HttpServletResponse response, Object object, Exception exception) {
        	 ModelAndView view=new ModelAndView("error/500");
        	 if(exception  instanceof BusinessException){
        		 response.setStatus(500);
                 response.setContentType("text/html;charset=UTF-8");
                 response.setCharacterEncoding("UTF-8");
                 PrintWriter writer = null;
    			try {
    				writer = response.getWriter();
    				if(null!=exception.getMessage()){
    				writer.println(exception.getMessage());
        			response.flushBuffer();
    				}
    			} catch (Exception e) {
    				logger.error("sys error",e);
    			}
                 return view;
        	 }
    		logger.error("sys error",exception);
        	return view;

      }
    }

