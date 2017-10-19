package com.haolinbang.modules.act.listener;

import java.util.Date;
import java.util.List;

import org.activiti.engine.EngineServices;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.service.WyApproveDetailService;
import com.haolinbang.modules.sns.service.WyApproveService;

/**
 * 一个节点成功结束
 * 
 * @author Administrator
 * 
 */
@Service
public class ActivityCompletedListener implements EventHandler {

	private static Logger logger = LoggerFactory.getLogger(ActivityCompletedListener.class);

	@Autowired
	private WyApproveDetailService wyApproveDetailService;

	@Autowired
	private WyApproveService wyApproveService;

	@Override
	public void handle(ActivitiEvent event) {

		EngineServices engineServices = event.getEngineServices();
		String executionId = event.getExecutionId();
		logger.info("-----ActivityCompletedListener-----executionId={}", executionId);

		List<Execution> exes = engineServices.getRuntimeService().createExecutionQuery().executionId(executionId).list();
		for (Execution execution : exes) {
			// 如果已经结束,更新结束节点,添加结束节点的detail,和更新节点状态
			if (execution.isEnded()) {
				String procInstId = execution.getProcessInstanceId();

				WyApproveDetail wyApproveDetail = new WyApproveDetail();
				wyApproveDetail.setProcInstId(procInstId);
				wyApproveDetail.setTaskKey("end");
				wyApproveDetail.setTaskName("结束节点");
				wyApproveDetail.setStartTime(new Date());
				wyApproveDetail.setEndTime(new Date());
				wyApproveDetailService.save(wyApproveDetail);

				WyApprove wyApprove = wyApproveService.getWyApproveByProcInstId(procInstId);
				if (wyApprove != null) {
					wyApprove.setCurrTaskKey("end");
					wyApprove.setCurrTaskName("结束节点");
					// 如果是末尾节点,则设置为已完成
					wyApprove.setEndTime(new Date());
					wyApprove.setFinished(true);
					wyApproveService.save(wyApprove);
				}
			}
		}

		// 在节点开始的时候，设置处理类，节点结束时，删除该处理类
		if (engineServices.getRuntimeService().getVariable(executionId, "commonEventListener") != null) {
			engineServices.getRuntimeService().removeVariable(executionId, "commonEventListener");
		}
		// 删除流向临时变量
		if (engineServices.getRuntimeService().getVariable(executionId, "flowValue") != null) {
			engineServices.getRuntimeService().removeVariable(executionId, "flowValue");
		}

	}
}
