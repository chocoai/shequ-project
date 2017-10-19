package com.haolinbang.modules.weixin.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.service.WyConvenienceServiceService;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.dao.WxMassMsgCommonDao;
import com.haolinbang.modules.weixin.entity.WxMassMsgCommon;

/**
 * 通用实例消息Service
 * 
 * @author nlp
 * @version 2017-08-29
 */
@Service
@Transactional(readOnly = true)
public class WxMassMsgCommonService extends CrudService<WxMassMsgCommonDao, WxMassMsgCommon> {

	@Autowired
	private WyConvenienceServiceService wyConvenienceServiceService;

	public WxMassMsgCommon get(String id) {
		return super.get(id);
	}

	public List<WxMassMsgCommon> findList(WxMassMsgCommon wxMassMsgCommon) {
		return super.findList(wxMassMsgCommon);
	}

	public Page<WxMassMsgCommon> findPage(Page<WxMassMsgCommon> page, WxMassMsgCommon wxMassMsgCommon) {
		return super.findPage(page, wxMassMsgCommon);
	}

	@Transactional(readOnly = false)
	public void save(WxMassMsgCommon wxMassMsgCommon) {

		String content = wxMassMsgCommon.getContent();
		if (StringUtils.isNotBlank(content)) {
			// 组装详情消息
			User user = UserUtils.getUser();
			WyConvenienceService wyConvenienceService = wyConvenienceServiceService.get(wxMassMsgCommon.getMsgDetailId());
			if (wyConvenienceService == null) {
				wyConvenienceService = new WyConvenienceService();
				wyConvenienceService.setSource(user.getSource());
				wyConvenienceService.setGroupId(user.getParentGroupId());
				wyConvenienceService.setType(WyConstants.WX_MASS_MSG_DETAIL_NOTICE);
			} else {
				wyConvenienceService.setId(wyConvenienceService.getServiceId().toString());
			}
			wyConvenienceService.setContent(content);
			wyConvenienceServiceService.save(wyConvenienceService);

			String serviceId = wyConvenienceService.getServiceId().toString();
			// 组装前端访问的链接地址,使用相对地址
			String url = "/wyConvenienceService/view/" + serviceId;
			wxMassMsgCommon.setUrl(url);
			wxMassMsgCommon.setMsgDetailId(serviceId);
		}
		super.save(wxMassMsgCommon);
	}

	@Transactional(readOnly = false)
	public void delete(WxMassMsgCommon wxMassMsgCommon) {
		super.delete(wxMassMsgCommon);
	}

}