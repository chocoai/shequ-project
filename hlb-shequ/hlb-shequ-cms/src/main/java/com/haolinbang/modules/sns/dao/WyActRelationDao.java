package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActRelation;

/**
 * 流程实例对应的关系表DAO接口
 * @author nlp
 * @version 2017-05-03
 */
@MyBatisDao
public interface WyActRelationDao extends CrudDao<WyActRelation> {
	
}