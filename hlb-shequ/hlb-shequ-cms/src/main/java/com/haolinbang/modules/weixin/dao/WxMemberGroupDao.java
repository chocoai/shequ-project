package com.haolinbang.modules.weixin.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMemberGroup;

/**
 * 微信公众号粉丝分组DAO接口
 * @author nlp
 * @version 2016-01-31
 */
@MyBatisDao
public interface WxMemberGroupDao extends CrudDao<WxMemberGroup> {
	
}