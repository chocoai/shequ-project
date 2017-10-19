package com.haolinbang.modules.sns.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.sns.dto.PvmTransitionDto;

/**
 * 工作流工具类
 * 
 * @author Administrator
 * 
 */
public class ActUtil {

	protected static Logger logger = LoggerFactory.getLogger(ActUtil.class);

	@Autowired
	private static RepositoryService repositoryService = SpringContextHolder.getBean("repositoryService");

	@Autowired
	private static HistoryService historyService = SpringContextHolder.getBean("historyService");

	/**
	 * 获取当前节点的入线
	 * 
	 * @param processDefinitionId
	 * @param activitiId
	 * @return
	 */
	public static List<PvmTransition> getIncomingTransitions(String processDefinitionId, String activitiId) {
		ProcessDefinitionEntity def2 = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList2 = def2.getActivities();
		for (ActivityImpl activityImpl : activitiList2) {
			if (activityImpl.getId().equals(activitiId)) {
				return activityImpl.getIncomingTransitions();
			}
		}
		return null;
	}

	/**
	 * 获取当前节点的出线
	 * 
	 * @param processDefinitionId
	 * @param activitiId
	 * @return
	 */
	public static List<PvmTransition> getOutgoingTransitions(String processDefinitionId, String activitiId) {
		ProcessDefinitionEntity def2 = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList2 = def2.getActivities();
		for (ActivityImpl activityImpl : activitiList2) {
			if (activityImpl.getId().equals(activitiId)) {
				return activityImpl.getOutgoingTransitions();
			}
		}
		return null;
	}

	/**
	 * 获取上一节点
	 * 
	 * @param procDefId
	 * @param taskId
	 * @param type
	 *            1=获取id;2=获取name
	 * @return
	 */
	public static String getPreActivitiSource(String procDefId, String taskId, String type) {
		// 显示流程节点,查找来源节点
		List<PvmTransition> pvmTransitionList = ActUtil.getIncomingTransitions(procDefId, taskId);
		List<PvmTransitionDto> pvmTransitionDtoList = new ArrayList<PvmTransitionDto>();
		for (PvmTransition pvm : pvmTransitionList) {
			// 组装数据来源
			PvmTransitionDto pvmDto = new PvmTransitionDto();
			Object desName = pvm.getDestination().getProperty("name") == null ? pvm.getDestination().getProperty("type") : pvm.getDestination().getProperty("name");
			String desName2 = String.valueOf(desName);
			if ("startEvent".equals(desName2)) {
				desName2 = "开始节点";
			} else if ("exclusiveGateway".equals(desName2)) {
				desName2 = "网关节点";
			}
			pvmDto.setDestination(desName2);

			Object sourceName = pvm.getSource().getProperty("name") == null ? pvm.getSource().getProperty("type") : pvm.getSource().getProperty("name");
			String sourceName2 = String.valueOf(sourceName);
			if ("startEvent".equals(sourceName2)) {
				sourceName2 = "开始节点";
			} else if ("exclusiveGateway".equals(sourceName2)) {
				sourceName2 = "网关节点";
			}
			pvmDto.setSource(sourceName2);
			pvmDto.setDestinationId(pvm.getDestination().getId());
			pvmDto.setSourceId(pvm.getSource().getId());
			pvmTransitionDtoList.add(pvmDto);
		}

		// 当只有一个节点来源时
		if (pvmTransitionDtoList != null && pvmTransitionDtoList.size() == 1) {
			if ("1".equals(type)) {
				// 获取id
				return pvmTransitionDtoList.get(0).getSourceId();
			} else if ("2".equals(type)) {
				// 获取名称
				return pvmTransitionDtoList.get(0).getSource();
			}
		}
		return null;
	}

	/**
	 * 获取历史节点最近的一个节点
	 * 
	 * @param processInstanceId
	 * @param defKey
	 * @param defid
	 * @return
	 */
	public static String getSourceActivitiId(String processInstanceId, String defid, String defKey) {
		// 查询流程中的节点数据
		List<PvmTransition> list = ActUtil.getIncomingTransitions(defid, defKey);
		if (list != null && !list.isEmpty()) {
			if (list.size() == 1) {
				return list.get(0).getSource().getId();
			}

		}
		List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery().// 过滤条件
				processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().desc().list();
		for (HistoricActivityInstance his : hais) {
			logger.info("actid={}", his.getActivityId());
		}
		if (hais != null && !hais.isEmpty()) {
			return hais.get(0).getActivityId();
		}
		if (hais == null || hais.isEmpty()) {

		}
		return null;
	}

	/**
	 * 获取流向的下一个节点
	 * 
	 * @param processInstanceId
	 * @param defKey
	 * @param defid
	 * @return
	 */
	public static String getOutgoingActivitiId(String processInstanceId, String defid, String defKey) {
		// 查询流程中的节点数据
		List<PvmTransition> list = ActUtil.getOutgoingTransitions(defid, defKey);
		if (list != null && !list.isEmpty()) {
			if (list.size() == 1) {
				return list.get(0).getDestination().getId();
			}
		}		
		return null;
	}
}
