package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyInstCandidate;

/**
 * 运行期间待办人DAO接口
 * 
 * @author nlp
 * @version 2017-06-05
 */
@MyBatisDao
public interface WyInstCandidateDao extends CrudDao<WyInstCandidate> {

	WyInstCandidate getWyInstCandidateByProcInsIdAndTaskIdAndCandidate(@Param("procInsId") String procInsId, @Param("taskId") String taskId, @Param("candidate") String candidate);

	List<WyInstCandidate> getWyInstCandidateByProcInsIdAndTaskId(@Param("procInstId") String procInstId, @Param("defKey") String defKey);

	boolean deleteWyActCandidateByDefIdAndTaskId(@Param("procInsId") String procInsId, @Param("taskId") String taskId);

	boolean saveList(@Param("list") List<WyInstCandidate> list);

}