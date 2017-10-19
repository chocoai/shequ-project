package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyReBizAct;

/**
 * 工作流和实际的业务对应关系表DAO接口
 * 
 * @author nlp
 * @version 2017-05-08
 */
@MyBatisDao
public interface WyReBizActDao extends CrudDao<WyReBizAct> {

	List<WyReBizAct> getBizActs(@Param("wyReBizAct") WyReBizAct wyReBizAct);

	WyReBizAct getWyReBizActByActIdAndBizId(@Param("actId") String actId, @Param("bizId") String bizId);

	WyReBizAct getWyReBizActByActId(@Param("bizId") String bizId);

	WyReBizAct getBizActByDefId(@Param("procDefinitionId") String procDefinitionId);

}