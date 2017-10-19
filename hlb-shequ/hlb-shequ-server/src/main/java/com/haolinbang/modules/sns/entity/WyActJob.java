package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 工作流定时任务时间表Entity
 * @author nlp
 * @version 2017-06-09
 */
public class WyActJob extends DataEntity<WyActJob> {
	
	private static final long serialVersionUID = 1L;
	private String procDefId;		// 流程定义id
	private String taskId;		// 任务节点id
	private String type;		// 定时任务类型,1=超时任务
	private Integer jobDay;		// 超时天数
	private Integer jobHour;		// 超时小时数
	private Integer jobMinute;		// 超时分钟数
	
	public WyActJob() {
		super();
	}

	public WyActJob(String id){
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
	
	@Length(min=1, max=5, message="定时任务类型,1=超时任务长度必须介于 1 和 5 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
}