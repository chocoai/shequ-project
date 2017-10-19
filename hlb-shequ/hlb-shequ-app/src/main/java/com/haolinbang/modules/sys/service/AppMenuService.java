package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.dao.AppMenuDao;

/**
 * 应用平台菜单表Service
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppMenuService extends CrudService<AppMenuDao, AppMenu> {

	public AppMenu get(String id) {
		return super.get(id);
	}
	
	public List<AppMenu> findList(AppMenu appMenu) {
		return super.findList(appMenu);
	}
	
	public Page<AppMenu> findPage(Page<AppMenu> page, AppMenu appMenu) {
		return super.findPage(page, appMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(AppMenu appMenu) {
		super.save(appMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppMenu appMenu) {
		super.delete(appMenu);
	}
	
}