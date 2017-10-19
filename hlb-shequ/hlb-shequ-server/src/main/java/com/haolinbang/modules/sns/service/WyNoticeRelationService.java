package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyNoticeRelation;
import com.haolinbang.modules.sns.dao.WyNoticeRelationDao;

/**
 * 不同通知处理方法Service
 * 
 * @author nlp
 * @version 2017-07-03
 */
@Service
@Transactional(readOnly = true)
public class WyNoticeRelationService extends CrudService<WyNoticeRelationDao, WyNoticeRelation> {

	@Autowired
	private WyNoticeRelationDao wyNoticeRelationDao;

	public WyNoticeRelation get(String id) {
		return super.get(id);
	}

	public List<WyNoticeRelation> findList(WyNoticeRelation wyNoticeRelation) {
		return super.findList(wyNoticeRelation);
	}

	public Page<WyNoticeRelation> findPage(Page<WyNoticeRelation> page, WyNoticeRelation wyNoticeRelation) {
		return super.findPage(page, wyNoticeRelation);
	}

	@Transactional(readOnly = false)
	public void save(WyNoticeRelation wyNoticeRelation) {
		super.save(wyNoticeRelation);
	}

	@Transactional(readOnly = false)
	public void delete(WyNoticeRelation wyNoticeRelation) {
		super.delete(wyNoticeRelation);
	}

	public List<WyNoticeRelation> getWyNoticeRelationByProcDefIdAndTaskKey(String procDefId, String taskKey) {
		return wyNoticeRelationDao.getWyNoticeRelationByProcDefIdAndTaskKey(procDefId, taskKey);
	}

}