package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.TreeDao;
import com.haolinbang.common.persistence.annotation.DataSource;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * 
 */
@MyBatisDao
@DataSource("ds1")
public interface OfficeDao extends TreeDao<Office> {

}
