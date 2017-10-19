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
	private String id;// id

	private static final long serialVersionUID = 1L;
	private String procDefId; // 流程定义id
	private String taskId; // 用户节点任务id
	private String status; // 状态,对应具体的状态

	private String group;// 分组
	private String assignee;// 候选人
	private String[] assignees;// 多个候选人
	private String actId;// 相同处理人节点
	private String memberid;// 会员id

	private String assignee0;// 候选人0
	private String assignee1;// 候选人1
	private String assignee2;// 候选人2
	private String assignee3;// 候选人3

	private String[] assignees0;// 多个候选人1
	private String[] assignees1;// 多个候选人1
	private String[] assignees2;// 多个候选人2
	private String[] assignees3;// 多个候选人3

	private String actId0;// 相同处理人节点1
	private String actId1;// 相同处理人节点1
	private String actId2;// 相同处理人节点2
	private String actId3;// 相同处理人节点3

	private String source;// 来源
	private String source0;// 来源0
	private String source1;// 来源1
	private String source2;// 来源2
	private String source3;// 来源3

	private String[] candidateIds;// 选择的人员id

	private String historyActivitiId;// 历史节点id

	private String[] orgIds;// 组织结构id

	public String[] getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}

	public String getHistoryActivitiId() {
		return historyActivitiId;
	}

	public void setHistoryActivitiId(String historyActivitiId) {
		this.historyActivitiId = historyActivitiId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getCandidateIds() {
		return candidateIds;
	}

	public void setCandidateIds(String[] candidateIds) {
		this.candidateIds = candidateIds;
	}

	public String getSource0() {
		return source0;
	}

	public void setSource0(String source0) {
		this.source0 = source0;
	}

	public String getSource1() {
		return source1;
	}

	public void setSource1(String source1) {
		this.source1 = source1;
	}

	public String getSource2() {
		return source2;
	}

	public void setSource2(String source2) {
		this.source2 = source2;
	}

	public String getSource3() {
		return source3;
	}

	public void setSource3(String source3) {
		this.source3 = source3;
	}

	public String getAssignee0() {
		return assignee0;
	}

	public void setAssignee0(String assignee0) {
		this.assignee0 = assignee0;
	}

	public String[] getAssignees0() {
		return assignees0;
	}

	public void setAssignees0(String[] assignees0) {
		this.assignees0 = assignees0;
	}

	public String getActId0() {
		return actId0;
	}

	public void setActId0(String actId0) {
		this.actId0 = actId0;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAssignee1() {
		return assignee1;
	}

	public void setAssignee1(String assignee1) {
		this.assignee1 = assignee1;
	}

	public String getAssignee2() {
		return assignee2;
	}

	public void setAssignee2(String assignee2) {
		this.assignee2 = assignee2;
	}

	public String getAssignee3() {
		return assignee3;
	}

	public void setAssignee3(String assignee3) {
		this.assignee3 = assignee3;
	}

	public String[] getAssignees1() {
		return assignees1;
	}

	public void setAssignees1(String[] assignees1) {
		this.assignees1 = assignees1;
	}

	public String[] getAssignees2() {
		return assignees2;
	}

	public void setAssignees2(String[] assignees2) {
		this.assignees2 = assignees2;
	}

	public String[] getAssignees3() {
		return assignees3;
	}

	public void setAssignees3(String[] assignees3) {
		this.assignees3 = assignees3;
	}

	public String getActId1() {
		return actId1;
	}

	public void setActId1(String actId1) {
		this.actId1 = actId1;
	}

	public String getActId2() {
		return actId2;
	}

	public void setActId2(String actId2) {
		this.actId2 = actId2;
	}

	public String getActId3() {
		return actId3;
	}

	public void setActId3(String actId3) {
		this.actId3 = actId3;
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