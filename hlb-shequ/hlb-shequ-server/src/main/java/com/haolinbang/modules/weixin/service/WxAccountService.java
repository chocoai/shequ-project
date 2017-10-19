package com.haolinbang.modules.weixin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.dao.WxAccountDao;

/**
 * 微信公共帐号Service
 * 
 * @author nlp
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class WxAccountService extends CrudService<WxAccountDao, WxAccount> {

	@Autowired
	private WxAccountDao wxAccountDao;

	public WxAccount get(String id) {
		return super.get(id);
	}

	public List<WxAccount> findList(WxAccount wxAccount) {
		return super.findList(wxAccount);
	}

	public Page<WxAccount> findPage(Page<WxAccount> page, WxAccount wxAccount) {
		return super.findPage(page, wxAccount);
	}

	@Transactional(readOnly = false)
	public void save(WxAccount wxAccount) {
		super.save(wxAccount);
	}

	@Transactional(readOnly = false)
	public void delete(WxAccount wxAccount) {
		super.delete(wxAccount);
	}

	public List<WxAccount> getWxAccountByAppid(String appid) {
		return wxAccountDao.getWxAccountByAppid(appid);
	}

	public List<WxAccount> getByWxAccount(WxAccount wxAccount) {
		
		return wxAccountDao.getByWxAccount(wxAccount);
	}

}