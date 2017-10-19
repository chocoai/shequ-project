package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 候选人表或者群组表Entity
 * 
 * @author NLP
 * @version 2017-05-03
 */
public class WyActCandidate extends DataEntity<WyActCandidate> {

	private static final long serialVersionUID = 1L;
	private Integer relationId; // 关联id
	private String candidate; // 候选人
	private String candidateType; // 候选人类型，1=候选人，2=部门候选人类型
	private String procDefId;// 流程定义id
	private String taskId;// 任务节点定义id

	private String sameTaskId;// 与哪个处理节点相同
	private String type;// 处理人类型
	private String specifyTaskId;// 有上一节点指定的处理人

	private String source;// 从哪个节点过来的任务节点

	private boolean allowDelegateTask;// 是否允许代理任务
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getSameTaskId() {
		return sameTaskId;
	}

	public void setSameTaskId(String sameTaskId) {
		this.sameTaskId = sameTaskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public WyActCandidate() {
		super();
	}

	public WyActCandidate(String id) {
		super(id);
	}

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	@Length(min = 1, max = 64, message = "候选人长度必须介于 1 和 64 之间")
	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	@Length(min = 1, max = 5, message = "候选人类型，1=候选人，2=部门候选人类型长度必须介于 1 和 5 之间")
	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}

}