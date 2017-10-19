package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairSettlementMateriel;
import com.haolinbang.modules.sns.dao.WyRepairSettlementMaterielDao;

/**
 * 物业维修核算物料明细Service
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairSettlementMaterielService extends CrudService<WyRepairSettlementMaterielDao, WyRepairSettlementMateriel> {

	public WyRepairSettlementMateriel get(String id) {
		return super.get(id);
	}
	
	public List<WyRepairSettlementMateriel> findList(WyRepairSettlementMateriel wyRepairSettlementMateriel) {
		return super.findList(wyRepairSettlementMateriel);
	}
	
	public Page<WyRepairSettlementMateriel> findPage(Page<WyRepairSettlementMateriel> page, WyRepairSettlementMateriel wyRepairSettlementMateriel) {
		return super.findPage(page, wyRepairSettlementMateriel);
	}
	
	@Transactional(readOnly = false)
	public void save(WyRepairSettlementMateriel wyRepairSettlementMateriel) {
		super.save(wyRepairSettlementMateriel);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyRepairSettlementMateriel wyRepairSettlementMateriel) {
		super.delete(wyRepairSettlementMateriel);
	}
	
}