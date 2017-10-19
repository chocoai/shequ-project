package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMassMsgCommon;

/**
 * 通用实例消息DAO接口
 * @author nlp
 * @version 2017-08-29
 */
@MyBatisDao
public interface WxMassMsgCommonDao extends CrudDao<WxMassMsgCommon> {
	
}