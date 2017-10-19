package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.dao.WyConvenienceServiceDao;

/**
 * 便民服务Service
 * @author wxc
 * @version 2017-07-14
 */
@Service
@Transactional(readOnly = true)
public class WyConvenienceServiceService extends CrudService<WyConvenienceServiceDao, WyConvenienceService> {

	public WyConvenienceService get(String id) {
		return super.get(id);
	}
	
	public List<WyConvenienceService> findList(WyConvenienceService wyConvenienceService) {
		return super.findList(wyConvenienceService);
	}
	
	public Page<WyConvenienceService> findPage(Page<WyConvenienceService> page, WyConvenienceService wyConvenienceService) {
		return super.findPage(page, wyConvenienceService);
	}
	
	@Transactional(readOnly = false)
	public void save(WyConvenienceService wyConvenienceService) {
		super.save(wyConvenienceService);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyConvenienceService wyConvenienceService) {
		super.delete(wyConvenienceService);
	}
	
}