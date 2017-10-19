package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairBudget;
import com.haolinbang.modules.sns.dao.WyRepairBudgetDao;

/**
 * 物业维修预算汇总Service
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairBudgetService extends CrudService<WyRepairBudgetDao, WyRepairBudget> {

	public WyRepairBudget get(String id) {
		return super.get(id);
	}
	
	public List<WyRepairBudget> findList(WyRepairBudget wyRepairBudget) {
		return super.findList(wyRepairBudget);
	}
	
	public Page<WyRepairBudget> findPage(Page<WyRepairBudget> page, WyRepairBudget wyRepairBudget) {
		return super.findPage(page, wyRepairBudget);
	}
	
	@Transactional(readOnly = false)
	public void save(WyRepairBudget wyRepairBudget) {
		super.save(wyRepairBudget);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyRepairBudget wyRepairBudget) {
		super.delete(wyRepairBudget);
	}
	
}