/* 
* Copyright (C) 2014,PyFriend.com, 
* All Rights Reserved 
* Description: TODO 描述类的主要功能.   
* 
* @project 陪友网
* @author  tiantian
* @date    2014年4月14日-下午10:27:59
*
* 代码修改历史: 
**********************************************************
* 时间		       作者		          注释
* 2014年4月14日	       tiantian		     	Create
**********************************************************
*/
package com.cos.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * TODO What the class does
 * @author tiantian
 * @date   2014年4月14日-下午10:27:59
 *
 */
public final class PropertiesFileUtil
{
    public static ResourceBundle getPropertiesLocale(String fileName) throws  Exception {
        Locale locale = Locale.getDefault(); 
        ResourceBundle localResource=null;
        try{
        localResource = ResourceBundle.getBundle(fileName, locale);
        }catch(MissingResourceException ex){
            throw ex;
        }
        return localResource;
  }
    public static HashMap<String,String> getPropertiesMap(String fileName) throws  Exception {
        Locale locale = Locale.getDefault(); 
        ResourceBundle localResource=null;
        try{
        localResource = ResourceBundle.getBundle(fileName, locale);
        }catch(MissingResourceException ex){
            throw ex;
        }
        Set<String> keys= localResource.keySet();
        HashMap<String,String> map=new HashMap<String,String>();
        for(String key: keys){
            try
            {
                map.put(key, new String(localResource.getString(key).getBytes("ISO-8859-1"),"UTF-8"));
            }
            catch (UnsupportedEncodingException e)
            {
               throw e;
            }
        }
        return map;
  }
}
