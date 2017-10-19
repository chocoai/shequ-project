package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.dao.WyApproveDao;

/**
 * 工作流审批记录表Service
 * 
 * @author nlp
 * @version 2017-06-29
 */
@Service
@Transactional(readOnly = true)
public class WyApproveService extends CrudService<WyApproveDao, WyApprove> {

	@Autowired
	private WyApproveDao wyApproveDao;

	public WyApprove get(String id) {
		return super.get(id);
	}

	public List<WyApprove> findList(WyApprove wyApprove) {
		return super.findList(wyApprove);
	}

	public Page<WyApprove> findPage(Page<WyApprove> page, WyApprove wyApprove) {
		return super.findPage(page, wyApprove);
	}

	@Transactional(readOnly = false)
	public void save(WyApprove wyApprove) {
		super.save(wyApprove);
	}

	@Transactional(readOnly = false)
	public void delete(WyApprove wyApprove) {
		super.delete(wyApprove);
	}

	public List<WyApprove> getWyApproveByProcDefId(String procDefId) {
		return wyApproveDao.getWyApproveByProcDefId(procDefId);
	}

}