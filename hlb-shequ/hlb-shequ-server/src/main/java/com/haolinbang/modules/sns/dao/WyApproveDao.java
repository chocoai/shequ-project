package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyApprove;

/**
 * 工作流审批记录表DAO接口
 * 
 * @author nlp
 * @version 2017-06-29
 */
@MyBatisDao
public interface WyApproveDao extends CrudDao<WyApprove> {

	WyApprove getWyApproveByProcInstId(@Param("procInsId") String procInsId);

	List<WyApprove> getWyApproveListBymid(@Param("memberId") Integer memberId, @Param("type") String type, @Param("keywords") String keywords);

	List<WyApprove> getDoneWyApproveListBymid(@Param("memberId") Integer memberId, @Param("type") String type, @Param("keywords") String keywords);

	List<WyApprove> getDaibanWyApproveByMid(@Param("memberId") Integer memberId);

	List<WyApprove> getDaibanWyApproveByProcInstIds(@Param("procInstIdList") List<String> procInstIdList, @Param("type") String type, @Param("keywords") String keywords);

}