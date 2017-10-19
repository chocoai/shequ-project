package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.DictType;

/**
 * 字典分类DAO接口
 * @author nlp
 * @version 2017-08-15
 */
@MyBatisDao
public interface DictTypeDao extends CrudDao<DictType> {
	
}