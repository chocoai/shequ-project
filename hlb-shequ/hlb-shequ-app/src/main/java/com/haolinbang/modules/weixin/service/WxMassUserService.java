package com.haolinbang.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.entity.WxMassUser;
import com.haolinbang.modules.weixin.dao.WxMassUserDao;

/**
 * 群发消息用户表Service
 * @author nlp
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class WxMassUserService extends CrudService<WxMassUserDao, WxMassUser> {

	public WxMassUser get(String id) {
		return super.get(id);
	}
	
	public List<WxMassUser> findList(WxMassUser wxMassUser) {
		return super.findList(wxMassUser);
	}
	
	public Page<WxMassUser> findPage(Page<WxMassUser> page, WxMassUser wxMassUser) {
		return super.findPage(page, wxMassUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMassUser wxMassUser) {
		super.save(wxMassUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMassUser wxMassUser) {
		super.delete(wxMassUser);
	}
	
}