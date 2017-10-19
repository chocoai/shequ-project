package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.dao.WyBizDefDao;

/**
 * 业务功能定义Service
 * 
 * @author nlp
 * @version 2017-05-05
 */
@Service
@Transactional(readOnly = true)
public class WyBizDefService extends CrudService<WyBizDefDao, WyBizDef> {

	@Autowired
	private WyBizDefDao wyBizDefDao;

	public WyBizDef get(String id) {
		return super.get(id);
	}

	public List<WyBizDef> findList(WyBizDef wyBizDef) {
		return super.findList(wyBizDef);
	}

	public Page<WyBizDef> findPage(Page<WyBizDef> page, WyBizDef wyBizDef) {
		return super.findPage(page, wyBizDef);
	}

	@Transactional(readOnly = false)
	public void save(WyBizDef wyBizDef) {
		super.save(wyBizDef);
	}

	@Transactional(readOnly = false)
	public void delete(WyBizDef wyBizDef) {
		super.delete(wyBizDef);
	}

	public List<WyBizDef> getAllWyBizDefByGroupid(String groupID) {
		return wyBizDefDao.getAllWyBizDefByGroupid(groupID);
	}
}