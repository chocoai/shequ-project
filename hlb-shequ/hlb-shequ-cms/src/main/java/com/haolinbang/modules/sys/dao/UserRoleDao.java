package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.UserRole;

/**
 * 用户-角色DAO接口
 * @author nlp
 * @version 2017-08-14
 */
@MyBatisDao
public interface UserRoleDao extends CrudDao<UserRole> {
	
}