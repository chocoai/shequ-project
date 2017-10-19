package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActCandidateDetail;
import com.haolinbang.modules.sns.dao.WyActCandidateDetailDao;

/**
 * 具体人员明细表Service
 * 
 * @author nlp
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true)
public class WyActCandidateDetailService extends CrudService<WyActCandidateDetailDao, WyActCandidateDetail> {

	@Autowired
	private WyActCandidateDetailDao wyActCandidateDetailDao;

	public WyActCandidateDetail get(String id) {
		return super.get(id);
	}

	public List<WyActCandidateDetail> findList(WyActCandidateDetail wyActCandidateDetail) {
		return super.findList(wyActCandidateDetail);
	}

	public Page<WyActCandidateDetail> findPage(Page<WyActCandidateDetail> page, WyActCandidateDetail wyActCandidateDetail) {
		return super.findPage(page, wyActCandidateDetail);
	}

	@Transactional(readOnly = false)
	public void save(WyActCandidateDetail wyActCandidateDetail) {
		super.save(wyActCandidateDetail);
	}

	@Transactional(readOnly = false)
	public void delete(WyActCandidateDetail wyActCandidateDetail) {
		super.delete(wyActCandidateDetail);
	}

	@Transactional(readOnly = false)
	public boolean deleteWyActCandidateDetailByRelationId(String id) {
		return wyActCandidateDetailDao.deleteWyActCandidateDetailByRelationId(id);
	}

	@Transactional(readOnly = false)
	public void saveList(List<WyActCandidateDetail> list) {
		wyActCandidateDetailDao.saveList(list);
	}

	public List<WyActCandidateDetail> getWyActCandidateDetailByRelationId(String relationid) {
		return wyActCandidateDetailDao.getWyActCandidateDetailByRelationId(relationid);
	}

	public List<WyActCandidateDetail> getWyActCandidateDetailByRelationIdAndType(String relationid, String type) {
		return wyActCandidateDetailDao.getWyActCandidateDetailByRelationIdAndType(relationid,type);
	}

}