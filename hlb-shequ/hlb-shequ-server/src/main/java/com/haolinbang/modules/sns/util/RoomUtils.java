package com.haolinbang.modules.sns.util;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.sns.dao.RoomDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;

public class RoomUtils {

	private static RoomDao wxRoomDao = SpringContextHolder.getBean(RoomDao.class);

	/**
	 * 获取登录session中的Room信息
	 * 
	 * @param request
	 * @return
	 */
	public static Room getCurrentRoom() {
		String roomId = (String) ServletUtil.getSession().getAttribute("roomId");
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		if (StringUtils.isBlank(roomId)) {
			Member member = MemberUtils.getCurrentMember();
			return wxRoomDao.get(member.getRoomId());
		}
		return wxRoomDao.get(roomId);
	}
}
