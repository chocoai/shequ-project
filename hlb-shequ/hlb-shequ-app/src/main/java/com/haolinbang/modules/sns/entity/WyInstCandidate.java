package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 运行期间待办人Entity
 * @author nlp
 * @version 2017-06-05
 */
public class WyInstCandidate extends DataEntity<WyInstCandidate> {
	
	private static final long serialVersionUID = 1L;
	private String procInstId;		// 流程实例id
	private String taskId;		// 任务节点id
	private String type;		// 指定为处理人还是待办人
	private String candidate;		// 候选人
	private String candidateType;		// 候选人类型，1=候选人，2=部门候选人类型
	
	public WyInstCandidate() {
		super();
	}

	public WyInstCandidate(String id){
		super(id);
	}

	@Length(min=1, max=64, message="流程实例id长度必须介于 1 和 64 之间")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	@Length(min=0, max=64, message="任务节点id长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=0, max=10, message="指定为处理人还是待办人长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="候选人长度必须介于 0 和 64 之间")
	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	
	@Length(min=0, max=5, message="候选人类型，1=候选人，2=部门候选人类型长度必须介于 0 和 5 之间")
	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}
	
}