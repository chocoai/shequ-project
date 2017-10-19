package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.entity.WyRepair;

/**
 * 物业投诉表DAO接口
 * 
 * @author wxc
 * @version 2017-07-04
 */
@MyBatisDao
public interface WyComplainDao extends CrudDao<WyComplain> {

	List<WyComplain> getWyComplainByMemberId(@Param("mid") String mid);

	List<WyComplain> getWyComplainsByProcInsIds(@Param("procInsIdList") List<String> procInsIdList);

	Integer updateComplainstatus(WyComplain wyComplain);

}