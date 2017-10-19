package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * Activiti的事件处理器
 * 
 * @author nlp
 * 
 */
public interface EventHandler {
	/**
	 * 事件处理器
	 * 
	 * @param event
	 */
	public void handle(ActivitiEvent event);
}