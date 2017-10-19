package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActJob;
import com.haolinbang.modules.sns.dao.WyActJobDao;

/**
 * 工作流定时任务时间表Service
 * 
 * @author nlp
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class WyActJobService extends CrudService<WyActJobDao, WyActJob> {

	@Autowired
	private WyActJobDao wyActJobDao;

	public WyActJob get(String id) {
		return super.get(id);
	}

	public List<WyActJob> findList(WyActJob wyActJob) {
		return super.findList(wyActJob);
	}

	public Page<WyActJob> findPage(Page<WyActJob> page, WyActJob wyActJob) {
		return super.findPage(page, wyActJob);
	}

	@Transactional(readOnly = false)
	public void save(WyActJob wyActJob) {
		super.save(wyActJob);
	}

	@Transactional(readOnly = false)
	public void delete(WyActJob wyActJob) {
		super.delete(wyActJob);
	}

	public WyActJob getWyActJobByprocDefIdAndActId(String procDefinitionId, String activitiId) {
		return wyActJobDao.getWyActJobByprocDefIdAndActId(procDefinitionId, activitiId);
	}

	public WyActJob getWyActJobByProcdefidAndTaskidAndType(String procDefId, String taskId, String type) {
		return wyActJobDao.getWyActJobByProcdefidAndTaskidAndType(procDefId,taskId,type);
	}

}