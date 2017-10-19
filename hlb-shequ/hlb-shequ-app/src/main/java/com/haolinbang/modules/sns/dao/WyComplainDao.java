package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyComplain;

/**
 * 物业投诉表DAO接口
 * @author wxc
 * @version 2017-07-04
 */
@MyBatisDao
public interface WyComplainDao extends CrudDao<WyComplain> {
	
}