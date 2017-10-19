package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

/**
 * 流程实例对应的关系表Entity
 * 
 * @author nlp
 * @version 2017-05-03
 */
public class WyRelationCandidate implements Serializable {

	private static final long serialVersionUID = 1L;
	private String procDefId; // 流程定义id
	private String taskId; // 用户节点任务id
	private String status; // 状态,对应具体的状态

	private String group;// 分组
	private String assignee;// 候选人
	private String[] assignees;// 多个候选人
	private String actId;// 相同处理人节点
	private String memberid;// 会员id

	private String procInsId;// 流程实例id

	private String[] candidateIds;// 选择的人员id
	private String source;// 来源
	private String[] orgIds;// 组织机构

	private String type;// 类型

	private String sameTaskId;// 与哪个处理节点相同

	private String specifyTaskId;// 有上一节点指定的处理人

	private String bizId;// 业务处理id

	private boolean allowDelegateTask;// 是否允许代理
	private boolean allowBack;// 是否允许回退

	public boolean isAllowBack() {
		return allowBack;
	}

	public void setAllowBack(boolean allowBack) {
		this.allowBack = allowBack;
	}

	public boolean isAllowDelegateTask() {
		return allowDelegateTask;
	}

	public void setAllowDelegateTask(boolean allowDelegateTask) {
		this.allowDelegateTask = allowDelegateTask;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getSameTaskId() {
		return sameTaskId;
	}

	public void setSameTaskId(String sameTaskId) {
		this.sameTaskId = sameTaskId;
	}

	public String getSpecifyTaskId() {
		return specifyTaskId;
	}

	public void setSpecifyTaskId(String specifyTaskId) {
		this.specifyTaskId = specifyTaskId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String[] getCandidateIds() {
		return candidateIds;
	}

	public void setCandidateIds(String[] candidateIds) {
		this.candidateIds = candidateIds;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String[] getAssignees() {
		return assignees;
	}

	public void setAssignees(String[] assignees) {
		this.assignees = assignees;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	@Length(min = 0, max = 64, message = "流程定义id长度必须介于 0 和 64 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Length(min = 0, max = 64, message = "用户节点任务id长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Length(min = 0, max = 50, message = "状态,对应具体的状态长度必须介于 0 和 50 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}