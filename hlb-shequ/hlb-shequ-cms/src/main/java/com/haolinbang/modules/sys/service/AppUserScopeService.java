package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.dao.AppUserScopeDao;

/**
 * 用户使用数据范围Service
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppUserScopeService extends CrudService<AppUserScopeDao, AppUserScope> {

	@Autowired
	private AppUserScopeDao appUserScopeDao;

	public AppUserScope get(String id) {
		return super.get(id);
	}

	public List<AppUserScope> findList(AppUserScope appUserScope) {
		return super.findList(appUserScope);
	}

	public Page<AppUserScope> findPage(Page<AppUserScope> page, AppUserScope appUserScope) {
		return super.findPage(page, appUserScope);
	}

	@Transactional(readOnly = false)
	public void save(AppUserScope appUserScope) {
		super.save(appUserScope);
	}

	@Transactional(readOnly = false)
	public void delete(AppUserScope appUserScope) {
		super.delete(appUserScope);
	}

	public List<AppUserScope> getAppUserScopeByUid(String uid) {
		return appUserScopeDao.getAppUserScopeByUid(uid);
	}

	public void deleteByUid(String uid) {
		appUserScopeDao.deleteByUid(uid);
	}

}