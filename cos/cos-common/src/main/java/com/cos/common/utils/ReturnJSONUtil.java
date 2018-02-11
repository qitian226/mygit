package com.cos.common.utils;

public abstract class ReturnJSONUtil {

	public static String getSuccessInfo(String info){
		 String str="{\"isPass\":\"ok\",\"msg\":\"%s\"}";
		 return String.format(str, info);
	}
	public static String getSuccessExtendInfo(String info,JsonModel...  jsonModels){
		 String str="\"isPass\":\"ok\",\"msg\":\"%s\"";
		 str= String.format(str, info);
		 StringBuffer b=new StringBuffer();
		 b.append(str);
		 if(jsonModels.length>0){
		  b.append(",");
		  for(JsonModel model:jsonModels){
			 b.append("\""+model.getKey()+"\":\""+model.getValue()+"\",");
		   }
         String out="{"+ b.substring(0, b.length()-1)+"}";
         return out;
		 }
		 return null;
	}

	public static String getWarnInfo(String info){
		 String str="{\"isPass\":\"no\",\"warn\":\"%s\"}";
		 return String.format(str, info);
	}
	public static String getWarnExtendInfo(String info,JsonModel...  jsonModels){
		 String str="\"isPass\":\"no\",\"warn\":\"%s\"";
		 str= String.format(str, info);
		 StringBuffer b=new StringBuffer();
		 b.append(str);
		 if(jsonModels.length>0){
		  b.append(",");
		  for(JsonModel model:jsonModels){
			 b.append("\""+model.getKey()+"\":\""+model.getValue()+"\",");
		   }
        String out="{"+ b.substring(0, b.length()-1)+"}";
        return out;
		 }
		 return null;
	}
}

