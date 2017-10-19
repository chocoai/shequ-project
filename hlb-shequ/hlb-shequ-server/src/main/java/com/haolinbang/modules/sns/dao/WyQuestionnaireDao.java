package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyQuestionnaire;

/**
 * 问卷调查表DAO接口
 * @author wxc
 * @version 2017-06-08
 */
@MyBatisDao
public interface WyQuestionnaireDao extends CrudDao<WyQuestionnaire> {
	
}