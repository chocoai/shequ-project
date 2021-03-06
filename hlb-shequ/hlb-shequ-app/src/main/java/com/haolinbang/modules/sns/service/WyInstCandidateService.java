package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyInstCandidate;
import com.haolinbang.modules.sns.dao.WyInstCandidateDao;

/**
 * 运行期间待办人Service
 * @author nlp
 * @version 2017-06-05
 */
@Service
@Transactional(readOnly = true)
public class WyInstCandidateService extends CrudService<WyInstCandidateDao, WyInstCandidate> {

	public WyInstCandidate get(String id) {
		return super.get(id);
	}
	
	public List<WyInstCandidate> findList(WyInstCandidate wyInstCandidate) {
		return super.findList(wyInstCandidate);
	}
	
	public Page<WyInstCandidate> findPage(Page<WyInstCandidate> page, WyInstCandidate wyInstCandidate) {
		return super.findPage(page, wyInstCandidate);
	}
	
	@Transactional(readOnly = false)
	public void save(WyInstCandidate wyInstCandidate) {
		super.save(wyInstCandidate);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyInstCandidate wyInstCandidate) {
		super.delete(wyInstCandidate);
	}
	
}