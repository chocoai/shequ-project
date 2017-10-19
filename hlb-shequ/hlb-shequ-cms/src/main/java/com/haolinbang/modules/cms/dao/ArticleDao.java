package com.haolinbang.modules.cms.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.cms.entity.Article;
import com.haolinbang.modules.cms.entity.Category;

/**
 * 文章DAO接口
 * 
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {

	public List<Article> findByIdIn(String[] ids);

	public int updateHitsAddOne(String id);

	public int updateExpiredWeight(Article article);

	public List<Category> findStats(Category category);

}
