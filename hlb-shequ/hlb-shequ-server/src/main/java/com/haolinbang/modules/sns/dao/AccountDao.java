package com.haolinbang.modules.sns.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.DataSource;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.Account;

@MyBatisDao
@DataSource("ds2")
public interface AccountDao extends CrudDao<Account> {

	/**
	 * 获取微信账号
	 * 
	 * @return
	 */
	public List<Account> getWeixinAccount();
	
	public List<String> getSources();

}
