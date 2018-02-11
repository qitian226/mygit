
package com.cos.exception;
/**
 * TODO What the class does
 * @author 齐昆仑
 * @date   2013年12月5日-下午3:30:50
 *
 */
public class ServiceException  extends NestedException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4948541507887082254L;
	
	public ServiceException(String msg) {
		super(msg);
	}
	public ServiceException(String msg, Throwable ex){
		super(msg,ex);
	}
	
}
