package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyDianzanRecord;

/**
 * 点赞记录DAO接口
 * @author nlp
 * @version 2017-07-20
 */
@MyBatisDao
public interface WyDianzanRecordDao extends CrudDao<WyDianzanRecord> {
	
}