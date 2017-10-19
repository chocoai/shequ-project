package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMessage;

/**
 * 微信消息DAO接口
 * @author nlp
 * @version 2016-02-06
 */
@MyBatisDao
public interface WxMessageDao extends CrudDao<WxMessage> {
	
}