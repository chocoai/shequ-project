package com.haolinbang.modules.sys.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.Hlbregister;

/**
 * 注册表DAO接口
 * @author nlp
 * @version 2017-09-07
 */
@MyBatisDao
public interface HlbregisterDao extends CrudDao<Hlbregister> {

	Hlbregister getHlbregister(Hlbregister userHlbregister);
	
}