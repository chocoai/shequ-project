package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.WyOption;

public interface OptionService {

	List<WyOption> getOptionListBySubjectid(String subjectid);
	
}
