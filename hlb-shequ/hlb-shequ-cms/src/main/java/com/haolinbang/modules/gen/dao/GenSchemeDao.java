package com.haolinbang.modules.gen.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.gen.entity.GenScheme;

/**
 * 生成方案DAO接口
 * 
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {

}
