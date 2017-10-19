package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

/**
 * 处理关系dto
 * 
 * @author Administrator
 * 
 */
public class WyActHandlerDto implements Serializable {

	private static final long serialVersionUID = -1566159668042841242L;

	private String activitiId;// 节点id

	private String procDefinitionId;// 流程定义id

	private String[] createHandler;// 用户任务节点创建时处理类

	private String[] assignHandler;// 用户任务节点人员被分配处理类

	private String[] completeHandler;// 用户任务节点任务完成处理类

	private Integer jobDay; // 超时天数
	private Integer jobHour; // 超时小时数
	private Integer jobMinute; // 超时分钟数

	private String[] jobHandler;// 定时处理任务

	public String[] getJobHandler() {
		return jobHandler;
	}

	public void setJobHandler(String[] jobHandler) {
		this.jobHandler = jobHandler;
	}

	public Integer getJobDay() {
		return jobDay;
	}

	public void setJobDay(Integer jobDay) {
		this.jobDay = jobDay;
	}

	public Integer getJobHour() {
		return jobHour;
	}

	public void setJobHour(Integer jobHour) {
		this.jobHour = jobHour;
	}

	public Integer getJobMinute() {
		return jobMinute;
	}

	public void setJobMinute(Integer jobMinute) {
		this.jobMinute = jobMinute;
	}

	public String getActivitiId() {
		return activitiId;
	}

	public void setActivitiId(String activitiId) {
		this.activitiId = activitiId;
	}

	public String getProcDefinitionId() {
		return procDefinitionId;
	}

	public void setProcDefinitionId(String procDefinitionId) {
		this.procDefinitionId = procDefinitionId;
	}

	public String[] getCreateHandler() {
		return createHandler;
	}

	public void setCreateHandler(String[] createHandler) {
		this.createHandler = createHandler;
	}

	public String[] getAssignHandler() {
		return assignHandler;
	}

	public void setAssignHandler(String[] assignHandler) {
		this.assignHandler = assignHandler;
	}

	public String[] getCompleteHandler() {
		return completeHandler;
	}

	public void setCompleteHandler(String[] completeHandler) {
		this.completeHandler = completeHandler;
	}

}
