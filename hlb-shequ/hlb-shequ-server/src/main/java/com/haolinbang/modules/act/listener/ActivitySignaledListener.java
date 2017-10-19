package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 一个节点收到了一个信号触发
 * @author Administrator
 * 
 */
@Service
public class ActivitySignaledListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(ActivitySignaledListener.class);

	@Override
	public void handle(ActivitiEvent event) {

		logger.info("----ActivitySignaledListener-----");
	}

}
