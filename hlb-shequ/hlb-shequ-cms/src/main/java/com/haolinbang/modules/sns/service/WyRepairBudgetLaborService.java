package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairBudgetLabor;
import com.haolinbang.modules.sns.dao.WyRepairBudgetLaborDao;

/**
 * 物业维修预算工时明细Service
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairBudgetLaborService extends CrudService<WyRepairBudgetLaborDao, WyRepairBudgetLabor> {

	public WyRepairBudgetLabor get(String id) {
		return super.get(id);
	}
	
	public List<WyRepairBudgetLabor> findList(WyRepairBudgetLabor wyRepairBudgetLabor) {
		return super.findList(wyRepairBudgetLabor);
	}
	
	public Page<WyRepairBudgetLabor> findPage(Page<WyRepairBudgetLabor> page, WyRepairBudgetLabor wyRepairBudgetLabor) {
		return super.findPage(page, wyRepairBudgetLabor);
	}
	
	@Transactional(readOnly = false)
	public void save(WyRepairBudgetLabor wyRepairBudgetLabor) {
		super.save(wyRepairBudgetLabor);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyRepairBudgetLabor wyRepairBudgetLabor) {
		super.delete(wyRepairBudgetLabor);
	}
	
}