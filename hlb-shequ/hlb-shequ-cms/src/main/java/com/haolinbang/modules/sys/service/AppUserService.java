package com.haolinbang.modules.sys.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sys.dao.AppUserDao;
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.entity.AppUser;
import com.haolinbang.modules.sys.entity.AppUserPermission;
import com.haolinbang.modules.sys.entity.AppUserRole;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.entity.Menu;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.sys.vo.AppMenuVo;

/**
 * 应用平台用户Service
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppUserService extends CrudService<AppUserDao, AppUser> {

	@Autowired
	private AppUserDao appUserDao;

	@Autowired
	private AppUserPermissionService appUserPermissionService;

	@Autowired
	private AppUserRoleService appUserRoleService;

	@Autowired
	private AppUserScopeService appUserScopeService;

	public AppUser get(String id) {
		return super.get(id);
	}

	public List<AppUser> findList(AppUser appUser) {
		return super.findList(appUser);
	}

	public Page<AppUser> findPage(Page<AppUser> page, AppUser appUser) {
		return super.findPage(page, appUser);
	}

	@Transactional(readOnly = false)
	public void save(AppUser appUser) {
		super.save(appUser);
	}

	@Transactional(readOnly = false)
	public void delete(AppUser appUser) {
		super.delete(appUser);
	}

	public AppUser getAppUserByStaffId(String source, Integer staffId) {
		return appUserDao.getAppUserByStaffId(source, staffId);
	}

	/**
	 * 保存用户授权信息
	 * 
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean authSave(AppMenuVo vo) {
		String uid = vo.getUid();

		String permission = vo.getPermission();// 所有权限集合
		if (StringUtils.isNotBlank(permission)) {
			String[] permissionArr = permission.split(",");

			// 先删除原来的权限
			appUserPermissionService.deleteByPermissions(uid);

			// 在保存新的权限
			for (String menuId : permissionArr) {
				AppUserPermission appUserPermission = new AppUserPermission();
				appUserPermission.setUserId(Integer.valueOf(uid));
				appUserPermission.setMenuId(Integer.valueOf(menuId));
				appUserPermissionService.save(appUserPermission);
			}
		} else {
			// 如果没有勾选权限，则将全部的删除
			appUserPermissionService.deleteByPermissions(uid);
		}

		// 组装保存角色
		String role = vo.getRole();
		if (StringUtils.isNotBlank(role)) {
			// 批量删除角色
			appUserRoleService.deleteByRoles(uid, role);

			String[] roleArr = role.split(",");
			for (String roleId : roleArr) {
				AppUserRole appUserRole = new AppUserRole();
				appUserRole.setUserId(Long.valueOf(uid));
				appUserRole.setRoleId(Integer.valueOf(roleId));
				appUserRoleService.save(appUserRole);
			}
		} else {
			// 如果没有勾选角色,则将权限删除
			appUserRoleService.deleteByRoles(uid, role);
		}

		String gid = vo.getGid();
		String source = null;
		if (StringUtils.isNotBlank(gid) && gid.contains("___")) {
			String[] arr = gid.split("___");
			if (arr != null && arr.length == 3) {
				source = arr[1];
				gid = arr[2];
			}
		}

		// 已选择的数据范围
		String[] groupids = vo.getGroupIds();
		if (groupids != null && groupids.length > 0) {
			// 先刪除
			appUserScopeService.deleteByUid(vo.getUid());
			// 再保存
			for (String groupid : groupids) {
				String[] arr = groupid.split("_");
				String gid2 = arr[1];

				AppUserScope appUserScope = new AppUserScope();
				appUserScope.setType(arr[0]);
				appUserScope.setGroupId(gid2);
				appUserScope.setUserId(vo.getUid());
				if ("G".equals(arr[0])) {
					GroupInfo groupInfo = UserUtils.getGroupInfo(gid2, source);
					appUserScope.setParentGroupId(groupInfo.getParentId());
				}
				DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
				appUserScopeService.save(appUserScope);
			}
		}

		logger.info("uid={},permission={}", uid, permission);

		return false;
	}

	/**
	 * 获取授权列表
	 * 
	 * @param appMenuVo
	 * @param selectedMenuList
	 *            是否校验值，true,获取用户的值
	 * @param appMenu
	 * @return
	 */
	public List<AppMenuVo> getAuthList(List<Integer> selectedMenuList, AppMenu appMenu) {
		// 查询出菜单列表
		List<AppMenu> appMenuList = UserUtils.findAllAppMenu(appMenu);
		// 权限菜单
		List<AppMenu> permissionList = new ArrayList<AppMenu>();
		// 功能菜单
		List<AppMenu> functionList = new ArrayList<AppMenu>();

		// 遍历获取具体的权限set集合
		Set<String> permissionSet = new HashSet<String>();

		for (AppMenu menu : appMenuList) {
			if (StringUtils.isNotBlank(menu.getPermission())) {
				permissionList.add(menu);
				permissionSet.add(menu.getName());
			} else {
				functionList.add(menu);
			}
		}

		List<AppMenu> list = new ArrayList<AppMenu>();
		AppMenu.sortList(list, functionList, Menu.getRootId(), true);

		List<AppMenuVo> volist = new ArrayList<AppMenuVo>();
		for (AppMenu menu : list) {
			AppMenuVo vo = new AppMenuVo();
			BeanUtils.copyProperties(menu, vo);
			volist.add(vo);
		}

		// 查找该节点的子节点所需要的权限
		for (AppMenuVo menu2 : volist) {
			if (StringUtils.isNotBlank(menu2.getHref())) {
				for (AppMenu menu3 : permissionList) {
					if (menu2.getId().equals(menu3.getParentId())) {
						String permission = menu3.getPermission();
						String key = permission.substring(permission.lastIndexOf(":") + 1);
						// 是否需要勾选当前选项
						boolean flag = hasPermission(selectedMenuList, menu3.getId());
						switch (key) {
						case "view": {
							menu2.setView(menu3.getId());
							// 检查是否具有该权限
							if (flag) {
								menu2.setViewChecked(true);
							}
						}
							break;
						case "add": {
							menu2.setAdd(menu3.getId());
							if (flag) {
								menu2.setAddChecked(true);
							}
						}
							break;
						case "edit": {
							menu2.setEdit(menu3.getId());
							if (flag) {
								menu2.setEditChecked(true);
							}
						}
							break;
						case "del": {
							menu2.setDel(menu3.getId());
							if (flag) {
								menu2.setDelChecked(true);
							}
						}
							break;
						case "audit": {
							menu2.setAudit(menu3.getId());
							if (flag) {
								menu2.setAuditChecked(true);
							}
						}
							break;
						case "print": {
							menu2.setPrint(menu3.getId());
							if (flag) {
								menu2.setPrintChecked(true);
							}
						}
							break;
						case "export": {
							menu2.setExport(menu3.getId());
							if (flag) {
								menu2.setExportChecked(true);
							}
						}
							break;
						case "daoru": {
							menu2.setDaoru(menu3.getId());
							if (flag) {
								menu2.setDaoruChecked(true);
							}
						}
							break;
						case "exec": {
							menu2.setExec(menu3.getId());
							if (flag) {
								menu2.setExecChecked(true);
							}
						}
							break;
						default:
							break;
						}

					}
				}
			}
		}
		return volist;
	}

	/**
	 * 验证是否具有该权限
	 * 
	 * @param appUserPermissionList
	 * @param id
	 * @return
	 */
	private boolean hasPermission(List<Integer> appUserPermissionList, String id) {
		if (StringUtils.isBlank(id)) {
			return false;
		}
		if (appUserPermissionList == null || appUserPermissionList.isEmpty()) {
			return false;
		}
		for (Integer i : appUserPermissionList) {
			if (i != null && (i.intValue() == Integer.valueOf(id).intValue())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkIsAuth(String id) {
		return appUserDao.checkIsAuth(id);
	}

}