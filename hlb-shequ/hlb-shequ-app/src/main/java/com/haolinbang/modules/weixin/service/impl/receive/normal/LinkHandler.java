package com.haolinbang.modules.weixin.service.impl.receive.normal;

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
@Service(LinkHandler.SERVICE_ID)
public class LinkHandler implements WxMpMessageHandler {
	public final static String SERVICE_ID = "linkHandler";
	private Logger log = LoggerFactory.getLogger(LinkHandler.class);

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		String title = wxMessage.getTitle();
		String description = wxMessage.getDescription();
		String url = wxMessage.getUrl();

		String str = String.format("title=%s,description=%s,url=%s", title, description, url);

		log.debug(str);
		return XmlMsgUtil.toTxtMsg(wxMessage, str);
	}

}
