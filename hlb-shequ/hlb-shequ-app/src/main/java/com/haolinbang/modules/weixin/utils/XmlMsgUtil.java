package com.haolinbang.modules.weixin.utils;

import java.util.List;

import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;

/**
 * 同步回复消息
 * 
 * @author Administrator
 * 
 */
public class XmlMsgUtil {
	/**
	 * 产生文本消息回复
	 * 
	 * @param wxMessage
	 * @param respStr
	 *            回复的文本内容
	 * @return
	 */
	public static WxMpXmlOutMessage toTxtMsg(WxMpXmlMessage wxMessage, String respStr) {
		return WxMpXmlOutMessage.TEXT().content(respStr).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
	}

	/**
	 * 产生图片消息回复
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            存在于微信服务器的媒体文件id
	 * @return
	 */
	public static WxMpXmlOutMessage toImageMsg(WxMpXmlMessage wxMessage, String mediaId) {
		return WxMpXmlOutMessage.IMAGE().mediaId(mediaId).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
	}

	/**
	 * 回复语音消息
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            语音媒体ID
	 * @return
	 */
	public static WxMpXmlOutMessage toVoiceMsg(WxMpXmlMessage wxMessage, String mediaId) {
		return WxMpXmlOutMessage.VOICE().mediaId(mediaId).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
	}

	/**
	 * 回复视频消息
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            视频id
	 * @return
	 */
	public static WxMpXmlOutMessage toVideoMsg(WxMpXmlMessage wxMessage, String mediaId) {
		return WxMpXmlOutMessage.VIDEO().mediaId(mediaId).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
	}

	/**
	 * 回复视频消息,加上标题
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            视频id
	 * @param title
	 *            标题
	 * @return
	 */
	public static WxMpXmlOutMessage toVideoMsg(WxMpXmlMessage wxMessage, String mediaId, String title) {
		return WxMpXmlOutMessage.VIDEO().mediaId(mediaId).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).title(title).build();
	}

	/**
	 * 回复视频消息,加上标题、描述
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            视频id
	 * @param title
	 *            标题
	 * @param description
	 *            描述
	 * @return
	 */
	public static WxMpXmlOutMessage toVideoMsg(WxMpXmlMessage wxMessage, String mediaId, String title, String description) {
		return WxMpXmlOutMessage.VIDEO().mediaId(mediaId).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).title(title).description(description).build();
	}

	/**
	 * 回复音乐消息
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            音乐文件id
	 * @param title
	 *            音乐标题
	 * @param description
	 *            音乐描述
	 * @param hQMusicUrl
	 *            高质量音乐链接，WIFI环境优先使用该链接播放音乐S
	 * @param musicUrl
	 *            音乐链接
	 * @param thumbMediaId
	 *            缩略图的媒体id
	 * @return
	 */
	public static WxMpXmlOutMessage toMusicMsg(WxMpXmlMessage wxMessage, String mediaId, String title, String description, String hQMusicUrl, String musicUrl, String thumbMediaId) {
		return WxMpXmlOutMessage.MUSIC().fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).title(title).description(description).hqMusicUrl(hQMusicUrl).musicUrl(musicUrl).thumbMediaId(thumbMediaId).build();
	}

	/**
	 * 回复音乐消息
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            音乐文件id
	 * @param musicUrl
	 *            音乐链接
	 * @param title
	 *            音乐标题
	 * @return
	 */
	public static WxMpXmlOutMessage toMusicMsg(WxMpXmlMessage wxMessage, String mediaId, String musicUrl, String title) {
		return WxMpXmlOutMessage.MUSIC().fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).musicUrl(musicUrl).title(title).build();
	}

	/**
	 * 回复音乐消息
	 * 
	 * @param wxMessage
	 * @param mediaId
	 *            音乐文件id
	 * @param musicUrl
	 *            音乐链接
	 * @return
	 */
	public static WxMpXmlOutMessage toMusicMsg(WxMpXmlMessage wxMessage, String mediaId, String musicUrl) {
		return WxMpXmlOutMessage.MUSIC().fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).musicUrl(musicUrl).build();
	}

	/**
	 * 回复图文消息
	 * 
	 * @param wxMessage
	 * @param title
	 *            图文消息标题
	 * @param description
	 *            图文消息描述
	 * @param url
	 *            点击图文消息跳转链接
	 * @param picUrl
	 *            图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 * @return
	 */
	public static WxMpXmlOutMessage toNewsMsg(WxMpXmlMessage wxMessage, String title, String description, String url, String picUrl) {
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setDescription(description);
		item.setPicUrl(picUrl);
		item.setTitle(title);
		item.setUrl(url);
		return WxMpXmlOutMessage.NEWS().fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).addArticle(item).build();
	}

	/**
	 * 多篇图文消息
	 * 
	 * @param wxMessage
	 * @param items
	 *            每一篇图文消息的内容
	 * @return
	 */
	public static WxMpXmlOutMessage toNewsMsg(WxMpXmlMessage wxMessage, List<WxMpXmlOutNewsMessage.Item> items) {
		NewsBuilder newsBuilder = WxMpXmlOutMessage.NEWS().fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName());
		for (WxMpXmlOutNewsMessage.Item item : items) {
			newsBuilder.addArticle(item);
		}
		return newsBuilder.build();
	}
}
