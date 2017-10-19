package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyMenus;
import com.haolinbang.modules.sns.dao.WyMenusDao;

/**
 * 首页菜单Service
 * @author wxc
 * @version 2017-06-02
 */
@Service
@Transactional(readOnly = true)
public class WyMenusService extends CrudService<WyMenusDao, WyMenus> {

	public WyMenus get(String id) {
		return super.get(id);
	}
	
	public List<WyMenus> findList(WyMenus wyMenus) {
		return super.findList(wyMenus);
	}
	
	public Page<WyMenus> findPage(Page<WyMenus> page, WyMenus wyMenus) {
		return super.findPage(page, wyMenus);
	}
	
	@Transactional(readOnly = false)
	public void save(WyMenus wyMenus) {
		super.save(wyMenus);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyMenus wyMenus) {
		super.delete(wyMenus);
	}
	
}