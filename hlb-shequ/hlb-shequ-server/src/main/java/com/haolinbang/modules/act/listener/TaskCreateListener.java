package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 创建了新任务。它位于ENTITY_CREATE事件之后。当任务是由流程创建时， 这个事件会在TaskListener执行之前被执行。
 * 
 * @author nlp
 * 
 */
@Service
public class TaskCreateListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(TaskCreateListener.class);

	@Override
	public void handle(ActivitiEvent event) {
		ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
		TaskEntity taskEntity = (TaskEntity) eventImpl.getEntity();

		logger.info("----TaskCreateListener----taskname=" + taskEntity.getName());

	}

}