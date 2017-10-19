package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyBizForm;

/**
 * 业务表单定义DAO接口
 * 
 * @author nlp
 * @version 2017-06-02
 */
@MyBatisDao
public interface WyBizFormDao extends CrudDao<WyBizForm> {

	List<WyBizForm> getWyBizFormListByType(@Param("type") String type);

}