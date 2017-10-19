package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.WyBuildingDao;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.service.WyBuildingService;

@Service
public class WyBuildingServiceImpl implements WyBuildingService {
	
	private static Logger log = LoggerFactory.getLogger(WyBuildingServiceImpl.class);
	
	@Autowired
	private WyBuildingDao wyBuildingDao;

	@Override
	public List<WyBuilding> getBySearchName(String wymc) {
		return wyBuildingDao.getBySearchName(wymc);
	}

	@Override
	public WyBuilding getByBuildingId(String buildingId) {
		// TODO Auto-generated method stub
		return wyBuildingDao.getByBuildingId(buildingId);
	}

	@Override
	public List<WyBuilding> findWyBuilding(WyBuilding wyBuilding) {
		// TODO Auto-generated method stub
		return wyBuildingDao.findWyBuilding(wyBuilding);
	}


}
