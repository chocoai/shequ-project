package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppLog;

/**
 * 日志表DAO接口
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppLogDao extends CrudDao<AppLog> {
	
}