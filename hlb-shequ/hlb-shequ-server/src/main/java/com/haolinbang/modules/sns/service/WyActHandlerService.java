package com.haolinbang.modules.sns.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActHandler;
import com.haolinbang.modules.sns.dao.WyActHandlerDao;

/**
 * 流程处理后调用方法Service
 * 
 * @author nlp
 * @version 2017-05-26
 */
@Service
@Transactional(readOnly = true)
public class WyActHandlerService extends CrudService<WyActHandlerDao, WyActHandler> implements Serializable {

	private static final long serialVersionUID = -2364736171411270128L;
	@Autowired
	private WyActHandlerDao wyActHandlerDao;

	public WyActHandler get(String id) {
		return super.get(id);
	}

	public List<WyActHandler> findList(WyActHandler wyActHandler) {
		return super.findList(wyActHandler);
	}

	public Page<WyActHandler> findPage(Page<WyActHandler> page, WyActHandler wyActHandler) {
		return super.findPage(page, wyActHandler);
	}

	@Transactional(readOnly = false)
	public void save(WyActHandler wyActHandler) {
		super.save(wyActHandler);
	}

	@Transactional(readOnly = false)
	public void delete(WyActHandler wyActHandler) {
		super.delete(wyActHandler);
	}

	public List<WyActHandler> getWyActHandlerByProcdefidAndTaskkey(String procDefId, String taskDefinitionKey, String eventName) {
		return wyActHandlerDao.getWyActHandlerByProcdefidAndTaskkey(procDefId, taskDefinitionKey, eventName);
	}

	public List<WyActHandler> getWyActHandlerByProcdefidAndTaskkeyByType(String procDefId, String taskDefinitionKey, String eventName, String type) {
		return wyActHandlerDao.getWyActHandlerByProcdefidAndTaskkeyByType(procDefId, taskDefinitionKey, eventName, type);
	}

}