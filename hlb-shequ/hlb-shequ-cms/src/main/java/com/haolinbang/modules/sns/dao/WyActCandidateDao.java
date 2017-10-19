package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActCandidate;

/**
 * 候选人表或者群组表DAO接口
 * 
 * @author NLP
 * @version 2017-05-03
 */
@MyBatisDao
public interface WyActCandidateDao extends CrudDao<WyActCandidate> {

	WyActCandidate getWyActCandidateByProcDefIdAndTaskIdAndCandidate(@Param("procDefId") String procDefId, @Param("taskId") String taskId, @Param("candidate") String candidate);

	List<WyActCandidate> getWyActCandidateByProcDefIdAndTaskIdAndType(@Param("procDefId") String procDefId, @Param("taskId") String taskId, @Param("type") String type);

	List<WyActCandidate> getWyActCandidateByProcDefIdAndTaskId(@Param("procDefId") String procDefId, @Param("taskId") String taskId);

	WyActCandidate getWyActCandidateByPidAndTaskidAndType(@Param("procDefId") String procDefId, String taskId, String type);

	WyActCandidate getWyActCandidateByPidAndTaskidAndTypeAndSource(@Param("procDefId") String procDefId, @Param("taskId") String taskId, @Param("type") String type,
			@Param("source") String source);

	WyActCandidate getWyActCandidateByPidAndTaskidAndSource(@Param("procDefId") String procDefId, @Param("taskId") String taskId, @Param("source") String source);

	List<WyActCandidate> getWyActCandidateSByPidAndTaskidAndSource(@Param("procDefId") String procDefId, @Param("taskId") String taskId, @Param("source") String source);

	WyActCandidate getWyActCandidateByPidAndTaskidAndTypeAndSourceAndCandidate(@Param("procDefId") String procDefId, @Param("taskId") String taskId,
			@Param("source") String source, @Param("candidate") String candidate);

}