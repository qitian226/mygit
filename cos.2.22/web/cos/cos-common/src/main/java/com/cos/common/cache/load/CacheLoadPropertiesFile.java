package com.cos.common.cache.load;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cos.common.cache.CacheEnum;
import com.cos.common.cache.CacheFactory;
import com.cos.common.cache.ICachePool;
 

/**
 * TODO What the class does
 * @author tiantian
 * @date   2014年5月8日-下午11:22:02
 *
 */
public final class CacheLoadPropertiesFile {
	private static Logger logger = LoggerFactory.getLogger(CacheLoadPropertiesFile.class);
	private final static String[] localFiles=new String[]{"filePath","treeIcon"};//系列加载内存属性文件
	private static ICachePool cachePool=CacheFactory.getCacheUtil(CacheEnum.LOCAL);	
	/**
	 * 初始化属性文件
	 */
	public static void init(){
		for(String fileName:localFiles){
			initCache(fileName);
		}
	}
/**
 * 初始化属性文件
 * @param cacheGroupName
 */
	public static void initCache(String cacheGroupName){
		if(!cachePool.checkCacheGroup(cacheGroupName)){
			cachePool.createCachePoolGroup(cacheGroupName);
		}
		if(cachePool.getCachePoolGroup(cacheGroupName).size()== 0){ //map值为空
			Map<String,Object> map=CacheLoadPropertiesFile.loadProperties(cacheGroupName); 
			cachePool.putCachePoolGroup(cacheGroupName, map);
		}
	}
	/**
	 * 获得属性值
	 * @param cacheGroupName
	 * @param key
	 * @return
	 */
	public static String getPropertiesValue(String cacheGroupName,String key){
		if(!cachePool.checkCacheGroup(cacheGroupName)||cachePool.getCachePoolGroup(cacheGroupName)== null){
			initCache(cacheGroupName);
		}
		Map<String,Object> map=cachePool.getCachePoolGroup(cacheGroupName);
		Object o=null;
			o=map.get(key);
			if(o==null){
			initCache(cacheGroupName); //补偿加载数据
			}
		return o==null?"":o.toString();
	}
	/**
	 * 加载属性文件
	 * @param filePath
	 * @return
	 */
	public static Map<String,Object>  loadProperties(String filePath){
    	 Locale locale = Locale.getDefault();  
    	 ResourceBundle localResource=null;
    	 try{
    		 localResource = ResourceBundle.getBundle(filePath, locale);
    	 }catch(NullPointerException e){
    		 logger.debug("CacheLoadPropertiesFile Load PropertiesFile Error, FilePath is Null");
    		 throw e;
    	 }catch(MissingResourceException ex){
    		 logger.debug("CacheLoadPropertiesFile Load PropertiesFile Error Not This FilePath:"+filePath);
    		 throw ex;
    	 }
    	 Set<String> keys= localResource.keySet();
         HashMap<String,Object> map=new HashMap<String,Object>();
         for(String key: keys){
             try
             {
                 map.put(key, new String(localResource.getString(key).getBytes("ISO-8859-1"),"UTF-8"));
             }
             catch (UnsupportedEncodingException e)
             {
            	logger.debug("CacheLoadPropertiesFile:"+filePath+" Encoding change error");
                throw new RuntimeException(e);
             }
         }
         return map;
    }
}
