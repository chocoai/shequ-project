package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.dao.WxAreplyDao;
import com.haolinbang.modules.weixin.entity.WxAreply;
import com.haolinbang.modules.weixin.utils.AccountUtil;

/**
 * 自动回复Service
 * 
 * @author nlp
 * @version 2016-02-08
 */
@Service
@Transactional(readOnly = true)
public class WxAreplyService extends CrudService<WxAreplyDao, WxAreply> {

	public WxAreply get(String id) {
		return super.get(id);
	}

	public List<WxAreply> findList(WxAreply wxAreply) {
		return super.findList(wxAreply);
	}

	public Page<WxAreply> findPage(Page<WxAreply> page, WxAreply wxAreply) {
		return super.findPage(page, wxAreply);
	}

	@Transactional(readOnly = false)
	public void save(WxAreply wxAreply) {
		super.save(wxAreply);
	}

	@Transactional(readOnly = false)
	public void delete(WxAreply wxAreply) {
		super.delete(wxAreply);
	}

	@Transactional(readOnly = false)
	public void setDefaultAreply(String id) {
		WxAreply reply = new WxAreply();
		reply.setAccount(AccountUtil.getAccount());
		List<WxAreply> wxAreplys = findList(reply);
		for (WxAreply wxAreply : wxAreplys) {
			if (id.equals(wxAreply.getId())) {
				wxAreply.setIsDefault(true);
			} else {
				wxAreply.setIsDefault(false);
			}
		}
		dao.setDefaultAreply(wxAreplys);
	}

	/**
	 * 获取默认自动回复
	 * 
	 * @param wxAreply
	 * @return
	 */
	public WxAreply getDefaultAreply(WxAreply wxAreply) {
		List<WxAreply> wxAreplys = findList(wxAreply);
		for (WxAreply reply : wxAreplys) {
			if (reply.getIsDefault()) {
				return reply;
			}
		}
		return null;
	}
}