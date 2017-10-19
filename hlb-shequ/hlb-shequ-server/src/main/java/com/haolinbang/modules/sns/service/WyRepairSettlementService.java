package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairSettlement;
import com.haolinbang.modules.sns.dao.WyRepairSettlementDao;

/**
 * 物业维修核算汇总Service
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairSettlementService extends CrudService<WyRepairSettlementDao, WyRepairSettlement> {

	public WyRepairSettlement get(String id) {
		return super.get(id);
	}
	
	public List<WyRepairSettlement> findList(WyRepairSettlement wyRepairSettlement) {
		return super.findList(wyRepairSettlement);
	}
	
	public Page<WyRepairSettlement> findPage(Page<WyRepairSettlement> page, WyRepairSettlement wyRepairSettlement) {
		return super.findPage(page, wyRepairSettlement);
	}
	
	@Transactional(readOnly = false)
	public void save(WyRepairSettlement wyRepairSettlement) {
		super.save(wyRepairSettlement);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyRepairSettlement wyRepairSettlement) {
		super.delete(wyRepairSettlement);
	}
	
}