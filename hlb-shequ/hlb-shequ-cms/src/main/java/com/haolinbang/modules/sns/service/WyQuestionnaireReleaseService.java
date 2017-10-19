package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyQuestionnaireRelease;
import com.haolinbang.modules.sns.dao.WyQuestionnaireReleaseDao;

/**
 * 问卷发布表Service
 * @author wxc
 * @version 2017-06-19
 */
@Service
@Transactional(readOnly = true)
public class WyQuestionnaireReleaseService extends CrudService<WyQuestionnaireReleaseDao, WyQuestionnaireRelease> {
	@Autowired
	private WyQuestionnaireReleaseDao wyQuestionnaireReleaseDao;

	public WyQuestionnaireRelease get(String id) {
		return super.get(id);
	}
	
	public WyQuestionnaireRelease get1(String id) {
		return wyQuestionnaireReleaseDao.get1(id);
	}
	
	public List<WyQuestionnaireRelease> findList(WyQuestionnaireRelease wyQuestionnaireRelease) {
		return super.findList(wyQuestionnaireRelease);
	}
	
	public Page<WyQuestionnaireRelease> findPage(Page<WyQuestionnaireRelease> page, WyQuestionnaireRelease wyQuestionnaireRelease) {
		return super.findPage(page, wyQuestionnaireRelease);
	}
	
	@Transactional(readOnly = false)
	public void save(WyQuestionnaireRelease wyQuestionnaireRelease) {
		super.save(wyQuestionnaireRelease);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyQuestionnaireRelease wyQuestionnaireRelease) {
		super.delete(wyQuestionnaireRelease);
	}
	
	@Transactional(readOnly = false)
	public void update(WyQuestionnaireRelease wyQuestionnaireRelease) {
		wyQuestionnaireReleaseDao.update(wyQuestionnaireRelease);
	}
}