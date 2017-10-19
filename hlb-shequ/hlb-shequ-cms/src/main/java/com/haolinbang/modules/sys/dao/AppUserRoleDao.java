package com.haolinbang.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppUserRole;

/**
 * 用户角色表DAO接口
 * 
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppUserRoleDao extends CrudDao<AppUserRole> {

	void deleteByRoles(@Param("uid") String uid, @Param("role") String role);

}