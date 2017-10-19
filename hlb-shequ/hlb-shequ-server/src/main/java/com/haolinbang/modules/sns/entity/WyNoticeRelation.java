package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 不同通知处理方法Entity
 * 
 * @author nlp
 * @version 2017-07-03
 */
public class WyNoticeRelation extends DataEntity<WyNoticeRelation> {

	private static final long serialVersionUID = 1L;
	private String procDefId; // 流程定义id
	private String taskKey; // 任务节点id
	private String handlerClass; // 处理类的包名路径
	private String handlerMethod; // 处理方法
	private String handlerType;// 处理类型

	public String getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(String handlerType) {
		this.handlerType = handlerType;
	}

	public WyNoticeRelation() {
		super();
	}

	public WyNoticeRelation(String id) {
		super(id);
	}

	@Length(min = 1, max = 32, message = "流程定义id长度必须介于 1 和 32 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Length(min = 1, max = 64, message = "任务节点id长度必须介于 1 和 64 之间")
	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	@Length(min = 1, max = 255, message = "处理类的包名路径长度必须介于 1 和 255 之间")
	public String getHandlerClass() {
		return handlerClass;
	}

	public void setHandlerClass(String handlerClass) {
		this.handlerClass = handlerClass;
	}

	@Length(min = 1, max = 50, message = "处理方法长度必须介于 1 和 50 之间")
	public String getHandlerMethod() {
		return handlerMethod;
	}

	public void setHandlerMethod(String handlerMethod) {
		this.handlerMethod = handlerMethod;
	}

}