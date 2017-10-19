package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyConvenienceService;

/**
 * 便民服务DAO接口
 * @author wxc
 * @version 2017-07-14
 */
@MyBatisDao
public interface WyConvenienceServiceDao extends CrudDao<WyConvenienceService> {
	
}