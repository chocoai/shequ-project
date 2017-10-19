package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyReBizActInstance;
import com.haolinbang.modules.sns.dao.WyReBizActInstanceDao;

/**
 * 具体流程引用Service
 * @author nlp
 * @version 2017-09-11
 */
@Service
@Transactional(readOnly = true)
public class WyReBizActInstanceService extends CrudService<WyReBizActInstanceDao, WyReBizActInstance> {

	public WyReBizActInstance get(String id) {
		return super.get(id);
	}
	
	public List<WyReBizActInstance> findList(WyReBizActInstance wyReBizActInstance) {
		return super.findList(wyReBizActInstance);
	}
	
	public Page<WyReBizActInstance> findPage(Page<WyReBizActInstance> page, WyReBizActInstance wyReBizActInstance) {
		return super.findPage(page, wyReBizActInstance);
	}
	
	@Transactional(readOnly = false)
	public void save(WyReBizActInstance wyReBizActInstance) {
		super.save(wyReBizActInstance);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyReBizActInstance wyReBizActInstance) {
		super.delete(wyReBizActInstance);
	}
	
}