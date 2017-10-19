package com.haolinbang.modules.sns.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.dao.WyRepairDao;

/**
 * 物业报修Service
 * 
 * @author nlp
 * @version 2017-04-17
 */
@Service
@Transactional(readOnly = true)
public class WyRepairService extends CrudService<WyRepairDao, WyRepair> {

	@Autowired
	private WyRepairDao wyRepairDao;

	public WyRepair get(String id) {
		return super.get(id);
	}

	public List<WyRepair> findList(WyRepair wyRepair) {
		return super.findList(wyRepair);
	}

	public Page<WyRepair> findPage(Page<WyRepair> page, WyRepair wyRepair) {
		return super.findPage(page, wyRepair);
	}

	@Transactional(readOnly = false)
	public void save(WyRepair wyRepair) {
		super.save(wyRepair);
	}

	@Transactional(readOnly = false)
	public void delete(WyRepair wyRepair) {
		super.delete(wyRepair);
	}

	public List<WyRepair> getRepairsByProcInsIds(String memberId, List<String> procInsIdList) {
		return wyRepairDao.getRepairsByProcInsIds(memberId, procInsIdList);
	}

	public List<WyRepair> getWyRepairByMemberId(String memberId) {
		return wyRepairDao.getWyRepairByMemberId(memberId);
	}

	public List<WyRepair> getRepairsByProcInsIds2(List<String> procInsIdList) {
		return wyRepairDao.getRepairsByProcInsIds2(procInsIdList);
	}

	@Transactional(readOnly = false)
	public boolean upateAppointmentTime(String bizId, Date appointmentDate) {
		return wyRepairDao.upateAppointmentTime(bizId, appointmentDate);
	}

	@Transactional(readOnly = false)
	public Integer updateRepairstatus(WyRepair wyRepair) {
		return wyRepairDao.updateRepairstatus(wyRepair);
	}

}