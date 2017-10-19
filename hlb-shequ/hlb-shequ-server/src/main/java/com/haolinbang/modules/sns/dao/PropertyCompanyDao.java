package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.PropertyCompany;

/**
 * 字典DAO接口
 * 
 */
@MyBatisDao
public interface PropertyCompanyDao extends CrudDao<PropertyCompany> {

}
