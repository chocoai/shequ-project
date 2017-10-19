package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.WyQuestionnaire;

public interface QuestionaryService {

	List<WyQuestionnaire> getQuestionnaireList();

	WyQuestionnaire getByQuestionnaireid(String questionnaireid);
	
}
