package com.haolinbang.modules.weixin.service.impl.receive.interceptor;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageInterceptor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;

@Scope("prototype")
@Service(BeforeInterceptor.SERVICE_ID)
public class BeforeInterceptor implements WxMpMessageInterceptor {
	public final static String SERVICE_ID = "beforeInterceptor";

	@Override
	public boolean intercept(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

		// 拦截器逻辑，对一些字符串进行过滤
		return true;
	}
}
