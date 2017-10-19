package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.modules.sns.entity.WyBuilding;

/**
 * 楼盘表Service
 * @author wxc
 * @version 2017-07-15
 */
@Service
@Transactional(readOnly = true)
public interface WyBuildingService{
	public List<WyBuilding> getBySearchName(String wymc);

	public WyBuilding getByBuildingId(String buildingId);

	public List<WyBuilding> findWyBuilding(WyBuilding wyBuilding);
	
}

