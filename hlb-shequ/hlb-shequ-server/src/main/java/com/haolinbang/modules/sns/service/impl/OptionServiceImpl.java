package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.dao.WyOptionDao;
import com.haolinbang.modules.sns.entity.WyOption;
import com.haolinbang.modules.sns.service.OptionService;

@Service
public class OptionServiceImpl implements OptionService {
	
	private static Logger log = LoggerFactory.getLogger(OptionServiceImpl.class);
	
	@Autowired
	private WyOptionDao wyOptionDao;

	@Override
	public List<WyOption> getOptionListBySubjectid(String subjectid) {
		WyOption wyOption = new WyOption();
		wyOption.setSubjectid(StringUtils.toInteger(subjectid));
		return wyOptionDao.findList(wyOption);
	}

}
