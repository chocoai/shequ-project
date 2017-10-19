package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyRepairSettlementDetail;

/**
 * 物业维修核算明细DAO接口
 * @author nlp
 * @version 2017-05-18
 */
@MyBatisDao
public interface WyRepairSettlementDetailDao extends CrudDao<WyRepairSettlementDetail> {
	
}