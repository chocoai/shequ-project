package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppRole;

/**
 * 应用平台角色DAO接口
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppRoleDao extends CrudDao<AppRole> {
	
}