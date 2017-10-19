package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppRole;
import com.haolinbang.modules.sys.dao.AppRoleDao;

/**
 * 应用平台角色Service
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppRoleService extends CrudService<AppRoleDao, AppRole> {

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
	}
	
	@Transactional(readOnly = false)
	public void delete(AppRole appRole) {
		super.delete(appRole);
	}
	
}