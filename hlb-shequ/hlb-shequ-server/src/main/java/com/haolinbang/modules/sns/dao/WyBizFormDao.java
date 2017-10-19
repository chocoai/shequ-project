package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyBizForm;

/**
 * 业务表单定义DAO接口
 * @author nlp
 * @version 2017-06-07
 */
@MyBatisDao
public interface WyBizFormDao extends CrudDao<WyBizForm> {
	
}