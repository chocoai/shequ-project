package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.WyQuestionnaire;
import com.haolinbang.modules.sns.entity.WyQuestionnaireRelease;

public interface QuestionaryReleaseService {

	List<WyQuestionnaireRelease> getQuestionnaireReleaseList(String source);

	WyQuestionnaireRelease get(String releaseId);

	void update(WyQuestionnaireRelease wyQuestionnaireRelease);

	List<WyQuestionnaireRelease> getBySourceAndGroupId(String source,String groupid);
			
}
