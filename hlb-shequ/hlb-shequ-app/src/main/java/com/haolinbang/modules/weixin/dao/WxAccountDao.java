package com.haolinbang.modules.weixin.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxAccount;

/**
 * 微信管理DAO接口
 * 
 * @author nlp
 * @version 2016-01-09
 */
@MyBatisDao
public interface WxAccountDao extends CrudDao<WxAccount> {

	public void setDefaultAccount(List<WxAccount> wxAccounts);

	/**
	 * 通过token查找微信账号
	 * 
	 * @param token
	 * @return
	 */
	public WxAccount findWxAccountByToken(String token);

	public WxAccount getDefaultWxAccount();

}