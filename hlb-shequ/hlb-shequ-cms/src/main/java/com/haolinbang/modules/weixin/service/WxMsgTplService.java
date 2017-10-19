package com.haolinbang.modules.weixin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.entity.WxMsgTpl;
import com.haolinbang.modules.weixin.dao.WxMsgTplDao;

/**
 * 消息模板Service
 * 
 * @author nlp
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class WxMsgTplService extends CrudService<WxMsgTplDao, WxMsgTpl> {

	@Autowired
	private WxMsgTplDao wxMsgTplDao;

	public WxMsgTpl get(String id) {
		return super.get(id);
	}

	public List<WxMsgTpl> findList(WxMsgTpl wxMsgTpl) {
		return super.findList(wxMsgTpl);
	}

	public Page<WxMsgTpl> findPage(Page<WxMsgTpl> page, WxMsgTpl wxMsgTpl) {
		return super.findPage(page, wxMsgTpl);
	}

	@Transactional(readOnly = false)
	public void save(WxMsgTpl wxMsgTpl) {
		super.save(wxMsgTpl);
	}

	@Transactional(readOnly = false)
	public void delete(WxMsgTpl wxMsgTpl) {
		super.delete(wxMsgTpl);
	}

	@Transactional(readOnly = false)
	public void saveList(List<WxMsgTpl> list) {
		wxMsgTplDao.saveList(list);
	}

}