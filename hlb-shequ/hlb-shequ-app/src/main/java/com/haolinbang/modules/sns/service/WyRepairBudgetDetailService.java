package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairBudgetDetail;
import com.haolinbang.modules.sns.dao.WyRepairBudgetDetailDao;

/**
 * 物业维修预算明细Service
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairBudgetDetailService extends CrudService<WyRepairBudgetDetailDao, WyRepairBudgetDetail> {

	public WyRepairBudgetDetail get(String id) {
		return super.get(id);
	}
	
	public List<WyRepairBudgetDetail> findList(WyRepairBudgetDetail wyRepairBudgetDetail) {
		return super.findList(wyRepairBudgetDetail);
	}
	
	public Page<WyRepairBudgetDetail> findPage(Page<WyRepairBudgetDetail> page, WyRepairBudgetDetail wyRepairBudgetDetail) {
		return super.findPage(page, wyRepairBudgetDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(WyRepairBudgetDetail wyRepairBudgetDetail) {
		super.save(wyRepairBudgetDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyRepairBudgetDetail wyRepairBudgetDetail) {
		super.delete(wyRepairBudgetDetail);
	}
	
}