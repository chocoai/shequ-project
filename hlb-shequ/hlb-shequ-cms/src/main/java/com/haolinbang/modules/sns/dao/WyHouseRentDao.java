package com.haolinbang.modules.sns.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyHouseRent;

/**
 * 房屋租售DAO接口
 * 
 * @author nlp
 * @version 2017-07-07
 */
@MyBatisDao
public interface WyHouseRentDao extends CrudDao<WyHouseRent> {

	boolean audit(@Param("id") String id);

}