/* 
* Copyright (C) 2014,pyfried, 
* All Rights Reserved 
* Description: TODO 描述类的主要功能.   
* 
* @project 陪友网
* @author  tiantian
* @date    2014年5月8日-下午11:38:24
*
* 代码修改历史: 
**********************************************************
* 时间		       作者		          注释
* 2014年5月8日	       tiantian		     	Create
**********************************************************
*/
package com.cos.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cos.common.cache.util.CacheLocalUtil;
import com.cos.common.cache.util.CacheRemotingUtil;
import com.cos.common.cache.util.CacheSessionUtil;

/**
 * TODO 缓存工厂
 * @author tiantian
 * @date   2014年5月8日-下午11:38:24
 *
 */

public final class CacheFactory {
   private static Logger logger = LoggerFactory.getLogger(CacheFactory.class);
   public static ICachePool getCacheUtil(CacheEnum type){
	   switch(type){
	   case LOCAL:
		   logger.debug("CacheFactory Now load CacheLocal");
		   return new CacheLocalUtil();
	   case REMOTING:
		   logger.debug("CacheFactory Now load CacheRemoting");
		   return new CacheRemotingUtil();
		default:
			logger.debug("CacheFactory Now load CacheLocal");
			 return new CacheLocalUtil();
	   }

   }
}

