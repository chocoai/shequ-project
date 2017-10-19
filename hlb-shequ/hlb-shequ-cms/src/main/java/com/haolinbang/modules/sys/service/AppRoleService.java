package com.haolinbang.modules.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppRole;
import com.haolinbang.modules.sys.entity.AppRoleMenu;
import com.haolinbang.modules.sys.dao.AppRoleDao;

/**
 * 应用平台角色Service
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppRoleService extends CrudService<AppRoleDao, AppRole> {

	@Autowired
	private AppRoleMenuService appRoleMenuService;

	public AppRole get(String id) {
		return super.get(id);
	}

	public List<AppRole> findList(AppRole appRole) {
		return super.findList(appRole);
	}

	public Page<AppRole> findPage(Page<AppRole> page, AppRole appRole) {
		return super.findPage(page, appRole);
	}

	@Transactional(readOnly = false)
	public void save(AppRole appRole) {
		super.save(appRole);

		// 保存选择的权限菜单
		String permissions = appRole.getPermission();
		if (StringUtils.isNotBlank(permissions)) {
			// 先删除该角色下的所有菜单
			appRoleMenuService.deleteByRoleId(appRole.getId());

			String[] permissionArr = permissions.split(",");
			for (String menuId : permissionArr) {
				AppRoleMenu appRoleMenu = new AppRoleMenu();
				appRoleMenu.setRoleId(Integer.valueOf(appRole.getId()));
				appRoleMenu.setMenuId(Integer.valueOf(menuId));
				appRoleMenuService.save(appRoleMenu);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(AppRole appRole) {
		super.delete(appRole);
	}

}