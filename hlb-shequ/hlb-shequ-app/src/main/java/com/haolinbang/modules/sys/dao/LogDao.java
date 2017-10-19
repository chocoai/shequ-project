package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.DataSource;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * 
 */
@MyBatisDao
@DataSource("ds1")
public interface LogDao extends CrudDao<Log> {

}
