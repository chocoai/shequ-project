package com.haolinbang.modules.cms.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.cms.entity.ArticleData;

/**
 * 文章DAO接口
 * 
 */
@MyBatisDao
public interface ArticleDataDao extends CrudDao<ArticleData> {

}
