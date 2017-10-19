package com.haolinbang.modules.sns.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActForm;

/**
 * 流程表单管理DAO接口
 * 
 * @author nlp
 * @version 2017-06-01
 */
@MyBatisDao
public interface WyActFormDao extends CrudDao<WyActForm> {

	WyActForm getWyActFormByProcinsidAndTaskkey(@Param("procDefId") String procDefId, @Param("taskKey") String taskKey, @Param("formType") String formType);

	WyActForm getWyActFormByProcDefIdAndTaskKey(@Param("procDefId") String procDefId, @Param("taskKey") String taskKey);

}