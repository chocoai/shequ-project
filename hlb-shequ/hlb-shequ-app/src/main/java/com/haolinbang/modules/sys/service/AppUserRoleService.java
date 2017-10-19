package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppUserRole;
import com.haolinbang.modules.sys.dao.AppUserRoleDao;

/**
 * 用户角色表Service
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppUserRoleService extends CrudService<AppUserRoleDao, AppUserRole> {

	@Autowired
	private AppUserRoleDao appUserRoleDao;

	public AppUserRole get(String id) {
		return super.get(id);
	}

	public List<AppUserRole> findList(AppUserRole appUserRole) {
		return super.findList(appUserRole);
	}

	public Page<AppUserRole> findPage(Page<AppUserRole> page, AppUserRole appUserRole) {
		return super.findPage(page, appUserRole);
	}

	@Transactional(readOnly = false)
	public void save(AppUserRole appUserRole) {
		super.save(appUserRole);
	}

	@Transactional(readOnly = false)
	public void delete(AppUserRole appUserRole) {
		super.delete(appUserRole);
	}

	@Transactional(readOnly = false)
	public void deleteByUid(String uid) {
		appUserRoleDao.deleteByUid(uid);
	}

}