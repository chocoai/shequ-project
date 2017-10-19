package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyOrgGroup;
import com.haolinbang.modules.sns.dao.WyOrgGroupDao;

/**
 * 豪龙组织机构Service
 * 
 * @author nlp
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class WyOrgGroupService extends CrudService<WyOrgGroupDao, WyOrgGroup> {

	@Autowired
	private WyOrgGroupDao wyOrgGroupDao;

	public WyOrgGroup get(String id) {
		return super.get(id);
	}

	public List<WyOrgGroup> findList(WyOrgGroup wyOrgGroup) {
		return super.findList(wyOrgGroup);
	}

	public Page<WyOrgGroup> findPage(Page<WyOrgGroup> page, WyOrgGroup wyOrgGroup) {
		return super.findPage(page, wyOrgGroup);
	}

	@Transactional(readOnly = false)
	public void save(WyOrgGroup wyOrgGroup) {
		super.save(wyOrgGroup);
	}

	@Transactional(readOnly = false)
	public void delete(WyOrgGroup wyOrgGroup) {
		super.delete(wyOrgGroup);
	}

	public WyOrgGroup getWyOrgGroupByStaffId(String staffid) {
		return wyOrgGroupDao.getWyOrgGroupByStaffId(staffid);
	}

	public List<WyOrgGroup> getWyOrgGroupListByPid(String pid) {
		return wyOrgGroupDao.getWyOrgGroupListByPid(pid);
	}

}