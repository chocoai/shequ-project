package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.WxMassMsgDao;
import com.haolinbang.modules.sns.dao.WyBuildingDao;
import com.haolinbang.modules.sns.entity.WxMassMsg;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.service.WxMassMsgService;
import com.haolinbang.modules.sns.service.WyBuildingService;

@Service
public class WxMassMsgServiceImpl implements WxMassMsgService {
	
	private static Logger log = LoggerFactory.getLogger(WxMassMsgServiceImpl.class);
	
	@Autowired
	private WxMassMsgDao wxMassMsgDao;

	@Override
	public List<WxMassMsg> getWxMassMsgList(String source, String groupId, String wyId, String lyId, 
			String memberId) {
		
		return wxMassMsgDao.getWxMassMsgList(source, groupId, wyId, lyId, memberId);
	}
	
	@Override
	public List<WxMassMsg> getWxMassMsgList2(String source, String groupId,
			String memberId) {
		
		return wxMassMsgDao.getWxMassMsgList2(source, groupId, memberId);
	}
}
