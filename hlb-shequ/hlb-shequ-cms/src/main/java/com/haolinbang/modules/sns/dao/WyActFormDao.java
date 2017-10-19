package com.haolinbang.modules.sns.dao;

import java.util.List;

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

	List<WyActForm> getWyActFormListByprocDefIdAndTaskid(@Param("procDefinitionId") String procDefinitionId, @Param("activitiId") String activitiId);

	WyActForm getWyActFormByProcDefIdAndTaskIdAndFormType(@Param("procDefId") String procDefId, @Param("activitiId") String activitiId, @Param("formType") String formType);

	WyActForm getWyActFormByProcDefIdAndTaskId(@Param("procDefId") String procDefId, @Param("activitiId") String activitiId);

}