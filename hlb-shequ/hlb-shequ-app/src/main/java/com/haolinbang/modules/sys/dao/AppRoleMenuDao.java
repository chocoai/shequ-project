package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppRoleMenu;

/**
 * 角色菜单表DAO接口
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppRoleMenuDao extends CrudDao<AppRoleMenu> {
	
}