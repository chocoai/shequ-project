package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActBusiness;
import com.haolinbang.modules.sns.dao.WyActBusinessDao;

/**
 * 工作流程和业务关联配置表Service
 * @author nlp
 * @version 2017-05-05
 */
@Service
@Transactional(readOnly = true)
public class WyActBusinessService extends CrudService<WyActBusinessDao, WyActBusiness> {

	public WyActBusiness get(String id) {
		return super.get(id);
	}
	
	public List<WyActBusiness> findList(WyActBusiness wyActBusiness) {
		return super.findList(wyActBusiness);
	}
	
	public Page<WyActBusiness> findPage(Page<WyActBusiness> page, WyActBusiness wyActBusiness) {
		return super.findPage(page, wyActBusiness);
	}
	
	@Transactional(readOnly = false)
	public void save(WyActBusiness wyActBusiness) {
		super.save(wyActBusiness);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyActBusiness wyActBusiness) {
		super.delete(wyActBusiness);
	}
	
}