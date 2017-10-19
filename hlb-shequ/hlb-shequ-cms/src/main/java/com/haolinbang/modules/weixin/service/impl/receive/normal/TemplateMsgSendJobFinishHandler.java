package com.haolinbang.modules.weixin.service.impl.receive.normal;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.weixin.entity.WxMassMsg;
import com.haolinbang.modules.weixin.service.WxMassMsgService;
import com.haolinbang.modules.weixin.utils.XmlMsgUtil;

/**
 * 模板消息推送结束事件,处理推送后更新推送状态表
 * 
 * @author Administrator
 * 
 */
@Scope("prototype")
@Service(TemplateMsgSendJobFinishHandler.SERVICE_ID)
public class TemplateMsgSendJobFinishHandler implements WxMpMessageHandler {

	public final static String SERVICE_ID = "templateMsgSendJobFinishHandler";

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

		WxMassMsgService wxMassMsgService = SpringContextHolder.getBean(WxMassMsgService.class);
		// 更新消息群发状态
		WxMassMsg wxMassMsg = wxMassMsgService.getWxMassMsgByMsgid(wxMessage.getMsgId());
		wxMassMsg.setSendStatus(true);
		wxMassMsgService.save(wxMassMsg);

		return XmlMsgUtil.toTxtMsg(wxMessage, "收到群发推送消息");
	}

}
