package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 任务已超时，在TIMER_FIRED事件之后，会触发用户任务的超时事件， 当这个任务分配了一个定时器的时候。
 * 
 * @author Administrator
 * 
 */
@Service
public class TaskTimeoutListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(TaskTimeoutListener.class);

	@Override
	public void handle(ActivitiEvent event) {

		logger.info("-----TaskTimeoutListener----");
	}

}
