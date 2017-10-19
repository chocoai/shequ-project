package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.AccountDao;
import com.haolinbang.modules.sns.service.AccountService;

/**
 * 注册实现逻辑
 * 
 * @author Administrator
 * 
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountDao accountDao;

	@Override
	public List<String> getSources() {
		return accountDao.getSources();
	}

	
}
