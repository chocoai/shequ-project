package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActRelation;
import com.haolinbang.modules.sns.dao.WyActRelationDao;

/**
 * 流程实例对应的关系表Service
 * @author nlp
 * @version 2017-05-03
 */
@Service
@Transactional(readOnly = true)
public class WyActRelationService extends CrudService<WyActRelationDao, WyActRelation> {

	public WyActRelation get(String id) {
		return super.get(id);
	}
	
	public List<WyActRelation> findList(WyActRelation wyActRelation) {
		return super.findList(wyActRelation);
	}
	
	public Page<WyActRelation> findPage(Page<WyActRelation> page, WyActRelation wyActRelation) {
		return super.findPage(page, wyActRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(WyActRelation wyActRelation) {
		super.save(wyActRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyActRelation wyActRelation) {
		super.delete(wyActRelation);
	}
	
}