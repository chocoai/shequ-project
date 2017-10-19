package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyBuilding;

/**
 * 楼盘表DAO接口
 * 
 * @author wxc
 * @version 2017-07-15
 */
@MyBatisDao
public interface WyBuildingDao extends CrudDao<WyBuilding> {

	void deleteAll();

	List<WyBuilding> getBuildsBySource(@Param("source") String source);

	List<WyBuilding> getWyBuildingListByGid(@Param("source") String source, @Param("groupId") String groupId);

	WyBuilding getBySourceAndWyid(@Param("source") String source, @Param("wyid") String wyid);
}