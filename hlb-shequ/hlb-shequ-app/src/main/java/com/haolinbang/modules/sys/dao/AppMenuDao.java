package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.AppMenu;

/**
 * 应用平台菜单表DAO接口
 * @author nlp
 * @version 2017-08-02
 */
@MyBatisDao
public interface AppMenuDao extends CrudDao<AppMenu> {
	
}