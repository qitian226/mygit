package com.cos.common.cache.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cos.common.cache.CacheFactory;
import com.cos.common.cache.ICachePool;

 

/**
 * 内存构建桥接类 TODO What the class does
 * 
 * @author tiantian
 * @date 2014年5月8日-下午11:33:41
 * 
 */
public class CacheLocalUtil implements ICachePool {
	private static Logger logger = LoggerFactory.getLogger(CacheLocalUtil.class);
	private static Map<String, Map<String, Object>> CACHEPOOL;

	public CacheLocalUtil() {
	}

	private void initCachePool() {
		if (CACHEPOOL == null) {
			logger.debug("Create new CachePool");
			CACHEPOOL = new HashMap<String, Map<String, Object>>();
		}
	}

	@Override
	public void createCachePoolGroup(String groupKey)
			 {
		if (CACHEPOOL == null) {
			initCachePool();
		}
		if (!CACHEPOOL.containsKey(groupKey))
			logger.debug("Create new CachePoolGroup:"+groupKey);
			CACHEPOOL.put(groupKey, new HashMap<String,Object>());
	}
	@Override
	public Boolean checkCacheGroup(String groupKey) {
		if (CACHEPOOL == null) {
			initCachePool();
		}
		if(CACHEPOOL.containsKey(groupKey)){
			return true;
		}
		return false;
	}
	@Override
	public Map<String, Object> getCachePoolGroup(String groupKey){
		if (CACHEPOOL == null) {
			initCachePool();
		}
		if (CACHEPOOL.containsKey(groupKey)) {
			logger.debug("get CachePoolGroup:"+groupKey);
			Map<String, Object> map = CACHEPOOL.get(groupKey);
			return map;
		}
		return null;
	}
	
	@Override
	public void clearCachePoolGroup(String groupKey) throws NullPointerException {
		if (CACHEPOOL.containsKey(groupKey)) {
			 logger.debug("CachePool remove groupKey:" + groupKey);
			 CACHEPOOL.remove(groupKey);
		}
	}

	@Override
	public void putCachePoolGroup(String groupKey, Map<String, Object> valueMap) {
		Map<String, Object> map = getCachePoolGroup(groupKey);
		map.putAll(valueMap);
	}
	@Override
	public Object getCachePoolItem(String groupKey, String key) 
			{
		Map<String, Object> map = getCachePoolGroup(groupKey);
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return null;
		}
	}

	@Override
	public void putCachePoolItem(String groupKey, String key, Object value) throws NullPointerException
			 {
		Map<String, Object> map = getCachePoolGroup(groupKey);
		map.put(key, value);
	}
	
	@Override
	public void removeCahcePoolItem(String groupKey, String key) throws NullPointerException {
		Map<String, Object> map = getCachePoolGroup(groupKey);
		if(!map.containsKey(key)){
			throw new NullPointerException("cacheItem Key:" + key + " is null");
		}
		map.remove(key);
	}


	



}
