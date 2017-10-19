package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyFleaMarket;

/**
 * 跳蚤市场DAO接口
 * @author nlp
 * @version 2017-07-07
 */
@MyBatisDao
public interface WyFleaMarketDao extends CrudDao<WyFleaMarket> {

	void updates(WyFleaMarket wyFleaMarket);
	
}