package com.haolinbang.modules.weixin.service.impl.receive.event;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxAreply;
import com.haolinbang.modules.weixin.service.impl.WxAreplyService;
import com.haolinbang.modules.weixin.utils.XmlMsgUtil;

/**
 * 订阅事件
 * 
 * @author Administrator
 * 
 */

@Scope("prototype")
@Service(SubscribeHandler.SERVICE_ID)
public class SubscribeHandler implements WxMpMessageHandler {

	public final static String SERVICE_ID = "subscribeHandler";

	private Logger log = LoggerFactory.getLogger(SubscribeHandler.class);

	@Autowired
	private WxAreplyService wxAreplyService;

	private WxAccount wxAccount;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		log.debug("SubscribeHandler");

		// 获取数据库中自动回复信息
		wxAccount = (WxAccount) context.get("wxAccount");

		log.debug("wxAccount={}", wxAccount);
		WxAreply wxAreply = new WxAreply();
		wxAreply.setAccount(wxAccount);

		log.debug("wxAreply={}", wxAreply);

		wxAreply = wxAreplyService.getDefaultAreply(wxAreply);

		String str = (wxAreply != null && StringUtils.isNotBlank(wxAreply.getContent())) ? wxAreply.getContent() : "";
		return XmlMsgUtil.toTxtMsg(wxMessage, str);
	}

}
