package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.WyQuestionnaireDao;
import com.haolinbang.modules.sns.entity.WyQuestionnaire;
import com.haolinbang.modules.sns.service.QuestionaryService;

@Service
public class QuestionaryServiceImpl implements QuestionaryService {
	
	private static Logger log = LoggerFactory.getLogger(QuestionaryServiceImpl.class);
	
	@Autowired
	private WyQuestionnaireDao wyQuestionnaireDao;

	@Override
	public List<WyQuestionnaire> getQuestionnaireList() {
		WyQuestionnaire wyQuestionnaire = new WyQuestionnaire();
		wyQuestionnaire.setStatus(1);
		
		return wyQuestionnaireDao.findList(wyQuestionnaire);
	}

	@Override
	public WyQuestionnaire getByQuestionnaireid(String questionnaireid) {
		
		return wyQuestionnaireDao.get(questionnaireid);
	}

	
}
