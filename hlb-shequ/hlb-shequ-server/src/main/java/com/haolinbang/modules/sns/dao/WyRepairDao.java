package com.haolinbang.modules.sns.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyRepair;

/**
 * 物业报修DAO接口
 * 
 * @author nlp
 * @version 2017-04-17
 */
@MyBatisDao
public interface WyRepairDao extends CrudDao<WyRepair> {

	List<WyRepair> getRepairsByProcInsIds(@Param("memberId") String memberId, @Param("procInsIdList") List<String> procInsIdList);

	List<WyRepair> getWyRepairByMemberId(@Param("memberId") String memberId);

	List<WyRepair> getRepairsByProcInsIds2(@Param("procInsIdList") List<String> procInsIdList);

	boolean upateAppointmentTime(@Param("bizId") String bizId, @Param("appointmentDate") Date appointmentDate);

	Integer updateRepairstatus(WyRepair wyRepair);

}