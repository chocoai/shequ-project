package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyActCandidateDetail;

/**
 * 具体人员明细表DAO接口
 * 
 * @author nlp
 * @version 2017-06-20
 */
@MyBatisDao
public interface WyActCandidateDetailDao extends CrudDao<WyActCandidateDetail> {

	List<WyActCandidateDetail> getWyActCandidateDetailByRelationIdAndType(@Param("relationId") String relationId, @Param("type") String type);

	boolean deleteWyActCandidateDetailByRelationId(@Param("relationId") String relationId);

	void saveList(@Param("list") List<WyActCandidateDetail> list);

}