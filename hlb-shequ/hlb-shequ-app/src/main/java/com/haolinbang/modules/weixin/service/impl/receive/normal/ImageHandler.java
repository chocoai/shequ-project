package com.haolinbang.modules.weixin.service.impl.receive.normal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.FileUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;
import com.haolinbang.modules.weixin.utils.XmlMsgUtil;

@Scope("prototype")
@Service(ImageHandler.SERVICE_ID)
public class ImageHandler implements WxMpMessageHandler {

	public final static String SERVICE_ID = "imageHandler";

	private Logger log = LoggerFactory.getLogger(ImageHandler.class);

	@Autowired
	private WxAccountService wxAccountService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

		WxAccount wxAccount = (WxAccount) context.get("wxAccount");

		log.debug("wxAccount={}", wxAccount);

		// 获取图片的外网url
		String picUrl = wxMessage.getPicUrl();
		// 获取媒体文件ID
		String media_id = wxMessage.getMediaId();
		// 下载文件
		FileUtils.saveFileToDir(wxMpService.mediaDownload(media_id));

		log.debug("picUrl={}", picUrl);

		List<WxMpXmlOutNewsMessage.Item> items = new ArrayList<WxMpXmlOutNewsMessage.Item>();
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setTitle("图文测试图文测试图文测试图文测试图文测试");
		item.setDescription("图片消息功能强大啊图片消息功能强大啊图片消息功能强大啊图片消息功能强大啊");
		item.setPicUrl(picUrl);
		item.setUrl("http://wxc.oklong.com/hlb-shequ-server/index");
		items.add(item);
		items.add(item);
		items.add(item);
		items.add(item);
		items.add(item);

		return XmlMsgUtil.toNewsMsg(wxMessage, items);
	}

}
