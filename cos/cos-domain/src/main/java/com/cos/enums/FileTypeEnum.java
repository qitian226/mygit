package com.cos.enums;

public enum FileTypeEnum   implements IBaseEnum {
	
	Index(1, "index");

	
	private int value;
	private String name;
	
	public static String getName(int value) {
		for(FileTypeEnum t:FileTypeEnum.values()){
			if(value==t.getValue()){
				return t.getName();
			}
		}
		return null;
	}
	
	FileTypeEnum(int value, String name) {
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
