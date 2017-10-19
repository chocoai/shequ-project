package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyRepair;

/**
 * 物业报修DAO接口
 * @author nlp
 * @version 2017-04-17
 */
@MyBatisDao
public interface WyRepairDao extends CrudDao<WyRepair> {
	
}