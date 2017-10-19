package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.dao.WyRepairSettlementLaborDao;
import com.haolinbang.modules.sns.entity.WyRepairBudgetLabor;
import com.haolinbang.modules.sns.entity.WyRepairSettlementLabor;

/**
 * 物业维修核算工时明细Service
 * 
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairSettlementLaborService extends CrudService<WyRepairSettlementLaborDao, WyRepairSettlementLabor> {

	@Autowired
	private WyRepairSettlementLaborDao wyRepairSettlementLaborDao;

	public WyRepairSettlementLabor get(String id) {
		return super.get(id);
	}

	public List<WyRepairSettlementLabor> findList(WyRepairSettlementLabor wyRepairSettlementLabor) {
		return super.findList(wyRepairSettlementLabor);
	}

	public Page<WyRepairSettlementLabor> findPage(Page<WyRepairSettlementLabor> page, WyRepairSettlementLabor wyRepairSettlementLabor) {
		return super.findPage(page, wyRepairSettlementLabor);
	}

	@Transactional(readOnly = false)
	public void save(WyRepairSettlementLabor wyRepairSettlementLabor) {
		super.save(wyRepairSettlementLabor);
	}

	@Transactional(readOnly = false)
	public void delete(WyRepairSettlementLabor wyRepairSettlementLabor) {
		super.delete(wyRepairSettlementLabor);
	}

	public List<WyRepairBudgetLabor> getWyRepairBudgetLaborByRepairId(String repairId) {
		return wyRepairSettlementLaborDao.getWyRepairBudgetLaborByRepairId(repairId);
	}

	@Transactional(readOnly = false)
	public int saveList(List<WyRepairSettlementLabor> wyRepairSettlementLaborList) {
		return wyRepairSettlementLaborDao.saveList(wyRepairSettlementLaborList);
	}

	@Transactional(readOnly = false)
	public boolean deleteById(String id) {
		return wyRepairSettlementLaborDao.deleteById(id);
	}

	public List<WyRepairSettlementLabor> getWyRepairSettlementLaborsByRepairId(String bizId) {
		return wyRepairSettlementLaborDao.getWyRepairSettlementLaborsByRepairId(bizId);
	}

}