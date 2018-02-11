package com.cos.enums;

public enum FansGroupEnum  implements IStatusEnum {

	Default((short)0, "未分组"),
	Fans((short)1, "粉丝分组"),
	Attendtions((short)2, "关注分组");

	public static String getName(short value){
		for(FansGroupEnum t:FansGroupEnum.values()){
			if(value==t.getValue()){
				return t.getName();
			}
		}
		return null;
	}

	private short value;
	private String name;

	@Override
	public short getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name;
	}

	FansGroupEnum(short value, String name) {
		this.value = value;
		this.name = name;
	}

}
