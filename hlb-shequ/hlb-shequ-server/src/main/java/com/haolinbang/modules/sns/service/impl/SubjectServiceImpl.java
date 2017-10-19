package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.dao.WySubjectDao;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	private static Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);
	
	@Autowired
	private WySubjectDao wySubjectDao;

	@Override
	public List<WySubject> getSubjectListByQuestionnaireid(
			String questionnaireid) {
		WySubject wySubject = new WySubject();
		wySubject.setQuestionnaireid(StringUtils.toInteger(questionnaireid));
		wySubject.setStatus(1);
		return wySubjectDao.findAllList(wySubject);
	}
	
}
