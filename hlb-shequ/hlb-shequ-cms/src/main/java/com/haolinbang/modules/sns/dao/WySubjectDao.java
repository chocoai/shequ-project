package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyClassification;
import com.haolinbang.modules.sns.entity.WySubject;

/**
 * 题目表DAO接口
 * @author wxc
 * @version 2017-06-09
 */
@MyBatisDao
public interface WySubjectDao extends CrudDao<WySubject> {
	public List<WySubject> getbyclassificationid(@Param("classificationid") int classificationid);
}