package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyOption;

/**
 * 选项表DAO接口
 * @author wxc
 * @version 2017-06-12
 */
@MyBatisDao
public interface WyOptionDao extends CrudDao<WyOption> {
	
}