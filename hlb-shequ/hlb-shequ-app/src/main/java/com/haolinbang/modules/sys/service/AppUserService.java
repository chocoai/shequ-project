package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sys.dao.AppUserDao;
import com.haolinbang.modules.sys.entity.AppUser;
import com.haolinbang.modules.sys.entity.AppUserPermission;
import com.haolinbang.modules.sys.entity.AppUserRole;
import com.haolinbang.modules.sys.entity.AppUserScope;
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

		// 先删除原来的权限
		appUserPermissionService.deleteByPermissions(uid);
		if (StringUtils.isNotBlank(permission)) {
			String[] permissionArr = permission.split(",");
			// 在保存新的权限
			for (String menuId : permissionArr) {
				AppUserPermission appUserPermission = new AppUserPermission();
				appUserPermission.setUserId(Long.valueOf(uid));
				appUserPermission.setMenuId(Integer.valueOf(menuId));
				appUserPermissionService.save(appUserPermission);
			}
		}

		// 组装保存角色
		// 批量删除角色
		appUserRoleService.deleteByUid(uid);
		String role = vo.getRole();
		if (StringUtils.isNotBlank(role)) {
			String[] roleArr = role.split(",");
			for (String roleId : roleArr) {
				AppUserRole appUserRole = new AppUserRole();
				appUserRole.setUserId(Long.valueOf(uid));
				appUserRole.setRoleId(Integer.valueOf(roleId));
				appUserRoleService.save(appUserRole);
			}
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

		// 保存数据范围
		appUserScopeService.deleteByUserId(uid);
		String[] groupids = vo.getGroupIds();
		if (groupids != null && groupids.length > 0) {
			for (String groupid : groupids) {
				String[] arr = groupid.split("_");
				String gid2 = arr[1];
				AppUserScope appUserScope = new AppUserScope();
				appUserScope.setType(arr[0]);
				appUserScope.setGroupId(gid2);
				appUserScope.setUserId(uid);
				if ("G".equals(arr[0])) {
					GroupInfo groupInfo = UserUtils.getGroupInfo(gid2, source);
					appUserScope.setParentGroupId(groupInfo.getParentId());
				}
				if("WY".equals(arr[0])){
					appUserScope.setParentGroupId(arr[3]);
				}
				appUserScopeService.save(appUserScope);
			}
		}

		logger.info("uid={},permission={}", uid, permission);

		return true;
	}

	public boolean checkIsAuth(String id) {
		return appUserDao.checkIsAuth(id);
	}

}