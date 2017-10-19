package com.haolinbang.modules.act.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.act.entity.Act;

/**
 * 审批DAO接口
 * 
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);

	public String getContent(@Param("tableName") String tableName, @Param("bizId") String bizId);

}
