package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMsgTplCommon;

/**
 * 模板消息通用模板定义DAO接口
 * @author nlp
 * @version 2017-08-28
 */
@MyBatisDao
public interface WxMsgTplCommonDao extends CrudDao<WxMsgTplCommon> {
	
}