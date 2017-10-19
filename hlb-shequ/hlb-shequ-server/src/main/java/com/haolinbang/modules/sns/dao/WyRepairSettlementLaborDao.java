package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyRepairBudgetLabor;
import com.haolinbang.modules.sns.entity.WyRepairSettlementLabor;

/**
 * 物业维修核算工时明细DAO接口
 * 
 * @author nlp
 * @version 2017-05-18
 */
@MyBatisDao
public interface WyRepairSettlementLaborDao extends CrudDao<WyRepairSettlementLabor> {

	List<WyRepairBudgetLabor> getWyRepairBudgetLaborByRepairId(@Param("repairId") String repairId);

	int saveList(@Param("wyRepairSettlementLaborList") List<WyRepairSettlementLabor> wyRepairSettlementLaborList);

	boolean deleteById(@Param("id") String id);

	List<WyRepairSettlementLabor> getWyRepairSettlementLaborsByRepairId(@Param("repairId") String repairId);

}