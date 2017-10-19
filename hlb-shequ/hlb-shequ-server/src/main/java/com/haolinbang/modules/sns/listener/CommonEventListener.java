package com.haolinbang.modules.sns.listener;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haolinbang.common.utils.DateUtils;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.WyActHandler;
import com.haolinbang.modules.sns.entity.WyActJob;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.service.WyActHandlerService;
import com.haolinbang.modules.sns.service.WyActJobService;
import com.haolinbang.modules.sns.service.WyApproveDetailService;
import com.haolinbang.modules.sns.service.WyApproveService;
import com.haolinbang.modules.sns.service.handler.BusinessHandler;

/**
 * 通用处理类,
 * 
 * @author Administrator
 * 
 */
@Service("commonEventListener")
public class CommonEventListener implements TaskListener, ExecutionListener {

	private static final long serialVersionUID = -1785104693545133265L;

	private static Logger logger = LoggerFactory.getLogger(CommonEventListener.class);

	@Override
	public void notify(DelegateTask delegateTask) {

		String datestr = DateUtils.getISO8601DateTime(DateUtils.addMinutes(new Date(), 1));
		delegateTask.setVariable("commonTime", datestr);

		logger.info("------时间------date={}", datestr);

		// 直接从容器中获取处理类
		WyActHandlerService wyActHandlerService = SpringContextHolder.getBean("wyActHandlerService");
		WyActJobService wyActJobService = SpringContextHolder.getBean("wyActJobService");
		WyApproveDetailService wyApproveDetailService = SpringContextHolder.getBean("wyApproveDetailService");
		WyApproveService wyApproveService = SpringContextHolder.getBean("wyApproveService");

		// 获取节点信息，从数据库中查询处理方法
		// 流程定义id
		String procDefId = delegateTask.getProcessDefinitionId();
		// 任务节点定义key
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		// 处理事件名称
		String eventName = delegateTask.getEventName();
		String procInstId = delegateTask.getProcessInstanceId();

		logger.info("-------procDefId={},taskDefinitionKey={},eventName={}", procDefId, taskDefinitionKey, eventName);
		// 任务创建就清除临时变量和执行定时任务
		if (WyConstants.TASK_EVENT_CREATE.equals(eventName)) {
			logger.info("临时变量={}", delegateTask.getVariables());

			delegateTask.removeVariable(WyConstants.ACTIVITI_VALUE_COMMON_EVENT_LISTENER);
			delegateTask.removeVariable(WyConstants.ACTIVITI_VALUE_COMMON_FLOW);
			delegateTask.removeVariable(WyConstants.ACTIVITI_VALUE_COMMON_TIME);

			logger.info("临时变量={}", delegateTask.getVariables());

			// 执行定时任务
		}

		// 不同节点一般处理任务
		List<WyActHandler> commonHandlerList = wyActHandlerService.getWyActHandlerByProcdefidAndTaskkeyByType(procDefId, taskDefinitionKey, eventName,
				WyConstants.TASK_EVENT_HANDLER_NORMAL);
		if (commonHandlerList != null && commonHandlerList.size() > 0) {
			// 如果有多个处理类,每进行一次循环会执行一次,执行顺序按照排序进行处理
			for (WyActHandler wyActHandler : commonHandlerList) {
				logger.info("--------该处理类={}", wyActHandler.getHandlerInstance());
				// 从容器中查找处理类,并执行
				BusinessHandler handler = SpringContextHolder.getBean(wyActHandler.getHandlerInstance());
				// 调用实际的处理类进行处理
				handler.handle(delegateTask);
			}
		}

		if (WyConstants.TASK_EVENT_CREATE.equals(eventName) || WyConstants.TASK_EVENT_ASSIGN.equals(eventName) || WyConstants.TASK_EVENT_COMPLETE.equals(eventName)) {
			// 获取定时任务,常规定时任务
			WyActJob wyActJob = wyActJobService.getWyActJobByProcdefidAndTaskidAndType(procDefId, taskDefinitionKey, WyConstants.TASK_JOB_ACTIVITI_CREATE);
			if (wyActJob != null) {
				String jobHandler = "jobHandler";
				// 从容器中查找处理类,并执行
				BusinessHandler handler = SpringContextHolder.getBean(jobHandler);
				// 调用实际的处理类进行处理
				handler.handle(delegateTask);
			}
		}

		// 保存开始和结束时间
		if (WyConstants.TASK_EVENT_CREATE.equals(eventName) || WyConstants.TASK_EVENT_COMPLETE.equals(eventName)) {
			// 直接查询该节点的任务节点，创建多个
			WyApproveDetail wyApproveDetail = wyApproveDetailService.getWyApproveDetailByProcInstIdAndTaskKey(procInstId, taskDefinitionKey);
			if (wyApproveDetail == null) {
				// 如果为空,则进行新建,保存
				wyApproveDetail = new WyApproveDetail();
				wyApproveDetail.setProcInstId(procInstId);
				wyApproveDetail.setTaskKey(taskDefinitionKey);
				wyApproveDetail.setTaskName(delegateTask.getName());
				wyApproveDetail.setStartTime(new Date());
			} else {
				// 记录用户任务节点的开始和结束时间
				if (WyConstants.TASK_EVENT_CREATE.equals(eventName)) {
					wyApproveDetail.setStartTime(new Date());
				} else if (WyConstants.TASK_EVENT_COMPLETE.equals(eventName)) {
					wyApproveDetail.setEndTime(new Date());
				}
			}

			wyApproveDetailService.save(wyApproveDetail);
		}
		// 结束节点，加上事件监听，更新完成时间
		if (WyConstants.ACTIVITY_EVENT_END.equals(eventName) || WyConstants.TASK_EVENT_CREATE.equals(eventName) || WyConstants.TASK_EVENT_COMPLETE.equals(eventName)) {
			WyApprove wyApprove = wyApproveService.getWyApproveByProcInstId(procInstId);
			if (wyApprove != null) {
				wyApprove.setCurrTaskKey(taskDefinitionKey);
				wyApprove.setCurrTaskName(delegateTask.getName());
				//如果是末尾节点,则设置为已完成
				if(WyConstants.ACTIVITY_EVENT_END.equals(eventName)){
					wyApprove.setEndTime(new Date());
					wyApprove.setFinished(true);
				}				
				wyApproveService.save(wyApprove);
			} else {
				logger.error("节点中没有创建wyApprove，运行错误");
			}
		}

		// 结束节点，加上事件监听，更新完成时间
		if (WyConstants.ACTIVITY_EVENT_STARTED.equals(eventName)) {
			WyApprove wyApprove = wyApproveService.getWyApproveByProcInstId(procInstId);
			if (wyApprove != null) {
				wyApprove.setEndTime(new Date());
				wyApprove.setFinished(true);
				wyApproveService.save(wyApprove);
			}
		}

		logger.info("------CommonEventListener----delegateTask----");
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

		logger.info("------CommonEventListener----execution----");

	}

}
