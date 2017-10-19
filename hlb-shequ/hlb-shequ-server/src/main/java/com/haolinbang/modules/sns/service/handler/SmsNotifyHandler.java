package com.haolinbang.modules.sns.service.handler;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 短信通知处理类
 * 
 * @author Administrator
 * 
 */
@Service("smsNotifyHandler")
public class SmsNotifyHandler implements BusinessHandler {

	private static Logger logger = LoggerFactory.getLogger(SmsNotifyHandler.class);

	@Override
	public void handle(DelegateTask delegateTask) {
		logger.info("----smsNotifyHandler----#####----短信通知处理类----");
	}

}
