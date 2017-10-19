package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyOrgStaff;

/**
 * 豪龙内部员工表DAO接口
 * 
 * @author nlp
 * @version 2017-06-26
 */
@MyBatisDao
public interface WyOrgStaffDao extends CrudDao<WyOrgStaff> {

	List<WyOrgStaff> getWyOrgStaffByGroupId(@Param("groupId") Integer groupId);

}