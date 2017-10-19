package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairSettlementLabor;
import com.haolinbang.modules.sns.dao.WyRepairSettlementLaborDao;

/**
 * 物业维修核算工时明细Service
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairSettlementLaborService extends CrudService<WyRepairSettlementLaborDao, WyRepairSettlementLabor> {

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
	
}