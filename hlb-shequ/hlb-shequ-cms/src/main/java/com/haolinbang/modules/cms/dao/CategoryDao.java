package com.haolinbang.modules.cms.dao;

import java.util.List;
import java.util.Map;

import com.haolinbang.common.persistence.TreeDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.cms.entity.Category;

/**
 * 栏目DAO接口
 * 
 */
@MyBatisDao
public interface CategoryDao extends TreeDao<Category> {

	public List<Category> findModule(Category category);

	public List<Category> findByModule(String module);

	public List<Category> findByParentId(String parentId, String isMenu);

	public List<Category> findByParentIdAndSiteId(Category entity);

	public List<Map<String, Object>> findStats(String sql);

	public List<Category> findByIdIn(String[] ids);

	public List<Category> find(Category category);

	public List<Category> findByUserId(Long userId);

}
