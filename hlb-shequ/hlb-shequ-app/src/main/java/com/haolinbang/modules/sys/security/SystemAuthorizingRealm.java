package com.haolinbang.modules.sys.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.servlet.ValidateCodeServlet;
import com.haolinbang.common.thridwy.haolong.bean.GetRights;
import com.haolinbang.common.thridwy.haolong.bean.GetTimeStamp;
import com.haolinbang.common.thridwy.haolong.utils.AESUtils;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.ServletUtil;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.Servlets;
import com.haolinbang.modules.sys.entity.Menu;
import com.haolinbang.modules.sys.entity.Role;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.security.sso.vo.Auth;
import com.haolinbang.modules.sys.service.SystemService;
import com.haolinbang.modules.sys.utils.LogUtils;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.sys.web.LoginController;

/**
 * 系统安全认证实现类
 * 
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SystemService systemService;

	// 注入父类的属性，注入加密算法匹配密码时使用
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		super.setCredentialsMatcher(credentialsMatcher);
	}

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		int activeSessionSize = getSystemService().getSessionDao().getActiveSessions(false).size();
		if (logger.isDebugEnabled()) {
			logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
		}

		// 校验登录验证码
		if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)) {
			Session session = UserUtils.getSession();
			String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}

		// 数据库密码或者sso单点认证
		User user = getSystemService().getUserByLoginName(token.getUsername());
		if (user != null) {
			if (Global.NO.equals(user.getLoginFlag())) {
				throw new AuthenticationException("msg:该已帐号禁止登录.");
			}
			// 自定义密码实现
			return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))) {
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
			if (sessions.size() > 0) {
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()) {
					for (Session session : sessions) {
						getSystemService().getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else {
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			// 获取授权注册信息
			String unStr = (String) ServletUtil.getRequest().getSession().getAttribute("screenCodes");

			if (StringUtils.isBlank(unStr)) {
				String secretkey = Global.getConfig("okbang.secretkey");
				String soapactionstring = Global.getConfig("okbang.soapactionstring");
				String urlstring = Global.getConfig("okbang.urlstring");

				GetTimeStamp bean = new GetTimeStamp();
				bean.setSecretkey(secretkey);
				bean.setSoapActionString(soapactionstring);
				bean.setUrlString(urlstring);
				// 获取用户授权信息
				try {
					String timestampStr = HaolongUtils.getTimeStamp(bean);

					GetRights getRights = new GetRights();
					getRights.setSecretkey(secretkey);
					getRights.setSoapActionString(soapactionstring);
					getRights.setUrlString(urlstring);
					getRights.setSource(AESUtils.encryptAes16(user.getSource(), secretkey));
					getRights.setTimeStampT(AESUtils.encryptAes16(timestampStr, secretkey));
					String str = HaolongUtils.getRights(getRights);
					unStr = AESUtils.decryptAes16(str, secretkey);

					if (StringUtils.isNotBlank(unStr)) {
						unStr = unStr.split("\\^")[0];
					}
					ServletUtil.getRequest().getSession().setAttribute("screenCodes", unStr);

				} catch (Exception e) {
					logger.error("获取时间戳:{}", e);
				}
			}

			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 获取用户菜单权限
			List<Menu> list = UserUtils.getMenuList();
			for (int i = 0; i < list.size();) {
				int screenCode = StringUtils.toInteger(StringUtils.isBlank(list.get(i).getScreenCode()) ? "-1" : list.get(i).getScreenCode());
				if (screenCode == -1) {
					if (StringUtils.isNotBlank(list.get(i).getPermission())) {
						// 添加基于Permission的权限信息
						for (String permission : StringUtils.split(list.get(i).getPermission(), ",")) {
							info.addStringPermission(permission);
						}
					}
					i++;
				} else if (screenCode == 0) {
					if (StringUtils.isNotBlank(list.get(i).getPermission())) {
						// 添加基于Permission的权限信息
						for (String permission : StringUtils.split(list.get(i).getPermission(), ",")) {
							info.addStringPermission(permission);
						}
					}
					i++;
				} else if (screenCode <= unStr.length()) {
					String flag = unStr.substring(screenCode - 1, screenCode);
					if (flag.equals("Y")) {
						if (StringUtils.isNotBlank(list.get(i).getPermission())) {
							// 添加基于Permission的权限信息
							for (String permission : StringUtils.split(list.get(i).getPermission(), ",")) {
								info.addStringPermission(permission);
							}
						}
						i++;
					} else {
						list.remove(i);
					}
				} else {
					list.remove(i);
				}
			}

			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()) {
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");

			// 更新用户信息
			Auth auth = (Auth) Servlets.getRequest().getSession().getAttribute("auth");
			if (auth != null) {
				user.setName(auth.getStaffName());
				user.setLoginName(auth.getStaffName());
				user.setStaffId(auth.getStaffId());
				user.setGroupId(auth.getGroupId());

				user.setSex(auth.getSex());
				user.setEmail(auth.getEmail());
				user.setPhone(auth.getPhone());
				user.setLeaveDate(auth.getLeaveDate());
				user.setLeaveStatus(auth.isLeaveStatus());
				getSystemService().saveUser2(user);
			}

			// 设置组织机构信息
			UserUtils.setUserGroup(user.getGroupId().toString(), user.getSource());
			// 添加用户数据使用范围
			user.setAppUserScopeList(getSystemService().getAppUserScopeByUid(user.getId()));
			user.setAuth(auth);

			return info;
		} else {
			return null;
		}
	}

	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}

	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermitted(permissions, info);
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}

	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermittedAll(permissions, info);
	}

	/**
	 * 授权验证方法
	 * 
	 * @param permission
	 */
	private void authorizationValidate(Permission permission) {
		// 模块授权预留接口
	}

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	// @PostConstruct
	// public void initCredentialsMatcher() {
	// HashedCredentialsMatcher matcher = new
	// HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
	// matcher.setHashIterations(SystemService.HASH_INTERATIONS);
	// setCredentialsMatcher(matcher);
	// }

	// /**
	// * 清空用户关联权限认证，待下次使用时重新加载
	// */
	// public void clearCachedAuthorizationInfo(Principal principal) {
	// SimplePrincipalCollection principals = new
	// SimplePrincipalCollection(principal, getName());
	// clearCachedAuthorizationInfo(principals);
	// }

	/**
	 * 清空所有关联认证
	 * 
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
		// Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		// if (cache != null) {
		// for (Object key : cache.keys()) {
		// cache.remove(key);
		// }
		// }
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null) {
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private String id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录

		// private Map<String, Object> cacheMap;

		public Principal(User user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.name = user.getName();
			this.mobileLogin = mobileLogin;
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

		// @JsonIgnore
		// public Map<String, Object> getCacheMap() {
		// if (cacheMap==null){
		// cacheMap = new HashMap<String, Object>();
		// }
		// return cacheMap;
		// }

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try {
				return (String) UserUtils.getSession().getId();
			} catch (Exception e) {
				return "";
			}
		}

		@Override
		public String toString() {
			return id;
		}
	}
}
