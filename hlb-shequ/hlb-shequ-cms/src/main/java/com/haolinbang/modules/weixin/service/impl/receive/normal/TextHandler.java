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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxNews;
import com.haolinbang.modules.weixin.service.impl.WxNewsMultiService;
import com.haolinbang.modules.weixin.service.impl.WxNewsService;
import com.haolinbang.modules.weixin.service.impl.WxTextService;
import com.haolinbang.modules.weixin.utils.XmlMsgUtil;

@Scope("prototype")
@Service(TextHandler.SERVICE_ID)
public class TextHandler implements WxMpMessageHandler {
	public final static String SERVICE_ID = "textHandler";
	private Logger log = LoggerFactory.getLogger(TextHandler.class);

	@Autowired
	private WxTextService wxTextService;

	@Autowired
	private WxNewsService wxNewsService;

	@Autowired
	private WxNewsMultiService wxNewsMultiService;

	@Autowired
	private ImageHandler imageHandler;

	private WxAccount wxAccount;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		wxAccount = (WxAccount) context.get("wxAccount");

		log.debug("wxAccount={}", wxAccount);
		String keyword = wxMessage.getContent();
		if (keyword.contains("图片")) {
			return imageHandler.handle(wxMessage, context, wxMpService, sessionManager);
		}

		return dealMsg(wxMessage, context, wxMpService, sessionManager);
	}

	/**
	 * 处理业务逻辑
	 * 
	 * @param wxMessage
	 * @param context
	 * @param wxMpService
	 * @param sessionManager
	 * @return
	 */
	private WxMpXmlOutMessage dealMsg(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
		// 获取发送的消息
		String keyword = wxMessage.getContent();
		// 通过模糊查询，获取要回复的匹配关键字
		// 一般传过来的参数是文本内容
		// 1.先查询文本回复内容
		String content = wxTextService.query(keyword, wxAccount);
		if (StringUtils.isNotBlank(content)) {
			return XmlMsgUtil.toTxtMsg(wxMessage, content);
		}
		// 2.在查询图文内容回复
		WxNews news = wxNewsService.query(keyword, wxAccount);
		if (news != null) {
			return XmlMsgUtil.toNewsMsg(wxMessage, news.getTitle(), news.getDescription(), news.getUrl(), news.getPicurl());
		}

		// 3.查询多图文回复内容
		List<WxNews> newsList = wxNewsMultiService.query(keyword, wxAccount);
		if (newsList != null && newsList.size() > 0) {
			List<WxMpXmlOutNewsMessage.Item> items = new ArrayList<WxMpXmlOutNewsMessage.Item>();
			for (WxNews wxNews : newsList) {
				WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
				item.setDescription(wxNews.getDescription());
				item.setPicUrl(wxNews.getPicurl());
				item.setTitle(wxNews.getTitle());
				item.setUrl(wxNews.getUrl());
				items.add(item);
			}
			return XmlMsgUtil.toNewsMsg(wxMessage, items);
		}
		return XmlMsgUtil.toTxtMsg(wxMessage, "没有查找到相关信息");
	}
}
