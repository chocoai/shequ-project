package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyBizHandler;
import com.haolinbang.modules.sns.dao.WyBizHandlerDao;

/**
 * 业务处理器Service
 * 
 * @author nlp
 * @version 2017-05-26
 */
@Service
@Transactional(readOnly = true)
public class WyBizHandlerService extends CrudService<WyBizHandlerDao, WyBizHandler> {

	@Autowired
	private WyBizHandlerDao wyBizHandlerDao;

	public WyBizHandler get(String id) {
		return super.get(id);
	}

	public List<WyBizHandler> findList(WyBizHandler wyBizHandler) {
		return super.findList(wyBizHandler);
	}

	public Page<WyBizHandler> findPage(Page<WyBizHandler> page, WyBizHandler wyBizHandler) {
		return super.findPage(page, wyBizHandler);
	}

	@Transactional(readOnly = false)
	public void save(WyBizHandler wyBizHandler) {
		super.save(wyBizHandler);
	}

	@Transactional(readOnly = false)
	public void delete(WyBizHandler wyBizHandler) {
		super.delete(wyBizHandler);
	}

	public List<String> getWyBizHandlerGroupByType() {
		return wyBizHandlerDao.getWyBizHandlerGroupByType();
	}

	public List<WyBizHandler> getWyRelationCandidateByType(String type) {
		return wyBizHandlerDao.getWyRelationCandidateByType(type);
	}

}