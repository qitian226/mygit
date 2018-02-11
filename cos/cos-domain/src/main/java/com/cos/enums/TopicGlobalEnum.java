
package com.cos.enums;

/**
 * Topic全局设置
 * TODO  TopicGlobalEnum enum
 * @author qikunlun
 * @date   2015年12月22日-下午5:45:40
 *
 */
public enum TopicGlobalEnum  {

	Top(20L, "置顶"),
	Carousel(10L, "轮播");

	public static String getName(Long value){
		for(TopicGlobalEnum t:TopicGlobalEnum.values()){
			if(value==t.getValue()){
				return t.getName();
			}
		}
		return null;
	}

	private Long value;
	private String name;


	public Long getValue() {
		return value;
	}


	public String getName() {
		return name;
	}

	TopicGlobalEnum(Long value, String name) {
		this.value = value;
		this.name = name;
	}


}
