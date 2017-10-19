package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 任务被分配给了一个人员。事件包含任务。
 * 
 * @author Administrator
 * 
 */
@Service
public class TaskAssignedListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(TaskAssignedListener.class);

	@Override
	public void handle(ActivitiEvent event) {
		logger.info("---=-TaskAssignedListener------");
	}

}
