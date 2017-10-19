package com.haolinbang.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppUserScope;

/**
 * 用户使用数据范围DAO接口
 * 
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppUserScopeDao extends CrudDao<AppUserScope> {

	List<AppUserScope> getAppUserScopeByUid(@Param("uid") String uid);

	void deleteByUid(@Param("uid") String uid);

}