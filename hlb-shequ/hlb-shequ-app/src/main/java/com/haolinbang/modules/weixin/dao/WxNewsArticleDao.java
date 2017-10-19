package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;

/**
 * 微信图文消息DAO接口
 * @author nlp
 * @version 2017-07-11
 */
@MyBatisDao
public interface WxNewsArticleDao extends CrudDao<WxNewsArticle> {
	
}