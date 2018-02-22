
package com.cos.common.cache.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cos.common.cache.CacheEnum;
import com.cos.common.cache.CacheFactory;
import com.cos.common.cache.ICachePool;
import com.cos.common.cache.load.CacheLoadPropertiesFile;



/**
 * TODO What the class does
 * @author tiantian
 * @date   2014年4月15日-下午9:54:06
 *
 */
public final class CacheSessionUtil {
 private static Logger logger = LoggerFactory.getLogger(CacheSessionUtil.class);	
 private static ICachePool cachePool=CacheFactory.getCacheUtil(CacheEnum.LOCAL);
 public static void createSession(String sessionKey){
	 logger.debug("Create new Session:"+sessionKey);
	 cachePool.createCachePoolGroup(sessionKey);
 }
 
 public static void putSession(String sessionKey,String key,Object value){
	 logger.debug("Put Session:"+sessionKey+" Session Key:"+key+" Session Value:"+value);
	 cachePool.putCachePoolItem(sessionKey, key, value);
 }
 
 public static Map<String,Object> getSession(String sessionKey){
	 logger.debug("Get Session:"+sessionKey);
	 return cachePool.getCachePoolGroup(sessionKey);
 }
 
 public static Object getSessionValue(String sessionKey,String key){
  Object o= cachePool.getCachePoolItem(sessionKey, key);
  logger.debug("Get SessionValue Key:"+key+" Value:"+o.toString());
  return o;
 }
 
}