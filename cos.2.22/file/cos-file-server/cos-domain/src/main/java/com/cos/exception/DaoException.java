
package com.cos.exception;
/**
 * TODO What the class does
 * @author 齐昆仑
 * @date   2013年12月5日-下午3:30:50
 *
 */
public class DaoException extends NestedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3832658497652301938L;
	public DaoException(String msg) {
		super(msg);
	}
	public DaoException(String msg, Throwable ex){
		super(msg,ex);
	}
}
