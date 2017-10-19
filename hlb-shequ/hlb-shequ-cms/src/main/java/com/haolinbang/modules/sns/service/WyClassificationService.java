package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyClassification;
import com.haolinbang.modules.sns.dao.WyClassificationDao;

/**
 * 分类表Service
 * @author wxc
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class WyClassificationService extends CrudService<WyClassificationDao, WyClassification> {
	
	@Autowired
	public WyClassificationDao wyClassificationDao;

	public WyClassification get(String id) {
		return super.get(id);
	}
	
	public List<WyClassification> findList(WyClassification wyClassification) {
		return super.findList(wyClassification);
	}
	
	public Page<WyClassification> findPage(Page<WyClassification> page, WyClassification wyClassification) {
		return super.findPage(page, wyClassification);
	}
	
	@Transactional(readOnly = false)
	public void save(WyClassification wyClassification) {
		super.save(wyClassification);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyClassification wyClassification) {
		super.delete(wyClassification);
	}
	
	public List<WyClassification> getbyquestionnaireid(int questionnaireid) {
		return wyClassificationDao.getbyquestionnaireid(questionnaireid);
	}
	

	public double getWeight(Integer questionnaireid) {
		return wyClassificationDao.getWeight(questionnaireid);
	}
	
}