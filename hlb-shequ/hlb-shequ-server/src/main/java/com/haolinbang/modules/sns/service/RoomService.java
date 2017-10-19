package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.Room;

/**
 * 房号实现类
 * 
 * @author Administrator
 * 
 */
public interface RoomService {
	
	List<Room> getRoomListByMemberID(Integer memberID);

	void insert(Room room);

	Room getRoom(String roomId);

	String getLastMonth(String wyid, String source) throws Exception;
	
	List<String> getSourceListByMemberId(String memberid);

	List<Room> getByMemberId_Source_WyId(Room room);

	void update(Room room);

}
