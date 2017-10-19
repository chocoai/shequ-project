package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyReBizAct;

/**
 * 工作流和实际的业务对应关系表DAO接口
 * 
 * @author nlp
 * @version 2017-05-08
 */
@MyBatisDao
public interface WyReBizActDao extends CrudDao<WyReBizAct> {

	List<WyReBizAct> getBizActs();

	WyReBizAct getBizActByBizKey(@Param("source") String source, @Param("groupId") String groupId, @Param("bizKey") String bizKey);

}