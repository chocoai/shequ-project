package com.haolinbang.modules.act.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 流程已结束。在最后一个节点的ACTIVITY_COMPLETED事件之后触发。 当流程到达的状态，没有任何后续连线时， 流程就会结束。
 * 
 * @author Administrator
 * 
 */
@Service
public class ProcessCompleteListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(ProcessCompleteListener.class);

	@Override
	public void handle(ActivitiEvent event) {
		// TODO Auto-generated method stub
		logger.info("-----ProcessCompleteListener----");
	}

}
