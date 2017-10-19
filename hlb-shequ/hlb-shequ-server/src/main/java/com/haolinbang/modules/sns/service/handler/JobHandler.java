package com.haolinbang.modules.sns.service.handler;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.WyActHandler;
import com.haolinbang.modules.sns.entity.WyActJob;
import com.haolinbang.modules.sns.service.WyActHandlerService;
import com.haolinbang.modules.sns.service.WyActJobService;

/**
 * 定时器任务处理
 * 
 * @author Administrator
 * 
 */
@Service
public class JobHandler implements BusinessHandler {

	private static Logger logger = LoggerFactory.getLogger(BusinessHandler.class);

	@Autowired
	private WyActJobService wyActJobService;

	@Autowired
	private WyActHandlerService wyActHandlerService;

	@Override
	public void handle(final DelegateTask delegateTask) {

		WyActJob wyActJob = wyActJobService.getWyActJobByProcdefidAndTaskidAndType(delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey(),
				WyConstants.TASK_JOB_ACTIVITI_CREATE);

		Integer hour = wyActJob.getJobHour() == null ? 0 : wyActJob.getJobHour();
		Integer day = wyActJob.getJobDay() == null ? 0 : wyActJob.getJobDay();
		Integer min = wyActJob.getJobMinute() == null ? 0 : wyActJob.getJobMinute();

		final long timeInterval = (3600 * hour + 60 * min + 86400 * day);
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					// 延迟时间设置
					Thread.sleep(timeInterval);
					String processDefinitionId = delegateTask.getProcessDefinitionId();
					String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
					String eventName = delegateTask.getEventName();

					// 启动定时任务去处理定时任务
					List<WyActHandler> jobList = wyActHandlerService.getWyActHandlerByProcdefidAndTaskkeyByType(processDefinitionId, taskDefinitionKey, eventName,
							WyConstants.TASK_EVENT_HANDLER_JOB);
					for (WyActHandler wyActHandler : jobList) {
						logger.info("--------定时任务处理,该处理类={}", wyActHandler.getHandlerInstance());
						// 从容器中查找处理类,并执行
						BusinessHandler handler = SpringContextHolder.getBean(wyActHandler.getHandlerInstance());
						// 调用实际的处理类进行处理
						handler.handle(delegateTask);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();

	}
}
