package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyApproveDetail;

/**
 * 审批明细表DAO接口
 * 
 * @author nlp
 * @version 2017-06-29
 */
@MyBatisDao
public interface WyApproveDetailDao extends CrudDao<WyApproveDetail> {

	List<WyApprove> getWyApproveByProcDefId(@Param("procDefId") String procDefId);

}