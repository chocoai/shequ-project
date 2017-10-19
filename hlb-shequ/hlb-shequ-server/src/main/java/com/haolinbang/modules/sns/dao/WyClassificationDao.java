package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyClassification;

/**
 * 分类表DAO接口
 * @author wxc
 * @version 2017-06-09
 */
@MyBatisDao
public interface WyClassificationDao extends CrudDao<WyClassification> {
	public List<WyClassification> getbyquestionnaireid(@Param("questionnaireid") int questionnaireid);
}