package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.RoleMenu;

/**
 * 角色-菜单DAO接口
 * @author nlp
 * @version 2017-08-14
 */
@MyBatisDao
public interface RoleMenuDao extends CrudDao<RoleMenu> {
	
}