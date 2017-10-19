package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyNoticeRelation;

/**
 * 不同通知处理方法DAO接口
 * @author nlp
 * @version 2017-07-03
 */
@MyBatisDao
public interface WyNoticeRelationDao extends CrudDao<WyNoticeRelation> {
	
}