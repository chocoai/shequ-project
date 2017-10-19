package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyQuestionnaire;
import com.haolinbang.modules.sns.dao.WyQuestionnaireDao;

/**
 * 问卷调查表Service
 * @author wxc
 * @version 2017-06-08
 */
@Service
@Transactional(readOnly = true)
public class WyQuestionnaireService extends CrudService<WyQuestionnaireDao, WyQuestionnaire> {

	public WyQuestionnaire get(String id) {
		return super.get(id);
	}
	
	public List<WyQuestionnaire> findList(WyQuestionnaire wyQuestionnaire) {
		return super.findList(wyQuestionnaire);
	}
	
	public Page<WyQuestionnaire> findPage(Page<WyQuestionnaire> page, WyQuestionnaire wyQuestionnaire) {
		return super.findPage(page, wyQuestionnaire);
	}
	
	@Transactional(readOnly = false)
	public void save(WyQuestionnaire wyQuestionnaire) {
		super.save(wyQuestionnaire);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyQuestionnaire wyQuestionnaire) {
		super.delete(wyQuestionnaire);
	}
	
}