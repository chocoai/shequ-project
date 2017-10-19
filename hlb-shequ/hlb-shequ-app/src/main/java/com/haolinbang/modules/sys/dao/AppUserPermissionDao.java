package com.haolinbang.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppUserPermission;
import com.haolinbang.modules.sys.entity.Menu;

/**
 * 用户权限表DAO接口
 * 
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppUserPermissionDao extends CrudDao<AppUserPermission> {

	void deleteByPermissions(@Param("uid") String uid);

	List<Menu> findByUserId(@Param("id") String id);

}