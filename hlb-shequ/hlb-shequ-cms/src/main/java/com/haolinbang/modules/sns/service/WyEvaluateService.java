package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyEvaluate;
import com.haolinbang.modules.sns.dao.WyEvaluateDao;

/**
 * 物业评论表Service
 * @author nlp
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class WyEvaluateService extends CrudService<WyEvaluateDao, WyEvaluate> {

	public WyEvaluate get(String id) {
		return super.get(id);
	}
	
	public List<WyEvaluate> findList(WyEvaluate wyEvaluate) {
		return super.findList(wyEvaluate);
	}
	
	public Page<WyEvaluate> findPage(Page<WyEvaluate> page, WyEvaluate wyEvaluate) {
		return super.findPage(page, wyEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(WyEvaluate wyEvaluate) {
		super.save(wyEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyEvaluate wyEvaluate) {
		super.delete(wyEvaluate);
	}
	
}