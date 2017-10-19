package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyRepairSettlementMateriel;

/**
 * 物业维修核算物料明细DAO接口
 * @author nlp
 * @version 2017-05-18
 */
@MyBatisDao
public interface WyRepairSettlementMaterielDao extends CrudDao<WyRepairSettlementMateriel> {
	
}