package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 流程实例对应的关系表Entity
 * @author nlp
 * @version 2017-05-03
 */
public class WyActRelation extends DataEntity<WyActRelation> {
	
	private static final long serialVersionUID = 1L;
	private String procDefId;		// 流程定义id
	private String taskId;		// 用户节点任务id
	private String status;		// 状态,对应具体的状态
	
	public WyActRelation() {
		super();
	}

	public WyActRelation(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程定义id长度必须介于 0 和 64 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	@Length(min=0, max=64, message="用户节点任务id长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=0, max=50, message="状态,对应具体的状态长度必须介于 0 和 50 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}