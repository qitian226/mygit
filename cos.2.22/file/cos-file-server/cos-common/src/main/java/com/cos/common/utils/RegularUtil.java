
package com.cos.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO What the class does
 * @author qikunlun
 * @date   2015年11月18日-下午5:28:38
 *
 */
public abstract class RegularUtil {
	//英文
    public final static String LETTER_REGEX="^[A-Za-z]+$";
    //字母和数字
    public final static String LETTER_AND_NUMBER_REGEX="[A-Za-z0-9]*$";
    //校验密码：只能输入8-16个字母、数字、下划线;密码格式为头尾字母或数字,中间特殊字符!
    public final static String PASSWORD_REGEX="^[a-zA-Z]{1}([a-zA-Z0-9]|[*_ # @ % $ &  = +]){7,14}[a-zA-Z0-9]{1}$";
    //email
    public final static String EMAIL_REGEX="^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    //括号判断
    public final static String BRACKET_REGEX="\\(.*?\\)";
    //包含汉字
    public final static String CHINESE_REGEX="^[\\u0391-\\uFFE5]+$";
    //全部是汉字
    public final static String ALL_CHINESE_REGEX="^[\\u4e00-\\u9fa5]*$";
    //包含特殊字符
/*    public final static String SPECIAL_CODE="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";*/
    public final static String SPECIAL_CODE="[~·`@#￥$%^&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|\\\\<>]";
    //是否全部是数字
    public final static String IS_NUMBER="^\\d+$";
    //包含中英文
    public final static String INCLUDE_ENGLISH_CHINESE="^([\\u4e00-\\u9fa5]|[a-zA-Z]){1,20}$";
    //电话号码
    public final static String MOBILE_CODE="^1(3|4|5|7|8)\\d{9}$";

    public final static String QQ_CODE="^[1-9][0-9]{4,9}$";

    public final static String WeChat_CODE="^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$";

    public final static String MOMO_CODE="^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$";

    public static Boolean  isMatch(String str,String reg){
         Pattern pattern = Pattern.compile(reg);
         Matcher matcher = pattern.matcher(str);
         Boolean flag = matcher.matches();
         return flag;
	}
    public static Boolean  isContain(String str,String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        Boolean flag = matcher.find();
        return flag;
	}
    /**
     * 正则替换所有特殊字符
     * @param str
     * @return
     */
    public static String replaceSpecStr(String str,String reg){
        if (null!=str&&!"".equals(str.trim())) {
        	Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(str);
            str= m.replaceAll("");
            str= str.replaceAll("\\s+", " ");
            return str;
        }
        return null;
    }
}
