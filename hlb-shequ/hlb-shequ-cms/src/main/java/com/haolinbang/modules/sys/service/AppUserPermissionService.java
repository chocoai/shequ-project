package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppUserPermission;
import com.haolinbang.modules.sys.dao.AppUserPermissionDao;

/**
 * 用户权限表Service
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppUserPermissionService extends CrudService<AppUserPermissionDao, AppUserPermission> {

	@Autowired
	private AppUserPermissionDao appUserPermissionDao;

	public AppUserPermission get(String id) {
		return super.get(id);
	}

	public List<AppUserPermission> findList(AppUserPermission appUserPermission) {
		return super.findList(appUserPermission);
	}

	public Page<AppUserPermission> findPage(Page<AppUserPermission> page, AppUserPermission appUserPermission) {
		return super.findPage(page, appUserPermission);
	}

	@Transactional(readOnly = false)
	public void save(AppUserPermission appUserPermission) {
		super.save(appUserPermission);
	}

	@Transactional(readOnly = false)
	public void delete(AppUserPermission appUserPermission) {
		super.delete(appUserPermission);
	}

	@Transactional(readOnly = false)
	public void deleteByPermissions(String uid) {
		appUserPermissionDao.deleteByPermissions(uid);
	}

}