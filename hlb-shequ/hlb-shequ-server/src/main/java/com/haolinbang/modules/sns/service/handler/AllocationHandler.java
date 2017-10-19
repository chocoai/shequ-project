package com.haolinbang.modules.sns.service.handler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyActCandidateDetail;
import com.haolinbang.modules.sns.entity.WyInstCandidate;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.WyActCandidateDetailService;
import com.haolinbang.modules.sns.service.WyActCandidateService;
import com.haolinbang.modules.sns.service.WyInstCandidateService;
import com.haolinbang.modules.sns.util.ActUtil;

/**
 * 分配待办处理人
 * 
 * @author Administrator
 * 
 */
@Service
public class AllocationHandler implements BusinessHandler {

	private static Logger logger = LoggerFactory.getLogger(AllocationHandler.class);

	@Autowired
	private HistoryService historyService;

	@Autowired
	private WyActCandidateService wyActCandidateService;

	@Autowired
	private WyInstCandidateService wyInstCandidateService;

	@Autowired
	private WyActCandidateDetailService wyActCandidateDetailService;

	@Autowired
	private MemberService memberService;

	@Override
	public void handle(DelegateTask delegateTask) {

		// 根据不同的流程进行处理流程
		String defKey = delegateTask.getTaskDefinitionKey();
		String defid = delegateTask.getProcessDefinitionId();
		String procInstId = delegateTask.getProcessInstanceId();

		logger.info("----defKey={},defid={}", defKey, defid);

		String source = ActUtil.getSourceActivitiId(procInstId, defid, defKey);
		logger.info("----source={}", source);

		// 将原来工作流的数据源切换回来
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		// 查询数据,类型表只有一条记录
		WyActCandidate wyActCandidate = wyActCandidateService.getWyActCandidateByDefidAndDefkey2(defid, defKey);
		if (wyActCandidate != null) {
			switch (wyActCandidate.getType()) {
			case WyConstants.CANDIDATE_TYPE_SAME_OTHER_TASK: {
				// 查找历史节点
				List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).list();
				HistoricTaskInstance historicTaskInstance = null;
				if (list != null && list.size() > 0) {
					for (HistoricTaskInstance hti : list) {
						if (hti.getTaskDefinitionKey().equals(wyActCandidate.getSameTaskId())) {
							historicTaskInstance = hti;
							break;
						}
					}
				}
				// 获取历史节点处理人
				if (historicTaskInstance != null) {
					delegateTask.setAssignee(historicTaskInstance.getAssignee());
				}
			}
				break;
			case WyConstants.CANDIDATE_TYPE_PRE_ACT_HANDLER: {
				List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).list();
				HistoricTaskInstance historicTaskInstance = null;
				// 按照完成时间倒序排列,便于找到最近的完成的任务节点
				Collections.sort(list, new Comparator<HistoricTaskInstance>() {
					@Override
					public int compare(HistoricTaskInstance o1, HistoricTaskInstance o2) {
						return (int) (o2.getEndTime().getTime() / 1000 - o1.getEndTime().getTime() / 1000);
					}
				});

				if (list != null && list.size() > 0) {
					historicTaskInstance = list.get(0);
					// 获取历史节点处理人
					if (historicTaskInstance != null) {
						delegateTask.setAssignee(historicTaskInstance.getAssignee());
					} else {
						throw new RuntimeException("获取历史任务节点出错,无法获取历史任务处理人");
					}
				} else {
					throw new RuntimeException("运行出错,没有完成的任务实例");
				}

			}
				break;
			case WyConstants.CANDIDATE_TYPE_SAME_STARTER: {
				// 处理人和发起人一致
				// 获取发起人
				HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
				String authenticatedUserId = hpi.getStartUserId();
				if (StringUtils.isNotBlank(authenticatedUserId)) {
					delegateTask.setAssignee(authenticatedUserId);
				}
				
			}
				break;
			case WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY: {
				// 由上一个节点指定的处理人，从流程实例处理人中查询
				List<WyInstCandidate> wyInstCandidateList = wyInstCandidateService.getWyInstCandidateByProcInsIdAndTaskId(procInstId, defKey);
				if (wyInstCandidateList != null && wyInstCandidateList.size() > 0) {
					// 从流程实例id中获取处理人
					if (wyInstCandidateList.size() == 1 && WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER.equals(wyInstCandidateList.get(0).getCandidateType())) {
						String userid = wyInstCandidateList.get(0).getCandidate();
						userid = userid.replace(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER, "");

						String memberid = memberService.getMemberByYgid(Integer.valueOf(userid));
						if (StringUtils.isNotBlank(memberid)) {
							delegateTask.setAssignee(memberid);
						}
					} else {
						// 多个候选人认领任务
						for (WyInstCandidate wyInstCandidate : wyInstCandidateList) {
							if (WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER.equals(wyInstCandidate.getCandidateType())) {
								String userid = wyInstCandidate.getCandidate();
								userid = userid.replace(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER, "");
								String memberid = memberService.getMemberByYgid(Integer.valueOf(userid));
								if (StringUtils.isNotBlank(memberid)) {
									delegateTask.addCandidateUser(memberid);
								}

							} else if (WyConstants.CANDIDATE_TYPE_GROUP.equals(wyInstCandidate.getCandidateType())) {
								String departId = wyInstCandidate.getCandidate();
								// 替换掉原来的标示
								departId = departId.replace(WyConstants.CANDIDATE_TYPE_GROUP, "");

								// 查询出groupId下面的会员有哪些
								List<Member> memberList = memberService.getMemberBygroupId(Integer.valueOf(departId));
								if (memberList != null && !memberList.isEmpty()) {
									for (Member member : memberList) {
										delegateTask.addCandidateUser(member.getMemberId().toString());
									}
								}
							}
						}
					}
				}
			}
				break;
			case WyConstants.CANDIDATE_TYPE_SELECT_STAFF: {
				// 设置部门
				List<WyActCandidateDetail> orgList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate.getId(),
						WyConstants.CANDIDATE_TYPE_GROUP);
				if (orgList != null && !orgList.isEmpty()) {
					for (WyActCandidateDetail detail : orgList) {
						// 从接口获取部门人员列表
						String departId = detail.getCandidate();
						// 替换掉原来的标示
						departId = departId.replace(WyConstants.CANDIDATE_TYPE_GROUP, "");
						// 查询出groupId下面的会员有哪些
						List<Member> memberList = memberService.getMemberBygroupId(Integer.valueOf(departId));
						if (memberList != null && !memberList.isEmpty()) {
							for (Member member : memberList) {
								delegateTask.addCandidateUser(member.getMemberId().toString());
							}
						}
					}
				}

				// 设置人员
				List<WyActCandidateDetail> candidateList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate.getId(),
						WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER);
				if (candidateList != null && !candidateList.isEmpty()) {
					for (WyActCandidateDetail detail : candidateList) {
						// 获取员工id
						String ygid = detail.getCandidate();
						// 替换掉原来的标示
						ygid = ygid.replace(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER, "");
						// 将管理员id转成会员id
						String memberid = memberService.getMemberByYgid(Integer.valueOf(ygid));
						if (StringUtils.isNotBlank(memberid)) {
							delegateTask.addCandidateUser(memberid);
						}
					}
				}
			}
				break;

			}

		}
	}
}
