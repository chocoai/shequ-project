package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyMenus;

/**
 * 首页菜单DAO接口
 * @author wxc
 * @version 2017-06-02
 */
@MyBatisDao
public interface WyMenusDao extends CrudDao<WyMenus> {
	
}