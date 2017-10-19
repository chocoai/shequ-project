package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyMemberRefQuestionnaire;

/**
 * 会员调查表DAO接口
 * @author wxc
 * @version 2017-06-19
 */
@MyBatisDao
public interface WyMemberRefQuestionnaireDao extends CrudDao<WyMemberRefQuestionnaire> {

	Integer getNum(Integer subjectid);
	
}