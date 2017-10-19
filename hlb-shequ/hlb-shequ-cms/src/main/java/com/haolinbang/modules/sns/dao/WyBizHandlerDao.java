package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyBizHandler;

/**
 * 业务处理器DAO接口
 * 
 * @author nlp
 * @version 2017-05-26
 */
@MyBatisDao
public interface WyBizHandlerDao extends CrudDao<WyBizHandler> {

	List<String> getWyBizHandlerGroupByType();

	List<WyBizHandler> getWyRelationCandidateByType(@Param("type") String type);

}