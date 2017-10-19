package com.haolinbang.modules.act.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.SpringContextHolder;

/**
 * Activiti的全局事件监听器，即所有事件均需要在这里统一分发处理
 * 
 * @author Administrator
 * 
 */
@Service
public class GlobalEventListener implements ActivitiEventListener {

	/**
	 * 日志处理器
	 */
	public final static Logger logger = LoggerFactory.getLogger(GlobalEventListener.class);

	// 更换为以下模式，可以防止Spring容器启动时，ProcessEngine尚未创建，而业务类中又使用了这个引用
	private Map<String, String> handlers = new HashMap<String, String>();

	@Override
	public void onEvent(ActivitiEvent event) {
		String eventType = event.getType().name();

		logger.info("事件类型={}", eventType);

		// 根据事件的类型ID,找到对应的事件处理器
		String eventHandlerBeanId = handlers.get(eventType);
		if (eventHandlerBeanId != null) {
			EventHandler handler = (EventHandler) SpringContextHolder.getBean(eventHandlerBeanId);
			handler.handle(event);
		}
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

	public Map<String, String> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map<String, String> handlers) {
		this.handlers = handlers;
	}

}
