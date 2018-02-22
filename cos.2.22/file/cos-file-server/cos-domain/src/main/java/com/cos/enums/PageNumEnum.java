
package com.cos.enums;

/**
 * 分页数目
 * TODO  PageNumEnum enum
 * @author qikunlun
 * @date   2015年12月22日-下午5:45:40
 *
 */
public enum PageNumEnum implements IBaseEnum {
	 
	Topic(30, "Topic分页"),
	Atttention(5, "Topic分页"),
	Comment(10,"评论分页"), 
	Dynamic(10,"动态分页"), 
	Email(10,"邮件分页");
   
	public static String getName(int value){
		for(PageNumEnum t:PageNumEnum.values()){
			if(value==t.getValue()){
				return t.getName();
			}
		}
		return null;
	}
	
	private int value;
	private String name;

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	PageNumEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	
}
