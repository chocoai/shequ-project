package com.haolinbang.modules.sns.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.Room;

/**
 * 字典DAO接口
 * 
 */
@MyBatisDao
public interface RoomDao extends CrudDao<Room> {

	List<String> getSourceList(String memberId);

	List<Room> getByMemberId_Source_WyId(Room room);

}
