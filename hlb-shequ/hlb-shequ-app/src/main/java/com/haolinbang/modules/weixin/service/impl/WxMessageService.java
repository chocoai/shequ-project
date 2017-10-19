package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.dao.WxMessageDao;
import com.haolinbang.modules.weixin.entity.WxMessage;

/**
 * 微信消息Service
 * @author nlp
 * @version 2016-02-06
 */
@Service
@Transactional(readOnly = true)
public class WxMessageService extends CrudService<WxMessageDao, WxMessage> {

	public WxMessage get(String id) {
		return super.get(id);
	}
	
	public List<WxMessage> findList(WxMessage wxMessage) {
		return super.findList(wxMessage);
	}
	
	public Page<WxMessage> findPage(Page<WxMessage> page, WxMessage wxMessage) {
		return super.findPage(page, wxMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMessage wxMessage) {
		super.save(wxMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMessage wxMessage) {
		super.delete(wxMessage);
	}
	
}