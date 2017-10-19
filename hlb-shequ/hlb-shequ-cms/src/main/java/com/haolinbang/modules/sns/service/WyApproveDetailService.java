package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.dao.WyApproveDetailDao;

/**
 * 审批明细表Service
 * @author nlp
 * @version 2017-06-29
 */
@Service
@Transactional(readOnly = true)
public class WyApproveDetailService extends CrudService<WyApproveDetailDao, WyApproveDetail> {

	public WyApproveDetail get(String id) {
		return super.get(id);
	}
	
	public List<WyApproveDetail> findList(WyApproveDetail wyApproveDetail) {
		return super.findList(wyApproveDetail);
	}
	
	public Page<WyApproveDetail> findPage(Page<WyApproveDetail> page, WyApproveDetail wyApproveDetail) {
		return super.findPage(page, wyApproveDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(WyApproveDetail wyApproveDetail) {
		super.save(wyApproveDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyApproveDetail wyApproveDetail) {
		super.delete(wyApproveDetail);
	}
	
}