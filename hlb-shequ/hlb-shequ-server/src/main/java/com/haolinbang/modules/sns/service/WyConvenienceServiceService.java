package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.WyConvenienceService;


public interface WyConvenienceServiceService {

	WyConvenienceService getWyConvenience(WyConvenienceService wyConvenienceService);  
	
	WyConvenienceService getbysource(String source);

	WyConvenienceService get(String id);

	List<WyConvenienceService> getByAppid_Wymc_Type(String appid, String wymc,
			String type);
	
}