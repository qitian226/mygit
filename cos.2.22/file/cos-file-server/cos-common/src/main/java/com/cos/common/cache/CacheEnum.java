/* 
* Copyright (C) 2014,pyfried, 
* All Rights Reserved 
* Description: TODO 描述类的主要功能.   
* 
* @project 陪友网
* @author  tiantian
* @date    2014年5月8日-下午11:58:36
*
* 代码修改历史: 
**********************************************************
* 时间		       作者		          注释
* 2014年5月8日	       tiantian		     	Create
**********************************************************
*/
package com.cos.common.cache;

/**
 * TODO What the class does
 * @author tiantian
 * @date   2014年5月8日-下午11:58:36
 *
 */
public enum CacheEnum {

	    LOCAL("loacl",1),REMOTING("remoting",2);
	 // 成员变量  
	    private String name;
	    private int index; 
	    // 构造方法  
	    private CacheEnum(String name, int index) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    //覆盖方法  
	    @Override  
	    public String toString() {  
	        return this.index+"_"+this.name;  
	    }  
	
}
