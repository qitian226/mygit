package com.cos.common.cache;

import java.util.Map;

public interface ICachePool {
	/**
	 * 创建缓存分组
	 * @param groupKey
	 * @param map
	 * 
	 */
 public void createCachePoolGroup(String groupKey);
 /**
  * 检查缓存分组是否存在
  * @param groupKey
  * @return
  */
 public Boolean checkCacheGroup(String groupKey);
 /**
  * 获得缓存分组
  * @param groupKey
  * @return map
  * @throws NullPointerException
  */
 public Map<String,Object> getCachePoolGroup(String groupKey);
 /**
  * 移除缓存分组
  * @param groupKey
  * @throws NullPointerException
  */
 public void clearCachePoolGroup(String groupKey)throws NullPointerException;
 /**
  * 添加缓存分组
  * @param groupKey
  * @param map
  */
 public void putCachePoolGroup(String groupKey,Map<String,Object> map);
 /**
  * 获得缓存项
  * @param groupKey
  * @param key
  * @return
  * @throws NullPointerException
  */
 public Object getCachePoolItem(String groupKey,String key) throws NullPointerException;
 /**
  * 推入缓存项
  * @param groupKey
  * @param key
  * @param value
  * @throws NullPointerException
  */
 public void putCachePoolItem(String groupKey,String key,Object value) throws NullPointerException;
 /**
  * 移除缓存项
  * @param groupKey
  * @param key
  * @throws NullPointerException
  */
 public void removeCahcePoolItem(String groupKey,String key)  throws NullPointerException;
 
}
