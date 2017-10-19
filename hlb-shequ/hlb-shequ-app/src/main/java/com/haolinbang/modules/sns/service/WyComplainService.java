package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.dao.WyComplainDao;

/**
 * 物业投诉表Service
 * @author wxc
 * @version 2017-07-04
 */
@Service
@Transactional(readOnly = true)
public class WyComplainService extends CrudService<WyComplainDao, WyComplain> {

	public WyComplain get(String id) {
		return super.get(id);
	}
	
	public List<WyComplain> findList(WyComplain wyComplain) {
		return super.findList(wyComplain);
	}
	
	public Page<WyComplain> findPage(Page<WyComplain> page, WyComplain wyComplain) {
		return super.findPage(page, wyComplain);
	}
	
	@Transactional(readOnly = false)
	public void save(WyComplain wyComplain) {
		super.save(wyComplain);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyComplain wyComplain) {
		super.delete(wyComplain);
	}
	
}