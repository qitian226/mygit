package com.cos.enums;

public enum SiteUserTypeEnum   implements IBaseEnum {
	//员工 客户 系统临时 网站
	employee(1, "e"),customer(2, "c"),temp(3, "t"),site(4,"s");

	
	private int value;
	private String name;
	
	public static String getName(int value) {
		for(SiteUserTypeEnum t:SiteUserTypeEnum.values()){
			if(value==t.getValue()){
				return t.getName();
			}
		}
		return null;
	}
	
	SiteUserTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	@Override
	public int getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	 
	

}
