package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 流程表单管理Entity
 * @author nlp
 * @version 2017-06-01
 */
public class WyActForm extends DataEntity<WyActForm> {
	
	private static final long serialVersionUID = 1L;
	private String procDefId;		// 流程定义id
	private String taskId;		// 任务节点id
	private String formId;		// formid自定义，有实际含义
	private String formName;		// 表单名称
	private String formType;		// 表单类型
	private String formUrl;		// 跳转url地址
	
	public WyActForm() {
		super();
	}

	public WyActForm(String id){
		super(id);
	}

	@Length(min=1, max=64, message="流程定义id长度必须介于 1 和 64 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	@Length(min=1, max=64, message="任务节点id长度必须介于 1 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=1, max=64, message="formid自定义，有实际含义长度必须介于 1 和 64 之间")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=100, message="表单名称长度必须介于 0 和 100 之间")
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	@Length(min=0, max=10, message="表单类型长度必须介于 0 和 10 之间")
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	@Length(min=0, max=255, message="跳转url地址长度必须介于 0 和 255 之间")
	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	
}