package com.haolinbang.modules.gen.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * 
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {

	public void deleteByGenTableId(String genTableId);
}
