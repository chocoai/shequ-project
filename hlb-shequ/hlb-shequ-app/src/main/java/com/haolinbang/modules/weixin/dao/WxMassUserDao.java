package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMassUser;

/**
 * 群发消息用户表DAO接口
 * @author nlp
 * @version 2017-08-22
 */
@MyBatisDao
public interface WxMassUserDao extends CrudDao<WxMassUser> {
	
}