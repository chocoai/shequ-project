package com.haolinbang.modules.sns.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyBizDef;

/**
 * 业务功能定义DAO接口
 * 
 * @author nlp
 * @version 2017-05-05
 */
@MyBatisDao
public interface WyBizDefDao extends CrudDao<WyBizDef> {

	WyBizDef getWyBizDefByCategory(@Param("category") String category);

}