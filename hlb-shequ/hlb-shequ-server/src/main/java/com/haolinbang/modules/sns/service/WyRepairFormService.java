package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepairForm;
import com.haolinbang.modules.sns.dao.WyRepairFormDao;

/**
 * 维修物料明细表单Service
 * @author wxc
 * @version 2017-05-05
 */
@Service
@Transactional(readOnly = true)
public class WyRepairFormService extends CrudService<WyRepairFormDao, WyRepairForm> {

	public WyRepairForm get(String id) {
		return super.get(id);
	}
	
	public List<WyRepairForm> findList(WyRepairForm wyRepairForm) {
		return super.findList(wyRepairForm);
	}
	
	public Page<WyRepairForm> findPage(Page<WyRepairForm> page, WyRepairForm wyRepairForm) {
		return super.findPage(page, wyRepairForm);
	}
	
	@Transactional(readOnly = false)
	public void save(WyRepairForm wyRepairForm) {
		super.save(wyRepairForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyRepairForm wyRepairForm) {
		super.delete(wyRepairForm);
	}
	
}