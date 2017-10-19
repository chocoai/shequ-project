package com.haolinbang.modules.weixin.utils;

import java.util.List;

import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;

/**
 * 客服消息 --主动发送消息
 * 
 * @author Administrator
 * 
 */
public class CustomMsgUtil {

	/**************  *********************/
	public static WxMpCustomMessage toNewsMsg(WxMpXmlMessage wxMessage, List<WxMpCustomMessage.WxArticle> articles) {
		WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
		return WxMpCustomMessage.TEXT().toUser("OPENID").content("sfsfdsdf").build();
	}

}
