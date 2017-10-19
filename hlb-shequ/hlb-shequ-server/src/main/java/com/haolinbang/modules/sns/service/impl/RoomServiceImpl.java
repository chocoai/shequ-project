package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.thridwy.haolong.bean.GetLastMonth;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.modules.sns.dao.RoomDao;
import com.haolinbang.modules.sns.dao.UrlmapDao;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.service.RoomService;

/**
 * 注册实现逻辑
 * 
 * @author Administrator
 * 
 */
@Service
public class RoomServiceImpl implements RoomService {

	private static Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private UrlmapDao urlmapDao;

	@Override
	public List<Room> getRoomListByMemberID(Integer memberID) {
		Room room = new Room();
		room.setMemberId(memberID);
		List<Room> roomList = roomDao.findAllList(room);

		return roomList;
	}

	@Override
	public void insert(Room room) {
		roomDao.insert(room);
	}

	@Override
	public Room getRoom(String roomId) {

		return roomDao.get(roomId);
	}

	@Override
	public String getLastMonth(String wyid, String source) throws Exception {
		Urlmap urlmap = new Urlmap();
		urlmap.setUrlkey(source);
		urlmap.setStatus("1");
		urlmap = urlmapDao.getUrlmap(urlmap);

		GetLastMonth bean = new GetLastMonth();
		bean.setUrlString(urlmap.getUrlstring());
		bean.setSoapActionString(urlmap.getSoapactionstring());
		bean.setWYID(wyid);
		bean.setSecretkey(urlmap.getSecretkey());

		String JFYF = HaolongUtils.getLastMonth(bean);

		return JFYF;
	}

	@Override
	public List<String> getSourceListByMemberId(String memberId) {

		return roomDao.getSourceList(memberId);
	}

	@Override
	public List<Room> getByMemberId_Source_WyId(Room room) {
		// TODO Auto-generated method stub
		return roomDao.getByMemberId_Source_WyId(room);
	}

	@Override
	public void update(Room room) {
		roomDao.update(room);
	}

}
