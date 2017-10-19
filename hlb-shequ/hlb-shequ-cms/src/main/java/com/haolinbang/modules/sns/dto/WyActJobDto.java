package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

public class WyActJobDto implements Serializable {

	private static final long serialVersionUID = 1596920668896200448L;

	private String procDefId; // 流程定义id
	private String taskId; // 任务节点id
	private String type; // 定时任务类型,1=超时任务
	private Integer jobDay; // 超时天数
	private Integer jobHour; // 超时小时数
	private Integer jobMinute; // 超时分钟数

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

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
