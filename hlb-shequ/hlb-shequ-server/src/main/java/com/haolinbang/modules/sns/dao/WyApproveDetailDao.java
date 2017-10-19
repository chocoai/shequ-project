package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyApproveDetail;

/**
 * 审批明细表DAO接口
 * 
 * @author nlp
 * @version 2017-06-29
 */
@MyBatisDao
public interface WyApproveDetailDao extends CrudDao<WyApproveDetail> {

	WyApproveDetail getWyApproveDetailByRelationIdAndTaskKey(@Param("relationId") String relationId, @Param("taskKey") String taskKey);

	List<WyApproveDetail> getWyApproveDetailListByRelationId(@Param("relationId") String relationId);

	WyApproveDetail getWyApproveDetailByProcInstIdAndTaskKey(@Param("procInsId") String procInsId, @Param("taskKey") String taskKey);

	List<WyApproveDetail> getWyApproveDetailListByProcInstId(@Param("procInstId") String procInstId);

	WyApproveDetail geLatestWyApproveDetailByProcInstId(@Param("procInstId") String procInstId);

}