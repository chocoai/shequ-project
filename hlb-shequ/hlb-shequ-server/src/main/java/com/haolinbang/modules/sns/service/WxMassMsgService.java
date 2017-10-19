package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.modules.sns.entity.WxMassMsg;
import com.haolinbang.modules.sns.entity.WyBuilding;

/**
 * 群发消息记录表Service
 * @author wxc
 * @versions 2017-8-31
 */
public interface WxMassMsgService{
	public List<WxMassMsg> getWxMassMsgList(String source, String groupId, String wyId, String lyId, String memberId);
	
	public List<WxMassMsg> getWxMassMsgList2(String source, String groupId, String memberId);
}

