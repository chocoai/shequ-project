package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyConvenienceService;

/**
 * 问卷调查表DAO接口
 * @author wxc
 * @version 2017-06-08
 */
@MyBatisDao
public interface WyConvenienceServiceDao extends CrudDao<WyConvenienceService> {

	WyConvenienceService getbysource(String source);

	WyConvenienceService getbySourceAndGroupId(@Param("source") String source, @Param("groupId") Integer groupId);

	WyConvenienceService getbySourceAndGroupIdAndType(@Param("source") String source,
			@Param("groupId") Integer groupId, @Param("type") String type);
	
	WyConvenienceService getbySourceAndType(@Param("source") String source, @Param("type") String type);

	WyConvenienceService getCompanyProfile(@Param("source") String source, @Param("groupId") Integer groupId,
			@Param("wxAccountId") String wxAccountId);

	List<WyConvenienceService> getByAppid_Wymc_Type(@Param("appid") String appid, @Param("wymc") String wymc,
			@Param("type") String type);
	
}