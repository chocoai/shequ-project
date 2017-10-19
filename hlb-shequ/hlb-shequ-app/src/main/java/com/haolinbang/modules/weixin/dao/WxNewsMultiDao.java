package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxNewsMulti;

/**
 * 微信多图文DAO接口
 * @author nlp
 * @version 2016-02-13
 */
@MyBatisDao
public interface WxNewsMultiDao extends CrudDao<WxNewsMulti> {
	
}