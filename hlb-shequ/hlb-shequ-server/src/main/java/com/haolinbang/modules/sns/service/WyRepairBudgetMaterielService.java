package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairBudgetMateriel;
import com.haolinbang.modules.sns.dao.WyRepairBudgetMaterielDao;

/**
 * 物业维修预算物料明细Service
 * 
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairBudgetMaterielService extends CrudService<WyRepairBudgetMaterielDao, WyRepairBudgetMateriel> {
	@Autowired
	private WyRepairBudgetMaterielDao wyRepairBudgetMaterielDao;

	public WyRepairBudgetMateriel get(String id) {
		return super.get(id);
	}

	public List<WyRepairBudgetMateriel> findList(WyRepairBudgetMateriel wyRepairBudgetMateriel) {
		return super.findList(wyRepairBudgetMateriel);
	}

	public Page<WyRepairBudgetMateriel> findPage(Page<WyRepairBudgetMateriel> page, WyRepairBudgetMateriel wyRepairBudgetMateriel) {
		return super.findPage(page, wyRepairBudgetMateriel);
	}

	@Transactional(readOnly = false)
	public void save(WyRepairBudgetMateriel wyRepairBudgetMateriel) {
		super.save(wyRepairBudgetMateriel);
	}

	@Transactional(readOnly = false)
	public void delete(WyRepairBudgetMateriel wyRepairBudgetMateriel) {
		super.delete(wyRepairBudgetMateriel);
	}

	public List<WyRepairBudgetMateriel> getWyRepairBudgetMaterielByRepairId(String repairId) {
		return wyRepairBudgetMaterielDao.getWyRepairBudgetMaterielByRepairId(repairId);
	}

	@Transactional(readOnly = false)
	public boolean deleteById(String id) {
		return wyRepairBudgetMaterielDao.deleteById(id);
	}

}