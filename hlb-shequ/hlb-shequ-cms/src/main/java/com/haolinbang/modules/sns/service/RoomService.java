package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.dao.RoomDao;

/**
 * 房屋信息Service
 * @author wxc
 * @version 2017-06-05
 */
@Service
@Transactional(readOnly = true)
public class RoomService extends CrudService<RoomDao, Room> {

	public Room get(String id) {
		return super.get(id);
	}
	
	public List<Room> findList(Room room) {
		return super.findList(room);
	}
	
	public Page<Room> findPage(Page<Room> page, Room room) {
		return super.findPage(page, room);
	}
	
	@Transactional(readOnly = false)
	public void save(Room room) {
		super.save(room);
	}
	
	@Transactional(readOnly = false)
	public void delete(Room room) {
		super.delete(room);
	}
	
}