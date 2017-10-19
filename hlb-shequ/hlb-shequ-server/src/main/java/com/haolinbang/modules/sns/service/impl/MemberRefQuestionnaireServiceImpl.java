package com.haolinbang.modules.sns.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.WyMemberRefQuestionnaireDao;
import com.haolinbang.modules.sns.entity.WyMemberRefQuestionnaire;
import com.haolinbang.modules.sns.service.MemberRefQuestionnaireService;

/**
 * 注册实现逻辑
 * 
 * @author Administrator
 * 
 */
@Service
public class MemberRefQuestionnaireServiceImpl implements MemberRefQuestionnaireService {

	private static Logger log = LoggerFactory.getLogger(MemberRefQuestionnaireServiceImpl.class);
	
	@Autowired
	private WyMemberRefQuestionnaireDao memberRefQuestionnaireDao;

	@Override
	public void save(WyMemberRefQuestionnaire wyMemberRefQuestionnaire) {
		memberRefQuestionnaireDao.insert(wyMemberRefQuestionnaire);
		
	}
	
	

	
}
