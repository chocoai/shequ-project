package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.UserRole;
import com.haolinbang.modules.sys.dao.UserRoleDao;

/**
 * 用户-角色Service
 * @author nlp
 * @version 2017-08-14
 */
@Service
@Transactional(readOnly = true)
public class UserRoleService extends CrudService<UserRoleDao, UserRole> {

	public UserRole get(String id) {
		return super.get(id);
	}
	
	public List<UserRole> findList(UserRole userRole) {
		return super.findList(userRole);
	}
	
	public Page<UserRole> findPage(Page<UserRole> page, UserRole userRole) {
		return super.findPage(page, userRole);
	}
	
	@Transactional(readOnly = false)
	public void save(UserRole userRole) {
		super.save(userRole);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserRole userRole) {
		super.delete(userRole);
	}
	
}