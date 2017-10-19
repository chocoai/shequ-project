package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 任务被完成了。它会在ENTITY_DELETE事件之前触发。当任务是流程一部分时，事件会在流程继续运行之前，
 * 后续事件将是ACTIVITY_COMPLETE，对应着完成任务的节点。
 * 
 * @author Administrator
 * 
 */
@Service
public class TaskCompleteListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(TaskCompleteListener.class);

	@Override
	public void handle(ActivitiEvent event) {

		logger.info("-----TaskCompleteListener");

	}

}
