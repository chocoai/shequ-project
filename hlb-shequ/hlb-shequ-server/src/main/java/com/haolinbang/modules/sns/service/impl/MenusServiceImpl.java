package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.MenusDao;
import com.haolinbang.modules.sns.entity.Menus;
import com.haolinbang.modules.sns.service.MenusService;

/**
 * 注册实现逻辑
 * 
 * @author Administrator
 * 
 */
@Service
public class MenusServiceImpl implements MenusService {

	private static Logger log = LoggerFactory.getLogger(MenusServiceImpl.class);

	@Autowired
	private MenusDao menusDao;

	@Override
	public List<Menus> getMenusList(int type) {
		return menusDao.getMenusList(type);
	}


	
}
