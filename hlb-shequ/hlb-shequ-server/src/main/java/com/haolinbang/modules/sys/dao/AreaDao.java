package com.haolinbang.modules.sys.dao;

import java.util.List;

import com.haolinbang.common.persistence.TreeDao;
import com.haolinbang.common.persistence.annotation.DataSource;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * 
 */
@MyBatisDao
@DataSource("ds1")
public interface AreaDao extends TreeDao<Area> {

	public List<Area> findPovinces(String type);

}
