package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.dao.WyActDefDao;

/**
 * 工作流程定义表Service
 * @author nlp
 * @version 2017-05-05
 */
@Service
@Transactional(readOnly = true)
public class WyActDefService extends CrudService<WyActDefDao, WyActDef> {

	public WyActDef get(String id) {
		return super.get(id);
	}
	
	public List<WyActDef> findList(WyActDef wyActDef) {
		return super.findList(wyActDef);
	}
	
	public Page<WyActDef> findPage(Page<WyActDef> page, WyActDef wyActDef) {
		return super.findPage(page, wyActDef);
	}
	
	@Transactional(readOnly = false)
	public void save(WyActDef wyActDef) {
		super.save(wyActDef);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyActDef wyActDef) {
		super.delete(wyActDef);
	}
	
}