package com.haolinbang.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.constant.Constants;
import com.haolinbang.common.service.TreeService;
import com.haolinbang.modules.sys.dao.AreaDao;
import com.haolinbang.modules.sys.entity.Area;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * 
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {
	/**
	 * 查找所有区域
	 * 
	 * @return
	 */
	public List<Area> findAll() {
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	/**
	 * 查询省份
	 * 
	 * @param type
	 * @return
	 */
	public List<Area> findPovinces() {
		return findAreas(Constants.SYS_AREA_POVINCE, "");
	}

	/**
	 * 查找城市
	 * 
	 * @return
	 */
	public List<Area> findCities(String id) {
		return findAreas(Constants.SYS_AREA_CITY, id);
	}

	/**
	 * 查找区县
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public List<Area> findQus(String id) {
		return findAreas(Constants.SYS_AREA_QU, id);
	}

	/**
	 * 找到区域
	 * 
	 * @param type
	 *            类型
	 * @param id
	 *            父id
	 * @return
	 */
	public List<Area> findAreas(String type, String id) {
		List<Area> areas = UserUtils.getAreaList();
		List<Area> areas1 = new ArrayList<Area>();
		for (Area area : areas) {
			if (type.equals(area.getType())) {
				if (StringUtils.isNotBlank(id)) {
					if (id.equals(area.getParentId())) {
						areas1.add(area);
					}
				} else {
					areas1.add(area);
				}
			}
		}
		return areas1;
	}

	public List<Area> findPovinces(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
