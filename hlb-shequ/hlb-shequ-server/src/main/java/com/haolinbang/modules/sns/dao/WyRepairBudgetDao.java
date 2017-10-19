package com.haolinbang.modules.sns.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyRepairBudget;

/**
 * 物业维修预算汇总DAO接口
 * 
 * @author nlp
 * @version 2017-05-18
 */
@MyBatisDao
public interface WyRepairBudgetDao extends CrudDao<WyRepairBudget> {

	WyRepairBudget getWyRepairBudgetByRepairId(@Param("repairId") String repairId);

}