package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppRoleMenu;
import com.haolinbang.modules.sys.dao.AppRoleMenuDao;

/**
 * 角色菜单表Service
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppRoleMenuService extends CrudService<AppRoleMenuDao, AppRoleMenu> {

	@Autowired
	private AppRoleMenuDao appRoleMenuDao;

	public AppRoleMenu get(String id) {
		return super.get(id);
	}

	public List<AppRoleMenu> findList(AppRoleMenu appRoleMenu) {
		return super.findList(appRoleMenu);
	}

	public Page<AppRoleMenu> findPage(Page<AppRoleMenu> page, AppRoleMenu appRoleMenu) {
		return super.findPage(page, appRoleMenu);
	}

	@Transactional(readOnly = false)
	public void save(AppRoleMenu appRoleMenu) {
		super.save(appRoleMenu);
	}

	@Transactional(readOnly = false)
	public void delete(AppRoleMenu appRoleMenu) {
		super.delete(appRoleMenu);
	}

	@Transactional(readOnly = false)
	public void deleteByRoleId(String id) {
		appRoleMenuDao.deleteByRoleId(id);
	}

}