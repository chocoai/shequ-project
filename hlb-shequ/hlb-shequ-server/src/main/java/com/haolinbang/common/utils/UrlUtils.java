package com.haolinbang.common.utils;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {

	/**
	 * 通过请求获取url
	 * 
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		String strBackUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath(); // 项目名称
		return strBackUrl;
	}

}
