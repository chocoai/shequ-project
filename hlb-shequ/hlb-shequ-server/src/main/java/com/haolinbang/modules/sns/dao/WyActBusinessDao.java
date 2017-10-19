package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActBusiness;

/**
 * 工作流程和业务关联配置表DAO接口
 * @author nlp
 * @version 2017-05-05
 */
@MyBatisDao
public interface WyActBusinessDao extends CrudDao<WyActBusiness> {
	
}