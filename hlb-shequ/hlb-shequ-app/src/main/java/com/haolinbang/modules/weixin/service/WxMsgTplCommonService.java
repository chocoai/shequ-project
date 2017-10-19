package com.haolinbang.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.entity.WxMsgTplCommon;
import com.haolinbang.modules.weixin.dao.WxMsgTplCommonDao;

/**
 * 模板消息通用模板定义Service
 * @author nlp
 * @version 2017-08-28
 */
@Service
@Transactional(readOnly = true)
public class WxMsgTplCommonService extends CrudService<WxMsgTplCommonDao, WxMsgTplCommon> {

	public WxMsgTplCommon get(String id) {
		return super.get(id);
	}
	
	public List<WxMsgTplCommon> findList(WxMsgTplCommon wxMsgTplCommon) {
		return super.findList(wxMsgTplCommon);
	}
	
	public Page<WxMsgTplCommon> findPage(Page<WxMsgTplCommon> page, WxMsgTplCommon wxMsgTplCommon) {
		return super.findPage(page, wxMsgTplCommon);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMsgTplCommon wxMsgTplCommon) {
		super.save(wxMsgTplCommon);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMsgTplCommon wxMsgTplCommon) {
		super.delete(wxMsgTplCommon);
	}
	
}