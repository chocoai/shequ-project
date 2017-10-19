package com.haolinbang.modules.sns.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.act.entity.Act;
import com.haolinbang.modules.act.service.ActTurnBackService;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyActForm;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.service.WuyeService;
import com.haolinbang.modules.sns.service.WyActFormService;
import com.haolinbang.modules.sns.service.WyApproveDetailService;
import com.haolinbang.modules.sns.service.WyApproveService;
import com.haolinbang.modules.sns.service.WyComplainService;
import com.haolinbang.modules.sns.service.WyRepairService;
import com.haolinbang.modules.sns.util.MemberUtils;

@Service
@Transactional(readOnly = true)
public class WuyeServiceImpl implements WuyeService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TaskService taskService;

	@Autowired
	private WyApproveService wyApproveService;

	@Autowired
	private WyApproveDetailService wyApproveDetailService;

	@Autowired
	private ActTurnBackService actTurnBackService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private WyActFormService wyActFormService;
	
	@Override
	@Transactional(readOnly = false)
	public boolean fulfilTask(String bizId, String procInsId, String vars) {
		Member member = MemberUtils.getCurrentMember();

		@SuppressWarnings("rawtypes")
		List<Map> list = null;
		// 解析key,value值
		if (StringUtils.isNotBlank(vars)) {
			try {
				// 将html实体中的引号转成引号
				vars = StringEscapeUtils.unescapeHtml4(vars);
				// 将url编码的字符串转码成元字符串
				vars = URLDecoder.decode(vars, "UTF-8");
				// 解析字符串成list map集合
				list = JSONArray.parseArray(vars, Map.class);
			} catch (UnsupportedEncodingException e) {
				logger.error("解码出错:{}", e);
			}
		}

		// 局部变量
		Map<String, Object> vars2 = new HashMap<String, Object>();
		if (list != null && !list.isEmpty()) {
			for (Map<String, Object> map : list) {
				String key = String.valueOf(map.get("key"));
				String value = String.valueOf(map.get("value"));
				if ("true".equals(value)) {
					vars2.put(key, true);
				} else if ("false".equals(value)) {
					vars2.put(key, false);
				} else {
					vars2.put(key, value);
				}
			}
		}

		String taskKey = null;
		String taskName = null;

		// 认领任务
		Task claimTask = taskService.createTaskQuery().processInstanceId(procInsId).taskCandidateUser(member.getMemberId().toString()).active().orderByTaskCreateTime().desc()
				.singleResult();
		if (claimTask != null) {
			// 如果有未认领任务,先认领
			taskService.claim(claimTask.getId(), String.valueOf(member.getMemberId()));
			taskKey = claimTask.getTaskDefinitionKey();
			taskName = claimTask.getName();
		}

		// 完成任务
		Task currTask = taskService.createTaskQuery().processInstanceId(procInsId).taskAssignee(member.getMemberId().toString()).active().orderByTaskCreateTime().desc()
				.singleResult();
		if (currTask != null) {
			taskService.complete(currTask.getId(), vars2);
			taskKey = currTask.getTaskDefinitionKey();
			taskName = currTask.getName();
		}

		// 记录流程处理节点
		WyApproveDetail wyApproveDetail = wyApproveDetailService.getWyApproveDetailByProcInstIdAndTaskKey(procInsId, taskKey);
		if (wyApproveDetail == null) {
			wyApproveDetail=new WyApproveDetail();
			wyApproveDetail.setProcInstId(procInsId);
			wyApproveDetail.setTaskKey(taskKey);
		}
		wyApproveDetail.setApprover(member.getMemberId().toString());
		wyApproveDetail.setTaskName(taskName);
		wyApproveDetail.setRemarks("完成任务");
		wyApproveDetailService.save(wyApproveDetail);

		return true;
	}

	/**
	 * 回退任务
	 */
	@SuppressWarnings("null")
	@Override
	@Transactional(readOnly = false)
	public boolean backTask(String bizId, String procInsId) throws Exception {
		Member member = MemberUtils.getCurrentMember();
		Task claimTask = taskService.createTaskQuery().processInstanceId(procInsId).taskCandidateUser(member.getMemberId().toString()).active().orderByTaskCreateTime().desc()
				.singleResult();
		// 如果是认领任务,则设置成当前用户
		if (claimTask != null) {
			taskService.claim(claimTask.getId(), member.getMemberId().toString());
		}

		String taskKey = null;
		String taskName = null;
		// 获取待办理任务
		Task currTask = taskService.createTaskQuery().processInstanceId(procInsId).taskAssignee(member.getMemberId().toString()).active().orderByTaskCreateTime().desc()
				.singleResult();

		Map<String, Object> vars = new HashMap<String, Object>();
		List<HistoricTaskInstance> histTaskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInsId).orderByHistoricTaskInstanceEndTime().desc().list();
		HistoricTaskInstance histTask = null;
		if (histTaskList != null && !histTaskList.isEmpty()) {
			histTask = histTaskList.get(0);
		}

		if (currTask != null) {
			taskKey = currTask.getTaskDefinitionKey();
			taskName = currTask.getName();
		}

		Act act = new Act();
		act.setProcInsId(procInsId);
		act.setTaskId(currTask.getId());
		act.setHistTask(histTask);
		act.setAssignee(member.getMemberId().toString());
		act.setTaskVars(vars);

		// 回退到上一节点
		actTurnBackService.backProcess(act);

		// 记录流程处理节点
		WyApprove wyApprove = wyApproveService.getWyApproveByProcInstId(procInsId);
		if (wyApprove != null) {
			WyApproveDetail wyApproveDetail = wyApproveDetailService.getWyApproveDetailByProcInstIdAndTaskKey(procInsId, taskKey);
			if (wyApproveDetail == null) {
				wyApproveDetail.setProcInstId(procInsId);
				wyApproveDetail.setTaskKey(taskKey);
			}
			wyApproveDetail.setApprover(member.getMemberId().toString());
			wyApproveDetail.setTaskName(taskName);
			wyApproveDetail.setRemarks("回退任务");
			wyApproveDetailService.save(wyApproveDetail);
		}

		return true;
	}

	/**
	 * 委托任务
	 */
	@SuppressWarnings("null")
	@Override
	@Transactional(readOnly = false)
	public String delegateTask(String bizId, String procInsId, String memberid) {
		Member member = MemberUtils.getCurrentMember();
		// 获取当前用户的处理流程的处理任务节点
		String memberId2 = String.valueOf(member.getMemberId());
		String taskKey = null;
		String taskName = null;

		Task claimTask = taskService.createTaskQuery().processInstanceId(procInsId).taskCandidateUser(memberId2).active().orderByTaskCreateTime().desc().singleResult();
		if (claimTask != null) {
			taskService.claim(claimTask.getId(), memberId2);
			taskKey = claimTask.getTaskDefinitionKey();
			taskName = claimTask.getName();
		}
		Task currTask = taskService.createTaskQuery().processInstanceId(procInsId).taskAssignee(memberId2).active().orderByTaskCreateTime().desc().singleResult();
		if (currTask != null) {
			taskService.delegateTask(currTask.getId(), memberid);
			logger.info("委托人:{},处理人:{}", currTask.getOwner(), currTask.getAssignee());
			taskKey = currTask.getTaskDefinitionKey();
			taskName = currTask.getName();
		}

		// 记录流程处理节点
		WyApprove wyApprove = wyApproveService.getWyApproveByProcInstId(procInsId);
		if (wyApprove != null) {
			WyApproveDetail wyApproveDetail = wyApproveDetailService.getWyApproveDetailByRelationIdAndTaskKey(wyApprove.getId(), taskKey);
			if (wyApproveDetail == null) {
				wyApproveDetail.setProcInstId(procInsId);
				wyApproveDetail.setTaskKey(taskKey);
			}
			wyApproveDetail.setApprover(member.getMemberId().toString());
			wyApproveDetail.setTaskName(taskName);
			wyApproveDetail.setRemarks("委托任务开始");
			wyApproveDetailService.save(wyApproveDetail);
		}

		String formKey = null;
		// 从表中查询显示的页面
		WyActForm wyActForm = wyActFormService.getWyActFormByProcinsidAndTaskkey(currTask.getProcessDefinitionId(), currTask.getTaskDefinitionKey(),
				WyConstants.TASK_FORM_URL_COMPLETED_REDIRECT_URL);
		if (wyActForm != null) {
			// 组装调用地址
			formKey = "redirect:" + wyActForm.getFormUrl() + "?bizId=" + bizId + "&procInsId=" + procInsId;
		}
		return formKey;
	}

	@SuppressWarnings("null")
	@Override
	@Transactional(readOnly = false)
	public String resolveTask(String bizId, String procInsId) {
		Member member = MemberUtils.getCurrentMember();

		// 获取当前用户的处理流程的处理任务节点
		Task task = getMyTask(procInsId, member.getMemberId().toString(), true);
		Map<String, Object> var = new HashMap<String, Object>();

		taskService.resolveTask(task.getId(), var);
		String formKey = null;
		// 从表中查询显示的页面
		WyActForm wyActForm = wyActFormService.getWyActFormByProcinsidAndTaskkey(task.getProcessDefinitionId(), task.getTaskDefinitionKey(),
				WyConstants.TASK_FORM_URL_COMPLETED_REDIRECT_URL);
		if (wyActForm != null) {
			// 组装调用地址
			formKey = "redirect:" + wyActForm.getFormUrl() + "?bizId=" + bizId + "&procInsId=" + procInsId;
		}

		// 记录流程处理节点
		WyApprove wyApprove = wyApproveService.getWyApproveByProcInstId(procInsId);
		if (wyApprove != null) {
			WyApproveDetail wyApproveDetail = wyApproveDetailService.getWyApproveDetailByRelationIdAndTaskKey(wyApprove.getId(), task.getTaskDefinitionKey());
			if (wyApproveDetail == null) {
				wyApproveDetail.setProcInstId(procInsId);
				wyApproveDetail.setTaskKey(task.getTaskDefinitionKey());
			}
			wyApproveDetail.setApprover(member.getMemberId().toString());
			wyApproveDetail.setTaskName(task.getName());
			wyApproveDetail.setRemarks("完成委托任务");
			wyApproveDetailService.save(wyApproveDetail);
		}
		return formKey;
	}

	/**
	 * 获取当前的处理任务节点
	 * 
	 * @param procInsId
	 *            实例id
	 * @param memberId2
	 *            会员id
	 * @param isAssignee
	 *            是否为处理人
	 * @return
	 */
	private Task getMyTask(String procInsId, String memberId2, boolean isAssignee) {
		// 获取当前用户在当前实例中的处理节点
		List<Task> taskList = null;

		if (isAssignee) {
			taskList = taskService.createTaskQuery().processInstanceId(procInsId).taskAssignee(memberId2).active().orderByTaskCreateTime().desc().list();
		} else {
			taskList = taskService.createTaskQuery().processInstanceId(procInsId).taskCandidateUser(memberId2).active().orderByTaskCreateTime().desc().list();
		}

		Task task = null;
		if (taskList != null && taskList.size() == 1) {
			task = taskList.get(0);
		}
		// 如果當前处理任务为空,则为异常
		if (task == null) {
			throw new RuntimeException("当前任务节点为空，处理异常");
		}
		return task;
	}
}
