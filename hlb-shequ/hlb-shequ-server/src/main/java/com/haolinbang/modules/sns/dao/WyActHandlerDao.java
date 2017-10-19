package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActHandler;

/**
 * 流程处理后调用方法DAO接口
 * 
 * @author nlp
 * @version 2017-05-26
 */
@MyBatisDao
public interface WyActHandlerDao extends CrudDao<WyActHandler> {

	List<WyActHandler> getWyActHandlerByProcdefidAndTaskkey(@Param("procDefId") String procDefId, @Param("taskDefinitionKey") String taskDefinitionKey,
			@Param("eventName") String eventName);

	List<WyActHandler> getWyActHandlerByProcdefidAndTaskkeyByType(@Param("procDefId") String procDefId, @Param("taskDefinitionKey") String taskDefinitionKey,
			@Param("eventName") String eventName, @Param("type") String type);

}