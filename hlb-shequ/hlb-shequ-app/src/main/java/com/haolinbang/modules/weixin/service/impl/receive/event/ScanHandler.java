package com.haolinbang.modules.weixin.service.impl.receive.event;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.weixin.utils.XmlMsgUtil;

@Scope("prototype")
@Service(ScanHandler.SERVICE_ID)
public class ScanHandler implements WxMpMessageHandler {

	public final static String SERVICE_ID = "scanHandler";

	private Logger log = LoggerFactory.getLogger(ScanHandler.class);

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		log.debug("ScanHandler");
		String str = "ScanHandler";
		return XmlMsgUtil.toTxtMsg(wxMessage, str);
	}

}
