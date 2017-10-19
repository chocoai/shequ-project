package com.haolinbang.modules.cms.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.cms.entity.Site;

/**
 * 站点DAO接口
 * 
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

}
