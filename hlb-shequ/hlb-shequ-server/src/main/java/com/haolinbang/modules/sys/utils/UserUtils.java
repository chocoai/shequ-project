package com.haolinbang.modules.sys.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.service.BaseService;
import com.haolinbang.common.thridwy.haolong.bean.GetEmployeeInfo;
import com.haolinbang.common.thridwy.haolong.bean.GetGroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.bean.bean.UserInfo;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.sns.dto.ZtreeDataDto;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sys.dao.AreaDao;
import com.haolinbang.modules.sys.dao.MenuDao;
import com.haolinbang.modules.sys.dao.OfficeDao;
import com.haolinbang.modules.sys.dao.RoleDao;
import com.haolinbang.modules.sys.dao.UserDao;
import com.haolinbang.modules.sys.entity.Area;
import com.haolinbang.modules.sys.entity.Menu;
import com.haolinbang.modules.sys.entity.Office;
import com.haolinbang.modules.sys.entity.Role;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * 
 */
public class UserUtils {

	protected static Logger logger = LoggerFactory.getLogger(UserUtils.class);

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	private static UrlmapService urlmapService = SpringContextHolder.getBean(UrlmapService.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";

	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList_";
	public static final String CACHE_HAOLONG_OFFICE_ALL_LIST = "haolongOfficeAllList_";
	public static final String CACHE_SOURCE_KEY = "sourceKey_";

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user == null) {
			user = userDao.get(id);
			if (user == null) {
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName) {
		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null) {
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null) {
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache() {
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}

	/**
	 * 清除指定用户缓存
	 * 
	 * @param user
	 */
	public static void clearCache(User user) {
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		// CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ +
		// user.getOldLoginName());
		if (user.getOffice() != null && user.getOffice().getId() != null) {
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static User getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
			User user = get(principal.getId());
			if (user != null) {
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}

	/**
	 * 获取当前用户角色列表
	 * 
	 * @return
	 */
	public static List<Role> getRoleList() {
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
		if (roleList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				roleList = roleDao.findAllList(new Role());
			} else {
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleDao.findList(role);
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuList() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
		if (menuList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				menuList = menuDao.findAllList(new Menu());
			} else {
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}

	/**
	 * 获取当前用户授权的区域
	 * 
	 * @return
	 */
	public static List<Area> getAreaList() {
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>) getCache(CACHE_AREA_LIST);
		if (areaList == null) {
			areaList = areaDao.findAllList(new Area());
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<Office> getOfficeList() {
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
		if (officeList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				officeList = officeDao.findAllList(new Office());
			} else {
				Office office = new Office();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				officeList = officeDao.findList(office);
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<Office> getOfficeAllList() {
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null) {
			officeList = officeDao.findAllList(new Office());
		}
		return officeList;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal;
			}
			// subject.logout();
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	// ============== User Cache ==============

	@SuppressWarnings("unchecked")
	public static <T> T getCache(String key) {
		return (T) getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		// Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		// getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		// getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

	/**
	 * 从缓存中获取group
	 * 
	 * @param gid
	 * @return
	 */
	public static GroupInfo getGroup(String gid, String source) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		for (GroupInfo groupInfo : groupInfoList) {
			if (gid.equals(groupInfo.getGroupId())) {
				return groupInfo;
			}
		}
		return null;
	}

	/**
	 * 获取该用户的组织机构的树结构
	 * 
	 * @param source
	 * 
	 * @return
	 */
	public static List<ZtreeDataDto> getOrgTreeList(String source) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		List<ZtreeDataDto> orgTreeList = new ArrayList<ZtreeDataDto>();
		Member member = MemberUtils.getCurrentMember();
		GroupInfo pgroup = member.getStaff().getGroupInfo();
		GroupInfo parentGroup = getGroup(pgroup.getParentId(), source);

		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		getSubGroupInfoList(pgroup.getParentId(), groupInfoList, subGroupInfoList);
		subGroupInfoList.add(parentGroup);

		for (GroupInfo group : subGroupInfoList) {
			ZtreeDataDto dto = new ZtreeDataDto();
			dto.setId("G_" + group.getGroupId().toString());
			dto.setPid("G_" + group.getParentId().toString());
			dto.setName(group.getName());
			dto.setType(group.getGroupType());
			orgTreeList.add(dto);
			if ("3".equals(group.getGroupType())) {
				List<User> userList = userDao.getUsersByGroupId(member.getSource(), group.getGroupId());
				for (User staff : userList) {
					ZtreeDataDto dto2 = new ZtreeDataDto();
					dto2.setId("M_" + staff.getStaffId().toString());
					dto2.setName(staff.getName());
					dto2.setPid("G_" + group.getGroupId());
					dto2.setNocheck(true);
					orgTreeList.add(dto2);
				}
			}
		}
		return orgTreeList;
	}

	/**
	 * 递归获取子节点
	 * 
	 * @param parentId
	 * @param groupInfoList
	 * @param subGroupInfoList
	 */
	private static void getSubGroupInfoList(String parentId, List<GroupInfo> groupInfoList, List<GroupInfo> subGroupInfoList) {
		for (GroupInfo groupInfo : groupInfoList) {
			if (groupInfo.getParentId().equals(parentId)) {
				subGroupInfoList.add(groupInfo);
				getSubGroupInfoList(groupInfo.getGroupId(), groupInfoList, subGroupInfoList);
			}
		}
	}

	/**
	 * 获取source下的所有的组织结构
	 * 
	 * @param id
	 * @param source
	 * @return
	 */
	public static List<GroupInfo> getAllGetGroupInfo(String source) {
		if (StringUtils.isBlank(source)) {
			throw new RuntimeException("source不能为空");
		}
		List<GroupInfo> list = CacheUtils.get(CACHE_HAOLONG_OFFICE_ALL_LIST + source);
		if (list == null) {
			Urlmap urlmap = getUrlmap(source);
			// ----调用组织结构
			GetGroupInfo getGroupInfo = new GetGroupInfo();
			getGroupInfo.setSecretkey(urlmap.getSecretkey());
			getGroupInfo.setSoapActionString(urlmap.getSoapactionstring());
			getGroupInfo.setUrlString(urlmap.getUrlstring());
			getGroupInfo.setSource(urlmap.getUrlkey());
			try {
				list = HaolongUtils.getGroupInfo(getGroupInfo);
				// 缓存数据
				CacheUtils.put(CACHE_HAOLONG_OFFICE_ALL_LIST + source, list);
			} catch (IOException e) {
				logger.error("获取组织机构接口出错:{}", e);
			}
		}
		return list;
	}

	/**
	 * 获取source配置信息
	 * 
	 * @param source
	 * @return
	 */
	public static Urlmap getUrlmap(String source) {
		Urlmap urlmap = CacheUtils.get(CACHE_SOURCE_KEY + source);
		if (urlmap == null) {
			urlmap = new Urlmap();
			urlmap.setStatus("1");
			urlmap.setUrlkey(source);
			// 设置数据源
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
			urlmap = urlmapService.getUrlmap(urlmap);
			// 还原数据源
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			CacheUtils.put(CACHE_SOURCE_KEY + source, urlmap);
		}
		return urlmap;
	}

	/**
	 * 设置用户的组织机构信息
	 * 
	 * @param gid
	 * @param source
	 */
	public static void setUserGroup(String gid, String source, User user) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		for (GroupInfo groupInfo : groupInfoList) {
			if (groupInfo.getGroupId().equals(gid)) {
				user.setGroupInfo(groupInfo);
				break;
			}
		}
	}

	/**
	 * 获取部门下的员工列表
	 * 
	 * @param source
	 * @param gid
	 * @return
	 */
	public static List<UserInfo> getEmployeeInfoByGroupId(String source, String gid) {
		List<UserInfo> elist = null;
		if (elist == null) {
			Urlmap urlmap = getUrlmap(source);

			GetEmployeeInfo getEmployeeInfo = new GetEmployeeInfo();
			getEmployeeInfo.setSecretkey(urlmap.getSecretkey());
			getEmployeeInfo.setSoapActionString(urlmap.getSoapactionstring());
			getEmployeeInfo.setUrlString(urlmap.getUrlstring());
			getEmployeeInfo.setGroupID(gid);

			try {
				elist = HaolongUtils.getEmployeeInfo(getEmployeeInfo);
				logger.info("获取部门员工elist:{}", elist);

				// CacheUtils.put(CACHE_SOURCE_KEY + source + gid, elist);
			} catch (IOException e) {
				logger.error("获取部门员工出错：{}", e);
			}
		}
		if (elist == null) {
			elist = new ArrayList<UserInfo>();
		}
		return elist;
	}

	/**
	 * 获取单个用户
	 * 
	 * @param source
	 * @param groupId
	 * @param employeeId
	 * @return
	 */
	public static UserInfo getEmployeeInfo(String source, String groupId, String employeeId) {
		UserInfo userInfo = null;
		if (StringUtils.isBlank(source) || StringUtils.isBlank(groupId) || StringUtils.isBlank(employeeId)) {
			return userInfo;
		}
		List<UserInfo> elist = getEmployeeInfoByGroupId(source, groupId);
		for (UserInfo info : elist) {
			if (employeeId.equals(info.getStaffId())) {
				userInfo = info;
				break;
			}
		}
		return userInfo;
	}

	/**
	 * 获取组织机构信息
	 * 
	 * @param source
	 * @param gid
	 * @return
	 */
	public static List<ZtreeDataDto> getOrgTreeList(String source, String gid) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		List<ZtreeDataDto> orgTreeList = new ArrayList<ZtreeDataDto>();
		GroupInfo pgroup = getGroupInfo(gid, source);

		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		getSubGroupInfoList(pgroup.getParentId(), groupInfoList, subGroupInfoList);
		// 获取父级group
		GroupInfo parentGroup = getGroupInfo(pgroup.getParentId(), source);
		subGroupInfoList.add(parentGroup);

		for (GroupInfo group : subGroupInfoList) {
			ZtreeDataDto dto = new ZtreeDataDto();
			dto.setId("G_" + group.getGroupId().toString());
			dto.setPid("G_" + group.getParentId().toString());
			dto.setName(group.getName());
			dto.setType(group.getGroupType());
			orgTreeList.add(dto);
			if ("3".equals(group.getGroupType())) {
				// 通过接口获取员工列表
				List<UserInfo> userList = UserUtils.getEmployeeInfoByGroupId(source, group.getGroupId());

				for (UserInfo staff : userList) {
					ZtreeDataDto dto2 = new ZtreeDataDto();
					dto2.setId("M_" + staff.getStaffId().toString());
					dto2.setName(staff.getStaffName());
					dto2.setPid("G_" + group.getGroupId());
					dto2.setNocheck(true);
					orgTreeList.add(dto2);
				}
			}
		}
		return orgTreeList;
	}

	/**
	 * 从缓存中获取上级groupid
	 * 
	 * @param gid
	 * @return
	 */
	public static GroupInfo getGroupInfo(String gid, String source) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		for (GroupInfo groupInfo : groupInfoList) {
			if (gid.equals(groupInfo.getGroupId())) {
				return groupInfo;
			}
		}
		return null;
	}

}
