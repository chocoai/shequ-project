package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyOrgGroup;

/**
 * 豪龙组织机构DAO接口
 * 
 * @author nlp
 * @version 2017-06-26
 */
@MyBatisDao
public interface WyOrgGroupDao extends CrudDao<WyOrgGroup> {

	WyOrgGroup getWyOrgGroupByStaffId(@Param("staffid") String staffid);

	List<WyOrgGroup> getWyOrgGroupListByPid(@Param("pid") String pid);

}