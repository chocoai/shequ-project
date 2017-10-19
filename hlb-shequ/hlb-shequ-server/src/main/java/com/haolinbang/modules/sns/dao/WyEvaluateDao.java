package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyEvaluate;

/**
 * 物业评论表DAO接口
 * @author nlp
 * @version 2017-05-11
 */
@MyBatisDao
public interface WyEvaluateDao extends CrudDao<WyEvaluate> {
	
}