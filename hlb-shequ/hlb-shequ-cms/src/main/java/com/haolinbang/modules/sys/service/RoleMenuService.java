package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.RoleMenu;
import com.haolinbang.modules.sys.dao.RoleMenuDao;

/**
 * 角色-菜单Service
 * @author nlp
 * @version 2017-08-14
 */
@Service
@Transactional(readOnly = true)
public class RoleMenuService extends CrudService<RoleMenuDao, RoleMenu> {

	public RoleMenu get(String id) {
		return super.get(id);
	}
	
	public List<RoleMenu> findList(RoleMenu roleMenu) {
		return super.findList(roleMenu);
	}
	
	public Page<RoleMenu> findPage(Page<RoleMenu> page, RoleMenu roleMenu) {
		return super.findPage(page, roleMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(RoleMenu roleMenu) {
		super.save(roleMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(RoleMenu roleMenu) {
		super.delete(roleMenu);
	}
	
}