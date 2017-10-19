package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyReBizActInstance;

/**
 * 具体流程引用DAO接口
 * @author nlp
 * @version 2017-09-11
 */
@MyBatisDao
public interface WyReBizActInstanceDao extends CrudDao<WyReBizActInstance> {
	
}