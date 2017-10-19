package com.haolinbang.modules.sns.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActDef;

/**
 * 工作流程定义表DAO接口
 * 
 * @author nlp
 * @version 2017-05-05
 */
@MyBatisDao
public interface WyActDefDao extends CrudDao<WyActDef> {

	WyActDef getWyActDefByProcDefId(@Param("defid") String defid);

}