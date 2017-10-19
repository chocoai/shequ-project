package com.haolinbang.modules.cms.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.cms.entity.Link;

/**
 * 链接DAO接口
 * 
 */
@MyBatisDao
public interface LinkDao extends CrudDao<Link> {

	public List<Link> findByIdIn(String[] ids);

	public int updateExpiredWeight(Link link);

	public List<Link> findListByEntity();
}
