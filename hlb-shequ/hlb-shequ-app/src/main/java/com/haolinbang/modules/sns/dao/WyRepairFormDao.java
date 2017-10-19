package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyRepairForm;

/**
 * 维修物料明细表单DAO接口
 * @author wxc
 * @version 2017-05-05
 */
@MyBatisDao
public interface WyRepairFormDao extends CrudDao<WyRepairForm> {
	
}