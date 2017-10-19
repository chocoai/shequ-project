package com.haolinbang.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;
import com.haolinbang.common.config.Global;
import com.haolinbang.common.mapper.JsonMapper;
import com.haolinbang.common.security.shiro.session.SessionDAO;
import com.haolinbang.common.servlet.ValidateCodeServlet;
import com.haolinbang.common.thridwy.haolong.utils.AESUtils;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.CookieUtils;
import com.haolinbang.common.utils.Encodes;
import com.haolinbang.common.utils.IdGen;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.web.Servlets;
import com.haolinbang.modules.sys.security.FormAuthenticationFilter;
import com.haolinbang.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.haolinbang.modules.sys.security.UsernamePasswordToken;
import com.haolinbang.modules.sys.security.sso.vo.Auth;
import com.haolinbang.modules.sys.service.SystemService;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * 
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private SessionDAO sessionDAO;

	@Autowired
	private SystemService systemService;

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

		Principal principal = UserUtils.getPrincipal();

		if (logger.isDebugEnabled()) {
			logger.debug("login, active session size,当前在线用户: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			CookieUtils.setCookie(response, "LOGINED", "false");
		}

		// 如果已经登录，则跳转到管理首页
		if (principal != null && !principal.isMobileLogin()) {
			return "redirect:" + adminPath;
		}
		String source = request.getParameter("source");
		String csource = CookieUtils.getCookie(request, "source");
		source = StringUtils.isBlank(source) ? csource : source;
		if (StringUtils.isBlank(source)) {
			return "modules/sys/sysLogin";
		}
		CookieUtils.setCookie(response, "source", source);
		// 从参数中和cookies中的source都为空或者手動強制系統登錄,避免cookies中有source無法登錄的情况,则默认系统登录
		if ("0".equals(source)) {
			return "modules/sys/sysLogin";
		} else {
			// sso回调地址
			String url = Global.getConfig("server.project.url.sso.callback") + "/sso";
			try {
				url = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("url编码加密出错:{}", e);
			}
			// 参数说明：bizCode=子系统编码；source=公司编码；url=认证成功后的url地址
			String redirect = "redirect:" + Global.getConfig("sso.server.url") + "/login?bizCode=shequ&source=" + source + "&url=" + url;
			return redirect;
		}
	}

	/**
	 * 通过sso进行登录认证
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sso/{token}")
	public String sso(@PathVariable String token, @RequestParam(required = true) String param, String relogin, Model model) {

		try {			
			// 解密aes,这个地方不用再次urlDecode了,因为在传输的过程中,已经进行了一次解码
			param = AESUtils.decryptAes16(param, Global.getConfig("shiro.sso.secretKey"));

		} catch (Exception e) {
			logger.error("json字符串解码错误:{}", e);
			e.printStackTrace();
		}

		Auth auth = JsonMapper.fromJsonString(param, Auth.class);
		if (auth == null) {
			throw new RuntimeException("返回的参数为空,程序异常");
		}
		String source = auth.getSource();

		// 缓存授权信息
		Servlets.getRequest().getSession().setAttribute("auth", auth);
		Servlets.getRequest().getSession().setAttribute("source", source);
		Principal principal = UserUtils.getPrincipal();

		// 如果已经登录
		if (principal != null) {
			// 如果设置强制重新登录，则重新登录
			if (BooleanUtils.toBoolean(relogin)) {
				UserUtils.getSubject().logout();
			}
			// 否则，直接跳转到目标页
			else {
				return "redirect:" + adminPath;
			}
		}
		// 进行单点登录
		if (token != null) {
			UsernamePasswordToken upt = new UsernamePasswordToken();
			try {
				// 查询对应的用户名
				String loginName = systemService.getUserByStaffid(source, auth.getStaffId());

				upt.setSource(source);// 设置公司编码
				upt.setStaffId(auth.getStaffId());
				upt.setUsername(loginName); // 登录用户名
				upt.setPassword(token.toCharArray()); // 密码组成：sso密钥+用户名+日期，进行md5加密，举例：
														// Digests.md5(secretKey+username+20150101)）
				upt.setParams(upt.toString()); // 单点登录识别参数，see：
												// AuthorizingRealm.assertCredentialsMatch
			} catch (Exception ex) {
				if (!ex.getMessage().startsWith("msg:")) {
					ex = new AuthenticationException("msg:授权令牌错误，请联系管理员。");
				}
				model.addAttribute("exception", ex);
			}
			try {
				UserUtils.getSubject().login(upt);
				// 登录成功直接跳转到主页面
				return "redirect:" + adminPath;
			} catch (AuthenticationException ae) {
				logger.error("授权出错：{}", ae);
				if (!ae.getMessage().startsWith("msg:")) {
					ae = new AuthenticationException("msg:授权错误，请检查用户配置，若不能解决，请联系管理员。");
				}
				model.addAttribute("exception", ae);
			}
		}
		return "error/403";
	}

	/**
	 * sso退出登录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/ssoLogout")
	public String ssoLogout(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 清除缓存
		UserUtils.clearCache(UserUtils.getUser());
		UserUtils.clearCache();
		UserUtils.getSubject().logout();

		String source = CookieUtils.getCookie(request, "source");
		if (StringUtils.isBlank(source) || ("0".equals(source))) {
			return "redirect:" + adminPath + "/login";
		}
		String url = Global.getConfig("server.project.url.sso.callback") + "/sso";
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("url编码加密出错:{}", e);
		}

		return "redirect:" + Global.getConfig("sso.server.url") + "/logout?bizCode=shequ&source=" + source + "&url=" + url;

	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

		if (logger.isDebugEnabled()) {
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", sessionDAO.getActiveSessions(false).size(), message, exception);
		}

		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)) {
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}

		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

		// 如果是手机登录，则返回JSON字符串
		if (mobile) {
			return renderString(response, model);
		}

		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);

		if (logger.isDebugEnabled()) {
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)) {
				CookieUtils.setCookie(response, "LOGINED", "true");
			} else if (StringUtils.equals(logined, "true")) {
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}

		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()) {
			if (request.getParameter("login") != null) {
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null) {
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}

		return "modules/sys/sysIndex";
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(theme)) {
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:" + request.getParameter("url");
	}

	/**
	 * 是否是验证码登录，用户三次登录出错需要输入验证码
	 * 
	 * @param useruame
	 *            用户名
	 * @param isFail
	 *            计数加1
	 * @param clean
	 *            计数清零
	 * @return
	 */
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
		Map<String, Integer> loginFailMap = CacheUtils.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
}
