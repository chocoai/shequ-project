package com.haolinbang.modules.sns.service.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import me.chanjar.weixin.mp.bean.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.act.dao.ActDao;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyActCandidateDetail;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.entity.WyInstCandidate;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.WeixinService;
import com.haolinbang.modules.sns.service.WyActCandidateDetailService;
import com.haolinbang.modules.sns.service.WyActCandidateService;
import com.haolinbang.modules.sns.service.WyApproveDetailService;
import com.haolinbang.modules.sns.service.WyInstCandidateService;
import com.haolinbang.modules.sns.util.ActUtil;
import com.haolinbang.modules.sns.util.GroupidsUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxMsgTpl;
import com.haolinbang.modules.weixin.entity.WxMsgTplCommon;
import com.haolinbang.modules.weixin.service.WxAccountService;
import com.haolinbang.modules.weixin.service.WxMsgTplService;

/**
 * 微信通知处理类
 * 
 * @author Administrator
 * 
 */
@Service
public class WeixinNotifyHandler implements BusinessHandler {

	private static Logger logger = LoggerFactory.getLogger(WeixinNotifyHandler.class);

	@Autowired
	private HistoryService historyService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private WyActCandidateService wyActCandidateService;

	@Autowired
	private WyInstCandidateService wyInstCandidateService;

	@Autowired
	private WyActCandidateDetailService wyActCandidateDetailService;

	@Resource
	private WeixinService weixinService;

	@Autowired
	private ActDao actDao;

	@Autowired
	private WyApproveDetailService wyApproveDetailService;

	@Autowired
	private WxAccountService wxAccountService;

	@Autowired
	private WxMsgTplService wxMsgTplService;

	@Override
	public void handle(DelegateTask delegateTask) {

		notice2(delegateTask);

		logger.info("-------###----微信通知处理类-------");
	}

	/**
	 * 通知维修人员抢单 待处理任务提醒
	 * 
	 * @param delegateTask
	 */
	private void notice2(DelegateTask delegateTask) {
		List<Member> list = getSender(delegateTask);
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();

		String businessKey = String.valueOf(delegateTask.getVariable("businessKey"));
		String arr[] = businessKey.split(":");

		String bizId = (arr != null && arr.length > 1) ? arr[1] : null;
		String tableName = (arr != null && arr.length > 1) ? arr[0] : null;

		if (StringUtils.isBlank(bizId) || StringUtils.isBlank(tableName)) {
			throw new RuntimeException("tableName或者bizId为空");
		}
		String content = actDao.getContent(tableName, bizId);

		String procInstId = delegateTask.getProcessInstanceId();
		// 查询当前节点的处理状态
		String currStep = "";
		WyApproveDetail approveDetail = wyApproveDetailService.geLatestWyApproveDetailByProcInstId(procInstId);
		if (approveDetail != null) {
			currStep = approveDetail.getTaskName();
		}
		try {
			for (Member member : list) {
				templateMessage.setToUser(member.getOpenid());// 接受消息的用户openid

				String code = Global.getConfig("weixin.msg.tpl.common,code");

				Map<String, Object> vars = delegateTask.getVariables();
				String source = (String) vars.get("app_source");
				String groupid = (String) vars.get("app_groupid");

				WxAccount wxAccount = new WxAccount();
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				wxAccount.setSource(source);
				List<WxAccount> wxAccounts = wxAccountService.findList(wxAccount);

				WxMsgTpl wxMsgTpl = new WxMsgTpl();
				WxMsgTplCommon wxMsgTplCommon = new WxMsgTplCommon();
				wxMsgTplCommon.setCode(code);
				wxMsgTpl.setCommonTpl(wxMsgTplCommon);
				wxMsgTpl.setSource(source);
				wxMsgTpl.setAccountId(wxAccounts.get(0).getId());
				wxMsgTpl = wxMsgTplService.getWxMsgTpl(wxMsgTpl);

				if (wxMsgTpl == null) {
					List<String> groupids = GroupidsUtils.getLastGroupIDs(source, StringUtils.toInteger(groupid));
					if (groupids != null && groupids.size() > 1) {
						int length = groupids.size() - 2;
						while (wxMsgTpl == null && length >= 0) {
							wxAccount = new WxAccount();
							wxAccount.setGroupId(StringUtils.toInteger(groupids.get(length)));
							wxAccount.setSource(source);
							wxAccounts = wxAccountService.findList(wxAccount);

							if (wxAccounts.size() > 0) {
								wxMsgTpl = new WxMsgTpl();
								wxMsgTpl.setCommonTpl(wxMsgTplCommon);
								wxMsgTpl.getCommonTpl().setCode(code);
								wxMsgTpl.setSource(source);
								wxMsgTpl.setAccountId(wxAccounts.get(0).getId());
								wxMsgTpl = wxMsgTplService.getWxMsgTpl(wxMsgTpl);
							}
							length--;
						}
					}
				}

				if (wxMsgTpl == null) {
					throw new RuntimeException("模板配置异常,请检查配置信息");
				}

				templateMessage.setTemplateId(wxMsgTpl.getTplId());
				templateMessage.setUrl(Global.getConfig("server.project.hlb-shequ-server") + "/wuye/form2?bizId=" + bizId + "&procInsId=" + delegateTask.getProcessInstanceId()
						+ "&app_source=" + source + "&app_groupid=" + groupid);// 点击的跳转url地址,分享出去的链接地址带上source和组织机构id
				templateMessage.setTopColor("#FF0000");

				// 头部提示消息
				templateMessage.getDatas().add(new WxMpTemplateData("first", "您有任务未完成，请赶快处理", "#173177"));
				// 待处理任务
				String content2 = content;
				if (StringUtils.isNotBlank(currStep)) {
					content2 = content + "；当前节点：" + currStep;
				}
				templateMessage.getDatas().add(new WxMpTemplateData("keyword1", content2, "#173177"));
				// 已延期任务
				templateMessage.getDatas().add(new WxMpTemplateData("keyword2", "暂无", "#173177"));

				// 备注信息
				templateMessage.getDatas().add(new WxMpTemplateData("remark", "点击进行处理", "#173177"));
				String json = weixinService.tplMsgSend(templateMessage);// 发送消息
				logger.info("发送消息,{}" + json);
			}
		} catch (Exception e) {
			logger.error("客服中心收到的通知,出现错误:{}", e);
		}
	}

	private List<Member> getSender(DelegateTask delegateTask) {
		List<Member> memberList = new ArrayList<Member>();
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
					Member member = memberService.getMember(Integer.valueOf(historicTaskInstance.getAssignee()));
					memberList.add(member);
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
						Member member = memberService.getMember(Integer.valueOf(historicTaskInstance.getAssignee()));
						memberList.add(member);
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
					Member member = memberService.getMember(Integer.valueOf(authenticatedUserId));
					memberList.add(member);
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
							Member member = memberService.getMember(Integer.valueOf(memberid));
							memberList.add(member);

						}
					} else {
						// 多个候选人认领任务
						for (WyInstCandidate wyInstCandidate : wyInstCandidateList) {
							if (WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER.equals(wyInstCandidate.getCandidateType())) {
								String userid = wyInstCandidate.getCandidate();
								userid = userid.replace(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER, "");
								String memberid = memberService.getMemberByYgid(Integer.valueOf(userid));
								if (StringUtils.isNotBlank(memberid)) {
									Member member = memberService.getMember(Integer.valueOf(memberid));
									memberList.add(member);
								}

							} else if (WyConstants.CANDIDATE_TYPE_GROUP.equals(wyInstCandidate.getCandidateType())) {
								String departId = wyInstCandidate.getCandidate();
								// 替换掉原来的标示
								departId = departId.replace(WyConstants.CANDIDATE_TYPE_GROUP, "");

								// 查询出groupId下面的会员有哪些
								List<Member> memberList2 = memberService.getMemberBygroupId(Integer.valueOf(departId));
								if (memberList2 != null && !memberList2.isEmpty()) {
									for (Member member : memberList2) {
										Member member2 = memberService.getMember(Integer.valueOf(member.getMemberId()));
										memberList.add(member2);
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
						List<Member> memberList2 = memberService.getMemberBygroupId(Integer.valueOf(departId));
						if (memberList2 != null && !memberList2.isEmpty()) {
							for (Member member : memberList2) {
								Member member2 = memberService.getMember(Integer.valueOf(member.getMemberId()));
								memberList.add(member2);
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
							Member member2 = memberService.getMember(Integer.valueOf(memberid));
							memberList.add(member2);
						}
					}
				}
			}
				break;

			}
		}
		return memberList;
	}
}
