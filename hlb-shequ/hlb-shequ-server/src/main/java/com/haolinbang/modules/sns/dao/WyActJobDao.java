package com.haolinbang.modules.sns.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActJob;

/**
 * 工作流定时任务时间表DAO接口
 * 
 * @author nlp
 * @version 2017-06-09
 */
@MyBatisDao
public interface WyActJobDao extends CrudDao<WyActJob> {

	WyActJob getWyActJobByProcdefidAndTaskidAndType(@Param("procDefId") String procDefId, @Param("taskDefinitionKey") String taskDefinitionKey, @Param("type") String type);

}