package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyDianzanRecord;
import com.haolinbang.modules.sns.dao.WyDianzanRecordDao;

/**
 * 点赞记录Service
 * 
 * @author nlp
 * @version 2017-07-20
 */
@Service
@Transactional(readOnly = true)
public class WyDianzanRecordService extends CrudService<WyDianzanRecordDao, WyDianzanRecord> {

	@Autowired
	private WyDianzanRecordDao wyDianzanRecordDao;

	public WyDianzanRecord get(String id) {
		return super.get(id);
	}

	public List<WyDianzanRecord> findList(WyDianzanRecord wyDianzanRecord) {
		return super.findList(wyDianzanRecord);
	}

	public Page<WyDianzanRecord> findPage(Page<WyDianzanRecord> page, WyDianzanRecord wyDianzanRecord) {
		return super.findPage(page, wyDianzanRecord);
	}

	@Transactional(readOnly = false)
	public void save(WyDianzanRecord wyDianzanRecord) {
		super.save(wyDianzanRecord);
	}

	@Transactional(readOnly = false)
	public void delete(WyDianzanRecord wyDianzanRecord) {
		super.delete(wyDianzanRecord);
	}

	public boolean dianzan(WyDianzanRecord wyDianzanRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	public WyDianzanRecord getWyDianzanRecordByRelationId(WyDianzanRecord wyDianzanRecord) {
		return wyDianzanRecordDao.getWyDianzanRecordByRelationId(wyDianzanRecord);
	}

}