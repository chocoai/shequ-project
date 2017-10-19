package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxAccountArticle;

/**
 * 账号和文章对应DAO接口
 * @author nlp
 * @version 2016-02-07
 */
@MyBatisDao
public interface WxAccountArticleDao extends CrudDao<WxAccountArticle> {
	
}