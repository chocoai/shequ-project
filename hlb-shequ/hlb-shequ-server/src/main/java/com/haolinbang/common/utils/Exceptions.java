package com.haolinbang.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import com.haolinbang.common.exception.MemberException;
import com.haolinbang.common.exception.MemberNeedRegException;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.sns.constant.WyConstants;

/**
 * 关于异常的工具类.
 * 
 */
public class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (e == null) {
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	@SuppressWarnings("unchecked")
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}

	/**
	 * 在request中获取异常类
	 * 
	 * @param request
	 * @return
	 */
	public static Throwable getThrowable(HttpServletRequest request) {
		Throwable ex = null;
		if (request.getAttribute("exception") != null) {
			ex = (Throwable) request.getAttribute("exception");
		} else if (request.getAttribute("javax.servlet.error.exception") != null) {
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
		return ex;
	}

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static MemberException memberIsNull(Exception e) {
		if (e instanceof MemberException) {
			return (MemberException) e;
		} else {
			return new MemberException(e);
		}
	}

	/**
	 * 会员需要注册才可以进行
	 */
	public static MemberNeedRegException memberNeedReg(Exception e) {
		if (e instanceof MemberNeedRegException) {
			return (MemberNeedRegException) e;
		} else {
			return new MemberNeedRegException(e);
		}
	}

	/**
	 * 处理异常信息
	 * 
	 * @return
	 */
	public static String deal(Exception e) {
		// 需要登录
		if (e instanceof MemberException) {
			String sessionid = StringUtils.getUUID();
			String url = ServletUtil.getRequestUrlAndParas();
			url = changeUrl(url);
			CacheUtils.put(sessionid, WyConstants.CACHE_KEY_REDIRECT_URL, url);
			return "redirect:/weixin/oAuth2?sessionid=" + sessionid;
			// 需要注册
		} else if (e instanceof MemberNeedRegException) {

			return "redirect:/reg";
			// 其他错误信息
		} else {
			return "";
		}
	}
	
	/*
	 * 修改回调地址,适应于非会员和游客的免注册登录
	 */
	public static String changeUrl(String url){
		String tempurl = url;
		if(url.contains("dirUrl")){
			String dirType = (String) ServletUtil.getSession().getAttribute("dirType");
			switch (dirType) {
				case "houseRent":
					tempurl = url.replace("dirUrl", "houseRent/list");
					break;
				case "fleaMarket":
					tempurl = url.replace("dirUrl", "fleaMarket/list");
					break;
				case "convenience":
					tempurl = url.replace("dirUrl", "/wyConvenienceService/index");
					break;
				case "convenience2":
					tempurl = url.replace("dirUrl", "/wyConvenienceService/index2");
					break;
				default:
					break;
			}
		}
		return tempurl;
	}
	
	public static void main(String[] args){
		String url = "12314214dirUrl123123213";
		url = url.replace("dirUrl", "houseRent/list");
		System.out.println(url);
	}
}
