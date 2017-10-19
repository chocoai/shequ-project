package com.haolinbang.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppUser;

/**
 * 应用平台用户DAO接口
 * 
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppUserDao extends CrudDao<AppUser> {

	AppUser getAppUserByStaffId(@Param("source") String source, @Param("staffId") Integer staffId);

	boolean checkIsAuth(@Param("id") String id);

}