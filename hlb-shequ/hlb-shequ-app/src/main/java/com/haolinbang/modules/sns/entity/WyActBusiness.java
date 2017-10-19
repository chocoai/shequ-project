package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 工作流程和业务关联配置表Entity
 * @author nlp
 * @version 2017-05-05
 */
public class WyActBusiness extends DataEntity<WyActBusiness> {
	
	private static final long serialVersionUID = 1L;
	private String procDefKey;		// 流程定义key
	private String procDefName;		// 流程定义名称
	private String procDefId;		// 流程定义id
	private String bizKey;		// 业务key
	private String bizName;		// 业务名称
	
	public WyActBusiness() {
		super();
	}

	public WyActBusiness(String id){
		super(id);
	}

	@Length(min=1, max=100, message="流程定义key长度必须介于 1 和 100 之间")
	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	
	@Length(min=0, max=100, message="流程定义名称长度必须介于 0 和 100 之间")
	public String getProcDefName() {
		return procDefName;
	}

	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}
	
	@Length(min=1, max=100, message="流程定义id长度必须介于 1 和 100 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	@Length(min=1, max=100, message="业务key长度必须介于 1 和 100 之间")
	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	
	@Length(min=0, max=100, message="业务名称长度必须介于 0 和 100 之间")
	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	
}