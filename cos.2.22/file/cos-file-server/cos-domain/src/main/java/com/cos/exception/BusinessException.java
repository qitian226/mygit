
package com.cos.exception;
/**
 * TODO What the class does
 * @author 齐昆仑
 * @date   2013年12月5日-下午3:30:50
 *
 */
public class BusinessException extends NestedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2894610434397816398L;
	public BusinessException(String msg) {
		super(msg);
	}
	public BusinessException(String msg, Throwable ex){
		super(msg,ex);
	}

}
