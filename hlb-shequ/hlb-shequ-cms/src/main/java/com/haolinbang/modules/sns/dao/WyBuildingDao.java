package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyBuilding;

/**
 * 楼盘表DAO接口
 * @author wxc
 * @version 2017-07-15
 */
@MyBatisDao
public interface WyBuildingDao extends CrudDao<WyBuilding> {

	void deleteAll();

	WyBuilding getBySourceAndWyid(WyBuilding wyBuilding);
	
}