package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyOption;

/**
 * 选项表DAO接口
 * @author wxc
 * @version 2017-06-12
 */
@MyBatisDao
public interface WyOptionDao extends CrudDao<WyOption> {
	 public List<WyOption> getbysubjectid(@Param("subjectid") int subjectid);

	public double getWeight(Integer subjectid);

	public double getWeight1(Integer subjectid);
}