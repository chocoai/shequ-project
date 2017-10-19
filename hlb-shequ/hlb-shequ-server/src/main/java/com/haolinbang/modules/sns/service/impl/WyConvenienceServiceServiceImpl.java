package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.WyConvenienceServiceDao;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.service.WyConvenienceServiceService;

@Service
public class WyConvenienceServiceServiceImpl implements WyConvenienceServiceService {

	private static Logger log = LoggerFactory.getLogger(WyConvenienceServiceServiceImpl.class);

	@Autowired
	private WyConvenienceServiceDao wyConvenienceServiceDao;

	@Override
	public WyConvenienceService getWyConvenience(WyConvenienceService wyConvenienceService) {

		return wyConvenienceServiceDao.get(wyConvenienceService);
	}

	@Override
	public WyConvenienceService getbysource(String source) {

		return wyConvenienceServiceDao.getbysource(source);
	}

	@Override
	public WyConvenienceService get(String id) {
		return wyConvenienceServiceDao.get(id);
	}

	@Override
	public List<WyConvenienceService> getByAppid_Wymc_Type(String appid, String wymc,
			String type) {

		return wyConvenienceServiceDao.getByAppid_Wymc_Type(appid, wymc, type);
	}

}
