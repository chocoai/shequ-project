package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxText;

/**
 * 微信文本DAO接口
 * @author nlp
 * @version 2016-02-13
 */
@MyBatisDao
public interface WxTextDao extends CrudDao<WxText> {
	
}