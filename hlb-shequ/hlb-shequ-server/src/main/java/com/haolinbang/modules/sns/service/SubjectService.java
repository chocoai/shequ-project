package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.WySubject;

public interface SubjectService {

	List<WySubject> getSubjectListByQuestionnaireid(String questionnaireid);
	
}
