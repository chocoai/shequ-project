package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.WyQuestionnaireReleaseDao;
import com.haolinbang.modules.sns.entity.WyQuestionnaireRelease;
import com.haolinbang.modules.sns.service.QuestionaryReleaseService;

@Service
public class QuestionaryReleaseServiceImpl implements QuestionaryReleaseService {
	
	private static Logger log = LoggerFactory.getLogger(QuestionaryReleaseServiceImpl.class);
	
	@Autowired
	private WyQuestionnaireReleaseDao wyQuestionnaireReleaseDao;

	@Override
	public List<WyQuestionnaireRelease> getQuestionnaireReleaseList(String source) {
		
		return wyQuestionnaireReleaseDao.getReleaseList(source);
	}

	@Override
	public WyQuestionnaireRelease get(String releaseId) {

		return wyQuestionnaireReleaseDao.get(releaseId);
	}

	@Override
	public void update(WyQuestionnaireRelease wyQuestionnaireRelease) {

		wyQuestionnaireReleaseDao.update(wyQuestionnaireRelease);
	}

	@Override
	public List<WyQuestionnaireRelease> getBySourceAndGroupId(String source,
			String groupid) {
		
		return wyQuestionnaireReleaseDao.getBySourceAndGroupId(source, groupid);
	}

	
	
}
