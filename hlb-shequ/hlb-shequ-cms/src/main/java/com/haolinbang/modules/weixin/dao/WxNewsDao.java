package com.haolinbang.modules.weixin.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxNews;

/**
 * 微信图文DAO接口
 * 
 * @author nlp
 * @version 2016-02-13
 */
@MyBatisDao
public interface WxNewsDao extends CrudDao<WxNews> {

	List<WxNews> queryMutilNews(WxNews wxNews);

}