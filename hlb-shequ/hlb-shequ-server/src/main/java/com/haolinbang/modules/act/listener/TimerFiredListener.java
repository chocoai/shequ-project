package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.service.WyActJobService;

/**
 * 触发了定时器。job包含在事件中。
 * 
 * @author Administrator
 * 
 */
@Service
public class TimerFiredListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(TimerFiredListener.class);

	@Autowired
	private WyActJobService wyActJobService;

	@Override
	public void handle(ActivitiEvent event) {

		// 触发定时器.设置定时时间
		String procDefId = event.getProcessDefinitionId();
		String actId = event.getExecutionId();
		

		logger.info("---procDefId=" + procDefId + ";actId=" + actId);
		

		String type = event.getType().name();
		logger.info("----TimerFiredListener---type=" + type);
		
		
	}

}
