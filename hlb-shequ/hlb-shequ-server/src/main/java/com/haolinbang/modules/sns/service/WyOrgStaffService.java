package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyOrgGroup;
import com.haolinbang.modules.sns.entity.WyOrgStaff;
import com.haolinbang.modules.sns.dao.WyOrgStaffDao;

/**
 * 豪龙内部员工表Service
 * 
 * @author nlp
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class WyOrgStaffService extends CrudService<WyOrgStaffDao, WyOrgStaff> {
	
	@Autowired
	private WyOrgStaffDao wyOrgStaffDao;

	public WyOrgStaff get(String id) {
		return super.get(id);
	}

	public List<WyOrgStaff> findList(WyOrgStaff wyOrgStaff) {
		return super.findList(wyOrgStaff);
	}

	public Page<WyOrgStaff> findPage(Page<WyOrgStaff> page, WyOrgStaff wyOrgStaff) {
		return super.findPage(page, wyOrgStaff);
	}

	@Transactional(readOnly = false)
	public void save(WyOrgStaff wyOrgStaff) {
		super.save(wyOrgStaff);
	}

	@Transactional(readOnly = false)
	public void delete(WyOrgStaff wyOrgStaff) {
		super.delete(wyOrgStaff);
	}

	public List<WyOrgStaff> getWyOrgStaffByGroupId(Integer groupId) {
		return wyOrgStaffDao.getWyOrgStaffByGroupId(groupId);
	}

	public WyOrgGroup getWyOrgGroupByStaffId(String staffid) {
		return wyOrgStaffDao.getWyOrgGroupByStaffId(staffid);
	}

}