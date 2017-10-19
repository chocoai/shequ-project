package com.haolinbang.modules.sns.service.handler;

import org.activiti.engine.delegate.DelegateTask;

/**
 * 单独的业务处理，如设置处理人，发送通知等 
 * 
 * @author Administrator
 * 
 */
public interface BusinessHandler {

	public void handle(DelegateTask delegateTask);

}
