package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.dao.WyBuildingDao;

/**
 * 楼盘表Service
 * @author wxc
 * @version 2017-07-15
 */
@Service
@Transactional(readOnly = true)
public class WyBuildingService extends CrudService<WyBuildingDao, WyBuilding> {
	@Autowired
	private WyBuildingDao wyBuildingDao;

	public WyBuilding get(String id) {
		return super.get(id);
	}
	
	public List<WyBuilding> findList(WyBuilding wyBuilding) {
		return super.findList(wyBuilding);
	}
	
	public Page<WyBuilding> findPage(Page<WyBuilding> page, WyBuilding wyBuilding) {
		return super.findPage(page, wyBuilding);
	}
	
	@Transactional(readOnly = false)
	public void save(WyBuilding wyBuilding) {
		super.save(wyBuilding);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyBuilding wyBuilding) {
		super.delete(wyBuilding);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll() {
		wyBuildingDao.deleteAll();
	}

	public WyBuilding getBySourceAndWyid(WyBuilding wyBuilding) {
		return wyBuildingDao.getBySourceAndWyid(wyBuilding);
	}

	@Transactional(readOnly = false)
	public void update(WyBuilding wyBuilding) {
		wyBuildingDao.update(wyBuilding);
		
	}
	
}