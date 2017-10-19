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

import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.utils.XmlMsgUtil;

@Scope("prototype")
@Service(LocationHandler.SERVICE_ID)
public class LocationHandler implements WxMpMessageHandler {
	public final static String SERVICE_ID = "locationHandler";
	private Logger log = LoggerFactory.getLogger(LocationHandler.class);

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		WxAccount wxAccount = (WxAccount) context.get("wxAccount");

		log.debug("wxAccount={}", wxAccount);

		// 地理位置维度
		double x = wxMessage.getLocationX();
		// 地理位置经度
		double y = wxMessage.getLocationX();
		// 地图缩放大小
		double scale = wxMessage.getScale();
		// 地理位置信息
		String label = wxMessage.getLabel();
		String str = String.format("x=%s,y=%s,scale=%s,label=%s", x, y, scale, label);

		log.debug(str);
		return XmlMsgUtil.toTxtMsg(wxMessage, str);
	}
}
