package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyOption;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.dao.WyOptionDao;
import com.haolinbang.modules.sns.dao.WySubjectDao;

/**
 * 选项表Service
 * @author wxc
 * @version 2017-06-12
 */
@Service
@Transactional(readOnly = true)
public class WyOptionService extends CrudService<WyOptionDao, WyOption> {
	
	@Autowired
	public WyOptionDao wyOptionDao;

	public WyOption get(String id) {
		return super.get(id);
	}
	
	public List<WyOption> findList(WyOption wyOption) {
		return super.findList(wyOption);
	}
	
	public Page<WyOption> findPage(Page<WyOption> page, WyOption wyOption) {
		return super.findPage(page, wyOption);
	}
	
	@Transactional(readOnly = false)
	public void save(WyOption wyOption) {
		super.save(wyOption);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyOption wyOption) {
		super.delete(wyOption);
	}
	
	public List<WyOption> getbysubjectid(int subjectid){
		return wyOptionDao.getbysubjectid(subjectid);
	}
	

	public double getWeight(Integer subjectid) {
		return wyOptionDao.getWeight(subjectid);
	}
	

	public double getWeight1(Integer subjectid) {
		return wyOptionDao.getWeight1(subjectid);
	}
	
}