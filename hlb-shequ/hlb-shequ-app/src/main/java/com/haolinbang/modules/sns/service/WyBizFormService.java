package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyBizForm;
import com.haolinbang.modules.sns.dao.WyBizFormDao;

/**
 * 业务表单定义Service
 * 
 * @author nlp
 * @version 2017-06-02
 */
@Service
@Transactional(readOnly = true)
public class WyBizFormService extends CrudService<WyBizFormDao, WyBizForm> {
	@Autowired
	private WyBizFormDao wyBizFormDao;

	public WyBizForm get(String id) {
		return super.get(id);
	}

	public List<WyBizForm> findList(WyBizForm wyBizForm) {
		return super.findList(wyBizForm);
	}

	public Page<WyBizForm> findPage(Page<WyBizForm> page, WyBizForm wyBizForm) {
		return super.findPage(page, wyBizForm);
	}

	@Transactional(readOnly = false)
	public void save(WyBizForm wyBizForm) {
		super.save(wyBizForm);
	}

	@Transactional(readOnly = false)
	public void delete(WyBizForm wyBizForm) {
		super.delete(wyBizForm);
	}

	public List<WyBizForm> getWyBizFormListByType(String type) {
		return wyBizFormDao.getWyBizFormListByType(type);
	}

}