package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.dao.WySubjectDao;

/**
 * 题目表Service
 * @author wxc
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class WySubjectService extends CrudService<WySubjectDao, WySubject> {
	
	@Autowired
	public WySubjectDao wySubjectDao;

 	public WySubject get(String id) {
		return super.get(id);
	}
	
	public List<WySubject> findList(WySubject wySubject) {
		return super.findList(wySubject);
	}
	
	public Page<WySubject> findPage(Page<WySubject> page, WySubject wySubject) {
		return super.findPage(page, wySubject);
	}
	
	@Transactional(readOnly = false)
	public void save(WySubject wySubject) {
		super.save(wySubject);
	}
	
	@Transactional(readOnly = false)
	public void delete(WySubject wySubject) {
		super.delete(wySubject);
	}
	
	public List<WySubject> getbyclassificationid(int classificationid){
		return wySubjectDao.getbyclassificationid(classificationid);
	}
	
}