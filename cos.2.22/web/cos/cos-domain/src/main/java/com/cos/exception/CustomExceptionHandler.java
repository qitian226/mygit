package  com.cos.exception;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;




/**
 * TODO 异常集中处理器  处理范围在Controller 需要甩到页面的异常，这里集中处理，提示的信息直接方法内JSON返回
 * @author 齐昆仑
 * @date   2013年11月5日-下午8:36:47
 *
 */
    public class CustomExceptionHandler implements HandlerExceptionResolver {
        private static final Log logger = LogFactory.getLog(CustomExceptionHandler.class);
        @Override
        public ModelAndView resolveException(HttpServletRequest request,
                HttpServletResponse response, Object object, Exception exception) {
        	ModelAndView view=null;
        	 if(exception  instanceof BusinessException){
        		 view =new ModelAndView("error/501");
        		 view.addObject("exception", exception);
        	 }else {
        		 view=new ModelAndView("error/500");
        		 view.addObject("error", exception);
        	 }
    		logger.error("sys error",exception);
        	return view;

      }
    }

