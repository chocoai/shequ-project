package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairSettlementDetail;
import com.haolinbang.modules.sns.dao.WyRepairSettlementDetailDao;

/**
 * 物业维修核算明细Service
 * @author nlp
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class WyRepairSettlementDetailService extends CrudService<WyRepairSettlementDetailDao, WyRepairSettlementDetail> {

	public WyRepairSettlementDetail get(String id) {
		return super.get(id);
	}
	
	public List<WyRepairSettlementDetail> findList(WyRepairSettlementDetail wyRepairSettlementDetail) {
		return super.findList(wyRepairSettlementDetail);
	}
	
	public Page<WyRepairSettlementDetail> findPage(Page<WyRepairSettlementDetail> page, WyRepairSettlementDetail wyRepairSettlementDetail) {
		return super.findPage(page, wyRepairSettlementDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(WyRepairSettlementDetail wyRepairSettlementDetail) {
		super.save(wyRepairSettlementDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyRepairSettlementDetail wyRepairSettlementDetail) {
		super.delete(wyRepairSettlementDetail);
	}
	
}