package com.haolinbang.modules.gen.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * 
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {

}
