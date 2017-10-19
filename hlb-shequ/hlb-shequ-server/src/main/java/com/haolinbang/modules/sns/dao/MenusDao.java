package com.haolinbang.modules.sns.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.Menus;

/**
 * 字典DAO接口
 * 
 */
@MyBatisDao
public interface MenusDao extends CrudDao<Menus> {
	
	public List<Menus> getMenusList(int type);
	
}
