package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyMemberRefQuestionnaire;
import com.haolinbang.modules.sns.dao.WyMemberRefQuestionnaireDao;
import com.haolinbang.modules.sns.dao.WyOptionDao;

/**
 * 会员调查表Service
 * @author wxc
 * @version 2017-06-19
 */
@Service
@Transactional(readOnly = true)
public class WyMemberRefQuestionnaireService extends CrudService<WyMemberRefQuestionnaireDao, WyMemberRefQuestionnaire> {

	@Autowired
	public WyMemberRefQuestionnaireDao wyMemberRefQuestionnaireDao;
	
	public WyMemberRefQuestionnaire get(String id) {
		return super.get(id);
	}
	
	public List<WyMemberRefQuestionnaire> findList(WyMemberRefQuestionnaire wyMemberRefQuestionnaire) {
		return super.findList(wyMemberRefQuestionnaire);
	}
	
	public Page<WyMemberRefQuestionnaire> findPage(Page<WyMemberRefQuestionnaire> page, WyMemberRefQuestionnaire wyMemberRefQuestionnaire) {
		return super.findPage(page, wyMemberRefQuestionnaire);
	}
	
	@Transactional(readOnly = false)
	public void save(WyMemberRefQuestionnaire wyMemberRefQuestionnaire) {
		super.save(wyMemberRefQuestionnaire);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyMemberRefQuestionnaire wyMemberRefQuestionnaire) {
		super.delete(wyMemberRefQuestionnaire);
	}
	

	public Integer getNum(Integer subjectid) {
		return wyMemberRefQuestionnaireDao.getNum(subjectid);
	}
	
}