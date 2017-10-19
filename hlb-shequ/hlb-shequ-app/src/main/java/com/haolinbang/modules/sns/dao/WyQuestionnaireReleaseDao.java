package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyQuestionnaireRelease;

/**
 * 问卷发布表DAO接口
 * @author wxc
 * @version 2017-06-19
 */
@MyBatisDao
public interface WyQuestionnaireReleaseDao extends CrudDao<WyQuestionnaireRelease> {
	public WyQuestionnaireRelease get1(String id);
}