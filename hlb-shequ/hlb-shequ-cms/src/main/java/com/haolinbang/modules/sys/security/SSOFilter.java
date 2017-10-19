package com.haolinbang.modules.sys.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.utils.StringUtils;

/**
 * 单点登录认证
 * 
 * @author Administrator
 * 
 */
@Service("ssoFilter")
public class SSOFilter implements javax.servlet.Filter {

	private String serverLoginUrl = Global.getConfig("sso.server.url");// 登录服务器地址设置

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// 请求地址
		String reqUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
				+ httpServletRequest.getRequestURI();
		String queryStr = httpServletRequest.getQueryString();
		if (StringUtils.isNotBlank(queryStr)) {
			reqUrl += "?" + queryStr;
		}

		Object username = httpServletRequest.getSession().getAttribute("username");
		// 如果由用户登录信息，继续后面过滤器
		if (username != null) {
			chain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}

		String sid = httpServletRequest.getParameter("SHAREJSESSIONID");
		if (StringUtils.isNotEmpty(sid)) {
			Cookie cookie = new Cookie("SHAREJSESSIONID", sid);
			cookie.setPath("/");
			httpServletResponse.addCookie(cookie);
			String html = "<html><head><script type=\"text/javascript\">location.href='" + reqUrl + "'</script></head><body></body></html>";
			byte[] bytes = html.getBytes();
			httpServletResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
			httpServletResponse.getOutputStream().write(bytes);
			httpServletResponse.getOutputStream().flush();
			httpServletResponse.getOutputStream().close();
			return;
		}

		httpServletResponse.sendRedirect(serverLoginUrl + "?bizCode=bizsys&redirectUrl=" + reqUrl);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
