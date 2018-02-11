package com.cos.common.cache.util;

import java.util.HashMap;
import java.util.Map;

import com.cos.common.cache.ICachePool;




/**
 * 内存构建桥接类
 * TODO What the class does
 * @author tiantian
 * @date   2014年5月8日-下午11:33:41
 *
 */
public class CacheRemotingUtil implements ICachePool {
	private static Map<String, Map<String, Object>> CACHEPOOL;
	public CacheRemotingUtil(){}
	public static void initCachePool() {
		if(CACHEPOOL==null){
			CACHEPOOL=new HashMap<String, Map<String, Object>>();
		}
	}

 
	@Override
	public Map<String, Object> getCachePoolGroup(String groupKey) throws RuntimeException  {
 
		return null;
	}

	@Override
	public Object getCachePoolItem(String groupKey, String key) throws RuntimeException {
		 
		return null;
	}

	@Override
	public void putCachePoolItem(String groupKey, String key, Object value) throws RuntimeException {
		 
	}
	/* (non-Javadoc)
	 * @see com.pyfriend.base.cache.ICachePool#createCachePoolGroup(java.lang.String)
	 */
	@Override
	public void createCachePoolGroup(String groupKey) {
		 
		
	}
	/* (non-Javadoc)
	 * @see com.pyfriend.base.cache.ICachePool#clearCachePoolGroup(java.lang.String)
	 */
	@Override
	public void clearCachePoolGroup(String groupKey) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.pyfriend.base.cache.ICachePool#removeCahcePoolItem(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeCahcePoolItem(String groupKey, String key) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.pyfriend.base.cache.ICachePool#checkCacheGroup(java.lang.String)
	 */
	@Override
	public Boolean checkCacheGroup(String groupKey) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.pyfriend.base.cache.ICachePool#putCachePoolGroup(java.lang.String, java.util.Map)
	 */
	@Override
	public void putCachePoolGroup(String groupKey, Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

}
