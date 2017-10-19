package com.haolinbang.modules.sns.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyDianzanRecord;
import com.haolinbang.modules.sns.entity.WyHouseRent;

/**
 * 房屋租售DAO接口
 * 
 * @author nlp
 * @version 2017-07-07
 */
@MyBatisDao
public interface WyHouseRentDao extends CrudDao<WyHouseRent> {

	boolean dianzan(WyDianzanRecord wyDianzanRecord);

	WyDianzanRecord getWyDianzanRecordByRelationId(WyDianzanRecord wyDianzanRecord);

	List<WyHouseRent> getWyHouseRentList(WyHouseRent wyHouseRent);

	void updates(WyHouseRent wyHouseRent);

}