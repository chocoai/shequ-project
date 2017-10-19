package com.haolinbang.modules.sys.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.haolinbang.common.service.BaseService;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.utils.UserAgentUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.WxAccountService;

/**
 * 手机端视图拦截器
 * 
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {

	@Autowired
	private WxAccountService wxAccountService;

	/**
	 * 每个@RequestMapping执行前都会运行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 过滤静态资源请求
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String staticPath = ctx + "/static";
		if (uri.contains(staticPath)) {
			return true;
		}

		// 将必传的两个参数放到session中,供后面获取
		/*String source = request.getParameter("app_source");
		String groupid = request.getParameter("app_groupid");

		if (StringUtils.isBlank(source)) {
			source = (String) request.getSession().getAttribute("app_source");
		}
		if (StringUtils.isBlank(groupid)) {
			groupid = (String) request.getSession().getAttribute("app_groupid");
		}
		String wxAccountId = (String) request.getSession().getAttribute("wxAccountId");
		if (StringUtils.isBlank(wxAccountId)) {
			if (StringUtils.isNotBlank(source) && StringUtils.isNotBlank(groupid)) {
				WxAccount wxAccount = new WxAccount();
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				wxAccount.setSource(source);
				List<WxAccount> wxAccounts = wxAccountService.findList(wxAccount);
				if (wxAccounts.size() > 0) {
					wxAccountId = wxAccounts.get(0).getId();
				}
			}
		}
		request.getSession().setAttribute("app_source", source);
		request.getSession().setAttribute("app_groupid", groupid);
		request.getSession().setAttribute("wxAccountId", wxAccountId);

		String uriWx = "/weixin/oAuth2";
		// 如果source和grouopid都不为空,每次都重新进行微信登录,则跳转到微信登录授权页面
		if (StringUtils.isNotBlank(source) && StringUtils.isNotBlank(groupid) && !uri.contains(uriWx)) {
			request.getRequestDispatcher(uriWx).forward(request, response);
			return false;
		} else {
			return true;
		}*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			// 如果是手机或平板访问的话，则跳转到手机视图页面。
			if (UserAgentUtils.isMobileOrTablet(request) && !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")) {
				// modelAndView.setViewName("mobile/" +
				// modelAndView.getViewName());
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
