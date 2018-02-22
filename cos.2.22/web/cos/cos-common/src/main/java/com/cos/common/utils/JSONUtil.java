package com.cos.common.utils;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;




/**
 *
 * @author QI
 *
 */

public abstract class JSONUtil {
	public static <T> T changeFormString(String json,Class<T> t){
		Gson gson = new GsonBuilder() .registerTypeAdapter(Short.class, new TypeAdapter<Short>() {
            @Override
            public void write(JsonWriter out, Short value) throws IOException {
                out.value(String.valueOf(value));
            }
            @Override
            public Short read(JsonReader in) throws IOException {
                try {
                    return Short.parseShort(in.nextString());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }).registerTypeAdapter(Long.class, new TypeAdapter<Long>() {
            @Override
            public void write(JsonWriter out, Long value) throws IOException {
                out.value(String.valueOf(value));
            }
            @Override
            public Long read(JsonReader in) throws IOException {
                try {
                    return Long.parseLong(in.nextString());
                } catch (NumberFormatException e) {
                    return 0L;
                }
            }
        }).registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
            @Override
            public void write(JsonWriter out, Integer value) throws IOException {
                out.value(String.valueOf(value));
            }
            @Override
            public Integer read(JsonReader in) throws IOException {
                try {
                    return Integer.parseInt(in.nextString());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        })
        .create();
		T r= gson.fromJson(json,t);
		return r;
	}
	public static <T> List<T> parseJsonArrayWithGson(String jsonData,Class<T> type) {
			         Gson gson = new Gson();
			         List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
			         }.getType());
			         return result;
			     }
	/**
	 * 输出分页JSON字符串
	 *
	 * @param listMap
	 * @return String
	 */
	public static String outPutPageJSONData(Object listMap) {
		Gson gson = new GsonBuilder()
		        .registerTypeAdapter(Long.class, new TypeAdapter<Long>() {
		            @Override
		            public void write(JsonWriter out, Long value) throws IOException {
		                out.value(String.valueOf(value));
		            }
		            @Override
		            public Long read(JsonReader in) throws IOException {
		                try {
		                    return Long.parseLong(in.nextString());
		                } catch (NumberFormatException e) {
		                    return 0L;
		                }
		            }
		        })
		        .create();
		   Map<String,Object>  items=new HashMap<String,Object>();
		   items.put("rows", listMap);
		   String out=gson.toJson(items);
		return out;
	}
	public static String outPutPageJSONData(Object listMap,int totalcount) {
		Gson gson = new GsonBuilder()
		        .registerTypeAdapter(Long.class, new TypeAdapter<Long>() {
		            @Override
		            public void write(JsonWriter out, Long value) throws IOException {
		                out.value(String.valueOf(value));
		            }
		            @Override
		            public Long read(JsonReader in) throws IOException {
		                try {
		                    return Long.parseLong(in.nextString());
		                } catch (NumberFormatException e) {
		                    return 0L;
		                }
		            }
		        }).registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
		            @Override
		            public void write(JsonWriter out, Integer value) throws IOException {
		                out.value(String.valueOf(value));
		            }
		            @Override
		            public Integer read(JsonReader in) throws IOException {
		                try {
		                    return Integer.parseInt(in.nextString());
		                } catch (NumberFormatException e) {
		                    return 0;
		                }
		            }
		        }).registerTypeAdapter(Object.class, new TypeAdapter<Object>() {
		            @Override
		            public void write(JsonWriter out, Object value) throws IOException {
		                out.value(String.valueOf(value));
		            }
		            @Override
		            public Object read(JsonReader in) throws IOException {
		                    return  in.nextString();
		            }
		        })
		        .create();
		   Map<String,Object>  items=new HashMap<String,Object>();
		   items.put("total", totalcount);
		   items.put("rows", listMap);
		   String out=gson.toJson(items);
		return out;
	}
	public static String outPutObjectJSONData(Object listMap) {
		  Gson gson = new GsonBuilder()
			        .registerTypeAdapter(Long.class, new TypeAdapter<Long>() {
			            @Override
			            public void write(JsonWriter out, Long value) throws IOException {
			                out.value(String.valueOf(value));
			            }
			            @Override
			            public Long read(JsonReader in) throws IOException {
			                try {
			                    return Long.parseLong(in.nextString());
			                } catch (NumberFormatException e) {
			                    return 0L;
			                }
			            }
			        }).registerTypeAdapter(BigDecimal.class,  new TypeAdapter<BigDecimal>() {
			            @Override
			            public void write(JsonWriter out, BigDecimal value) throws IOException {
			                out.value(String.valueOf(value));
			            }
			            @Override
			            public BigDecimal read(JsonReader in) throws IOException {
			                try {
			                    return new BigDecimal(in.nextString());
			                } catch (NumberFormatException e) {
			                    throw e;
			                }
			            }
			        }).registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
			            @Override
			            public void write(JsonWriter out, Integer value) throws IOException {
			                out.value(String.valueOf(value));
			            }
			            @Override
			            public Integer read(JsonReader in) throws IOException {
			                try {
			                    return Integer.parseInt(in.nextString());
			                } catch (NumberFormatException e) {
			                    return 0;
			                }
			            }
			        }).registerTypeAdapter(BigInteger.class, new TypeAdapter<BigInteger>() {
			            @Override
			            public void write(JsonWriter out, BigInteger value) throws IOException {
			                out.value(String.valueOf(value));
			            }
			            @Override
			            public BigInteger read(JsonReader in) throws IOException {
			            	  try {
				                    return new BigInteger(in.nextString());
				                } catch (NumberFormatException e) {
				                    return new BigInteger("0");
				                }
			            }
			        }).create();
		   String out=gson.toJson(listMap);
		return out;
	}



}