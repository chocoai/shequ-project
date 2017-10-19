package com.haolinbang.modules.weixin.service.impl.receive.normal;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

/**
 * 事件为群发消息发送结束
 * 
 * @author Administrator
 * 
 */
@Scope("prototype")
@Service(MassSendJobFinishHandler.SERVICE_ID)
public class MassSendJobFinishHandler implements WxMpMessageHandler {

	public final static String SERVICE_ID = "massSendJobFinishHandler";

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		long msgId = wxMessage.getMsgId();

		// 更新数据库，更新数据库发送状态
		
		

		return null;
	}

}
