package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActForm;
import com.haolinbang.modules.sns.dao.WyActFormDao;

/**
 * 流程表单管理Service
 * 
 * @author nlp
 * @version 2017-06-01
 */
@Service
@Transactional(readOnly = true)
public class WyActFormService extends CrudService<WyActFormDao, WyActForm> {

	@Autowired
	private WyActFormDao wyActFormDao;

	public WyActForm get(String id) {
		return super.get(id);
	}

	public List<WyActForm> findList(WyActForm wyActForm) {
		return super.findList(wyActForm);
	}

	public Page<WyActForm> findPage(Page<WyActForm> page, WyActForm wyActForm) {
		return super.findPage(page, wyActForm);
	}

	@Transactional(readOnly = false)
	public void save(WyActForm wyActForm) {
		super.save(wyActForm);
	}

	@Transactional(readOnly = false)
	public void delete(WyActForm wyActForm) {
		super.delete(wyActForm);
	}

	public List<WyActForm> getWyActFormListByprocDefIdAndTaskid(String procDefinitionId, String activitiId) {
		return wyActFormDao.getWyActFormListByprocDefIdAndTaskid(procDefinitionId, activitiId);
	}

	public WyActForm getWyActFormByProcDefIdAndTaskIdAndFormType(String procDefId, String activitiId, String formType) {
		return wyActFormDao.getWyActFormByProcDefIdAndTaskIdAndFormType(procDefId, activitiId, formType);
	}

	public WyActForm getWyActFormByProcDefIdAndTaskId(String procDefId, String activitiId) {
		return wyActFormDao.getWyActFormByProcDefIdAndTaskId(procDefId, activitiId);
	}

}