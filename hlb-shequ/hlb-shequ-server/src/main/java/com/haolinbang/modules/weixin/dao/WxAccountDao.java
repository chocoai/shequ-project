package com.haolinbang.modules.weixin.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxAccount;

/**
 * 微信公共帐号DAO接口
 * @author nlp
 * @version 2017-09-07
 */
@MyBatisDao
public interface WxAccountDao extends CrudDao<WxAccount> {

	List<WxAccount> getWxAccountByAppid(String appid);

	List<WxAccount> getByWxAccount(WxAccount wxAccount);
	
}