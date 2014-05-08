package com.wb.citylife.config;

/**
 * 此类用于定义网络接口名称
 * @author Administrator
 *
 */
public interface NetInterface {
	
	/***************************** 资讯  **********************************************/
	public static final String METHOD_NEWS_LIST = "newsList";
	public static final String METHOD_NEWS_DETAIL= "newsDetail";
	
	/***************************** 评论  **********************************************/
	public static final String METHOD_COMMENT_LIST = "commentList";
	public static final String METHOD_COMMENT = "comment";
	
	/***************************** 登录注册  **********************************************/
	public static final String METHOD_LOGIN = "login";
	
	/***************************** 栏目  **********************************************/
	public static final String METHOD_CHANNEL = "channel";
	
	/***************************** 广告  **********************************************/
	public static final String METHOD_SCROLL_NEWS = "scrollNews";
	
	/***************************** 收藏  **********************************************/
	public static final String METHOD_COLLECT = "collect";
}
