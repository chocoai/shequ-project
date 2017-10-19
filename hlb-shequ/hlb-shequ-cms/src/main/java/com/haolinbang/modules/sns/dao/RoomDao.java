package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.Room;

/**
 * 房屋信息DAO接口
 * 
 * @author wxc
 * @version 2017-06-05
 */
@MyBatisDao
public interface RoomDao extends CrudDao<Room> {

	List<Room> getRoomListByGid(@Param("source") String source, @Param("groupId") String groupId);

}