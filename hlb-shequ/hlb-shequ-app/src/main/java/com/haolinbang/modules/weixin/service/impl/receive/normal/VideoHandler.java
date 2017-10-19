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

import com.haolinbang.common.utils.FileUtils;
import com.haolinbang.modules.weixin.utils.XmlMsgUtil;

@Scope("prototype")
@Service(VideoHandler.SERVICE_ID)
public class VideoHandler implements WxMpMessageHandler {
	public final static String SERVICE_ID = "videoHandler";
	private Logger log = LoggerFactory.getLogger(VideoHandler.class);

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		String media_id = wxMessage.getMediaId();
		FileUtils.saveFileToDir(wxMpService.mediaDownload(media_id));

		log.debug("media_id={}", media_id);
		return XmlMsgUtil.toTxtMsg(wxMessage, "上传视频成功！");
	}

}
