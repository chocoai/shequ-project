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

	List<WyActCandidate> getWyActCandidateByDefidAndDefkey(@Param("defid") String defid, @Param("defKey") String defKey);

	WyActCandidate getWyActCandidateByDefidAndDefkeyAndType(@Param("procDefId") String procDefId, @Param("taskKey") String taskKey, @Param("type") String type);

	WyActCandidate getWyActCandidateByDefidAndDefkeyAndSource(@Param("defid") String defid, @Param("defKey") String defKey, @Param("source") String source);

	WyActCandidate getWyActCandidateByPidAndTaskidAndSource(@Param("procDefId") String procDefId, @Param("taskId") String taskId, @Param("source") String source);

	WyActCandidate getWyActCandidateByDefidAndSpecifyIdAndSource(@Param("defid") String defid, @Param("specifyId") String specifyId, @Param("source") String source);

	WyActCandidate getWyActCandidateByDefidAndDefkey2(@Param("defid") String defid, @Param("defKey") String defKey, @Param("refId") String refId);

}