package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.dao.WxMemberGroupDao;
import com.haolinbang.modules.weixin.entity.WxMemberGroup;

/**
 * 微信公众号粉丝分组Service
 * @author nlp
 * @version 2016-01-31
 */
@Service
@Transactional(readOnly = true)
public class WxMemberGroupService extends CrudService<WxMemberGroupDao, WxMemberGroup> {

	public WxMemberGroup get(String id) {
		return super.get(id);
	}
	
	public List<WxMemberGroup> findList(WxMemberGroup wxMemberGroup) {
		return super.findList(wxMemberGroup);
	}
	
	public Page<WxMemberGroup> findPage(Page<WxMemberGroup> page, WxMemberGroup wxMemberGroup) {
		return super.findPage(page, wxMemberGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMemberGroup wxMemberGroup) {
		super.save(wxMemberGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMemberGroup wxMemberGroup) {
		super.delete(wxMemberGroup);
	}
	
}