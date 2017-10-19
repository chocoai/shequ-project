package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.Menus;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public interface MenusService {

	List<Menus> getMenusList(int type);
}
