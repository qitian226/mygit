package com.cos.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
/**
 * html 元素过滤
 * @author q00373723
 *
 */
public abstract class JSOUPUtil {
	private final static Whitelist user_content_filter = Whitelist.relaxed();
	static {
		//增加可信标签到白名单
		/*user_content_filter.addTags("embed","object","param","span","div");
		//增加可信属性
		user_content_filter.addAttributes(":all", "style", "class", "id", "name");
		user_content_filter.addAttributes("object", "width", "height","classid","codebase");
		user_content_filter.addAttributes("param", "name", "value");
		user_content_filter.addAttributes("embed", "src","quality","width","height","allowFullScreen","allowScriptAccess","flashvars","name","type","pluginspage");*/
	}

	/**
	 * 对用户输入内容进行过滤
	 * @param html
	 * @return
	 */
	public static String filterBasic(String html) {
		if(StringUtils.isBlank(html)) return "";
		return Jsoup.clean(html, Whitelist.basic());
	}

	/**
	 * 比较宽松的过滤，但是会过滤掉object，script， span,div等标签，适用于富文本编辑器内容或其他html内容
	 * @param html
	 * @return
	 */
	public static String relaxed(String html) {
		return Jsoup.clean(html, Whitelist.relaxed());
	}

	/**
	 * 去掉所有标签，返回纯文字.适用于textarea，input
	 * @param html
	 * @return
	 */
	public static String pureText(String html) {
		return Jsoup.clean(html, Whitelist.none());
	}


public static void main(String[] args){
	String html="<a href=\"\"></a><p>&lt;script&gt;<span style=\"color:#366092\">江河</span><span style=\"color:#ff0000;<script></script>\">-;//sss</script></span>&lt;/script&gt;</p>";
	System.out.println(pureText(html));
}

}
