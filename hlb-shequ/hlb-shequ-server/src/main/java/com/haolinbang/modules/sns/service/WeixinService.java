package com.haolinbang.modules.sns.service;

import javax.servlet.http.HttpServletRequest;

import com.haolinbang.modules.weixin.entity.WxAccount;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;

public interface WeixinService {

	String oAuth2(String sessionid);

	String authCallback(HttpServletRequest request) throws Exception;

	WxJsapiSignature getWxJsapiSignature(String curUrl) throws WxErrorException;

	String dealPhoto(String media_id) throws WxErrorException;

	String tplMsgSend(WxMpTemplateMessage templateMessage) throws WxErrorException;

	WxAccount getAccount(String appid);
}
