package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyHouseRent;
import com.haolinbang.modules.sns.dao.WyHouseRentDao;

/**
 * 房屋租售Service
 * 
 * @author nlp
 * @version 2017-07-07
 */
@Service
@Transactional(readOnly = true)
public class WyHouseRentService extends CrudService<WyHouseRentDao, WyHouseRent> {

	@Autowired
	private WyHouseRentDao wyHouseRentDao;

	public WyHouseRent get(String id) {
		return super.get(id);
	}

	public List<WyHouseRent> findList(WyHouseRent wyHouseRent) {
		return super.findList(wyHouseRent);
	}

	public Page<WyHouseRent> findPage(Page<WyHouseRent> page, WyHouseRent wyHouseRent) {
		return super.findPage(page, wyHouseRent);
	}

	@Transactional(readOnly = false)
	public void save(WyHouseRent wyHouseRent) {
		super.save(wyHouseRent);
	}

	@Transactional(readOnly = false)
	public void delete(WyHouseRent wyHouseRent) {
		super.delete(wyHouseRent);
	}

	/**
	 * 审核房屋租售信息
	 * 
	 * @param wyHouseRent
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean audit(String id) {
		return wyHouseRentDao.audit(id);
	}

}