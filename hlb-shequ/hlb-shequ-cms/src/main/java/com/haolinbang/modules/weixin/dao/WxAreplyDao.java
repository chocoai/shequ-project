package com.haolinbang.modules.weixin.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxAreply;

/**
 * 自动回复DAO接口
 * 
 * @author nlp
 * @version 2016-02-08
 */
@MyBatisDao
public interface WxAreplyDao extends CrudDao<WxAreply> {

	void setDefaultAreply(List<WxAreply> wxAreplys);

}