package com.haolinbang.modules.sys.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.haolinbang.common.thridwy.haolong.bean.GetBuilding;
import com.haolinbang.common.thridwy.haolong.bean.GetEmployeeInfo;
import com.haolinbang.common.thridwy.haolong.bean.GetGroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.LyBuilding;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.bean.bean.UserInfo;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.common.web.Servlets;
import com.haolinbang.modules.sns.dao.WyBuildingDao;
import com.haolinbang.modules.sns.dao.WyMemberDao;
import com.haolinbang.modules.sns.dto.ZtreeDataDto;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.entity.WyMember;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sys.dao.AppMenuDao;
import com.haolinbang.modules.sys.dao.AppUserPermissionDao;
import com.haolinbang.modules.sys.dao.AreaDao;
import com.haolinbang.modules.sys.dao.MenuDao;
import com.haolinbang.modules.sys.dao.OfficeDao;
import com.haolinbang.modules.sys.dao.RoleDao;
import com.haolinbang.modules.sys.dao.UserDao;
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.entity.Area;
import com.haolinbang.modules.sys.entity.Menu;
import com.haolinbang.modules.sys.entity.Office;
import com.haolinbang.modules.sys.entity.Role;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.haolinbang.modules.weixin.dao.WxAccountDao;
import com.haolinbang.modules.weixin.entity.WxAccount;

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
	private static AppMenuDao appMenuDao = SpringContextHolder.getBean(AppMenuDao.class);
	private static UrlmapService urlmapService = SpringContextHolder.getBean(UrlmapService.class);
	private static AppUserPermissionDao appUserPermissionDao = SpringContextHolder.getBean(AppUserPermissionDao.class);
	private static WyMemberDao wyMemberDao = SpringContextHolder.getBean(WyMemberDao.class);
	private static WyBuildingDao wyBuildingDao = SpringContextHolder.getBean(WyBuildingDao.class);
	private static WxAccountDao wxAccountDao = SpringContextHolder.getBean(WxAccountDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList_";
	public static final String CACHE_MENU_LIST = "menuList_";
	public static final String CACHE_AREA_LIST = "areaList_";
	public static final String CACHE_OFFICE_LIST = "officeList_";
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
		User user = CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null) {
			// 从缓存中获取source,source必传
			String source = (String) Servlets.getRequest().getSession().getAttribute("source");
			if (StringUtils.isBlank(source)) {
				throw new RuntimeException("用户session中的source为空");
			}
			user = userDao.getByLoginName(new User(source, null, loginName));
			if (user == null) {
				return null;
			}
			// 权限授权登录
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
		List<Menu> menuList = getCache(CACHE_MENU_LIST);
		if (menuList == null) {
			User user = getUser();

			if (user.isAdmin()) {
				menuList = menuDao.findAllList(new Menu());
			} else {
				Menu m = new Menu();
				m.setUserId(user.getId());
				// 用户权限list
				List<Menu> userPermissionList = menuDao.findByUserId(m);
				// 所有菜单
				List<Menu> allMenuList = menuDao.findAllList(new Menu());

				if (menuList == null) {
					menuList = new ArrayList<Menu>();
				}
				// 添加菜单
				for (Menu menu : allMenuList) {
					if (StringUtils.isBlank(menu.getPermission())) {
						menuList.add(menu);
					}
				}
				// 添加权限
				if (userPermissionList != null && !userPermissionList.isEmpty()) {
					menuList.addAll(userPermissionList);
				}

			}
			// 添加单个权限
			List<Menu> menus2 = appUserPermissionDao.findByUserId(user.getId());
			if (menus2 != null && !menus2.isEmpty()) {
				menuList.addAll(menus2);
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
	 * 从缓存中获取部门名称
	 * 
	 * @param gid
	 * @return
	 */
	public static String getOfficeNameFromGroupInfo(String gid, String source) {
		List<GroupInfo> list = getAllGetGroupInfo(source);
		for (GroupInfo groupInfo : list) {
			if (gid.equals(groupInfo.getGroupId())) {
				return groupInfo.getName();
			}
		}
		return null;
	}

	/**
	 * 查找子节点
	 * 
	 * @param id
	 * @param source
	 * @return
	 */
	public static List<Office> getSubOfficeList(String id, String source) {
		List<Office> list = new ArrayList<Office>();
		List<GroupInfo> mapList = getAllGetGroupInfo(source);
		// 调用递归
		getSubOfficeList(mapList, id, list);
		return list;
	}

	/**
	 * 获取底下的组织机构
	 * 
	 * @param id
	 * @param source
	 * @return
	 */
	public static List<GroupInfo> getSubGroupList(String id, String source) {
		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		// 调用递归
		getSubGroupInfoList(id, groupInfoList, subGroupInfoList);
		GroupInfo group = getGroupInfo(id, source);
		subGroupInfoList.add(group);

		return subGroupInfoList;
	}

	/**
	 * 获取source下的所有的组织结构
	 * 
	 * @param id
	 * @param source
	 * @return
	 */
	public static List<GroupInfo> getAllGetGroupInfo(String source) {
		List<GroupInfo> list = null; /*
									 * CacheUtils.get(CACHE_HAOLONG_OFFICE_ALL_LIST
									 * + source);
									 */
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
	 * 获取用户的组织结构
	 * 
	 * @param id
	 * @param source
	 * @return
	 */
	public static List<GroupInfo> getUserGroupInfo(String source) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		GroupInfo pgroup = getUser().getGroupInfo();

		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		getSubGroupInfoList(pgroup.getParentId(), groupInfoList, subGroupInfoList);
		// 获取父级group
		GroupInfo parentGroup = UserUtils.getParentGroup(pgroup.getParentId(), source);
		subGroupInfoList.add(parentGroup);
		return subGroupInfoList;
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
	 * 递归调用
	 * 
	 * @param groupList
	 * @param id
	 * @param list
	 */
	private static void getSubOfficeList(List<GroupInfo> groupList, String id, List<Office> list) {
		for (GroupInfo groupInfo : groupList) {
			if (id.equals(groupInfo.getParentId())) {
				Office office = new Office();
				office.setName(groupInfo.getName());
				office.setId(groupInfo.getGroupId());
				office.setCode(groupInfo.getCode());
				Office parent = new Office();
				parent.setId(id);
				office.setParent(parent);
				office.setType(groupInfo.getGroupType());
				// 递归调用子节点,循环出子节点
				getSubOfficeList(groupList, groupInfo.getGroupId(), list);
				list.add(office);
			}
		}
	}

	public static List<AppMenu> findAllAppMenu() {
		return appMenuDao.findAllList(new AppMenu());
	}

	/**
	 * 从缓存中获取上级groupid
	 * 
	 * @param gid
	 * @return
	 */
	public static String getParentGroupId(String gid, String source) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		for (GroupInfo groupInfo : groupInfoList) {
			if (gid.equals(groupInfo.getGroupId())) {
				return groupInfo.getParentId();
			}
		}
		return null;
	}

	/**
	 * 从缓存中获取上级groupid
	 * 
	 * @param gid
	 * @return
	 */
	public static GroupInfo getParentGroup(String gid, String source) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		for (GroupInfo groupInfo : groupInfoList) {
			if (gid.equals(groupInfo.getGroupId())) {
				return groupInfo;
			}
		}
		return null;
	}

	/**
	 * 获取部门下的员工列表
	 * 
	 * @param source
	 * @param gid
	 * @return
	 */
	public static List<UserInfo> getEmployeeInfoByGroupId(String source, String gid) {
		List<UserInfo> elist = null;/*
									 * CacheUtils.get(CACHE_SOURCE_KEY + source
									 * + gid)
									 */
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
	 * 获取该用户的组织机构的树结构
	 * 
	 * @param source
	 * @param flag
	 * @param gid
	 * 
	 * @return
	 */
	public static List<ZtreeDataDto> getOrgTreeList(String source, String gid, boolean flag) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		List<ZtreeDataDto> orgTreeList = new ArrayList<ZtreeDataDto>();
		GroupInfo pgroup = getUser().getGroupInfo();

		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		getSubGroupInfoList(pgroup.getParentId(), groupInfoList, subGroupInfoList);
		// 获取父级group
		GroupInfo parentGroup = UserUtils.getParentGroup(pgroup.getParentId(), source);
		subGroupInfoList.add(parentGroup);

		for (GroupInfo group : subGroupInfoList) {
			ZtreeDataDto dto = new ZtreeDataDto();
			dto.setId("G_" + group.getGroupId().toString());
			dto.setPid("G_" + group.getParentId().toString());
			dto.setName(group.getName());
			dto.setType(group.getGroupType());
			orgTreeList.add(dto);
			if ("3".equals(group.getGroupType()) && flag) {
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
	 * 设置用户的组织机构信息
	 * 
	 * @param gid
	 * @param source
	 */
	public static void setUserGroup(String gid, String source) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		for (GroupInfo groupInfo : groupInfoList) {
			if (groupInfo.getGroupId().equals(gid)) {
				getUser().setGroupInfo(groupInfo);
				break;
			}
		}
	}

	/**
	 * 获取组织机构物业树结构
	 * 
	 * @param source
	 * @param groupId
	 * @return
	 */
	public static List<ZtreeDataDto> getOrgWYTreeList(String source, String groupId) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		List<ZtreeDataDto> orgTreeList = new ArrayList<ZtreeDataDto>();
		GroupInfo pgroup = getGroupInfo(groupId, source);

		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		getSubGroupInfoList(pgroup.getParentId(), groupInfoList, subGroupInfoList);
		// 获取父级group
		GroupInfo parentGroup = getGroupInfo(pgroup.getParentId(), source);
		subGroupInfoList.add(parentGroup);

		for (GroupInfo group : subGroupInfoList) {
			if (group != null && StringUtils.isNotBlank(group.getGroupType()) && !"3".equals(group.getGroupType()) && !"4".equals(group.getGroupType())) {
				ZtreeDataDto dto = new ZtreeDataDto();
				dto.setId("G_" + group.getGroupId().toString());
				dto.setPid("G_" + group.getParentId().toString());
				dto.setName(group.getName());
				dto.setType(group.getGroupType());
				orgTreeList.add(dto);
				if ("2".equals(group.getGroupType())) {
					// /组装物业信息,从物业表中查询
					List<WyBuilding> buildingList = wyBuildingDao.getWyBuildingListByGid(source, group.getGroupId());
					for (WyBuilding wyBuilding : buildingList) {
						ZtreeDataDto dto2 = new ZtreeDataDto();
						dto2.setId("WY_" + wyBuilding.getWyid());
						dto2.setName(wyBuilding.getWymc());
						dto2.setPid("G_" + group.getGroupId());
						dto2.setNocheck(false);
						orgTreeList.add(dto2);
					}
				}
			}
		}

		return orgTreeList;
	}

	/**
	 * 获取子节点和本身的组织机构
	 * 
	 * @param source
	 * @param groupId
	 * @return
	 */
	public static List<ZtreeDataDto> getOrgWYTreeListSub(String source, String groupId) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		List<ZtreeDataDto> orgTreeList = new ArrayList<ZtreeDataDto>();
		GroupInfo pgroup = getGroupInfo(groupId, source);

		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		getSubGroupInfoList(pgroup.getGroupId(), groupInfoList, subGroupInfoList);
		subGroupInfoList.add(pgroup);

		for (GroupInfo group : subGroupInfoList) {
			if (group != null && StringUtils.isNotBlank(group.getGroupType()) && !"3".equals(group.getGroupType()) && !"4".equals(group.getGroupType())) {
				ZtreeDataDto dto = new ZtreeDataDto();
				dto.setId("G_" + group.getGroupId().toString());
				dto.setPid("G_" + group.getParentId().toString());
				dto.setName(group.getName());
				dto.setType(group.getGroupType());
				orgTreeList.add(dto);
				if ("2".equals(group.getGroupType())) {
					// /组装物业信息,从物业表中查询
					List<WyBuilding> buildingList = wyBuildingDao.getWyBuildingListByGid(source, group.getGroupId());
					for (WyBuilding wyBuilding : buildingList) {
						ZtreeDataDto dto2 = new ZtreeDataDto();
						dto2.setId("WY_" + wyBuilding.getWyid());
						dto2.setName(wyBuilding.getWymc());
						dto2.setPid("G_" + group.getGroupId());
						dto2.setNocheck(false);
						orgTreeList.add(dto2);
					}
				}
			}
		}

		return orgTreeList;
	}

	/**
	 * 获取物业下面的会员信息
	 * 
	 * @param source
	 * @param groupId
	 * @return
	 */
	public static List<ZtreeDataDto> getOrgMemberTreeList(String source, String groupId) {
		List<ZtreeDataDto> memberTreeList = new ArrayList<ZtreeDataDto>();
		List<ZtreeDataDto> orgTreeList = getOrgWYTreeList(source, groupId);
		// 查找物业底下的会员信息
		for (ZtreeDataDto dto : orgTreeList) {
			memberTreeList.add(dto);
			if (dto.getId().startsWith("WY_")) {
				String id = dto.getId().substring(3);
				// 查询会员信息
				List<WyMember> list = wyMemberDao.getMemberListByWyids(source, "'" + id + "'");
				for (WyMember wyMember : list) {
					ZtreeDataDto dto2 = new ZtreeDataDto();
					dto2.setId("M_" + wyMember.getMemberId());
					dto2.setName(wyMember.getMemberName());
					dto2.setPid(dto.getId());
					dto2.setNocheck(true);
					memberTreeList.add(dto2);
				}
			}
		}
		return memberTreeList;
	}

	/**
	 * 获取子节点和本身的组织机构下面的会员
	 * 
	 * @param source
	 * @param groupId
	 * @return
	 */
	public static List<ZtreeDataDto> getOrgMemberTreeListSub(String source, String groupId) {
		List<ZtreeDataDto> memberTreeList = new ArrayList<ZtreeDataDto>();
		List<ZtreeDataDto> orgTreeList = getOrgWYTreeListSub(source, groupId);
		// 查找物业底下的会员信息
		for (ZtreeDataDto dto : orgTreeList) {
			memberTreeList.add(dto);
			if (dto.getId().startsWith("WY_")) {
				String id = dto.getId().substring(3);
				// 查询会员信息
				List<WyMember> list = wyMemberDao.getMemberListByWyids(source, "'" + id + "'");
				for (WyMember wyMember : list) {
					ZtreeDataDto dto2 = new ZtreeDataDto();
					dto2.setId("M_" + wyMember.getMemberId());
					dto2.setName(wyMember.getMemberName());
					dto2.setPid(dto.getId());
					dto2.setNocheck(true);
					memberTreeList.add(dto2);
				}
			}
		}
		return memberTreeList;
	}

	/**
	 * 获取groupid下面的所有物业信息
	 * 
	 * @param source
	 * @param groupId
	 * @return
	 */
	public static List<WyBuilding> getWYListByGroupid(String source, String groupId) {
		List<GroupInfo> groupInfoList = getAllGetGroupInfo(source);
		GroupInfo pgroup = getGroupInfo(groupId, source);

		List<GroupInfo> subGroupInfoList = new ArrayList<GroupInfo>();
		getSubGroupInfoList(pgroup.getGroupId(), groupInfoList, subGroupInfoList);
		subGroupInfoList.add(pgroup);
		List<WyBuilding> buildingListAll = new ArrayList<WyBuilding>();

		for (GroupInfo group : subGroupInfoList) {
			if ("2".equals(group.getGroupType())) {
				List<WyBuilding> buildingList = wyBuildingDao.getWyBuildingListByGid(source, group.getGroupId());
				if (buildingList != null && !buildingList.isEmpty()) {
					buildingListAll.addAll(buildingList);
				}
			}
		}
		return buildingListAll;
	}

	/**
	 * 通过groupid获取wyids
	 * 
	 * @param source
	 * @param groupid
	 * @return
	 */
	public static String getWyidsByGroupid(String source, String groupid) {
		String wyids = "";
		List<WyBuilding> buildingList = UserUtils.getWYListByGroupid(source, groupid);
		for (WyBuilding wyBuilding : buildingList) {
			wyids += ",'" + wyBuilding.getWyid() + "'";
		}
		if (StringUtils.isNotBlank(wyids) && wyids.startsWith(",")) {
			wyids = wyids.substring(1);
		}
		return wyids;
	}

	/**
	 * 获取物业id下的楼宇信息
	 * 
	 * @param id
	 * @param source
	 * @return
	 */
	public static List<LyBuilding> getLouYuList(String source, String wyid) {
		List<LyBuilding> list = null;
		if (list == null) {
			Urlmap urlmap = UserUtils.getUrlmap(source);
			GetBuilding getBuilding = new GetBuilding();
			getBuilding.setWYID(wyid);
			getBuilding.setLYID("");
			getBuilding.setSecretkey(urlmap.getSecretkey());
			getBuilding.setSoapActionString(urlmap.getSoapactionstring());
			getBuilding.setUrlString(urlmap.getUrlstring());
			try {
				list = HaolongUtils.getBuilding(getBuilding);
			} catch (Exception e) {
				logger.error("获取物业id下的楼宇信息接口出错:{}", e);
			}
		}
		return list;
	}

	/**
	 * 获取用户的微信账号列表
	 * 
	 * @return
	 */
	public static List<WxAccount> getWxAccountList() {
		String groupIds = buildSqlInGroupids();
		WxAccount wxAccount = new WxAccount();
		wxAccount.setSource(UserUtils.getUser().getSource());
		wxAccount.setGroupIds(groupIds);
		return wxAccountDao.findList(wxAccount);
	}

	/**
	 * 组装登录用户查询的组织机构ids
	 * 
	 * @return
	 */
	public static String buildSqlInGroupids() {
		User user = UserUtils.getUser();
		GroupInfo group = user.getGroupInfo();
		List<GroupInfo> subGroupList = getSubGroupList(group.getParentId(), user.getSource());
		String groupIds = "";
		if (subGroupList != null && !subGroupList.isEmpty()) {
			for (GroupInfo groupinfo : subGroupList) {
				groupIds += ",'" + groupinfo.getGroupId() + "'";
			}
			if (StringUtils.isNotBlank(groupIds) && groupIds.startsWith(",")) {
				groupIds = groupIds.substring(1);
			}
		}
		return groupIds;
	}

	/**
	 * 组装用户可以查询的公众号ids
	 * 
	 * @return
	 */
	public static String buildSqlInWxAccountids() {
		List<WxAccount> list = getWxAccountList();
		String accountIds = "";
		if (list != null && !list.isEmpty()) {
			for (WxAccount account : list) {
				accountIds += ",'" + account.getId() + "'";
			}
			if (StringUtils.isNotBlank(accountIds) && accountIds.startsWith(",")) {
				accountIds = accountIds.substring(1);
			}
		}
		return accountIds;
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
		if (StringUtils.isBlank(source) || StringUtils.isBlank(groupId) || StringUtils.isBlank(employeeId)) {
			return null;
		}
		UserInfo userInfo = null;
		List<UserInfo> elist = getEmployeeInfoByGroupId(source, groupId);
		for (UserInfo info : elist) {
			if (employeeId.equals(info.getStaffId())) {
				userInfo = info;
				break;
			}
		}
		return userInfo;
	}
	
	/*
	 * 获取用户能操作的物业数据范围 （物业一级）
	 */
	public static List<AppUserScope> getEmployeeOfWuye(){
		User user = UserUtils.getUser();
		List<AppUserScope> list = new ArrayList<AppUserScope>();
		List<AppUserScope> user_scope_list = new ArrayList<AppUserScope>();
		user_scope_list.addAll(UserUtils.getUser().getAppUserScopeList());
		if(user_scope_list!=null && user_scope_list.size()>0){
			for(int i=0; i<user_scope_list.size();){
				AppUserScope aus = user_scope_list.get(i);
				if(aus.getType().equals("WY")){
					WyBuilding wyBuilding = wyBuildingDao.getBySourceAndWyid(user.getSource(), aus.getGroupId());
					if(wyBuilding!=null){
						aus.setWymc(wyBuilding.getWymc());
						aus.setSource(user.getSource());
					}else{
						logger.error("获取用户能操作的物业数据范围——查询房屋表出错："+" source:"+user.getSource()+" wyid："+aus.getGroupId());
					}
					i++;
				}else{
					user_scope_list.remove(i);
				}
			}
		}

		return user_scope_list;
	}
	
	/*
	 * 获取用户能操作的物业数据范围 （组织机构一级）
	 */
	public static List<String> getEmployeeOfGroups(){
		User user = UserUtils.getUser();
		List<AppUserScope> list = new ArrayList<AppUserScope>();
		List<AppUserScope> user_scope_list = new ArrayList<AppUserScope>();
		List<String> groupids = new ArrayList<String>();
		user_scope_list.addAll(UserUtils.getUser().getAppUserScopeList());
		if(user_scope_list!=null && user_scope_list.size()>0){
			for(int i=0; i<user_scope_list.size();){
				AppUserScope aus = user_scope_list.get(i);
				if(aus.getType().equals("G")){
					groupids.add(aus.getGroupId());
					i++;
				}else{
					user_scope_list.remove(i);
				}
			}
		}

		return groupids;
	}
}
