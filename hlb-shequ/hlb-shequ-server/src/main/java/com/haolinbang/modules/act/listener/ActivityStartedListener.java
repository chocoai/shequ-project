package com.haolinbang.modules.act.listener;

import org.activiti.engine.EngineServices;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.SpringContextHolder;

/**
 * 一个节点开始执行
 * 
 * @author Administrator
 * 
 */
@Service
public class ActivityStartedListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(ActivityStartedListener.class);

	@Override
	public void handle(ActivitiEvent event) {

		// 从数据库中查询出该节点的处理方法
		// 获取活动的认为节点id信息
		ActivitiActivityEventImpl eventImpl = (ActivitiActivityEventImpl) event;

		// 节点在xml中对应的id
		String activityId = eventImpl.getActivityId();

		EngineServices engineServices = eventImpl.getEngineServices();
		String executionId = event.getExecutionId();

		logger.info("------ActivityStartedListener-----activityId=" + activityId + "-----executionId=" + executionId);

		//在节点开始的时候，设置处理类，节点结束时，删除该处理类
		engineServices.getRuntimeService().setVariable(executionId, "commonEventListener", SpringContextHolder.getBean("commonEventListener"));

	}

}
