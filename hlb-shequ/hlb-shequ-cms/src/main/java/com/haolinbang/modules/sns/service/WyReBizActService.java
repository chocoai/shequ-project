package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.dao.WyReBizActDao;

/**
 * 工作流和实际的业务对应关系表Service
 * 
 * @author nlp
 * @version 2017-05-08
 */
@Service
@Transactional(readOnly = true)
public class WyReBizActService extends CrudService<WyReBizActDao, WyReBizAct> {

	@Autowired
	private WyReBizActDao wyReBizActDao;

	public WyReBizAct get(String id) {
		return super.get(id);
	}

	public List<WyReBizAct> findList(WyReBizAct wyReBizAct) {
		return super.findList(wyReBizAct);
	}

	public Page<WyReBizAct> findPage(Page<WyReBizAct> page, WyReBizAct wyReBizAct) {
		Page<WyReBizAct> page2 = super.findPage(page, wyReBizAct);

		return page2;
	}

	@Transactional(readOnly = false)
	public void save(WyReBizAct wyReBizAct) {
		super.save(wyReBizAct);
	}

	@Transactional(readOnly = false)
	public void delete(WyReBizAct wyReBizAct) {
		super.delete(wyReBizAct);
	}

	public List<WyReBizAct> getBizActs(WyReBizAct wyReBizAct) {
		return wyReBizActDao.getBizActs(wyReBizAct);
	}

	public WyReBizAct getWyReBizActByActIdAndBizId(String actId, String bizId) {
		return wyReBizActDao.getWyReBizActByActIdAndBizId(actId, bizId);
	}

	public WyReBizAct getWyReBizActByActId(String bizId) {
		return wyReBizActDao.getWyReBizActByActId(bizId);
	}

	public WyReBizAct getBizActByDefId(String procDefinitionId) {
		return wyReBizActDao.getBizActByDefId(procDefinitionId);
	}

}